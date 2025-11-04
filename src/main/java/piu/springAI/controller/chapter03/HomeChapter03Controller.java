package piu.springAI.controller.chapter03;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeChapter03Controller {

	@GetMapping("/")
	public String home() {
		return "chapter03/home";
	}

	@GetMapping("/chat-model")
	public String chatModel() {
		return "chapter03/chat-model";
	}

	@GetMapping("/chat-model-stream")
	public String chatModelStream() {
		return "chapter03/chat-model-stream";
	}

	@GetMapping("/chat-prompt-template")
	public String chatPromptTemplate() {
		return "chapter03/chat-prompt-template";
	}

	@GetMapping("/chat-multi-messages")
	public String chatMultiMessageTemplate() {
		return "chapter03/chat-multi-messages";
	}

	@GetMapping("/zero-shot-prompt")
	public String zeroShotPrompt() {
		return "chapter03/zero-shot-prompt";
	}

	@GetMapping("/few-shot-prompt")
	public String fewShotPrompt() {
		return "chapter03/few-shot-prompt";
	}

	@GetMapping("/role-assignment")
	public String roleAssignment() {
		return "chapter03/role-assignment";
	}

	@GetMapping("/step-back-prompt")
	public String stepBackPrompt() {
		return "chapter03/step-back-prompt";
	}

	@GetMapping("/chain-of-thought")
	public String chainOfThought() {
		return "chapter03/chain-of-thought";
	}

	@GetMapping("/self-consistency")
	public String selfConsistency() {
		return "chapter03/self-consistency";
	}

}
