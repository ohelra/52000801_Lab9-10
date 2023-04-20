package midterm.commerce.api.controller;

import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import midterm.commerce.api.model.Order;
import midterm.commerce.api.request.OrderRequest;
import midterm.commerce.api.response.OrderResponse;
import midterm.commerce.api.service.implement.OrderServiceImpl;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private OrderServiceImpl orderServiceImpl;

    @GetMapping
    public OrderResponse<List<Order>> index() {
        List<Order> order = orderServiceImpl.index();
        return order.isEmpty()
                ? new OrderResponse<>(false, "Hóa đơn sản phẩm hiện tại đang trống!")
                : new OrderResponse<>(true, "", order);
    }

    @GetMapping("{id}")
    public OrderResponse<Order> show(@PathVariable("id") String id) {
        Order order = orderServiceImpl.findByBillId(id);
        return order == null
                ? new OrderResponse<>(false, "Không tìm thấy hóa đơn sản phẩm!")
                : new OrderResponse<>(true, "", order);
    }

    @PostMapping
    public OrderResponse<Order> store(@RequestBody Order order) {
        OrderRequest validator = OrderRequest.getInstance();
        Map<String, List<String>> errors = validator.validateOrder(order, orderServiceImpl.index());

        if (!errors.isEmpty()) {
            return new OrderResponse<>(false, "Yêu cầu dữ liệu không hợp lệ!", errors);
        }

        order.setOrderDate(LocalDateTime.now());
        Order createdOrder = orderServiceImpl.store(order);
        return createdOrder == null
                ? new OrderResponse<>(false, "Không thể thêm hóa đơn sản phẩm!")
                : new OrderResponse<>(true, "Thêm hóa đơn sản phẩm thành công!", createdOrder);
    }
}
