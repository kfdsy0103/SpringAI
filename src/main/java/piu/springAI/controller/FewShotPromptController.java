package piu.springAI.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.FewShotPromptService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class FewShotPromptController {

	private final FewShotPromptService fewShotPromptService;

	@PostMapping(
		value = "/few-shot-prompt",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public String fewShotPrompt(@RequestParam("order") String order) {
		String json = fewShotPromptService.fewShotPrompt(order);
		return json;
	}
}
