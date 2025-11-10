package piu.springAI.controller.chapter11;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter11.DateTimeService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class DateTimeController {

	private final DateTimeService dateTimeService;

	@PostMapping(
		value = "/date-time-tools",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String dateTimeTools(@RequestParam("question") String question) {
		String answer = dateTimeService.chat(question);
		return answer;
	}
}
