#terraform {
#  required_providers {
#    aws = {
#      source  = "hashicorp/aws"
#      version = "~> 4.16"
#    }
#  }
#  required_version = ">= 1.2.0"
#}


provider "aws" {
  region  = "ap-southeast-2" 
}

resource "aws_vpc" "mar_vpc" {
  cidr_block = "10.0.0.0/16"
  enable_dns_hostnames = true
  enable_dns_support = true
  tags = {
    Name = "mar_vpc"
  }
}

resource "aws_internet_gateway" "mar_internet_gateway" {
  vpc_id = aws_vpc.mar_vpc.id
  tags = {
    Name = "mar_internet_gateway"
  }
}

resource "aws_route_table" "mar-route-table-public" {
  vpc_id = aws_vpc.mar_vpc.id
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.mar_internet_gateway.id
  }
  tags = {
    Name = "mar-route-table-public"
  }
}

resource "aws_route_table_association" "mar-public-subnet1-association" {
  subnet_id      = aws_subnet.mar-public-subnet1.id
  route_table_id = aws_route_table.mar-route-table-public.id
}

resource "aws_route_table_association" "mar-public-subnet2-association" {
  subnet_id      = aws_subnet.mar-public-subnet2.id
  route_table_id = aws_route_table.mar-route-table-public.id
}

resource "aws_route_table_association" "mar-public-subnet3-association" {
  subnet_id      = aws_subnet.mar-public-subnet3.id
  route_table_id = aws_route_table.mar-route-table-public.id
}

resource "aws_subnet" "mar-public-subnet1" {
  vpc_id = aws_vpc.mar_vpc.id
  cidr_block = "10.0.1.0/24"
  map_public_ip_on_launch = true
  availability_zone = "ap-southeast-2a"
  tags = {
    Name = "mar-public-subnet1"
  }
}

resource "aws_subnet" "mar-public-subnet2" {
  vpc_id = aws_vpc.mar_vpc.id
  cidr_block = "10.0.2.0/24"
  map_public_ip_on_launch = true
  availability_zone = "ap-southeast-2b"
  tags = {
    Name = "mar-public-subnet2"
  }
}

resource "aws_subnet" "mar-public-subnet3" {
  vpc_id = aws_vpc.mar_vpc.id
  cidr_block = "10.0.3.0/24"
  map_public_ip_on_launch = true
  availability_zone = "ap-southeast-2c"
  tags = {
    Name = "mar-public-subnet3"
  }
}

resource "aws_security_group" "sg1" {
  name = "my_sec_grp"
  description = "Allow inbound SSH and HTTP traffic"
  vpc_id = aws_vpc.mar_vpc.id
  
  ingress {
    description ="ssh"
    from_port = 22
    to_port = 22
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  
  ingress {
    description = "kibana"
    from_port = 5601
    to_port = 5601
    protocol = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    description = "cadvisor"
    from_port = 8080
	  to_port = 8080
	  protocol = "tcp"
	  cidr_blocks = ["0.0.0.0/0"]
  }
  
  ingress {
    description = "prometheus"
    from_port = 9090
	  to_port = 9090
	  protocol = "tcp"
	  cidr_blocks = ["0.0.0.0/0"]  
  }
  
  ingress {
    description = "node exporter"
    from_port = 9100
	  to_port = 9100
	  protocol = "tcp"
	  cidr_blocks = ["0.0.0.0/0"]
  }

    ingress {
      description = "elastic search"
      from_port = 9200
      to_port = 9200
      protocol=  "tcp"
      cidr_blocks= ["0.0.0.0/0"]
  }

  ingress {
    description = "elastic search"
    from_port= 9300
    to_port= 9300
    protocol= "tcp"
    cidr_blocks= ["0.0.0.0/0"]
  }
  
  ingress {
    description = "grafana"
    from_port = 3000
	  to_port = 3000
	  protocol = "tcp"
	  cidr_blocks = ["0.0.0.0/0"]
  }
  
  egress {
    from_port = 0
    to_port = 0
    protocol = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

}

resource "aws_instance" "example_server" {
  ami = "ami-0e8fd5cc56e4d158c"
  instance_type = "t2.micro"
  key_name = "jenkins"
  vpc_security_group_ids  = [aws_security_group.sg1.id]
  subnet_id  = aws_subnet.mar-public-subnet1.id
  ebs_block_device {
    device_name = "/dev/xvda"
    volume_size = 8
  }

user_data = <<EOF
#!/bin/bash
echo "Copying the SSH Key to the server"
yum install docker -y
systemctl start docker
systemctl enable docker
systemctl restart docker

#docker run -d -p 9080:9080 prom/prometheus
#docker run -d -p 9100:9100 prom/node-exporter
#docker run -d -p 3000:3000 grafana/grafana
#docker run -d -p 8080:8080 --volume /var/lib/docker:/var/lib/docker --volume /var/run:/var/run --volume /sys:/sys --volume /dev/disk:/dev/disk gcr.io/cadvisor/cadvisor

#docker network create elastic
#docker run -d --net elastic --name es01 -p 9200:9200 -e "ELASTIC_PASSWORD=pass1234" -e "discovery.type=single-node" docker.elastic.co/elasticsearch/elasticsearch:7.15.0
#curl -X PUT "localhost:9200/products"
#curl -X POST -H 'Content-type:application/json' -d '{"type":"cdlp", "description":"cash deposit" }' http://localhost:9200/products/_doc
#curl -X GET "localhost:9200/products/_search?pretty" -H 'Content-Type:application/json' -d '{ "query": { "match" : { "type": "cdlp" } } } '

#docker run -d --name kib01 -p 5601:5601 docker.elastic.co/kibana/kibana:7.15.0
#docker run -d --name lgs docker.elastic.co/logstash/logstash:7.15.0

#-auto-approve

#relabel_configs:
#      source_labels: [__meta_ec2_public_ip]
#      regex: "(.*)"
#      replacement: "${1}:9100"
#      target_label: __address__
EOF

}

output "instance_public_ip" {
  description = "public ips"
  value = [aws_instance.example_server.*.public_ip]
}
