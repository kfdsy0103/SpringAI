package piu.springAI.controller.chapter11;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter11.RecommandMovieService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class RecommandMovieController {

	private final RecommandMovieService recommandMovieService;

	@PostMapping(
		value = "/recommend-movie-tools",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String recommendMovieTools(@RequestParam("question") String question) {
		String answer = recommandMovieService.chat(question);
		return answer;
	}

	@PostMapping(
		value = "/exception-handling",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String exceptionHandling(@RequestParam("question") String question) {
		String answer = recommandMovieService.chatException(question);
		return answer;
	}
}
