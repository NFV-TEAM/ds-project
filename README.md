
# WIP Asignment

## Start MySQL as container

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
## Authors
