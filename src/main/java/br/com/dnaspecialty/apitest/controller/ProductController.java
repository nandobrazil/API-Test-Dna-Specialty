package br.com.dnaspecialty.apitest.controller;

import br.com.dnaspecialty.apitest.configuration.ApiPageable;
import br.com.dnaspecialty.apitest.dto.commons.CreateResponseDto;
import br.com.dnaspecialty.apitest.dto.commons.DeleteResponseDto;
import br.com.dnaspecialty.apitest.dto.commons.ResponseBase;
import br.com.dnaspecialty.apitest.dto.commons.ResponseBasePaginated;
import br.com.dnaspecialty.apitest.dto.customer.CustomerGridDto;
import br.com.dnaspecialty.apitest.dto.customer.CustomerOptionsDto;
import br.com.dnaspecialty.apitest.dto.product.ProductGridDto;
import br.com.dnaspecialty.apitest.dto.product.ProductOptionsDto;
import br.com.dnaspecialty.apitest.dto.product.ProductParamDto;
import br.com.dnaspecialty.apitest.model.Customer;
import br.com.dnaspecialty.apitest.model.Product;
import br.com.dnaspecialty.apitest.service.ProductService;
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
@RequestMapping(value = "/product")
public class ProductController {

    private final ModelMapper modelMapper;

    private final ProductService productService;

    @Autowired
    public ProductController(
            final ModelMapper modelMapper,
            final ProductService productService
    ) {
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    @ApiPageable
    @GetMapping
    @ApiOperation("Endpoint responsible for listing all products")
    public ResponseEntity<ResponseBasePaginated<List<ProductGridDto>>> index(@ApiIgnore Pageable pageable,
                                                                         @RequestParam(value = "filter", defaultValue = "", required = false) String filter) {
        final Page<Product> products = this.productService.findAll(filter, pageable);

        final List<ProductGridDto> productsDto = products.getContent().stream()
                .map(ProductGridDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ResponseBasePaginated.ofPage(new PageImpl<>(productsDto, pageable, products.getTotalElements())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBase<ProductGridDto>> findById(@PathVariable(value = "id", required = true) Long id) {
        final Product product = this.productService.find(id);
        final ProductGridDto productDto = ProductGridDto.from(product);
        return ResponseEntity.ok(ResponseBase.of(productDto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseBase<CreateResponseDto>> update(
            @RequestBody @Valid final ProductParamDto productParamDto,
            @PathVariable(name = "id") final Long idProduct) {
        final Product product = this.productService.update(idProduct, productParamDto);
        return ResponseEntity.ok(ResponseBase.of(new CreateResponseDto(product)));
    }
    @PostMapping
    @Transactional
    public ResponseEntity<ResponseBase<CreateResponseDto>> create(
            @RequestBody ProductParamDto productParamDto) {
        final Product product = this.productService.save(productParamDto);
        return ResponseEntity.ok(ResponseBase.of(new CreateResponseDto(product)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBase<DeleteResponseDto>> delete(@PathVariable(name = "id") Long id) {
        final Long idProduct = this.productService.delete(id);
        return ResponseEntity.ok(ResponseBase.of(new DeleteResponseDto(idProduct)));
    }

    @GetMapping("/options")
    public ResponseEntity<ResponseBase<List<ProductOptionsDto>>> findOptions() {
        final List<Product> products = this.productService.findAllWithoutPage();
        final List<ProductOptionsDto> productOptionsDtos = products.stream()
                .map(ProductOptionsDto::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ResponseBase.of(productOptionsDtos));
    }
}
