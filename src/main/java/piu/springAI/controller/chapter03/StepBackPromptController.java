package piu.springAI.controller.chapter03;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter03.StepBackPromptService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class StepBackPromptController {

	private final StepBackPromptService stepBackPromptService;

	@PostMapping(
		value = "/step-back-prompt",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String stepBackPrompt(@RequestParam("question") String question) throws JsonProcessingException {
		String answer = stepBackPromptService.stepBackPrompt(question);
		return answer;
	}
}
