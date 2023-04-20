package midterm.commerce.api.service;

import java.util.List;

import midterm.commerce.api.model.Cart;

public interface ICartService {
    public List<Cart> index();

    public Cart show(Integer id);

    public List<Cart> findCartsByUser(Integer userId);

    public Cart store(Cart cart);

    public Cart update(Integer id, Cart cart);

    public boolean destroy(Integer id);
}
