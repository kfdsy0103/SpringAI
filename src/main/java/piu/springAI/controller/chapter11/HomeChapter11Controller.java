package piu.springAI.controller.chapter11;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeChapter11Controller {

	@GetMapping("/date-time-tools")
	public String dateTimeTools() {
		return "chapter11/date-time-tools";
	}

	@GetMapping("/heating-system-tools")
	public String heatingSystemTools() {
		return "chapter11/heating-system-tools";
	}

	@GetMapping("/recommend-movie-tools")
	public String recommendMovieTools() {
		return "chapter11/recommend-movie-tools";
	}
}
