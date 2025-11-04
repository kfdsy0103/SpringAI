package piu.springAI.controller.chapter03;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter03.SelfConsistencyService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class SelfConsistencyController {

	private final SelfConsistencyService selfConsistencyService;

	@PostMapping(
		value = "/self-consistency",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String selfConsistency(@RequestParam("content") String content) {
		String answer = selfConsistencyService.selfConsistency(content);
		return answer;
	}
}
