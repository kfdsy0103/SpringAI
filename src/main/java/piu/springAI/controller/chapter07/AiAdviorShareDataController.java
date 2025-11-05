package piu.springAI.controller.chapter07;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter07.AiAdvisorShareDataService;
import reactor.core.publisher.Flux;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiAdviorShareDataController {

	private final AiAdvisorShareDataService aiAdvisorShareDataService;

	@PostMapping(
		value = "/advisor-context",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_NDJSON_VALUE
	)
	public Flux<String> advisorContext(@RequestParam("question") String question) {
		Flux<String> stringFlux = aiAdvisorShareDataService.advisorContext(question);
		return stringFlux;
	}
}
