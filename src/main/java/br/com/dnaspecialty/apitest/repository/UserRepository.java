package br.com.dnaspecialty.apitest.repository;

import br.com.dnaspecialty.apitest.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT DISTINCT CASE " +
            "WHEN COUNT(o) > 0 THEN true " +
            "ELSE false " +
            "END " +
            "FROM Order o " +
            "JOIN o.user user " +
            "WHERE (user.id = :id) ")
    Optional<Boolean> existRelationship(@Param("id") Long id);
    @Query("SELECT (COUNT(u) > 0) " +
            "FROM User u " +
            "WHERE (u.cpf = :cpf " +
            "OR  u.login = :login) " +
            "AND (u.id <> :idUser OR :idUser IS NULL)")
    boolean existsByCpfOrLogin(@Param("cpf") String cpf, @Param("login") String login, @Param("idUser") Long idUser);

    @Query("SELECT u " +
            "FROM  User u " +
            "WHERE (:filter = ''" +
            "   OR (LOWER(u.name) LIKE LOWER(concat('%', :filter, '%')))" +
            "   OR (LOWER(u.login) LIKE LOWER(concat('%', :filter, '%')))" +
            "   OR (LOWER(u.cpf) LIKE LOWER(concat('%', :filter, '%'))))")
    Page<User> findAllByFilter(String filter, Pageable pageable);

}
