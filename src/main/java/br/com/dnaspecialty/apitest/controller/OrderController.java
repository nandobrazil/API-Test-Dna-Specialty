package br.com.dnaspecialty.apitest.controller;

import br.com.dnaspecialty.apitest.configuration.ApiPageable;
import br.com.dnaspecialty.apitest.dto.commons.CreateResponseDto;
import br.com.dnaspecialty.apitest.dto.commons.DeleteResponseDto;
import br.com.dnaspecialty.apitest.dto.commons.ResponseBase;
import br.com.dnaspecialty.apitest.dto.commons.ResponseBasePaginated;
import br.com.dnaspecialty.apitest.dto.customer.CustomerGridDto;
import br.com.dnaspecialty.apitest.dto.order.OrderGridDto;
import br.com.dnaspecialty.apitest.dto.order.OrderParamDto;
import br.com.dnaspecialty.apitest.model.Customer;
import br.com.dnaspecialty.apitest.model.Order;
import br.com.dnaspecialty.apitest.service.OrderService;
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
@RequestMapping(value = "/order")
public class OrderController {

    private final ModelMapper modelMapper;

    private final OrderService orderService;

    @Autowired
    public OrderController(
            final ModelMapper modelMapper,
            final OrderService orderService
    ) {
        this.modelMapper = modelMapper;
        this.orderService = orderService;
    }

    @ApiPageable
    @GetMapping
    @ApiOperation("Endpoint responsible for listing all orders")
    public ResponseEntity<ResponseBasePaginated<List<OrderGridDto>>> index(@ApiIgnore Pageable pageable,
                                                                           @RequestParam(value = "filter", defaultValue = "", required = false) String filter) {
        final Page<Order> orders = this.orderService.findAll(filter, pageable);

        final List<OrderGridDto> ordersDto = orders.getContent().stream()
                .map(OrderGridDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ResponseBasePaginated.ofPage(new PageImpl<>(ordersDto, pageable, orders.getTotalElements())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBase<OrderGridDto>> findById(@PathVariable(value = "id", required = true) Long id) {
        final Order order = this.orderService.find(id);
        final var orderGridDto = OrderGridDto.from(order);
        return ResponseEntity.ok(ResponseBase.of(orderGridDto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseBase<CreateResponseDto>> update(
            @RequestBody @Valid final OrderParamDto orderParamDto,
            @PathVariable(name = "id") final Long idProduct) {
        final var order = this.orderService.update(idProduct, orderParamDto);
        return ResponseEntity.ok(ResponseBase.of(new CreateResponseDto(order)));
    }
    @PostMapping
    @Transactional
    public ResponseEntity<ResponseBase<CreateResponseDto>> create(
            @RequestBody OrderParamDto orderParamDto) {
        final var order = this.orderService.save(orderParamDto);
        return ResponseEntity.ok(ResponseBase.of(new CreateResponseDto(order)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBase<DeleteResponseDto>> delete(@PathVariable(name = "id") Long id) {
        final var idOrder = this.orderService.delete(id);
        return ResponseEntity.ok(ResponseBase.of(new DeleteResponseDto(idOrder)));
    }
}
