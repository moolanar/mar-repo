1. yum install java -y
2. yum install ansible -y

3. ssh-keygen

4.  cat /home/ec2-user/.ssh/id_rsa.pub [ control node ] --> copy
5.  paste in .ssh/authorized_keys  [ worker node ] 

6. ansible all   -m ping -i inventory.yml
7. ansible-playbook -i inventory.yml mybook.yml

