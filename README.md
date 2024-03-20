Read-me:

Para rodar o projeto é preciso primeiro criar o banco de dados MariaDB. Se você fizer pelo docker pode adaptar os comandos:
- No terminal: docker run --name mariadb -p 3306:3306 -e MYSQL_ROOT_PASSWORD=admin -v C:\docker_image:/var/lib/mysql -d mariadb:latest
- No banco de dados como quary: CREATE [OR REPLACE] {DATABASE | SCHEMA} [IF NOT EXISTS] entrevista;


Ou instalar o MariaDB e configurar manualmente. Se optar por isso ainda consegue usar o script para criar o banco de dados:
- No banco de dados como quary: CREATE [OR REPLACE] {DATABASE | SCHEMA} [IF NOT EXISTS] entrevista;


Ao rodar o projeto ele mesmo vai criar as tabelas. Você pode testar com as collections do Postman que enviei por email ou via Swagger.
- Url: http://localhost:8080/swagger-ui/index.html#/
