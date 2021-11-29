# diario-classe-digital

Sistema online de controle de chamada

Este respositório contém o back-end em java com Spring Boot junto com uma versão build do front-end
Os fontes abertos do front-end estão em outro repositório: https://github.com/lucasdclopes/diario-classe-digital-frontend

É necessário ter um banco de dados Microsoft Sql Server instalado
Os dados de acesso ao banco de dados ficam no arquivo /diario-classe-digital/src/main/resources/application.properties

O sistema está configurado para modo de desenvolvimento, onde todos os dados do banco de dados são apagados a cada build. Para alterar este comportamento, altere a configuração spring.jpa.hibernate.ddl-auto, também no application.properties. Veja a documentação do Spring para esta propriedade https://docs.spring.io/spring-boot/docs/1.1.0.M1/reference/html/howto-database-initialization.html
