package piu.springAI.controller.chapter10;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter10.RAGService2;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class RAGController {

	private final RAGService2 ragService;

	@PostMapping(
		value = "/compression-query-transformer",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String compressionQueryTransformer(
		@RequestParam("question") String question,
		@RequestParam(value = "score", defaultValue = "0.0") double score,
		@RequestParam("source") String source,
		HttpSession httpSession
	) {
		String answer = ragService.chatWithCompression(question, score, source, httpSession.getId());
		return answer;
	}
}
