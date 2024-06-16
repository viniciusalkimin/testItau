# testItau
API developed for testing a Back-end Developer position at Itaú

### Pré-requisitos:
* Java21
* SpringBoot3.3.0
* Maven
* Docker

### Configurações Default
`Endereço API = ` http://localhost:8080/transferencia<br>
`Endereço Documentação = ` http://localhost:8080/swagger/ <br>
`Endereço DB = ` http://localhost:8080/h2-console <br>
`user DB = ` sa <br>
`password DB = `  <br>

### Como subir o projeto local
````bash
# Clone este repositório 
$ git clone <https://github.com/viniciusalkimin/testItau.git>

# Acesse a pasta do projeto no terminal/cmd 
$ cd testItau

# Suba o container com as Apis Mocks
$ docker compose up -d
````

## Proposta Arquitetural
![ArquiteturaItau.PNG](itau-api-transaction%2FArquiteturaItau.PNG)