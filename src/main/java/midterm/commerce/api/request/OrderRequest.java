package midterm.commerce.api.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import midterm.commerce.api.model.Order;

public class OrderRequest {
    public static OrderRequest getInstance() {
        return new OrderRequest();
    }

    public Map<String, List<String>> validateOrder(Order order, List<Order> orders) {
        Map<String, List<String>> errors = new HashMap<>();

        if (order.getName() == null || order.getName().isEmpty()) {
            errors.put("name", List.of("Vui lòng không bỏ trống Họ và tên!"));
        }
        if (order.getBillId() == null || order.getBillId().isEmpty()) {
            errors.put("billId", List.of("Chưa có bill id"));
        }
        if (order.getAddress() == null || order.getAddress().isEmpty()) {
            errors.put("address", List.of("Vui lòng không bỏ trống Địa chỉ!"));
        }
        if (order.getCity() == null || order.getCity().isEmpty()) {
            errors.put("city", List.of("Vui lòng không bỏ trống Tỉnh/Thành phố!"));
        }
        if (order.getPhone() == null || order.getPhone().isEmpty()) {
            errors.put("phone", List.of("Vui lòng không bỏ trống Số điện thoại!"));
        }
        if (order.getEmail() == null || order.getEmail().isEmpty()) {
            errors.put("email", List.of("Vui lòng không bỏ trống email!"));
        }
        if (order.getCart() == null || order.getCart().isEmpty()) {
            errors.put("cart", List.of("Vui lòng không bỏ trống sản phẩm trong giỏ hàng!"));
        }

        return errors;
    }
}
