package com.example.dataRest.repository

import com.example.dataRest.repository.model.Person
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RepositoryRestResource

@RepositoryRestResource(collectionResourceRel = "people", path = "people")
interface PersonRepository : PagingAndSortingRepository<Person, Long> {

    fun findByLastName(@Param("name") name: String): List<Person>
}

// https://spring.io/guides/gs/accessing-data-rest/

// List people
// curl http://localhost:8080/people

// Include new person
// curl -i -H "Content-Type:application/json" -d '{"firstName": "Frodo", "lastName": "Baggins"}' http://localhost:8080/people