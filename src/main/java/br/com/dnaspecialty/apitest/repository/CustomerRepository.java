package br.com.dnaspecialty.apitest.repository;

import br.com.dnaspecialty.apitest.model.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT DISTINCT CASE " +
        "WHEN COUNT(o) > 0 THEN true " +
        "ELSE false " +
        "END " +
        "FROM Order o " +
        "JOIN o.customer customer " +
        "WHERE (customer.id = :id) ")
    Optional<Boolean> existRelationship(@Param("id") Long id);

    @Query("SELECT (COUNT(c) > 0) " +
            "FROM Customer c " +
            "WHERE (c.cnpj = :cnpj)" +
            "AND   (c.id <> :id OR :id IS NULL)")
    boolean existsByCnpj(String cnpj, Long id);

    @Query("SELECT  c " +
            "FROM Customer c " +
            "WHERE (:filter = ''" +
            "   OR (LOWER(c.corporateName) LIKE LOWER(concat('%', :filter, '%')))" +
            "   OR (c.cnpj LIKE concat('%', :filter, '%')))")
    Page<Customer> findAllByFilter(String filter, Pageable pageable);
}
