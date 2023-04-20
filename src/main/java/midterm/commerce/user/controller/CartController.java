package midterm.commerce.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("userCartController")
public class CartController {

    @RequestMapping(value = "/cart", method = RequestMethod.GET)
    public String index() {
        return "user/cart";
    }
}
