package midterm.commerce.api.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import midterm.commerce.api.model.Order;
import midterm.commerce.api.repository.OrderRepository;
import midterm.commerce.api.service.IOrderService;

@Service
public class OrderServiceImpl implements IOrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> index() {
        return orderRepository.findAll();
    }

    @Override
    public Order show(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order store(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order findByBillId(String billId) {
        return orderRepository.findByBillId(billId);
    }
}
