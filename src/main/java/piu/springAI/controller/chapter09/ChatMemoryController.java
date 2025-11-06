package piu.springAI.controller.chapter09;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter09.ChatMemoryService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class ChatMemoryController {

	private final ChatMemoryService chatMemoryService;

	@PostMapping(
		value = "/chat",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String inMemoryChatMemory(
		@RequestParam("question") String question, HttpSession session
	) {
		String answer = chatMemoryService.chat(question, session.getId());
		return answer;
	}
}
