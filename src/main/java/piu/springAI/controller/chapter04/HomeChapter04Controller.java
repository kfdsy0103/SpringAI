package piu.springAI.controller.chapter04;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeChapter04Controller {

	@GetMapping("/list-output-converter")
	public String listOutputConverter() {
		return "chapter04/list-output-converter";
	}

	@GetMapping("/bean-output-converter")
	public String beanOutputConverter() {
		return "chapter04/bean-output-converter";
	}

	@GetMapping("/generic-bean-output-converter")
	public String genericBeanOutputConverter() {
		return "chapter04/generic-bean-output-converter";
	}
}
