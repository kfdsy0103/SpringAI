package piu.springAI.controller.chapter03;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter03.AiDefaultService;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/ai")
public class AiDefaultController {

	private final AiDefaultService aiDefaultService;

	@PostMapping(
		value = "/default-method",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_NDJSON_VALUE
	)
	public Flux<String> defaultMethod(@RequestParam("question") String question) {
		Flux<String> response = aiDefaultService.defaultMethod(question);
		return response;
	}
}
