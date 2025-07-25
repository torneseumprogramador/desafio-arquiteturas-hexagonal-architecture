# 🛒 Ecommerce Hexagonal Architecture - Desafio de Arquiteturas de Software

## 📚 Sobre o Projeto

Este projeto foi desenvolvido como parte do **Desafio de Arquiteturas de Software** do curso [Arquiteturas de Software Modernas](https://www.torneseumprogramador.com.br/cursos/arquiteturas_software) ministrado pelo **Prof. Danilo Aparecido** na plataforma [Torne-se um Programador](https://www.torneseumprogramador.com.br/).

### 🎯 Objetivo

Implementar um sistema de e-commerce utilizando **Arquitetura Hexagonal (Ports and Adapters)** com Java 17 e Spring Boot, demonstrando boas práticas de desenvolvimento e organização de código.

## 🏗️ Arquitetura

O projeto segue os princípios da **Arquitetura Hexagonal (Ports and Adapters)** com uma separação clara de responsabilidades:

```
┌─────────────────────────────────────┐
│           Adapters (In)             │ ← Controllers REST, DTOs
├─────────────────────────────────────┤
│         Application Layer           │ ← Use Cases, Services
├─────────────────────────────────────┤
│            Domain Layer             │ ← Entities, Business Rules
├─────────────────────────────────────┤
│          Adapters (Out)             │ ← Repositories, JPA Entities
└─────────────────────────────────────┘
```

### 📁 Estrutura do Projeto

```
src/main/java/com/ecommerce/
├── domain/model/                     # Entidades do domínio (sem dependências externas)
│   ├── User.java                     # Entidade de usuário
│   ├── Product.java                  # Entidade de produto
│   ├── Order.java                    # Entidade de pedido
│   └── OrderProduct.java             # Entidade de produto do pedido
├── application/ports/in/             # Interfaces dos casos de uso
│   ├── CreateUserUseCase.java        # Caso de uso para criar usuário
│   ├── CreateProductUseCase.java     # Caso de uso para criar produto
│   └── CreateOrderUseCase.java       # Caso de uso para criar pedido
├── application/ports/out/            # Interfaces dos repositórios
│   ├── UserRepository.java           # Interface do repositório de usuários
│   ├── ProductRepository.java        # Interface do repositório de produtos
│   └── OrderRepository.java          # Interface do repositório de pedidos
├── application/services/             # Implementações dos casos de uso
│   ├── UserService.java              # Serviço de usuários
│   ├── ProductService.java           # Serviço de produtos
│   └── OrderService.java             # Serviço de pedidos
├── adapters/in/rest/                 # Controllers REST
│   ├── UserController.java           # Controller de usuários
│   ├── ProductController.java        # Controller de produtos
│   ├── OrderController.java          # Controller de pedidos
│   └── dto/                          # Data Transfer Objects
├── adapters/out/persistence/         # Implementações JPA dos repositórios
│   ├── UserPersistenceAdapter.java   # Adaptador de persistência de usuários
│   ├── ProductPersistenceAdapter.java # Adaptador de persistência de produtos
│   ├── OrderPersistenceAdapter.java  # Adaptador de persistência de pedidos
│   └── entities/                     # Entidades JPA
└── config/                           # Configurações do Spring
```

## 🚀 Tecnologias Utilizadas

- **Java 17** - Linguagem de programação
- **Spring Boot 3.2.0** - Framework de desenvolvimento
- **Spring Data JPA** - ORM para acesso a dados
- **PostgreSQL** - Banco de dados (via Docker)
- **Maven** - Gerenciador de dependências
- **Lombok** - Redução de boilerplate code
- **Docker Compose** - Containerização do banco de dados
- **Arquitetura Hexagonal** - Padrão arquitetural



## 📋 Pré-requisitos

- [Java 17](https://adoptium.net/) ou superior
- [Maven 3.6+](https://maven.apache.org/download.cgi)
- [Docker Desktop](https://www.docker.com/products/docker-desktop)
- [Git](https://git-scm.com/)

## ⚡ Como Executar

### Pré-requisitos

- Java 17 ou superior
- Maven 3.6+
- Docker e Docker Compose
- jq (para o script interativo)

### Método Rápido (Recomendado)

```bash
# Clone o repositório
git clone <url-do-repositorio>
cd desafio-arquiteturas-hexagonal-architecture

# Execute o script que faz tudo automaticamente
./run.sh
```

### Script Interativo

```bash
# Após iniciar a aplicação, use o script interativo para testar
./scripts/interactive-api.sh
```

**Funcionalidades do Script Interativo:**
- 👥 Gerenciamento completo de usuários (CRUD)
- 📦 Gerenciamento completo de produtos (CRUD)
- 🛒 Gerenciamento completo de pedidos (CRUD)
- 💚 Verificação de saúde da API
- 📚 Acesso à documentação Swagger

### Método Manual

```bash
# 1. Iniciar PostgreSQL
docker-compose up -d

# 2. Compilar a aplicação
mvn clean compile

# 3. Executar a aplicação
mvn spring-boot:run
```

### Comandos Disponíveis no Script

```bash
./run.sh              # Executa tudo (Docker + Build + Run)
./run.sh build        # Apenas mvn compile
./run.sh clean        # Apenas mvn clean
./run.sh docker       # Apenas inicia Docker
./run.sh docker-stop  # Para containers Docker
./run.sh run          # Apenas executa a API
./run.sh stop         # Para a API Spring Boot
./run.sh help         # Mostra ajuda
```

## 🌐 Acessando a API

Após executar o projeto, a API estará disponível em:

- **API Base**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs
- **pgAdmin**: http://localhost:8081 (admin@admin.com / admin)

## 🔧 Configuração do Banco de Dados

O projeto utiliza **PostgreSQL** rodando em **Docker** com as seguintes configurações:

- **Host**: localhost
- **Porta**: 5432
- **Database**: ecommerce_db
- **Usuário**: postgres
- **Senha**: postgres

### Connection String

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/ecommerce_db
    username: postgres
    password: postgres
```



### Comandos Úteis do Docker

```bash
# Iniciar os serviços
docker-compose up -d

# Parar os serviços
docker-compose down

# Ver logs do PostgreSQL
docker-compose logs postgres

# Acessar o pgAdmin
# Abra http://localhost:8081 no navegador
```

## 📖 Endpoints da API

### 👥 Usuários (User)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/users` | Criar usuário |
| GET | `/api/users` | Listar todos os usuários |
| PUT | `/api/users/{id}` | Atualizar usuário |
| DELETE | `/api/users/{id}` | Deletar usuário |

**Exemplo de criação de usuário:**
```bash
curl -X POST "http://localhost:8080/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@email.com",
    "password": "123456"
  }'
```

**Exemplo de atualização de usuário:**
```bash
curl -X PUT "http://localhost:8080/api/users/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva Atualizado",
    "email": "joao.novo@email.com",
    "password": "123456"
  }'
```

**Exemplo de deleção de usuário:**
```bash
curl -X DELETE "http://localhost:8080/api/users/1"
```

### 📦 Produtos (Product)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/products` | Criar produto |
| GET | `/api/products` | Listar todos os produtos |
| PUT | `/api/products/{id}` | Atualizar produto |
| DELETE | `/api/products/{id}` | Deletar produto |

**Exemplo de criação de produto:**
```bash
curl -X POST "http://localhost:8080/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone XYZ",
    "description": "Smartphone de última geração",
    "price": 1299.99,
    "stockQuantity": 50
  }'
```

**Exemplo de atualização de produto:**
```bash
curl -X PUT "http://localhost:8080/api/products/1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone XYZ Pro",
    "description": "Smartphone de última geração - Versão Pro",
    "price": 1499.99,
    "stockQuantity": 30
  }'
```

**Exemplo de deleção de produto:**
```bash
curl -X DELETE "http://localhost:8080/api/products/1"
```

### 🛒 Pedidos (Order)

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| POST | `/api/orders` | Criar pedido |
| GET | `/api/orders` | Listar todos os pedidos |
| PUT | `/api/orders/{id}` | Atualizar pedido (apenas pendentes) |
| DELETE | `/api/orders/{id}` | Deletar pedido (apenas pendentes) |

### 🏥 Health Check

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/health` | Status geral da aplicação e banco |
| GET | `/health/ping` | Teste simples de conectividade |
| GET | `/health/database` | Status detalhado do banco de dados |

### 🏠 Home

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/` | Informações da API e endpoints disponíveis |

### 📚 Documentação

| Método | Endpoint | Descrição |
|--------|----------|-----------|
| GET | `/swagger-ui.html` | Interface web do Swagger |
| GET | `/v3/api-docs` | Especificação OpenAPI em JSON |

**Exemplo de criação de pedido:**
```bash
curl -X POST "http://localhost:8080/api/orders" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "productQuantities": {
      "1": 2
    }
  }'
```

**Exemplo de atualização de pedido:**
```bash
curl -X PUT "http://localhost:8080/api/orders/1" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "productQuantities": {
      "1": 3,
      "3": 1
    }
  }'
```

**Exemplo de deleção de pedido:**
```bash
curl -X DELETE "http://localhost:8080/api/orders/1"
```

**Exemplos de consulta:**
```bash
# Listar todos os usuários
curl http://localhost:8080/api/users

# Listar todos os produtos
curl http://localhost:8080/api/products

# Listar todos os pedidos
curl http://localhost:8080/api/orders

# Health check geral
curl http://localhost:8080/health

# Health check do banco
curl http://localhost:8080/health/database

# Informações da API
curl http://localhost:8080/

# Documentação OpenAPI
curl http://localhost:8080/v3/api-docs
```

## 🏛️ Conceitos de Arquitetura Hexagonal Implementados

### 📦 Entidades de Domínio

- **User** (Usuário) - Entidade de usuário do sistema
- **Product** (Produto) - Entidade de produto com controle de estoque
- **Order** (Pedido) - Entidade de pedido com múltiplos produtos
- **OrderProduct** (Produto do Pedido) - Entidade de relacionamento

### 🔄 Estados do Pedido

- **PENDING** - Pedido criado
- **CONFIRMED** - Pedido confirmado e processado
- **CANCELLED** - Pedido cancelado

### 🎯 Casos de Uso

- **CreateUserUseCase** - Criar usuário
- **UpdateUserUseCase** - Atualizar usuário
- **DeleteUserUseCase** - Deletar usuário
- **CreateProductUseCase** - Criar produto
- **UpdateProductUseCase** - Atualizar produto
- **DeleteProductUseCase** - Deletar produto
- **CreateOrderUseCase** - Criar pedido com múltiplos produtos
- **UpdateOrderUseCase** - Atualizar pedido (apenas pendentes)
- **DeleteOrderUseCase** - Deletar pedido (apenas pendentes)

## 🧪 Exemplos de Uso

### Fluxo Completo de Criação de Pedido

```bash
# 1. Criar usuário
curl -X POST "http://localhost:8080/api/users" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "João Silva",
    "email": "joao@email.com",
    "password": "123456"
  }'

# 2. Criar produto
curl -X POST "http://localhost:8080/api/products" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Smartphone XYZ",
    "description": "Smartphone de última geração",
    "price": 1299.99,
    "stockQuantity": 50
  }'

# 3. Criar pedido
curl -X POST "http://localhost:8080/api/orders" \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "productQuantities": {
      "1": 2
    }
  }'
```

## 🛡️ Funcionalidades Implementadas

- ✅ **Criação de usuários** com validação de email
- ✅ **Atualização de usuários** com validação de email único
- ✅ **Deleção de usuários**
- ✅ **Criação de produtos** com controle de estoque
- ✅ **Atualização de produtos**
- ✅ **Deleção de produtos**
- ✅ **Criação de pedidos** com múltiplos produtos
- ✅ **Atualização de pedidos** (apenas pendentes)
- ✅ **Deleção de pedidos** (apenas pendentes)
- ✅ **Controle automático de estoque** ao processar pedidos
- ✅ **Validações de negócio** nas entidades do domínio
- ✅ **Arquitetura Hexagonal** com separação clara de responsabilidades
- ✅ **Docker Compose** para PostgreSQL e pgAdmin
- ✅ **Script de automação** para facilitar o desenvolvimento
- ✅ **Swagger/OpenAPI** para documentação interativa da API

## 🎓 Aprendizados do Curso

Este projeto demonstra os seguintes conceitos aprendidos no curso:

1. **Arquitetura Hexagonal (Ports and Adapters)**
   - Separação clara entre domínio e infraestrutura
   - Inversão de dependência
   - Adaptadores de entrada e saída

2. **Domain-Driven Design (DDD)**
   - Entidades de domínio com regras de negócio
   - Casos de uso bem definidos
   - Repositórios como interfaces

3. **Padrões de Projeto**
   - Repository Pattern
   - Use Case Pattern
   - Adapter Pattern

4. **Boas Práticas**
   - SOLID Principles
   - Clean Code
   - Dependency Injection

## 🛡️ Regras de Negócio

### Usuário
- Nome não pode ser vazio
- Email deve ser válido e único
- Senha deve ter pelo menos 6 caracteres

### Produto
- Nome e descrição não podem ser vazios
- Preço deve ser maior que zero
- Quantidade em estoque não pode ser negativa

### Pedido
- Deve conter pelo menos um produto
- Produtos devem ter estoque suficiente
- Estoque é automaticamente diminuído ao processar o pedido
- Pedidos confirmados não podem ser modificados

## Estrutura do Banco de Dados

O Hibernate irá criar automaticamente as seguintes tabelas:
- `users` - Usuários do sistema
- `products` - Produtos disponíveis
- `orders` - Pedidos realizados
- `order_products` - Itens de cada pedido

## Testando a API

Você pode usar o curl ou qualquer cliente HTTP como Postman:

1. **Criar um usuário:**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"João Silva","email":"joao@email.com","password":"123456"}'
```

2. **Criar um produto:**
```bash
curl -X POST http://localhost:8080/api/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Smartphone","description":"Smartphone novo","price":1299.99,"stockQuantity":10}'
```

3. **Criar um pedido:**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"productQuantities":{"1":2}}'
```

### Acessando o pgAdmin

1. Abra http://localhost:8081 no navegador
2. Login: admin@admin.com / admin
3. O servidor PostgreSQL já estará configurado automaticamente

## 👨‍🏫 Sobre o Professor

**Prof. Danilo Aparecido** é instrutor na plataforma [Torne-se um Programador](https://www.torneseumprogramador.com.br/), especializado em arquiteturas de software e desenvolvimento de sistemas escaláveis.

## 📚 Curso Completo

Para aprender mais sobre arquiteturas de software e aprofundar seus conhecimentos, acesse o curso completo:

**[Arquiteturas de Software Modernas](https://www.torneseumprogramador.com.br/cursos/arquiteturas_software)**

## 🤝 Contribuição

Este projeto foi desenvolvido como parte de um desafio educacional. Contribuições são bem-vindas através de issues e pull requests.

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

---

**Desenvolvido com ❤️ para o curso de Arquiteturas de Software do [Torne-se um Programador](https://www.torneseumprogramador.com.br/)** 