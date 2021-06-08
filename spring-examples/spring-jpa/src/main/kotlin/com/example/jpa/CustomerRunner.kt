package com.example.jpa

import com.example.jpa.repository.Address
import com.example.jpa.repository.Customer
import com.example.jpa.service.CustomerService
import java.util.EnumSet.range
import java.util.logging.Logger
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class CustomerRunner(private val service: CustomerService) : CommandLineRunner {

    private val logger: Logger = Logger.getLogger(CustomerRunner::class.simpleName)

    override fun run(vararg args: String?) {
        val address = Address(
            street = "my street",
            number = "123",
            city = "city 1",
            zipCode = "991991last991"
        )
        service.addAddress(address)

        service.addCustomer(
            Customer(
                firstName = "first",
                lastName = "last",
                addresses = listOf(address)
            )
        )

        for (i in 1 until 100) {
            service.addCustomer(
                Customer(
                    firstName = "first $i",
                    lastName = "last $i",
                    addresses = emptyList()
                )
            )
        }

        service.getCustomerByLastName("last").let {
            logger.info(">>>>>> customer by last name $it")
        }

        service.getAllCustomers().let {
            logger.info(">>>>>> total customers ${it.size}")
        }

        service.findAllCustomerLikeQuery("20").let {
            logger.info(">>>>>> findAllCustomerLikeQuery $it")
        }

        service.findByLastNameContaining("30").let {
            logger.info(">>>>>> findByLastNameContaining $it")
        }

        service.getCustomersPaginated(0, 10).let {
            logger.info(">>>>>> getCustomersPaginated ${it.size}")
        }
    }
}