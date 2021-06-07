package com.example.jpa

import com.example.jpa.repository.Customer
import com.example.jpa.service.CustomerService
import java.util.logging.Logger
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CustomerRunner(private val service: CustomerService) : CommandLineRunner {

    private val logger: Logger = Logger.getLogger(CustomerRunner::class.simpleName)

    override fun run(vararg args: String?) {
        service.addCustomer(Customer(firstName = "test", lastName = "surtest"))

        service.getAllCustomers().forEach {
            logger.info(">>>>>> $it")
        }
    }
}