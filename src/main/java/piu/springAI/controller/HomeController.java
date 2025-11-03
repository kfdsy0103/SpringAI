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

	@GetMapping("/zero-shot-prompt")
	public String zeroShotPrompt() {
		return "zero-shot-prompt";
	}

	@GetMapping("/few-shot-prompt")
	public String fewShotPrompt() {
		return "few-shot-prompt";
	}

	@GetMapping("/role-assignment")
	public String roleAssignment() {
		return "role-assignment";
	}

	@GetMapping("/step-back-prompt")
	public String stepBackPrompt() {
		return "step-back-prompt";
	}

	@GetMapping("/chain-of-thought")
	public String chainOfThought() {
		return "chain-of-thought";
	}

	@GetMapping("/self-consistency")
	public String selfConsistency() {
		return "self-consistency";
	}
}
