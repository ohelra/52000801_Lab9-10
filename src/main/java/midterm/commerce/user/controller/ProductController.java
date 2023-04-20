package midterm.commerce.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("userProductController")
public class ProductController {

    @RequestMapping(value = "/product/{id}", method = RequestMethod.GET)
    public String index() {
        return "user/product-details";
    }
}
