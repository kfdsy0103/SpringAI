package piu.springAI.controller.chapter09;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeChapter09Controller {

	@GetMapping("/home")
	public String home() {
		return "chapter09/home";
	}
}
