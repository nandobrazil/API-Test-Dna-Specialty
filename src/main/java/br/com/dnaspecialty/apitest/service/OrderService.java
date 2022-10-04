package br.com.dnaspecialty.apitest.service;

import br.com.dnaspecialty.apitest.dto.order.OrderParamDto;
import br.com.dnaspecialty.apitest.enumerator.ApplicationMessageEnum;
import br.com.dnaspecialty.apitest.exception.BusinessException;
import br.com.dnaspecialty.apitest.exception.NotFoundException;
import br.com.dnaspecialty.apitest.model.Customer;
import br.com.dnaspecialty.apitest.model.Order;
import br.com.dnaspecialty.apitest.model.Product;
import br.com.dnaspecialty.apitest.model.User;
import br.com.dnaspecialty.apitest.repository.CustomerRepository;
import br.com.dnaspecialty.apitest.repository.OrderRepository;
import br.com.dnaspecialty.apitest.repository.ProductRepository;
import br.com.dnaspecialty.apitest.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.internal.bytebuddy.implementation.bytecode.Throw;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private static final String NOT_FOUND = ApplicationMessageEnum.X0_NOT_FOUND.handleMessage("Ordem de serviço");

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final ProductRepository productRepository;

    private final CustomerRepository customerRepository;

    private final ModelMapper modelMapper;


    public OrderService(
            final OrderRepository orderRepository,
            final UserRepository userRepository,
            final ProductRepository productRepository,
            final CustomerRepository customerRepository,
            final ModelMapper modelMapper
    ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    public Page<Order> findAll(String filter, Pageable pageable) {
        final var orders = this.orderRepository.findAllByFilter(filter, pageable);
        return orders;
    }

    public List<Order> findAllWithoutPage() {
        final var orders = this.orderRepository.findAll();
        return orders;
    }

    public Order save(final OrderParamDto orderParamDto) {
        final var order = this.modelMapper.map(orderParamDto, Order.class);
        this.fillOrderDtos(order, orderParamDto);
        return this.orderRepository.save(order);
    }

    public Order update(Long idOrder, OrderParamDto orderParamDto) {
        final boolean existsId = this.orderRepository.existsById(idOrder);
        if (!existsId) {
            throw new NotFoundException(NOT_FOUND);
        }
        Order order = this.modelMapper.map(orderParamDto, Order.class);
        this.fillOrderDtos(order, orderParamDto);
        order.setId(idOrder);
        return this.orderRepository.save(order);
    }

    public Order find(final Long idOrder) {
        return this.orderRepository.findById(idOrder).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    public Long delete(Long idOrder) {
        if (!this.orderRepository.existsById(idOrder)) {
            throw new NotFoundException(NOT_FOUND);
        }
//        final boolean existRelationship = this.productRepository.existRelationship(id).orElse(false);
//        if (existRelationship) {
//            final Customer customer = this.find(idCustomer);
//            final String message = "O cliente \"" + customer.getName() + "\""
//                    + " não pode ser excluído porque possui ordens de compras vinculadas";
//            throw new BusinessException(message);
//        }
        this.orderRepository.deleteById(idOrder);
        return idOrder;
    }

    public void fillOrderDtos(Order order, OrderParamDto orderParamDto) {
        Optional<User> user = this.userRepository.findById(orderParamDto.getIdUser());
        Optional<Product> product = this.productRepository.findById(orderParamDto.getIdProduct());
        Optional<Customer> customer = this.customerRepository.findById(orderParamDto.getIdCustomer());

        order.setUser(user
                .orElseThrow(() -> new BusinessException(ApplicationMessageEnum.X0_NOT_FOUND.handleMessage("Usuário"))));
        order.setProduct(product
                .orElseThrow(() -> new BusinessException(ApplicationMessageEnum.X0_NOT_FOUND.handleMessage("Produto"))));
        order.setCustomer(customer
                .orElseThrow(() -> new BusinessException(ApplicationMessageEnum.X0_NOT_FOUND.handleMessage("Cliente"))));
    }
}
