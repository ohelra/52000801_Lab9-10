package midterm.commerce.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller("useHomeController")
public class HomeController {
    
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String index() {
        return "user/index";
    }
}
