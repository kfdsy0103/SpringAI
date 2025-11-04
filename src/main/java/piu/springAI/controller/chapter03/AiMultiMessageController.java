package piu.springAI.controller.chapter03;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ai.chat.messages.Message;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter03.AiMultiMessageService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/ai")
public class AiMultiMessageController {

	private final AiMultiMessageService aiMultiMessageService;

	@PostMapping(
		value = "/multi-messages",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String multiMessages(
		@RequestParam("question") String question,
		HttpSession session
	) {
		List<Message> chatMemory = (List<Message>)session.getAttribute(
			"chatMemory");
		if (chatMemory == null) {
			chatMemory = new ArrayList<>();
			session.setAttribute("chatMemory", chatMemory);
		}
		String answer = aiMultiMessageService.multiMessage(question, chatMemory);
		return answer;
	}
}
