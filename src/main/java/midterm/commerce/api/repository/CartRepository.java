package midterm.commerce.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import midterm.commerce.api.model.Cart;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByUserId(Integer userId);
}
