# Spring Native + Redis + Postgres

This is just an example about how application, database and cache work together to reach great API throughput. 

</br>

### Tools
- Spring Native
- JDK 11, Kotlin
- Redis
- Postgres
- Apache AB (benchmarks)
- Python3 (script generate user database records)


</br>

### How to run

- Start Postgres, Regis and adminer containers 
    
    `docker-compose up -d adminer postgres-user redis-user`

- Generate database (from db-generator folder)
    
    `python3 generate.py`

- Run user-service
    
    `./gradlew bootRun`

- Check if API is available

    `curl localhost:8081/user`


</br>


### Some benchmarks

Tested on a database with over 300k+ rows

</br>

### List 10k users
`ab -n 5000 -c 10 -m GET localhost:8081/user?page=0\&size=10000`

With Redis cache
```
Requests per second:    248.47 [#/sec] (mean)
Requests per second:    165.06 [#/sec] (mean)
Requests per second:    208.09 [#/sec] (mean)
```

Without Redis (just hibernate cache)
```
Requests per second:    58.40 [#/sec] (mean)
Requests per second:    64.30 [#/sec] (mean)
Requests per second:    58.31 [#/sec] (mean)
```

</br>

### List users up to 10k with name contains 'and'
`ab -n 5000 -c 10 -m GET http://localhost:8081/user/search/and?page=0\&size=10000`

With Redis cache
```
Requests per second:    128.68 [#/sec] (mean)
Requests per second:    138.50 [#/sec] (mean)
```

Without Redis cache
```
Requests per second:    11.66 [#/sec] (mean)
live is too short to run benchmark again (over 428.746 seconds to run) ....
```