# 📁 Scripts

Esta pasta contém scripts utilitários para facilitar o desenvolvimento e teste da aplicação.

## 🛠️ Scripts Disponíveis

### `interactive-api.sh`

Script interativo para testar a API de forma amigável através do terminal.

#### Funcionalidades:
- 👥 **Gerenciamento completo de usuários** (CRUD)
- 📦 **Gerenciamento completo de produtos** (CRUD)  
- 🛒 **Gerenciamento completo de pedidos** (CRUD)
- 💚 **Verificação de saúde da API**
- 📚 **Acesso à documentação Swagger**

#### Como usar:
```bash
# Certifique-se de que a aplicação está rodando
./run.sh

# Em outro terminal, execute o script interativo
./scripts/interactive-api.sh
```

#### Pré-requisitos:
- Aplicação rodando em `http://localhost:8080`
- `jq` instalado para processamento de JSON
- `curl` para requisições HTTP

#### Instalação do jq:
```bash
# macOS
brew install jq

# Ubuntu/Debian
sudo apt-get install jq

# CentOS/RHEL
sudo yum install jq
```

#### Características:
- Interface colorida e amigável
- Validações de entrada
- Confirmações antes de operações destrutivas
- Abertura automática do Swagger no navegador
- Suporte multiplataforma (macOS, Linux, Windows)

## 🎯 Uso Recomendado

1. **Desenvolvimento**: Use o script para testar rapidamente as funcionalidades
2. **Demonstração**: Ideal para mostrar a API para stakeholders
3. **Testes**: Validação manual dos endpoints
4. **Debugging**: Verificação de respostas da API

## 🔧 Personalização

O script pode ser facilmente personalizado:
- Modificar cores no início do arquivo
- Adicionar novos endpoints
- Alterar validações
- Customizar mensagens 