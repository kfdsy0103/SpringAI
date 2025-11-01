package piu.springAI.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String home() {
		return "home";
	}

	@GetMapping("/chat-model")
	public String chatModel() {
		return "chat-model";
	}

	@GetMapping("/chat-model-stream")
	public String chatModelStream() {
		return "chat-model-stream";
	}

	@GetMapping("/chat-prompt-template")
	public String chatPromptTemplate() {
		return "chat-prompt-template";
	}

	@GetMapping("/chat-multi-messages")
	public String chatMultiMessageTemplate() {
		return "chat-multi-messages";
	}
}
