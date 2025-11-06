package piu.springAI.controller.chapter08;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeChapter08Controller {

	@GetMapping("/text-embedding")
	public String textEmbedding() {
		return "chapter08/text-embedding";
	}

	@GetMapping("/add-document")
	public String addDocument() {
		return "chapter08/add-document";
	}

	@GetMapping("/search-document-1")
	public String searchDocument1() {
		return "chapter08/search-document-1";
	}
}
