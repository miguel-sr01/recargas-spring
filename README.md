# Mensageria Docker - API de Recargas

> Projeto Java Backend desenvolvido utilizando **Spring Boot**, **RabbitMQ**, **Docker** e **JWT** para demonstrar o funcionamento de uma API REST moderna e escalável.

---

## Visão Geral

A aplicação simula uma **plataforma de recargas de celular**.  
Ela possui uma API REST que recebe requisições de recarga com o status "PENDENTE" e as envia de forma **assíncrona** para uma fila no **RabbitMQ**.
Um *consumer* (simulação da plataforma de recarga) processa a mensagem e atualiza o status da operação para "SUCESSO" ou "FALHA".

---

## Tecnologias Utilizadas

| Categoria            | Ferramentas                               |
|----------------------|-------------------------------------------|
| **Backend**          | Java 17 · Spring Boot 3 · JPA (Hibernate) |
| **Mensageria**       | RabbitMQ (via Spring AMQP)                |
| **Banco de Dados**   | H2 (memória / arquivo)                    |
| **Segurança**        | JWT (JSON Web Token)                      |
| **Documentação**     | Swagger / OpenAPI                         |
| **Containerização**  | Docker · Docker Compose                   |
| **Testes Unitários** | JUnit 5 · Mockito                         |

---

## Arquitetura da Aplicação


- **API REST (Controller):** Recebe requisições HTTP.
- **Service:** Lógica de negócio e envio de mensagens à fila.
- **RabbitMQ:** Fila de mensageria (assíncrona).
- **Consumer:** Simula a plataforma de recarga (mock).
- **Banco H2:** Armazena clientes, métodos de pagamento e recargas.

---

## Entidades Principais

| Entidade          | Descrição                                                       |
|-------------------|-----------------------------------------------------------------|
| `Cliente`         | Representa o usuário que realiza recargas.                      |
| `MetodoPagamento` | Forma de pagamento (ex: PIX, Cartão).                           |
| `Recarga`         | Operação de recarga associada ao cliente e método de pagamento. |

Relacionamentos:
- `Cliente` 1: N `Recarga`
- `Cliente` 1: N `MetodoPagamento`
- `MetodoPagamento` 1: N `Recarga`


---

## Autenticação JWT

A API utiliza **autenticação baseada em token JWT**.  
Fluxo:

1 - POST /api/v1/auth/login → recebe username e password  
2 - Gera token JWT válido por 1 hora  
3 - Todas as requisições seguintes devem conter o header Bearer + Token:


Usuário padrão para testes:
```

  "username": "admin",
  "password": "1234"

```

## ESTRUTURA DO PROJETO

```
mensageria-docker/
 ├── src/
 │   ├── main/
 │   │   ├── java/atividade/estagio/mensageria_docker/
 │   │   │   ├── controller/        # Endpoints REST
 │   │   │   ├── service/           # Lógica de negócio
 │   │   │   ├── entity/            # Entidades JPA
 │   │   │   ├── repository/        # Repositórios
 │   │   │   ├── mensageria/        # Configuração e Consumer RabbitMQ
 │   │   │   └── security/          # Autenticação JWT
 │   │   └── resources/
 │   │       ├── application.properties
 │   └── test/java/...              # Testes unitários com JUnit e Mockito
 ├── Dockerfile
 ├── docker-compose.yml
 ├── pom.xml
 └── README.md
```

## COMO BUILDAR O PROJETO E SUBIR A APLICAÇÃO COM DOCKER:

1 - Na pasta raiz do projeto, execute o comando: mvn clean install  
2 - Na pasta raiz do projeto, execute o comando: docker-compose up -- build


## COMO TESTAR A APLICAÇÂO VIA SWAGGER:
1 - Acesse a url: http://localhost:8080/swagger-ui/index.html  
2 - Gere o Token de Autenticação passando usuario e senha de teste via Json.  
3 - Realize as operações tradicionais do HTTP para as entidades.  
4 - Verifique o Status da recarga mudar de "PENDENTE" para "SUCESSO" ou "FALHA" após o processamento da fila RabbitMq.


## VERIFICAR PROCESSAMENTO DA FILA RABBITMQ
1 - Acesse a url: http://localhost:15672  
2 - Credenciais padrão da mensageria -> User: guest / Senha: guest

## DICAS DE USO
1 - No métedo POST, não passar o ID das entidades pois este é gerado automaticamente.  
2 - Deve-se seguir a seguinte ordem de criação de entidades: Cliente -> Metodo_Pagamento -> Recarga.  
3 - Usuário padrao para teste na criação do Token: User: admin / Senha: 1234.