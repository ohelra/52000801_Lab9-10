package midterm.commerce.api.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import midterm.commerce.api.model.Product;
import midterm.commerce.api.request.ProductRequest;
import midterm.commerce.api.response.ProductResponse;
import midterm.commerce.api.service.implement.ProductServiceImpl;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
    @Autowired
    private ProductServiceImpl productServiceImp;

    @GetMapping
    public ProductResponse<List<Product>> index() {
        List<Product> products = productServiceImp.index();
        return products.isEmpty()
                ? new ProductResponse<>(false, "Sản phẩm hiện tại đang trống!")
                : new ProductResponse<>(true, "", products);
    }

    @GetMapping("{id}")
    public ProductResponse<Product> show(@PathVariable("id") Integer id) {
        Product product = productServiceImp.show(id);
        return product == null
                ? new ProductResponse<>(false, "Không tìm thấy sản phẩm!")
                : new ProductResponse<>(true, "", product);
    }

    @PostMapping
    public ProductResponse<Product> store(Product product, @RequestParam("imageFile") MultipartFile imageFile) {
        ProductRequest validator = ProductRequest.getInstance();
        Map<String, List<String>> errors = validator.validateProduct(
                product, productServiceImp.index(), imageFile, true);

        if (!errors.isEmpty()) {
            return new ProductResponse<>(false, "Yêu cầu dữ liệu không hợp lệ!", errors);
        }

        Product createdProduct = productServiceImp.store(product, imageFile);
        return createdProduct == null
                ? new ProductResponse<>(false, "Không thể thêm sản phẩm!")
                : new ProductResponse<>(true, "Thêm sản phẩm thành công!", createdProduct);
    }

    @PutMapping("{id}")
    public ProductResponse<Product> update(@PathVariable("id") Integer id, Product product,
            @RequestParam("imageFile") MultipartFile imageFile) {
        
        product.setId(id);
        ProductRequest validator = ProductRequest.getInstance();
        Map<String, List<String>> errors = validator.validateProduct(
                product, productServiceImp.index(), imageFile, false);

        if (!errors.isEmpty()) {
            return new ProductResponse<>(false, "Yêu cầu dữ liệu không hợp lệ!", errors);
        }

        Product updatedProduct = productServiceImp.update(id, product, imageFile);
        return updatedProduct == null
                ? new ProductResponse<>(false, "Không tìm thấy thể loại sản phẩm!")
                : new ProductResponse<>(true, "Cập nhật thể loại sản phẩm thành công!", updatedProduct);
    }

    @DeleteMapping("{id}")
    public ProductResponse<Boolean> destroy(@PathVariable("id") Integer id) {
        boolean destroyProduct = productServiceImp.destroy(id);
        return destroyProduct
                ? new ProductResponse<>(true, "Xóa thể loại sản phẩm thành công!")
                : new ProductResponse<>(false, "Không tìm thấy thể loại sản phẩm!");
    }

    @GetMapping("find-by-category/{categoryId}")
    public ProductResponse<List<Product>> findProductsByCategory(@PathVariable("categoryId") Integer categoryId) {
        List<Product> products = productServiceImp.findProductsByCategory(categoryId);
        return products.isEmpty()
                ? new ProductResponse<>(false, "Giỏ hàng hiện tại đang trống hoặc người dùng không tồn tại!")
                : new ProductResponse<>(true, "", products);
    }

    @GetMapping("sort-by-price-asc")
    public ProductResponse<List<Product>> sortProductsByPriceAsc() {
        List<Product> products = productServiceImp.sortProductsByPriceAsc();
        return products.isEmpty()
                ? new ProductResponse<>(false, "Giỏ hàng hiện tại đang trống hoặc người dùng không tồn tại!")
                : new ProductResponse<>(true, "", products);
    }

    @GetMapping("sort-by-price-desc")
    public ProductResponse<List<Product>> sortProductsByPriceDesc() {
        List<Product> products = productServiceImp.sortProductsByPriceDesc();
        return products.isEmpty()
                ? new ProductResponse<>(false, "Giỏ hàng hiện tại đang trống hoặc người dùng không tồn tại!")
                : new ProductResponse<>(true, "", products);
    }

    @GetMapping("find-by-color/{color}")
    public ProductResponse<List<Product>> findProductsByCategory(@PathVariable("color") String color) {
        List<Product> products = productServiceImp.findProductsByColor(color);
        return products.isEmpty()
                ? new ProductResponse<>(false, "Giỏ hàng hiện tại đang trống hoặc người dùng không tồn tại!")
                : new ProductResponse<>(true, "", products);
    }

    @GetMapping("find-by-trademark/{trademark}")
    public ProductResponse<List<Product>> findProductsByTrademark(@PathVariable("trademark") String trademark) {
        List<Product> products = productServiceImp.findProductsByTrademark(trademark);
        return products.isEmpty()
                ? new ProductResponse<>(false, "Giỏ hàng hiện tại đang trống hoặc người dùng không tồn tại!")
                : new ProductResponse<>(true, "", products);
    }
}