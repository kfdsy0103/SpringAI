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

	@GetMapping("/map-output-converter")
	public String mapOutputConverter() {
		return "chapter04/map-output-converter";
	}

	@GetMapping("/system-message")
	public String systemMessage() {
		return "chapter04/system-message";
	}
}
