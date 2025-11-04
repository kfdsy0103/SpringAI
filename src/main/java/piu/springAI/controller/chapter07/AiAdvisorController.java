package piu.springAI.controller.chapter07;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter07.AiAdvisorService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class AiAdvisorController {

	private final AiAdvisorService aiService;

	@PostMapping(
		value = "/advisor-chain",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String advisorChain(@RequestParam("question") String question) {
		String response = aiService.advisorChain1(question);
		return response;
	}
}
