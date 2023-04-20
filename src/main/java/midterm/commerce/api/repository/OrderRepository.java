package midterm.commerce.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import midterm.commerce.api.model.Order;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    Order findByBillId(String billId);
}
