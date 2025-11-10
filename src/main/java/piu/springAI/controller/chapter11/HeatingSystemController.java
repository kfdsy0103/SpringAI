package piu.springAI.controller.chapter11;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter11.HeatingSystemService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class HeatingSystemController {

	private final HeatingSystemService heatingSystemService;

	@PostMapping(
		value = "/heating-system-tools",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String heatingSystemTools(@RequestParam("question") String question) {
		String answer = heatingSystemService.chat(question);
		return answer;
	}
}
