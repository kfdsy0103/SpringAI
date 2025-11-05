package piu.springAI.controller.chapter08;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter08.EmbeddingService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class EmbeddingController {

	private final EmbeddingService embeddingService;

	@PostMapping(
		value = "/text-embedding",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String textEmbedding(@RequestParam("question") String question) {
		embeddingService.textEmbedding(question);
		return "콘솔 출력을 확인하세요.";
	}

	@PostMapping(
		value = "/add-document",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String addDocument(@RequestParam("question") String question) {
		embeddingService.addDocument();
		return "벡터 DB에 저장되었습니다.";
	}
}