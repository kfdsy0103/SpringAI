package piu.springAI.controller.chapter04;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter04.WithSystemService;
import piu.springAI.service.chapter04.dto.ReviewClassification;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class WithSystemController {

	private final WithSystemService withSystemService;

	@PostMapping(
		value = "/system-message",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public ReviewClassification beanOutputConverter(@RequestParam("review") String review) {
		ReviewClassification reviewClassification = withSystemService.classifyReview(review);
		return reviewClassification;
	}
}
