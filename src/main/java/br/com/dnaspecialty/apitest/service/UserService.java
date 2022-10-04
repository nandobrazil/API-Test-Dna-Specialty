package br.com.dnaspecialty.apitest.service;

import br.com.dnaspecialty.apitest.dto.user.UserParamDto;
import br.com.dnaspecialty.apitest.enumerator.ApplicationMessageEnum;
import br.com.dnaspecialty.apitest.exception.BusinessException;
import br.com.dnaspecialty.apitest.exception.NotFoundException;
import br.com.dnaspecialty.apitest.model.User;
import br.com.dnaspecialty.apitest.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private static final String NOT_FOUND = ApplicationMessageEnum.X0_NOT_FOUND.handleMessage("Usuário");
    private final UserRepository userRepository;

    private final ModelMapper modelMapper;


    public UserService(
        final UserRepository userRepository,
        final ModelMapper modelMapper
    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    public Page<User> findAll(String filter, Pageable pageable) {
        final var users = this.userRepository.findAllByFilter(filter, pageable);
        return users;
    }

    public User save(final UserParamDto userParamDto) {
        final boolean exists =
                this.userRepository
                        .existsByCpfOrLogin(userParamDto.getCpf(), userParamDto.getLogin(), null);

        if (exists) {
            final String errorMessage = ApplicationMessageEnum.ALREADY_EXISTS
                    .handleMessage("Usuário", "CPF ou LOGIN");
            throw new BusinessException(errorMessage);
        }

        final var user = this.modelMapper.map(userParamDto, User.class);
        return this.userRepository.save(user);
    }

    public User update(Long idUser, UserParamDto userParamDto) {
        final boolean existsId = this.userRepository.existsById(idUser);
        final boolean existsLoginOrCpf =
                this.userRepository
                        .existsByCpfOrLogin(userParamDto.getCpf(), userParamDto.getLogin(), idUser);
        if (!existsId) {
            throw new NotFoundException(NOT_FOUND);
        }
        if (existsLoginOrCpf) {
            final String errorMessage = ApplicationMessageEnum.ALREADY_EXISTS
                    .handleMessage("Usuário", "CPF ou LOGIN");
            throw new BusinessException(errorMessage);
        }
        User user = this.modelMapper.map(userParamDto, User.class);
        user.setId(idUser);
        return this.userRepository.save(user);
    }

    public User find(final Long idUser) {
        return this.userRepository.findById(idUser).orElseThrow(() -> new NotFoundException(NOT_FOUND));
    }

    public Long delete(Long idUser) {
        if (!this.userRepository.existsById(idUser)) {
            throw new NotFoundException(NOT_FOUND);
        }
        final boolean existRelationship = this.userRepository.existRelationship(idUser).orElse(false);
        if (existRelationship) {
            final User user = this.find(idUser);
            final String message = "O usuário \"" + user.getName() + "\""
                    + " não pode ser excluído porque possui tabelas vinculadas";
            throw new BusinessException(message);
        }
        this.userRepository.deleteById(idUser);
        return idUser;
    }

    public List<User> findAllWithoutPage() {
        return this.userRepository.findAll();
    }
}
