package midterm.commerce.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import midterm.commerce.api.model.Cart;
import midterm.commerce.api.model.Product;
import midterm.commerce.api.model.User;
import midterm.commerce.api.repository.CartRepository;
import midterm.commerce.api.repository.ProductRepository;
import midterm.commerce.api.repository.UserRepository;
import midterm.commerce.api.request.CartRequest;
import midterm.commerce.api.response.CartResponse;
import midterm.commerce.api.service.implement.CartServiceImpl;

@RestController
@RequestMapping("api/v1/cart")
public class CartController {
    @Autowired
    private CartServiceImpl cartServiceImpl;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @GetMapping
    public CartResponse<List<Cart>> index() {
        List<Cart> carts = cartServiceImpl.index();
        return carts.isEmpty()
                ? new CartResponse<>(false, "Giỏ hàng hiện tại đang trống!")
                : new CartResponse<>(true, "", carts);
    }

    @GetMapping("{id}")
    public CartResponse<Cart> show(@PathVariable("id") Integer id) {
        Cart cart = cartServiceImpl.show(id);
        return cart == null
                ? new CartResponse<>(false, "Sản phẩm trong giỏ hàng không tồn tại!")
                : new CartResponse<>(true, "", cart);
    }

    @GetMapping("find-by-user/{userId}")
    public CartResponse<List<Cart>> findCartsByUser(@PathVariable("userId") Integer userId) {
        List<Cart> carts = cartServiceImpl.findCartsByUser(userId);
        return carts.isEmpty()
                ? new CartResponse<>(false, "Giỏ hàng hiện tại đang trống hoặc người dùng không tồn tại!")
                : new CartResponse<>(true, "", carts);
    }

    @PostMapping
    public CartResponse<Cart> store(@RequestBody JsonNode requestBody) {
        CartRequest validator = CartRequest.getInstance();
        Map<String, List<String>> errors = validator.validateCart(requestBody, true);

        JsonNode productNode = requestBody.get("product");
        JsonNode userNode = requestBody.get("user");
        JsonNode quantityNode = requestBody.get("quantity");

        Product product = productRepository.findById(productNode.asInt()).orElse(null);
        User user = userRepository.findById(userNode.asInt()).orElse(null);

        Cart cart = new Cart();
        cart.setProduct(product);
        cart.setUser(user);
        cart.setQuantity(quantityNode.asInt());

        if (product == null) {
            errors.put("product", List.of("Không tìm thấy sản phẩm hoặc sản phẩm không hợp lệ!"));
        }
        if (user == null) {
            errors.put("user", List.of("Không tìm thấy người dùng hoặc người dùng không hợp lệ!"));
        }
        if (product != null && user != null) {
            for (Cart existingCart : cartServiceImpl.index()) {
                if (existingCart.getUser().getId() == cart.getUser().getId() &&
                        existingCart.getProduct().getId() == cart.getProduct().getId()) {
                    errors.put("product", List.of("Sản phẩm đã tồn tại trong giỏ hàng của bạn!"));
                    break;
                }
            }
        }
        if (!errors.isEmpty()) {
            return new CartResponse<>(false, "Yêu cầu dữ liệu không hợp lệ!", errors);
        }

        Cart createCart = cartServiceImpl.store(cart);
        return createCart == null
                ? new CartResponse<>(false, "Không thể thêm thể loại sản phẩm!")
                : new CartResponse<>(true, "Thêm thể loại sản phẩm thành công!", createCart);
    }

    @PutMapping("{id}")
    public CartResponse<Cart> update(@PathVariable("id") Integer id, @RequestBody JsonNode requestBody) {
        CartRequest validator = CartRequest.getInstance();
        Map<String, List<String>> errors = validator.validateCart(requestBody, false);

        if (!errors.isEmpty()) {
            return new CartResponse<>(false, "Yêu cầu dữ liệu không hợp lệ!", errors);
        }

        JsonNode quantityNode = requestBody.get("quantity");
        Cart cart = cartRepository.findById(id).orElse(null);
        if (cart == null) {
            return new CartResponse<>(false, "Không tìm thấy thể loại sản phẩm!");
        }

        cart.setQuantity(quantityNode.asInt());

        Cart updatedCart = cartServiceImpl.update(id, cart);
        return updatedCart == null
                ? new CartResponse<>(false, "Không tìm thấy thể loại sản phẩm!")
                : new CartResponse<>(true, "Cập nhật thể loại sản phẩm thành công!", updatedCart);
    }

    @DeleteMapping("{id}")
    public CartResponse<Boolean> destroy(@PathVariable("id") Integer id) {
        boolean destroyCart = cartServiceImpl.destroy(id);
        return destroyCart
                ? new CartResponse<>(true, "Xóa sản phẩm trong giỏ hàng thành công!")
                : new CartResponse<>(false, "Không tìm thấy sản phẩm trong giỏ hàng!");
    }
}
