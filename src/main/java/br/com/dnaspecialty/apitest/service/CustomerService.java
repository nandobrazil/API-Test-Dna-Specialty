package br.com.dnaspecialty.apitest.service;

import br.com.dnaspecialty.apitest.dto.customer.CustomerParamDto;
import br.com.dnaspecialty.apitest.enumerator.ApplicationMessageEnum;
import br.com.dnaspecialty.apitest.exception.BusinessException;
import br.com.dnaspecialty.apitest.exception.NotFoundException;
import br.com.dnaspecialty.apitest.model.Customer;
import br.com.dnaspecialty.apitest.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private static final String NOT_FOUND = ApplicationMessageEnum.X0_NOT_FOUND.handleMessage("Produto");

    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;


    public CustomerService(
            final CustomerRepository customerRepository,
            final ModelMapper modelMapper
    ) {
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public Page<Customer> findAll(String filter, Pageable pageable) {
        final Page<Customer> customers = this.customerRepository.findAllByFilter(filter, pageable);
        return customers;
    }

    public Customer save(final CustomerParamDto customerParamDto) {
        final boolean exists = this.customerRepository.existsByCnpj(customerParamDto.getCnpj(), null);

        if (exists) {
            final String errorMessage = ApplicationMessageEnum.ALREADY_EXISTS
                    .handleMessage("Cliente", "CNPJ");
            throw new BusinessException(errorMessage);
        }

        final Customer customer = this.modelMapper.map(customerParamDto, Customer.class);
        return this.customerRepository.save(customer);
    }

    public Customer update(Long idCustomer, CustomerParamDto customerParamDto) {
        final boolean existsId = this.customerRepository.existsById(idCustomer);
        final boolean existsByName = this.customerRepository.existsByCnpj(customerParamDto.getCnpj(), idCustomer);
        if (!existsId) {
            throw new NotFoundException(NOT_FOUND);
        }
        if (existsByName) {
            final String errorMessage = ApplicationMessageEnum.ALREADY_EXISTS
                    .handleMessage("Cliente", "CNPJ");
            throw new BusinessException(errorMessage);
        }
        Customer customer = this.modelMapper.map(customerParamDto, Customer.class);
        customer.setId(idCustomer);
        return this.customerRepository.save(customer);
    }

    public Customer find(final Long idCustomer) {
        return this.customerRepository.findById(idCustomer).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    public Long delete(Long idCustomer) {
        if (!this.customerRepository.existsById(idCustomer)) {
            throw new NotFoundException(NOT_FOUND);
        }
        final boolean existRelationship = this.customerRepository.existRelationship(idCustomer).orElse(false);
        if (existRelationship) {
            final Customer customer = this.find(idCustomer);
            final String message = "O cliente \"" + customer.getCorporateName() + "\""
                    + " não pode ser excluído porque possui ordem de compra vinculada";
            throw new BusinessException(message);
        }
        this.customerRepository.deleteById(idCustomer);
        return idCustomer;
    }

    public List<Customer> findAllWithoutPage() {
        return this.customerRepository.findAll();
    }
}
