package br.com.dnaspecialty.apitest.repository;

import br.com.dnaspecialty.apitest.model.Customer;
import br.com.dnaspecialty.apitest.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT DISTINCT CASE " +
            "WHEN COUNT(o) > 0 THEN true " +
            "ELSE false " +
            "END " +
            "FROM Order o " +
            "JOIN o.product product " +
            "WHERE (product.id = :id) ")
    Optional<Boolean> existRelationship(@Param("id") Long id);

    @Query("SELECT (COUNT(p) > 0) " +
            "FROM Product p " +
            "WHERE (p.name = :name)" +
            "AND   (p.id <> :idProduct OR :idProduct IS NULL)")
    boolean existsByName(@Param("name") String name, @Param("idProduct") Long idProduct);

    @Query("SELECT p " +
            "FROM  Product p " +
            "WHERE (:filter = ''" +
            "   OR (LOWER(p.name) LIKE LOWER(concat('%', :filter, '%'))))")
    Page<Product> findAllByFilter(String filter, Pageable pageable);
}
