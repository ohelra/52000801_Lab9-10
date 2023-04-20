package midterm.commerce.api.service;

import java.util.List;

import midterm.commerce.api.model.User;

public interface IUserService {
    public List<User> index();

    public User show(Integer id);

    public User store(User user);

    public User update(Integer id, User user);

    public boolean destroy(Integer id);
}
