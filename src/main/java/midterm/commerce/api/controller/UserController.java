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

import midterm.commerce.api.model.User;
import midterm.commerce.api.request.UserRequest;
import midterm.commerce.api.response.UserResponse;
import midterm.commerce.api.service.implement.UserServiceImpl;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping
    public UserResponse<List<User>> index() {
        List<User> users = userServiceImpl.index();
        return users.isEmpty()
                ? new UserResponse<>(false, "Tài khoản người dùng hiện tại đang trống!")
                : new UserResponse<>(true, "", users);
    }

    @GetMapping("{id}")
    public UserResponse<User> show(@PathVariable("id") Integer id) {
        User user = userServiceImpl.show(id);
        return user == null
                ? new UserResponse<>(false, "Không tìm thấy tài khoản người dùng!")
                : new UserResponse<>(true, "", user);
    }

    @PostMapping
    public UserResponse<User> store(@RequestBody User user) {
        UserRequest validator = UserRequest.getInstance();
        Map<String, List<String>> errors = validator.validateUser(user, userServiceImpl.index(), true);

        if (!errors.isEmpty()) {
            return new UserResponse<>(false, "Yêu cầu dữ liệu không hợp lệ!", errors);
        }

        User createdUser = userServiceImpl.store(user);
        return createdUser == null
                ? new UserResponse<>(false, "Không thể thêm tài khoản người dùng!")
                : new UserResponse<>(true, "Thêm tài khoản người dùng thành công!", createdUser);
    }

    @PutMapping("{id}")
    public UserResponse<User> update(@PathVariable("id") Integer id, @RequestBody User user) {
        user.setId(id);
        UserRequest validator = UserRequest.getInstance();
        Map<String, List<String>> errors = validator.validateUser(user, userServiceImpl.index(), false);

        if (!errors.isEmpty()) {
            return new UserResponse<>(false, "Yêu cầu dữ liệu không hợp lệ!", errors);
        }

        User updatedUser = userServiceImpl.update(id, user);
        return updatedUser == null
                ? new UserResponse<>(false, "Không tìm thấy tài khoản người dùng!")
                : new UserResponse<>(true, "Cập nhật tài khoản người dùng thành công!", updatedUser);
    }

    @DeleteMapping("{id}")
    public UserResponse<Boolean> destroy(@PathVariable("id") Integer id) {
        boolean destroyUser = userServiceImpl.destroy(id);
        return destroyUser
                ? new UserResponse<>(true, "Xóa tài khoản người dùng thành công!")
                : new UserResponse<>(false, "Không tìm thấy tài khoản người dùng!");
    }
}
