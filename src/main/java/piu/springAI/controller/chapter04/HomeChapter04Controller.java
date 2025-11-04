package piu.springAI.controller.chapter04;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeChapter04Controller {

	@GetMapping("/list-output-converter")
	public String listOutputConverter() {
		return "chapter04/list-output-converter";
	}
}
