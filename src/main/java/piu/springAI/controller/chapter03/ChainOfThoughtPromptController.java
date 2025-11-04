package piu.springAI.controller.chapter03;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter03.ChainOfThoughtPromptService;
import piu.springAI.service.chapter03.StepBackPromptService;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class ChainOfThoughtPromptController {

	private final StepBackPromptService stepBackPromptService;
	private final ChainOfThoughtPromptService chainOfThoughtPromptService;

	@PostMapping(
		value = "/chain-of-thought",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_NDJSON_VALUE
	)
	public Flux<String> chainOfThoughtPrompt(@RequestParam("question") String question) {
		Flux<String> stringFlux = chainOfThoughtPromptService.chainOfThought(question);
		return stringFlux;
	}
}
