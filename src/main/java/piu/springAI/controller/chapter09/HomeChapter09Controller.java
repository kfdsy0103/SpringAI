package piu.springAI.controller.chapter09;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeChapter09Controller {

	@GetMapping("/in-memory-home")
	public String home() {
		return "chapter09/in-memory-home";
	}

	@GetMapping("/vector-home")
	public String vector() {
		return "chapter09/vector-home";
	}

	@GetMapping("/jdbc-home")
	public String jdbc() {
		return "chapter09/jdbc-home";
	}
}
