package piu.springAI.controller.chapter07;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeChapter07Controller {

	@GetMapping("/advisor-chain")
	public String advisorChain() {
		return "chapter07/advisor-chain";
	}

	@GetMapping("/advisor-context")
	public String advisorContext() {
		return "chapter07/advisor-context";
	}
}
