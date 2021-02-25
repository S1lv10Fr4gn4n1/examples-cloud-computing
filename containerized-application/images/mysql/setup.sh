#!/bin/sh

docker pull mysql

docker run --rm -d --name mysql -e MYSQL_ROOT_PASSWORD=changeme -e MYSQL_DATABASE=items -e MYSQL_USER=silvio -e MYSQL_PASSWORD=qwerty123 -p 30306:3306 -v ~/../../database/mysql/:/var/lib/mysql mysql