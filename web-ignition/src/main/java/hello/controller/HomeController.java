package hello.controller;

import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class HomeController {

	@RequestMapping("/hello.html")
	String hello(final Model model) {
		model.addAttribute("when", new Date());
		return "hello";
	}

	@RequestMapping("/")
	String index(final Model model) {
		model.addAttribute("when", new Date());
		return "hello";
	}

	// Login form
	@RequestMapping("/login")
	public String login(final HttpServletRequest request, @RequestParam final Map<String, String> allRequestParams) {
		System.out.println("" + allRequestParams);
		return "login";
	}

	// Login form with error
	@RequestMapping("/login-error.html")
	public String loginError(final Model model) {
		model.addAttribute("loginError", true);
		return "login.html";
	}

	@RequestMapping("/oops.html")
	void oops(final Model model) {
		throw new IllegalArgumentException("Sample error");
	}
}