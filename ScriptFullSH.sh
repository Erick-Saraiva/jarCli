#!/bin/bash

clear
echo "Olá irei instalar alguns programas necessários para que você possa usar nosso produto :)"
read -p "Pressione Enter para continuar ..."

# Instalando atualizações do sistema
sudo apt update && sudo apt upgrade -y
sudo apt install docker.io -y
sudo apt-get install maven -y
sudo add-apt-repository ppa:webupd8team/java -y
sudo apt update -y
sudo sdk install java 11.0.11-open -y

# Clonando nosso repositório do git
git clone https://github.com/Erick-Saraiva/jarCli.git

#Entrando nesse repositório e atualizando o Jar
cd jarCli/cli
mvn clean package

# Configurando o Container e a nossa aplicação
sudo systemctl start docker
sudo systemctl enable docker
sudo docker build -t ericksaraiva/healthcli .

# Iniciando aplicação
sudo docker run ericksaraiva/healthcli