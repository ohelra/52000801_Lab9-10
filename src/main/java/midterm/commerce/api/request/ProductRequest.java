package midterm.commerce.api.request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartFile;

import midterm.commerce.api.model.Product;
public class ProductRequest {
    public static ProductRequest getInstance() {
        return new ProductRequest();
    }

    public Map<String, List<String>> validateProduct(Product product, List<Product> products, MultipartFile imageFile,
            boolean isStore) {
        Map<String, List<String>> errors = new HashMap<>();

        if (product.getName() == null || product.getName().isEmpty()) {
            errors.put("name", List.of("Vui lòng không bỏ trống tên sản phẩm!"));
        } else if (product.getColor() == null || product.getColor().isEmpty()) {
            errors.put("color", List.of("Vui lòng không bỏ trống màu sản phẩm!"));
        } else if (products.stream().anyMatch(p -> {
            return isStore
                    ? p.getName().equals(product.getName()) && p.getColor().equals(product.getColor())
                    : p.getId() != product.getId() && p.getName().equals(product.getName())
                            && p.getColor().equals(product.getColor());
        })) {
            errors.put("color", List.of("Màu sản phẩm đã tồn tại!"));
        }

        if (product.getDescription() == null || product.getDescription().isEmpty()) {
            errors.put("description", List.of("Vui lòng không bỏ trống mô tả sản phẩm!"));
        }

        if (product.getPrice() == null) {
            errors.put("price", List.of("Vui lòng không bỏ trống giá sản phẩm!"));
        }

        if (isStore) {
            if (imageFile == null || imageFile.isEmpty()) {
                errors.put("image", List.of("Vui lòng chọn ảnh cho sản phẩm!"));
            } else {
                String fileName = imageFile.getOriginalFilename();
                String extension = FilenameUtils.getExtension(fileName);
                if (extension == null || !extension.matches("(?i)(jpg|jpeg|png|gif|bmp)")) {
                    errors.put("image", List.of("File không phải định dạng ảnh!"));
                }
            }
        }

        if (product.getTrademark() == null || product.getTrademark().isEmpty()) {
            errors.put("trademark", List.of("Vui lòng không bỏ trống thương hiệu sản phẩm!"));
        }

        if (product.getCategory() == null) {
            errors.put("category", List.of("Thể loại sản phẩm không hợp lệ!"));
        }

        return errors;
    }
}