package piu.springAI.controller.chapter3;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter3.ZeroShotPromptService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("/ai")
public class ZeroShotPromptController {

	private final ZeroShotPromptService aiZeroShotPromptService;

	@PostMapping(
		value = "/zero-shot-prompt",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String zeroShotPrompt(@RequestParam("review") String review) {
		String reviewSentiment = aiZeroShotPromptService.zeroShotPrompt(review);
		return reviewSentiment;
	}
}
