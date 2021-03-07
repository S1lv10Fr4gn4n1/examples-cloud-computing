# Sample of microservices

On this project there are 3 small services written in Java Spring using microservice approach.
- inventory-service
- pricing-service
- product-service

Where the product-service requests price and inventory services using `RestTemplate`.

The project was simplified using docker-compose where all services can be build and started easily.
- Run `docker-compose build` to build the images
- Run `docker-compose up -d` to run all services
- Run `docker-compose down` to stop all services

How to consume
- product-service  `curl localhost:8001/product/details/101`
- pricing-service  `curl localhost:8002/price/101`
- inventory-service  `curl localhost:8003/inventory/101`
