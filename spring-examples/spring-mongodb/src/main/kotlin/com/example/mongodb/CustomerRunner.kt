package com.example.mongodb

import com.example.mongodb.repository.Customer
import com.example.mongodb.service.CustomerService
import java.util.logging.Logger
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CustomerRunner(private val customerService: CustomerService) : CommandLineRunner {

    private val logger: Logger = Logger.getLogger(CustomerRunner::class.simpleName)

    override fun run(vararg args: String?) {
        logger.info(">>>> Hello World")

        customerService.addCustomer(
            Customer(
                firstName = "first",
                lastName = "last",
            )
        )

        customerService.getAllCustomers().forEach {
            logger.info(">>>> all customers $it")
        }

        for (i in 1 until 20) {
            customerService.addCustomer(
                Customer(
                    firstName = "first $i",
                    lastName = "last $i"
                )
            )
        }

        customerService.getByLastName("last 10").let {
            logger.info(">>>> getByLastName $it")
        }
    }
}