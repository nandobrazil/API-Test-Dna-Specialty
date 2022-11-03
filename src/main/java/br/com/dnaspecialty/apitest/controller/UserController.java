package br.com.dnaspecialty.apitest.controller;

import br.com.dnaspecialty.apitest.configuration.ApiPageable;
import br.com.dnaspecialty.apitest.dto.commons.DeleteResponseDto;
import br.com.dnaspecialty.apitest.dto.commons.ResponseBasePaginated;
import br.com.dnaspecialty.apitest.dto.customer.CustomerGridDto;
import br.com.dnaspecialty.apitest.dto.product.ProductOptionsDto;
import br.com.dnaspecialty.apitest.dto.user.UserGridDto;
import br.com.dnaspecialty.apitest.dto.commons.CreateResponseDto;
import br.com.dnaspecialty.apitest.dto.commons.ResponseBase;
import br.com.dnaspecialty.apitest.dto.user.UserOptionsDto;
import br.com.dnaspecialty.apitest.dto.user.UserParamDto;
import br.com.dnaspecialty.apitest.model.Customer;
import br.com.dnaspecialty.apitest.model.Product;
import br.com.dnaspecialty.apitest.model.User;
import br.com.dnaspecialty.apitest.service.UserService;
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
@RequestMapping(value = "/user")
public class UserController {

    private final ModelMapper modelMapper;

    private final UserService userService;

    @Autowired
    public UserController(
        final ModelMapper modelMapper,
        final UserService userService
    ) {
        this.modelMapper = modelMapper;
        this.userService = userService;
    }

    @ApiPageable
    @GetMapping
    @ApiOperation("Endpoint responsible for listing all users")
    public ResponseEntity<ResponseBasePaginated<List<UserGridDto>>> index(@ApiIgnore Pageable pageable,
                                                                          @RequestParam(value = "filter", defaultValue = "", required = false) String filter) {
        final Page<User> users = this.userService.findAll(filter, pageable);

        final List<UserGridDto> usersDto = users.getContent().stream()
                .map(UserGridDto::from)
                .collect(Collectors.toList());

        return ResponseEntity.ok(ResponseBasePaginated.ofPage(new PageImpl<>(usersDto, pageable, users.getTotalElements())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseBase<UserGridDto>> findById(@PathVariable(value = "id", required = true) Long id) {
        final User user = this.userService.find(id);
        final UserGridDto userGridDto = UserGridDto.from(user);
        return ResponseEntity.ok(ResponseBase.of(userGridDto));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<ResponseBase<CreateResponseDto>> update(
            @RequestBody @Valid final UserParamDto userParamDto,
            @PathVariable(name = "id") final Long idUser) {
        final User user = this.userService.update(idUser, userParamDto);
        return ResponseEntity.ok(ResponseBase.of(new CreateResponseDto(user)));
    }
    @PostMapping
    @Transactional
    public ResponseEntity<ResponseBase<CreateResponseDto>> create(
            @RequestBody UserParamDto userParamDto) {
        final User user = this.userService.save(userParamDto);
        return ResponseEntity.ok(ResponseBase.of(new CreateResponseDto(user)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseBase<DeleteResponseDto>> delete(@PathVariable(name = "id") Long id) {
        final Long userId = this.userService.delete(id);
        return ResponseEntity.ok(ResponseBase.of(new DeleteResponseDto(userId)));
    }

    @GetMapping("/options")
    public ResponseEntity<ResponseBase<List<UserOptionsDto>>> findOptions() {
        final List<User> users = this.userService.findAllWithoutPage();
        final List<UserOptionsDto> userOptionsDtos = users.stream()
                .map(UserOptionsDto::of)
                .collect(Collectors.toList());
        return ResponseEntity.ok(ResponseBase.of(userOptionsDtos));
    }
}
