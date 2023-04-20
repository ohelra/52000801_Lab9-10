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

import midterm.commerce.api.model.Category;
import midterm.commerce.api.request.CategoryRequest;
import midterm.commerce.api.response.CategoryResponse;
import midterm.commerce.api.service.implement.CategoryServiceImpl;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {
    @Autowired
    private CategoryServiceImpl categoryServiceImp;

    @GetMapping
    public CategoryResponse<List<Category>> index() {
        List<Category> categories = categoryServiceImp.index();
        return categories.isEmpty()
                ? new CategoryResponse<>(false, "Thể loại sản phẩm hiện tại đang trống!")
                : new CategoryResponse<>(true, "", categories);
    }

    @GetMapping("{id}")
    public CategoryResponse<Category> show(@PathVariable("id") Integer id) {
        Category category = categoryServiceImp.show(id);
        return category == null
                ? new CategoryResponse<>(false, "Không tìm thấy thể loại sản phẩm!")
                : new CategoryResponse<>(true, "", category);
    }

    @PostMapping
    public CategoryResponse<Category> store(@RequestBody Category category) {
        CategoryRequest validator = CategoryRequest.getInstance();
        Map<String, List<String>> errors = validator.validateCategory(category, categoryServiceImp.index(), true);

        if (!errors.isEmpty()) {
            return new CategoryResponse<>(false, "Yêu cầu dữ liệu không hợp lệ!", errors);
        }

        Category createdCategory = categoryServiceImp.store(category);
        return createdCategory == null
                ? new CategoryResponse<>(false, "Không thể thêm thể loại sản phẩm!")
                : new CategoryResponse<>(true, "Thêm thể loại sản phẩm thành công!", createdCategory);
    }

    @PutMapping("{id}")
    public CategoryResponse<Category> update(@PathVariable("id") Integer id, @RequestBody Category category) {
        category.setId(id);
        CategoryRequest validator = CategoryRequest.getInstance();
        Map<String, List<String>> errors = validator.validateCategory(category, categoryServiceImp.index(), false);

        if (!errors.isEmpty()) {
            return new CategoryResponse<>(false, "Yêu cầu dữ liệu không hợp lệ!", errors);
        }

        Category updatedCategory = categoryServiceImp.update(id, category);
        return updatedCategory == null
                ? new CategoryResponse<>(false, "Không tìm thấy thể loại sản phẩm!")
                : new CategoryResponse<>(true, "Cập nhật thể loại sản phẩm thành công!", updatedCategory);
    }

    @DeleteMapping("{id}")
    public CategoryResponse<Boolean> destroy(@PathVariable("id") Integer id) {
        boolean destroyCategory = categoryServiceImp.destroy(id);
        return destroyCategory
                ? new CategoryResponse<>(true, "Xóa thể loại sản phẩm thành công!")
                : new CategoryResponse<>(false, "Không tìm thấy thể loại sản phẩm!");
    }
}
