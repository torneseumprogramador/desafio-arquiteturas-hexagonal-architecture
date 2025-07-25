#!/bin/bash

# Cores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Função para mostrar ajuda
show_help() {
    echo -e "${BLUE}📖 Uso do script:${NC}"
    echo -e "  ${GREEN}./run.sh${NC}                    - Executa tudo (Docker + Build + Run)"
    echo -e "  ${GREEN}./run.sh build${NC}              - Apenas mvn compile"
    echo -e "  ${GREEN}./run.sh clean${NC}              - Apenas mvn clean"
    echo -e "  ${GREEN}./run.sh test${NC}               - Executa testes"
    echo -e "  ${GREEN}./run.sh docker${NC}             - Apenas inicia Docker (PostgreSQL + pgAdmin)"
    echo -e "  ${GREEN}./run.sh docker-stop${NC}        - Para containers Docker"
    echo -e "  ${GREEN}./run.sh run${NC}                - Apenas executa a API"
    echo -e "  ${GREEN}./run.sh stop${NC}               - Para a API Spring Boot"
    echo -e "  ${GREEN}./run.sh watch${NC}              - Executa mvn spring-boot:run com watch"
    echo -e "  ${GREEN}./run.sh help${NC}               - Mostra esta ajuda"
    echo ""
    echo -e "${YELLOW}💡 Exemplos:${NC}"
    echo -e "  ${GREEN}./run.sh build${NC}              - Para fazer apenas o build"
    echo -e "  ${GREEN}./run.sh docker && ./run.sh build${NC} - Inicia Docker e depois faz build"
}

# Verificar se o Java 17+ está instalado
check_java() {
    if ! java -version > /dev/null 2>&1; then
        echo -e "${RED}❌ Java não está instalado. Por favor, instale o Java 17+ e tente novamente.${NC}"
        exit 1
    fi
    
    # Verifica se é Java 17 ou superior
    JAVA_VERSION=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2 | cut -d'.' -f1)
    if [ "$JAVA_VERSION" -lt 17 ]; then
        echo -e "${RED}❌ Java 17+ é necessário. Versão atual: $JAVA_VERSION${NC}"
        exit 1
    fi
    
    echo -e "${GREEN}✅ Java $JAVA_VERSION detectado${NC}"
}

# Verificar se o Maven está instalado
check_maven() {
    if ! mvn -version > /dev/null 2>&1; then
        echo -e "${RED}❌ Maven não está instalado. Por favor, instale o Maven e tente novamente.${NC}"
        exit 1
    fi
}

# Verificar se o Docker está rodando
check_docker() {
    if ! docker info > /dev/null 2>&1; then
        echo -e "${RED}❌ Docker não está rodando. Por favor, inicie o Docker e tente novamente.${NC}"
        exit 1
    fi
}

# Função para verificar se o PostgreSQL já está rodando
check_postgres_running() {
    if nc -z localhost 5432 2>/dev/null; then
        echo -e "${GREEN}✅ PostgreSQL já está rodando!${NC}"
        return 0
    fi
    return 1
}

# Função para iniciar Docker
start_docker() {
    # Verifica se já está rodando
    if check_postgres_running; then
        return 0
    fi
    
    echo -e "${YELLOW}🐳 Iniciando PostgreSQL e pgAdmin...${NC}"
    docker-compose up -d
    
    echo -e "${YELLOW}⏳ Aguardando PostgreSQL estar pronto...${NC}"
    sleep 15
    
    echo -e "${YELLOW}🔍 Verificando conexão com PostgreSQL...${NC}"
    for i in {1..20}; do
        # Verifica se a porta está respondendo
        if nc -z localhost 5432 2>/dev/null; then
            echo -e "${GREEN}✅ PostgreSQL está pronto! (Porta 5432 respondendo)${NC}"
            echo -e "${BLUE}📊 pgAdmin estará disponível em: http://localhost:8081${NC}"
            echo -e "${BLUE}   Email: admin@admin.com${NC}"
            echo -e "${BLUE}   Senha: admin${NC}"
            return 0
        fi
        
        if [ $i -eq 20 ]; then
            echo -e "${RED}❌ Timeout aguardando PostgreSQL${NC}"
            echo -e "${YELLOW}💡 Dica: Verifique se o Docker está rodando e tente novamente${NC}"
            return 1
        fi
        echo -e "${YELLOW}⏳ Tentativa $i/20...${NC}"
        sleep 3
    done
}

