package com.a23.server.templatecontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class TemplateController {

	@GetMapping("login")
	public String loginPage() {
		return "login";
	}

	@GetMapping("main")
	public String main() {
		return "main";
	}

}
