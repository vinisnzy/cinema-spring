# 🎬 Cinema API

API RESTful para gerenciamento de um sistema de cinema, desenvolvida com Spring Boot 3.5.3 e Java 21.

## 🚀 Tecnologias

- **Java 21**
- **Spring Boot 3.5.3**
  - Spring Data JPA
  - Spring Security
  - Spring Validation
  - Spring Web
- **PostgreSQL**
- **Docker**

## 📋 Pré-requisitos

- Java 21 ou superior
- Maven 3.8.1 ou superior
- Docker e Docker Compose
- PostgreSQL 16 (opcional, pode ser executado via Docker)

## 🛠️ Configuração do Ambiente

1. **Clone o repositório**
   ```bash
   git clone https://github.com/vinisnzy/cinema-spring.git
   cd cinema-spring
   ```

2. **Inicie o banco de dados com Docker**
   ```bash
   docker-compose up -d
   ```
   
   Isso irá iniciar um container PostgreSQL na porta 5433 com as seguintes credenciais:
   - **Database**: cinema
   - **Usuário**: vinisnzy
   - **Senha**: root
   - **Porta**: 5433

## 🏃 Executando a Aplicação

```bash
# Compilar o projeto
mvn clean install

# Executar a aplicação
mvn spring-boot:run
```

A aplicação estará disponível em: `http://localhost:8080`

## 📚 Documentação da API

A documentação da API está disponível através do Swagger UI:
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`
- **OpenAPI**: `http://localhost:8080/v3/api-docs`

## 🏗️ Estrutura do Projeto

```
src/
├── main/
│   ├── java/com/vinisnzy/cinema/
│   │   ├── config/         # Configurações da aplicação
│   │   ├── controllers/    # Controladores REST
│   │   ├── dtos/          # Objetos de transferência de dados
│   │   ├── exceptions/    # Tratamento de exceções
│   │   ├── models/        # Entidades JPA
│   │   ├── repositories/  # Repositórios Spring Data JPA
│   │   ├── services/      # Lógica de negócios
│   │   └── CinemaApplication.java
│   └── resources/
│       ├── application-dev.properties  # Configurações de desenvolvimento
│       └── application-prod.properties # Configurações de produção
```

## 🧪 Testes

Para executar os testes:
```bash
mvn test
```

## 🔄 Banco de Dados

O banco de dados é gerenciado automaticamente pelo Hibernate com as seguintes configurações:
- **Modo de criação/atualização automática** em desenvolvimento
- **Validação do schema** em produção
- **Liquibase** para migrações (a implementar)

## 🤝 Contribuição

1. Faça um fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas alterações (`git commit -m 'Add some AmazingFeature'`)
4. Faça o push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

## 📄 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.

## ✉️ Contato

Vinícius Moraes - [@vinisnzy](https://github.com/vinisnzy)

---

Desenvolvido com ❤️ por Vinícius Moraes
