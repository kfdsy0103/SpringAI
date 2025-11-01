package piu.springAI.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.AiService;
import piu.springAI.service.AiServicePromptTemplate;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/ai")
public class AiController {

	private final AiService aiService;
	private final AiServicePromptTemplate aiServicePromptTemplate;

	@PostMapping(
		value = "/chat-model",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String chatModel(@RequestParam("question") String question) {
		String answerText = aiService.generateText(question);
		return answerText;
	}

	@PostMapping(
		value = "/chat-model-stream",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_NDJSON_VALUE
	)
	public Flux<String> chatModelStream(@RequestParam("question") String question) {
		Flux<String> answer = aiService.generateStreamText(question);
		return answer;
	}

	@PostMapping(
		value = "/chat-prompt-template",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_NDJSON_VALUE
	)
	public Flux<String> promptTemplate(
		@RequestParam("statement") String statement,
		@RequestParam("language") String language
	) {
		// Flux<String> response = aiServicePromptTemplate.promptTemplate1(statement, language);
		Flux<String> response = aiServicePromptTemplate.promptTemplate2(statement, language);
		return response;
	}
}
