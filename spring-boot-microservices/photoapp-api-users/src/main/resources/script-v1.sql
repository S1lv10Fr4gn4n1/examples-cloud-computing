CREATE DATABASE photo_app;

CREATE USER 'users-ws'@'%' IDENTIFIED BY 'test123';
GRANT ALL PRIVILEGES ON photo_app . * TO 'users-ws'@'%';

FLUSH PRIVILEGES;