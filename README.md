# Order Management Service

## Visão Geral

Este serviço é responsável por receber, processar e gerenciar pedidos, integrando-se com sistemas externos via Kafka e persistindo os dados em um banco H2 (para testes locais). 

---

## Tecnologias Utilizadas
- **Spring Boot**
- **Spring Data JPA**
- **Spring Kafka**
- **H2 Database** (ambiente local)
- **Kafka** (via Docker Compose)
- **Kafka UI** (interface web para testes)
- **Springdoc OpenAPI** (Swagger)

---

## Como Executar Localmente

### 1. Suba o Kafka, Zookeeper, Kafka UI e Postgres (opcional) com Docker Compose
```bash
docker-compose up -d
```

### 2. Execute a aplicação Spring Boot
- Via IDE (classe `OrderManagementServiceApplication`)
- Ou via terminal:
```bash
./mvnw spring-boot:run
```

---

## Configuração do Banco de Dados
- O banco padrão para testes é o H2 em memória.
- Console H2 disponível em: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - **JDBC URL:** `jdbc:h2:mem:testdb`
  - **Username:** `sa`
  - **Password:** (em branco)

---

## Kafka
- O serviço escuta dois tópicos:
  - `pedidos-sistema-a`
  - `pedidos-sistema-b`
- Use o Kafka UI em [http://localhost:8081](http://localhost:8081) para enviar mensagens de teste.
- Exemplo de mensagem:
```json
{
  "externalOrderId": 1
}
```

---

## Endpoints REST

### 1. Listar todos os pedidos
- **GET** `/orders`
- **Descrição:** Retorna todos os pedidos processados e salvos no banco.
- **Exemplo CURL:**
```bash
curl -X GET http://localhost:8080/orders
```

### 2. Processar pedido manualmente (simula mensagem do Sistema A)
- **POST** `/orders/process/{externalOrderId}`
- **Descrição:** Cria e processa um pedido com o ID informado, simulando o fluxo do Kafka.
- **Exemplo CURL:**
```bash
curl -X POST http://localhost:8080/orders/process/123
```

### 3. Health check
- **GET** `/actuator/health`
- **Descrição:** Verifica se a aplicação está saudável.
- **Exemplo CURL:**
```bash
curl -X GET http://localhost:8080/actuator/health
```

---

## Observações
- O fluxo principal é via Kafka, mas pode ser testado manualmente via endpoint REST.
- Os eventos de "pedido processado" são publicados após o processamento, podendo ser consumidos por outros sistemas.

## Arquitetura

```
+-------------------+         +-------------------+         +-------------------+
| Produto Externo A |         | Order Management  |         | Produto Externo B |
|   (Producer)      +-------->+   Service         +-------->+   (Consumer)      |
+-------------------+   |     | (Consumer/Producer)|        +-------------------+
                        |     +-------------------+
                        |             |
                        v             v
                 +---------------------------+
                 |         Kafka             |
                 |  (pedidos-recebidos)      |
                 |  (pedidos-processados)    |
                 +---------------------------+
                               |
                               v
                      +------------------+
                      |   H2 Database    |
                      +------------------+
```

## Pré-requisitos

- Java 17
- Docker e Docker Compose
- Maven

## Configuração do Ambiente

1. Clone o repositório
2. Execute o Docker Compose para iniciar os serviços necessários:

```bash
docker-compose up -d
```

Isso iniciará:
- Kafka (porta 9092)
- Zookeeper (porta 2181)
- Kafka UI (porta 8081)
- PostgreSQL (porta 5432, opcional)

## Configuração do Banco de Dados

O banco de dados padrão para testes é o H2 em memória. Para produção, configure o PostgreSQL conforme necessário.

## Executando a Aplicação

1. Compile o projeto:
```bash
mvn clean install
```

2. Execute a aplicação:
```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

## Documentação da API

A documentação da API está disponível através do Swagger UI:
- URL: http://localhost:8080/swagger-ui/index.html

A documentação OpenAPI também está disponível em:
- URL: http://localhost:8080/v3/api-docs

## Segurança

- Todos os endpoints estão públicos para facilitar o desenvolvimento e os testes locais.
- Não há autenticação ou geração de tokens.
- CSRF está desabilitado para facilitar o uso do H2 Console e dos endpoints REST.

## Estrutura do Projeto

```
src/main/java/com/company/orderservice/
├── adapters/
│   ├── api/           # Controllers REST
│   ├── external/      # Integrações com sistemas externos
│   └── repository/    # Implementações dos repositórios
├── application/       # Casos de uso da aplicação
├── config/           # Configurações do Spring
├── domain/           # Modelos e portas do domínio
└── OrderManagementServiceApplication.java
```

## Funcionalidades

- Processamento de pedidos do sistema A e B via Kafka
- Cálculo automático do valor total dos pedidos
- Persistência de pedidos em banco de dados H2 (padrão local)
- Documentação da API via Swagger/OpenAPI

## Testes

Para executar os testes:
```bash
mvn test
```
