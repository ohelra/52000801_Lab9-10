package midterm.commerce.api.request;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;

import java.util.HashMap;

public class CartRequest {
    public static CartRequest getInstance() {
        return new CartRequest();
    }

    public Map<String, List<String>> validateCart(JsonNode requestBody, boolean isStore) {
        Map<String, List<String>> errors = new HashMap<>();

        if (requestBody.isEmpty()) {
            errors.put("quantity", List.of("Vui lòng không bỏ trống số lượng sản phẩm!"));
           if (isStore) {
                errors.put("product", List.of("Vui lòng không bỏ trống sản phẩm!"));
                errors.put("user", List.of("Vui lòng không bỏ trống người dùng!"));
           }
        } else {
            if (isStore) {
                JsonNode productNode = requestBody.get("product");
                if (productNode != null && productNode.isInt()) {
                    // Integer productId = productNode.intValue();
                    // Product product = productServiceImpl.show(productId);
                    // if (product == null) {
                    //     errors.put("product", List.of("Không tìm thấy sản phẩm hoặc sản phẩm không hợp lệ!"));
                    // }
                } else {
                    errors.put("product", List.of("Vui lòng không bỏ trống sản phẩm!"));
                }
    
                JsonNode userNode = requestBody.get("user");
                if (userNode != null && userNode.isInt()) {
                    // Integer userId = userNode.intValue();
                    // User user = userServiceImpl.show(userId);
                    // if (user == null) {
                    //     errors.put("user", List.of("Không tìm thấy người dùng hoặc người dùng không hợp lệ!"));
                    // }
                } else {
                    errors.put("user", List.of("Vui lòng không bỏ trống người dùng hoặc kiểu dữ liệu không hợp lệ!"));
                }
            }

            JsonNode quantityNode = requestBody.get("quantity");
            if (quantityNode != null && quantityNode.isInt()) {
                Integer quantity = quantityNode.intValue();
                if (quantity != 1 && quantity != 2) {
                    errors.put("quantity", List.of("Số lượng chỉ cho phép bán là 1 đến 2 sản phẩm!"));
                }
            } else {
                errors.put("quantity", List.of("Vui lòng không bỏ trống số lượng sản phẩm!"));
            }
        }

        return errors;
    }
}
