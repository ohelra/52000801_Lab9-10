package midterm.commerce.api.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import midterm.commerce.api.model.User;
import midterm.commerce.api.repository.UserRepository;
import midterm.commerce.api.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> index() {
        return userRepository.findAll();
    }

    @Override
    public User show(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User store(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, User user) {
        return userRepository.findById(id).map(userUpdate -> {
			userUpdate.setName(user.getName());
            userUpdate.setUsername(user.getUsername());
            userUpdate.setEmail(user.getEmail());
            userUpdate.setPhone(user.getPhone());
            userUpdate.setAddress(user.getAddress());
            return userRepository.save(userUpdate);
		}).orElse(null);
    }

    @Override
    public boolean destroy(Integer id) {
        User userDestroy = userRepository.findById(id).orElse(null);
		if (userDestroy != null) {
			userDestroy.setActive(0);
			return userRepository.save(userDestroy) != null;
		}
		return false;
    }
    
}