# Função para executar a API
run_api() {
    echo -e "${GREEN}🎯 Iniciando a API Spring Boot...${NC}"
    echo -e "${BLUE}📱 A API estará disponível em: http://localhost:8080${NC}"
    echo -e "${BLUE}📚 Endpoints disponíveis:${NC}"
    echo -e "${BLUE}   - POST /api/users${NC}"
    echo -e "${BLUE}   - POST /api/products${NC}"
    echo -e "${BLUE}   - POST /api/orders${NC}"
    echo -e "${BLUE}   - GET /api/test/*${NC}"
    echo -e "${YELLOW}⏹️ Pressione Ctrl+C para parar${NC}"
    
    mvn spring-boot:run
}

# Verificar argumentos
case "${1:-}" in
    "build")
        echo -e "${BLUE}🔨 Executando mvn compile...${NC}"
        check_java
        check_maven
        mvn clean compile
        ;;
    "clean")
        echo -e "${BLUE}🧹 Executando mvn clean...${NC}"
        check_maven
        mvn clean
        ;;
    "test")
        echo -e "${BLUE}🧪 Executando testes...${NC}"
        check_java
        check_maven
        mvn test
        ;;
    "docker")
        echo -e "${BLUE}🐳 Iniciando Docker...${NC}"
        check_docker
        
        # Verifica se já está rodando
        if check_postgres_running; then
            echo -e "${YELLOW}ℹ️ PostgreSQL já está rodando, não é necessário reiniciar${NC}"
            exit 0
        fi
        
        # Se não estiver rodando, para containers e inicia
        docker-compose down
        start_docker
        ;;
    "docker-stop")
        echo -e "${BLUE}🛑 Parando Docker...${NC}"
        docker-compose down
        ;;
    "run")
        echo -e "${BLUE}🎯 Executando API...${NC}"
        check_java
        check_maven
        run_api
        ;;
    "stop")
        echo -e "${BLUE}🛑 Parando API Spring Boot...${NC}"
        
        # Procura por processos Java que estão rodando a aplicação
        PIDS=$(ps aux | grep "spring-boot:run" | grep -v grep | awk '{print $2}')
        
        if [ -z "$PIDS" ]; then
            echo -e "${YELLOW}ℹ️ Nenhuma aplicação Spring Boot encontrada rodando${NC}"
            exit 0
        fi
        
        echo -e "${YELLOW}🔍 Encontrados processos: $PIDS${NC}"
        
        for PID in $PIDS; do
            echo -e "${YELLOW}🛑 Parando processo $PID...${NC}"
            kill $PID
            
            # Aguarda um pouco e verifica se parou
            sleep 2
            if ps -p $PID > /dev/null 2>&1; then
                echo -e "${RED}⚠️ Processo $PID não parou, forçando...${NC}"
                kill -9 $PID
            else
                echo -e "${GREEN}✅ Processo $PID parado com sucesso${NC}"
            fi
        done
        
        echo -e "${GREEN}✅ API Spring Boot parada${NC}"
        ;;
    "watch")
        echo -e "${BLUE}👀 Executando mvn spring-boot:run...${NC}"
        check_java
        check_maven
        run_api
        ;;
    "help"|"-h"|"--help")
        show_help
        ;;
    "")
        # Execução completa (comportamento padrão)
        echo -e "${BLUE}🚀 Iniciando Ecommerce Hexagonal Architecture...${NC}"
        
        check_docker
        check_java
        check_maven
        echo -e "${GREEN}✅ Docker, Java 17 e Maven verificados${NC}"
        
        # Iniciar Docker (só para containers se necessário)
        if ! start_docker; then
            exit 1
        fi
        
        # Clean e build da aplicação
        echo -e "${YELLOW}🧹 Limpando build anterior...${NC}"
        mvn clean
        
        echo -e "${YELLOW}🔨 Fazendo build da aplicação...${NC}"
        if ! mvn compile; then
            echo -e "${RED}❌ Build falhou${NC}"
            exit 1
        fi
        
        echo -e "${GREEN}✅ Build concluído com sucesso!${NC}"
        
        # Executar a aplicação
        run_api
        ;;
    *)
        echo -e "${RED}❌ Comando desconhecido: $1${NC}"
        echo ""
        show_help
        exit 1
        ;;
esac 