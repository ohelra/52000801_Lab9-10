package midterm.commerce.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import midterm.commerce.api.model.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findProductsByCategory(@Param("categoryId") Integer categoryId);

    @Query("SELECT p FROM Product p ORDER BY p.price ASC")
    List<Product> sortProductsByPriceAsc();

    @Query("SELECT p FROM Product p ORDER BY p.price DESC")
    List<Product> sortProductsByPriceDesc();

    @Query("SELECT p FROM Product p WHERE p.color = :color")
    List<Product> findProductsByColor(@Param("color") String color);

    @Query("SELECT p FROM Product p WHERE p.trademark = :trademark")
    List<Product> findProductsByTrademark(@Param("trademark") String trademark);
}
