
# WIP Asignment

## Prerequisites:
JDK (used 17 for development)\
Maven\
MySQL server running 

### To start MySQL as docker container:

```bash
docker run --name my-mysql-container --rm \
-e MYSQL_ROOT_PASSWORD=kwdikos123 \
-e MYSQL_DATABASE=tax_db \
-e MYSQL_USER=myuser \
-e MYSQL_PASSWORD=myuserpassword \
-p 3306:3306 \
-v mysql-data:/var/lib/mysql \
-d mysql:latest
```
## To run application
(in project folder)\
mvn clean instal\
mvn spring-boot:run\

