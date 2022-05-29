#!/bin/bash

PURPLE='0;35'
NC='\033[0m' 
VERSAO=11

echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Olá irei lhe ajudar com a instalação de programas necessarios para nosso aplicativo!;"

echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Irei atualizar os pacotes agora:"
sudo apt update && sudo apt upgrade -y

echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Procurando o Zip Instalador zip"
which zip
if [ $? = 0 ]
then echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Voce ja possui zip instalado"
else echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Voce nao possui o zip irei iniciar a instalacao dele:"
sudo apt install zip -y

echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Adicionando o caminho sdk ao curl"
curl -s "https://get.sdkman.io" | bash
fi
echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) irei reiniciar o terminal agora:"
source "/home/$usuario/.sdkman/bin/sdkman-init.sh"

echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Instalando o Java:"
sudo sdk install java 11.0.11-open
fi

echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Versao do java instalada: "
java -version

echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Verificando se voce ja possui o Docker:"
which docker
if [ $? = 0 ]
then echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Voce ja possui o docker instalado."
else echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Voce nao possui o docker instalado, irei instalar ele agora:"
sudo apt install docker.io -y

fi


echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Iniciando o docker:"

sudo systemctl start docker
sudo systemctl enable docker

echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Verificando se voce ja possui o maven:"
which maven
if [ $? = 0 ]
then echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Voce ja possui o maven instalado."
else echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Voce nao possui o maven instalado, irei instalar agora mesmo."
sudo apt install maven -y
fi


echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Clonando nosso repositorio do git"

git clone https://github.com/Erick-Saraiva/jarCli.git
echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Entrando nesse repositorio e atualizando o Jar"
cd jarCli/cli
git pull
echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Atualizando o jar"
mvn clean package

echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Configurando o Container e a nossa aplicacao"

sudo docker build -t ericksaraiva/healthcli .
echo "$(tput setaf 10)[Bot assistant]:$(tput setaf 7) Iniciando aplicacao"
sudo docker run ericksaraiva/healthcli