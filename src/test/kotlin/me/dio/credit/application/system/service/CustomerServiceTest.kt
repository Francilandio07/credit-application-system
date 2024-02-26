package me.dio.credit.application.system.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import me.dio.credit.application.system.entity.Address
import me.dio.credit.application.system.entity.Customer
import me.dio.credit.application.system.exception.BusinessException
import me.dio.credit.application.system.repository.CustomerRepository
import me.dio.credit.application.system.service.impl.CustomerService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.*

//@ActiveProfiles("test")
@ExtendWith(MockKExtension::class)
class CustomerServiceTest {
    @MockK lateinit var customerRespository: CustomerRepository
    @InjectMockKs lateinit var customerService: CustomerService

    @Test
    fun shouldCreateCustomer() {
        //given
        val fakeCustomer: Customer = buildCustomer()
        every { customerRespository.save(any()) } returns fakeCustomer
        //when
        val actual: Customer = customerService.save(fakeCustomer)
        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRespository.save(fakeCustomer) }
    }

    @Test
    fun shouldFindCustomerById(){
        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRespository.findById(fakeId) } returns Optional.of(fakeCustomer)

        //when
        val actual: Customer = customerService.findById(fakeId)

        //then
        Assertions.assertThat(actual).isNotNull
        Assertions.assertThat(actual).isExactlyInstanceOf(Customer::class.java)
        Assertions.assertThat(actual).isSameAs(fakeCustomer)
        verify(exactly = 1) { customerRespository.findById(fakeId) }
    }

    @Test
    fun shouldNotFindCustomerByInvalidIdAndThrowBusinessException(){
        //given
        val fakeId: Long = Random().nextLong()
        every { customerRespository.findById(fakeId) } returns Optional.empty()

        //when

        //then
        Assertions.assertThatExceptionOfType(BusinessException::class.java)
            .isThrownBy { customerService.findById(fakeId) }
            .withMessage("Id $fakeId not found")
        verify(exactly = 1) { customerRespository.findById(fakeId) }
    }

    @Test
    fun shouldDeleteCustomerById(){
        //given
        val fakeId: Long = Random().nextLong()
        val fakeCustomer: Customer = buildCustomer(id = fakeId)
        every { customerRespository.findById(fakeId) } returns Optional.of(fakeCustomer)
        every { customerRespository.delete(fakeCustomer) } just runs

        //when
        customerService.delete(fakeId)

        //then
        verify(exactly = 1) { customerRespository.findById(fakeId) }
        verify(exactly = 1) { customerRespository.delete(fakeCustomer) }
    }

    private fun buildCustomer(
        firstName: String = "Cami",
        lastName: String = "Cavalcante",
        cpf: String = "07035030301",
        email: String = "Landim@gmail.com",
        password: String = "32456789",
        zipCode: String = "62184000",
        street: String = "Rua Aderbal",
        income: BigDecimal = BigDecimal.valueOf(1000.0),
        id: Long = 2
    ) = Customer(
        firstName = firstName,
        lastName = lastName,
        cpf = cpf,
        email = email,
        password = password,
        address = Address(
            zipCode = zipCode,
            street = street
        ),
        income = income,
        id = id
    )
}