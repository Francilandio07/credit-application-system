package me.dio.credit.application.system.service.impl

import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.service.ICustomerService
import org.springframework.stereotype.Service

@Service
class CustomerService: ICustomerService {
    private val customerRepository: CustomerRepository
    override fun save(customer: Customer): Customer {
        TODO("Not yet implemented")
    }

    override fun findById(id: Long): Customer {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long): Customer {
        TODO("Not yet implemented")
    }
}