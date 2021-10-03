package com.sfs.user

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// TODO SFS, TC, test containers
@TestPropertySource(properties = ["spring.datasource.url=jdbc:tc:postgresql:13///"])
class UserServiceApplicationTests {

	// TODO SFS, learn how to create tests for Spring
	// what should be tested
	// TDD

	// TODO SFs verify how to test in webflow, handlers/routes


	@Test
	fun test() {
	}

}
