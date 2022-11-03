package br.com.dnaspecialty.apitest.controller;

import br.com.dnaspecialty.apitest.configuration.ApiPageable;
import br.com.dnaspecialty.apitest.dto.commons.CreateResponseDto;
import br.com.dnaspecialty.apitest.dto.commons.DeleteResponseDto;
import br.com.dnaspecialty.apitest.dto.commons.ResponseBase;
import br.com.dnaspecialty.apitest.dto.commons.ResponseBasePaginated;
import br.com.dnaspecialty.apitest.dto.customer.CustomerGridDto;
import br.com.dnaspecialty.apitest.dto.customer.CustomerOptionsDto;
import br.com.dnaspecialty.apitest.dto.customer.CustomerParamDto;
import br.com.dnaspecialty.apitest.dto.order.OrderGridDto;
import br.com.dnaspecialty.apitest.model.Customer;
import br.com.dnaspecialty.apitest.model.Order;
import br.com.dnaspecialty.apitest.service.CustomerService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/customer")
public class CustomerController {

    private final ModelMapper modelMapper;

    private final CustomerService customerService;
    @Autowired
    public CustomerController(
            final ModelMapper modelMapper,
            final CustomerService customerService
    ) {
        this.modelMapper = modelMapper;
        this.customerService = customerService;
    }

    @ApiPageable
    @GetMapping
    @ApiOperation("Endpoint responsible for listing all customers")
    public ResponseEntity<ResponseBasePaginated<List<CustomerGridDto>>> index(@ApiIgnore Pageable pageable,
                                                                              @RequestParam(value = "filter", defaultValue = "", required = false) String filter) {
        final Page<Customer> customers = this.customerService.findAll(filter, pageable);

        final List<CustomerGridDto> customersDto = customers.getContent().stream()
                .map(CustomerGridDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ResponseBasePaginated.ofPage(new PageImpl<>(customersDto, pageable, customers.getTotalElements())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBase<CustomerGridDto>> findById(@PathVariable(value = "id", required = true) Long id) {
        final Customer customer = this.customerService.find(id);
        final CustomerGridDto customerDto = CustomerGridDto.from(customer);
        return ResponseEntity.ok(ResponseBase.of(customerDto));
    }

    @GetMapping("/options")
    public ResponseEntity<ResponseBase<List<CustomerOptionsDto>>> findOptions() {
        final List<Customer> customers = this.customerService.findAllWithoutPage();
        final List<CustomerOptionsDto> customerOptionsDtos = customers.stream()
                .map(CustomerOptionsDto::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ResponseBase.of(customerOptionsDtos));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseBase<CreateResponseDto>> update(
            @RequestBody @Valid final CustomerParamDto customerParamDto,
            @PathVariable(name = "id") final Long idProduct) {
        final Customer customer = this.customerService.update(idProduct, customerParamDto);
        return ResponseEntity.ok(ResponseBase.of(new CreateResponseDto(customer)));
    }
    @PostMapping
    @Transactional
    public ResponseEntity<ResponseBase<CreateResponseDto>> create(
            @RequestBody CustomerParamDto customerParamDto) {
        final Customer customer = this.customerService.save(customerParamDto);
        return ResponseEntity.ok(ResponseBase.of(new CreateResponseDto(customer)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBase<DeleteResponseDto>> delete(@PathVariable(name = "id") Long id) {
        final Long idCustomer = this.customerService.delete(id);
        return ResponseEntity.ok(ResponseBase.of(new DeleteResponseDto(idCustomer)));
    }
}

