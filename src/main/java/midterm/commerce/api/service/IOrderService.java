package midterm.commerce.api.service;

import java.util.List;

import midterm.commerce.api.model.Order;

public interface IOrderService {
    public List<Order> index();

    public Order show(Integer id);

    public Order store(Order order);

    public Order findByBillId(String billId);
}
