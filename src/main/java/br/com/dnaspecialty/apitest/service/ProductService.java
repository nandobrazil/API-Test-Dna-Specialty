package br.com.dnaspecialty.apitest.service;

import br.com.dnaspecialty.apitest.dto.product.ProductParamDto;
import br.com.dnaspecialty.apitest.enumerator.ApplicationMessageEnum;
import br.com.dnaspecialty.apitest.exception.BusinessException;
import br.com.dnaspecialty.apitest.exception.NotFoundException;
import br.com.dnaspecialty.apitest.model.Product;
import br.com.dnaspecialty.apitest.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final String NOT_FOUND = ApplicationMessageEnum.X0_NOT_FOUND.handleMessage("Produto");
    private final ProductRepository productRepository;

    private final ModelMapper modelMapper;


    public ProductService(
            final ProductRepository productRepository,
            final ModelMapper modelMapper
    ) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
    }

    public Page<Product> findAll(String filter, Pageable pageable) {
        final var products = this.productRepository.findAllByFilter(filter, pageable);
        return products;
    }

    public Product save(final ProductParamDto productParamDto) {
        final boolean exists = this.productRepository.existsByName(productParamDto.getName(), null);

        if (exists) {
            final String errorMessage = ApplicationMessageEnum.ALREADY_EXISTS
                    .handleMessage("Produto", "nome");
            throw new BusinessException(errorMessage);
        }

        final var product = this.modelMapper.map(productParamDto, Product.class);
        return this.productRepository.save(product);
    }

    public Product update(Long idProduct, ProductParamDto productParamDto) {
        final boolean existsId = this.productRepository.existsById(idProduct);
        final boolean existsByName = this.productRepository.existsByName(productParamDto.getName(), idProduct);
        if (!existsId) {
            throw new NotFoundException(NOT_FOUND);
        }
        if (existsByName) {
            final String errorMessage = ApplicationMessageEnum.ALREADY_EXISTS
                    .handleMessage("Produto", "nome");
            throw new BusinessException(errorMessage);
        }
        Product product = this.modelMapper.map(productParamDto, Product.class);
        product.setId(idProduct);
        return this.productRepository.save(product);
    }

    public Product find(final Long idProduct) {
        return this.productRepository.findById(idProduct).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    public Long delete(Long idProduct) {
        if (!this.productRepository.existsById(idProduct)) {
            throw new NotFoundException(NOT_FOUND);
        }
        final boolean existRelationship = this.productRepository.existRelationship(idProduct).orElse(false);
        if (existRelationship) {
            final Product product = this.find(idProduct);
            final String message = "O produto \"" + product.getName() + "\""
                    + " não pode ser excluído porque possui ordem de compra vinculada";
            throw new BusinessException(message);
        }
        this.productRepository.deleteById(idProduct);
        return idProduct;
    }

    public List<Product> findAllWithoutPage() {
        return this.productRepository.findAll();
    }
}
