package piu.springAI.controller.chapter10;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeChapter10Controller {

	@GetMapping("/txt-pdf-word-etl")
	public String transformedDocuments() {
		return "chapter10/txt-pdf-word-etl";
	}

	@GetMapping("/rag")
	public String rag() {
		return "chapter10/rag";
	}
}
