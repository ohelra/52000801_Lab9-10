package midterm.commerce.api.request;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import midterm.commerce.api.model.User;

public class UserRequest {
    public static UserRequest getInstance() {
        return new UserRequest();
    }

    public Map<String, List<String>> validateUser(User user, List<User> users, boolean isStore) {
        Map<String, List<String>> errors = new HashMap<>();

        if (user.getName() == null || user.getName().isEmpty()) {
            errors.put("password", List.of("Vui lòng không bỏ trống Họ và tên!"));
        }

        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            errors.put("username", List.of("Vui lòng không bỏ trống tên người dùng!"));
        } else if (users.stream().anyMatch(c -> {
            return isStore
                    ? c.getUsername().equals(user.getUsername())
                    : c.getId() != user.getId() && c.getUsername().equals(user.getUsername());
        })) {
            errors.put("username", List.of("Tên người dùng đã tồn tại!"));
        }

        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            errors.put("email", List.of("Vui lòng không bỏ trống Email!"));
        } else if (users.stream().anyMatch(c -> {
            return isStore
                    ? c.getEmail().equals(user.getEmail())
                    : c.getId() != user.getId() && c.getEmail().equals(user.getEmail());
        })) {
            errors.put("email", List.of("Email đã tồn tại!"));
        }

        if (user.getPhone() == null || user.getPhone().isEmpty()) {
            errors.put("phone", List.of("Vui lòng không bỏ trống Số điện thoại!"));
        } else if (users.stream().anyMatch(c -> {
            return isStore
                    ? c.getPhone().equals(user.getPhone())
                    : c.getId() != user.getId() && c.getPhone().equals(user.getPhone());
        })) {
            errors.put("phone", List.of("Số điện thoại đã tồn tại!"));
        }

        if (user.getAddress() == null || user.getAddress().isEmpty()) {
            errors.put("address", List.of("Vui lòng không bỏ trống Địa chỉ!"));
        } else if (users.stream().anyMatch(c -> {
            return isStore
                    ? c.getAddress().equals(user.getAddress())
                    : c.getId() != user.getId() && c.getAddress().equals(user.getAddress());
        })) {
            errors.put("address", List.of("Địa chỉ đã tồn tại!"));
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            errors.put("password", List.of("Vui lòng không bỏ trống mật khẩu!"));
        }

        return errors;
    }
}
