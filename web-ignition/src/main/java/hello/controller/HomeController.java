package hello.controller;


import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
class HomeController {
    @RequestMapping("/")
    String index(Model model) {
    	model.addAttribute("when", new Date());
        return "hello";
    }
}