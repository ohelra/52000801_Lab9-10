package midterm.commerce.api.request;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import midterm.commerce.api.model.Category;

public class CategoryRequest {
    public static CategoryRequest getInstance() {
        return new CategoryRequest();
    }

    public Map<String, List<String>> validateCategory(Category category, List<Category> categories, boolean isStore) {
        Map<String, List<String>> errors = new HashMap<>();

        if (category.getName() == null || category.getName().isEmpty()) {
            errors.put("name", List.of("Vui lòng không bỏ trống tên thể loại sản phẩm!"));
        } else if (categories.stream().anyMatch(c -> {
            return isStore
                    ? c.getName().equals(category.getName())
                    : c.getId() != category.getId() && c.getName().equals(category.getName());
        })) {
            errors.put("name", List.of("Tên thể loại sản phẩm đã tồn tại!"));
        }

        if (category.getDescription() == null || category.getDescription().isEmpty()) {
            errors.put("description", List.of("Vui lòng không bỏ trống mô tả thể loại sản phẩm!"));
        }

        return errors;
    }
}
