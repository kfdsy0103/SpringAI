package piu.springAI.controller.chapter11;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeChapter11Controller {

	@GetMapping("/date-time-tools")
	public String dateTimeTools() {
		return "chapter11/date-time-tools";
	}
}
