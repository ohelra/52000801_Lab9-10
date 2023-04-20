package midterm.commerce.api.service.implement;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import midterm.commerce.api.model.Cart;
import midterm.commerce.api.repository.CartRepository;
import midterm.commerce.api.service.ICartService;

@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> index() {
        return cartRepository.findAll();
    }

    @Override
    public Cart show(Integer id) {
        return cartRepository.findById(id).orElse(null);
    }

    @Override
    public List<Cart> findCartsByUser(Integer userId) {
        return cartRepository.findByUserId(userId);
    }

    @Override
    public Cart store(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart update(Integer id, Cart cart) {
        return cartRepository.findById(id).map(cartUpdate -> {
            cartUpdate.setQuantity(cart.getQuantity());
            return cartRepository.save(cart);
		}).orElse(null);
    }

    @Override
    public boolean destroy(Integer id) {
        try {
            cartRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
