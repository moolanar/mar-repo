sudo yum install -y yum-utils shadow-utils

sudo yum-config-manager --add-repo https://rpm.releases.hashicorp.com/AmazonLinux/hashicorp.repo

sudo yum -y install terraform

//////////////////////////



1. yum install java -y
2. sudo yum install java-1.8.0-amazon-corretto-devel   [ sudo yum install java-17-amazon-corretto-devel ]
3. yum install ansible -y

4. ssh-keygen

5.  cat /home/ec2-user/.ssh/id_rsa.pub [ control node ] --> copy
6.  paste in .ssh/authorized_keys  [ worker node ] 

7. ansible all   -m ping -i inventory.yml
8. ansible-playbook -i inventory.yml mybook.yml

9. sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
10. sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
11. sudo dnf install jenkins -y

//////////////

https://medium.com/@ishan_jan1/connecting-a-worker-node-to-a-jenkins-server-using-the-private-key-method-ae4d801282f4

yum-config-manager --enable epel

  /////////////
server1 ansible_host=203.0.113.111 ansible_user=sammy  

sudo wget -O /etc/yum.repos.d/jenkins.repo https://pkg.jenkins.io/redhat-stable/jenkins.repo
sudo rpm --import https://pkg.jenkins.io/redhat-stable/jenkins.io-2023.key
sudo yum upgrade
sudo yum install fontconfig java-17-openjdk
sudo yum install jenkins
sudo systemctl daemon-reload
/////////////
