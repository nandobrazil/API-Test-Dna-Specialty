package br.com.dnaspecialty.apitest.repository;

import br.com.dnaspecialty.apitest.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o " +
            "FROM  Order o " +
            "JOIN  o.customer c " +
            "JOIN  o.user u " +
            "WHERE (:filter = ''" +
            "   OR (LOWER(c.corporateName) LIKE LOWER(concat('%', :filter, '%')))" +
            "   OR (LOWER(c.cnpj) LIKE LOWER(concat('%', :filter, '%')))" +
            "   OR (LOWER(u.name) LIKE LOWER(concat('%', :filter, '%')))" +
            "   OR (LOWER(u.cpf) LIKE LOWER(concat('%', :filter, '%'))))")
    Page<Order> findAllByFilter(String filter, Pageable pageable);
}
