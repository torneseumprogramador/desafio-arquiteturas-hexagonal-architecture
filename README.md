# E-commerce com Arquitetura Hexagonal

Este projeto implementa um sistema de e-commerce utilizando a Arquitetura Hexagonal (Ports and Adapters) com Java 17 e Spring Boot.

## Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA**
- **PostgreSQL**
- **Maven**
- **Lombok**

## Estrutura da Arquitetura Hexagonal

```
src/main/java/com/ecommerce/
├── domain/model/           # Entidades do domínio (sem dependências externas)
├── application/ports/in/   # Interfaces dos casos de uso
├── application/ports/out/  # Interfaces dos repositórios
├── application/services/   # Implementações dos casos de uso
├── adapters/in/rest/       # Controllers REST
├── adapters/out/persistence/ # Implementações JPA dos repositórios
└── config/                 # Configurações do Spring
```

## Pré-requisitos

- Java 17
- Maven 3.6+
- PostgreSQL

## Configuração do Banco de Dados

### Opção 1: Usando Docker (Recomendado)

1. Certifique-se de ter o Docker e Docker Compose instalados
2. Execute o comando para subir o PostgreSQL:

```bash
docker-compose up -d
```

Isso irá:
- Iniciar o PostgreSQL na porta 5432
- Criar o banco de dados `ecommerce_db` automaticamente
- Iniciar o pgAdmin na porta 8081 (http://localhost:8081)
  - Email: admin@admin.com
  - Senha: admin

### Opção 2: PostgreSQL Local

1. Instale e configure o PostgreSQL
2. Crie um banco de dados chamado `ecommerce_db`
3. Configure as credenciais no arquivo `application.yml` se necessário

## Executando a Aplicação

1. Clone o repositório
2. Navegue até o diretório do projeto
3. Se estiver usando Docker, inicie o PostgreSQL primeiro:

```bash
docker-compose up -d
```

4. Execute a aplicação:

```bash
mvn spring-boot:run
```

A aplicação estará disponível em `http://localhost:8080`

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

## Endpoints da API

### Usuários

**POST** `/api/users`
```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "123456"
}
```

### Produtos

**POST** `/api/products`
```json
{
  "name": "Smartphone XYZ",
  "description": "Smartphone de última geração",
  "price": 1299.99,
  "stockQuantity": 50
}
```

### Pedidos

**POST** `/api/orders`
```json
{
  "userId": 1,
  "productQuantities": {
    "1": 2,
    "2": 1
  }
}
```

## Funcionalidades Implementadas

- ✅ Criação de usuários com validação de email
- ✅ Criação de produtos com controle de estoque
- ✅ Criação de pedidos com múltiplos produtos
- ✅ Controle automático de estoque ao processar pedidos
- ✅ Validações de negócio nas entidades do domínio
- ✅ Arquitetura Hexagonal com separação clara de responsabilidades

## Regras de Negócio

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

3. **Verificar dados salvos:**
```bash
curl http://localhost:8080/api/test/users
curl http://localhost:8080/api/test/products
```

4. **Criar um pedido:**
```bash
curl -X POST http://localhost:8080/api/orders \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"productQuantities":{"1":2}}'
```

5. **Criar um pedido (teste):**
```bash
curl http://localhost:8080/api/test/create-order
```

### Endpoints de Teste

- `GET /api/test/users` - Conta usuários no banco
- `GET /api/test/products` - Conta produtos no banco  
- `GET /api/test/orders` - Conta pedidos no banco
- `GET /api/test/create-order` - Cria um pedido de teste

### Acessando o pgAdmin

1. Abra http://localhost:8081 no navegador
2. Login: admin@admin.com / admin
3. O servidor PostgreSQL já estará configurado automaticamente 