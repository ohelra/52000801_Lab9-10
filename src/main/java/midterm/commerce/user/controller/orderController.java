package midterm.commerce.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("userOrderController")
public class orderController {
    // @RequestMapping(value = "/order/{billId}", method = RequestMethod.GET)
    // public String index() {
    // return "user/order";
    // }

    // @RequestMapping(value = "/order/{billId}", method = RequestMethod.GET)
    // public String checkBill(@PathVariable("billId") String billId, Model model) {
    // model.addAttribute("billId", billId);
    // return "user/order";
    // }

    @RequestMapping(value = "/order", method = RequestMethod.GET)
    public String index(@RequestParam(value = "billId", required = false) String billId, Model model) {
        // Truyền giá trị billId vào model để sử dụng trong view hoặc xử lý logic
        if (billId != null) {
            model.addAttribute("billId", billId);
        }
        // Thực hiện xử lý logic của phương thức

        return "user/order";
    }
}
