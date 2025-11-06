package piu.springAI.controller.chapter08;

import java.util.List;

import org.springframework.ai.document.Document;
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

	@PostMapping(
		value = "/search-document-1",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String searchDocument1(@RequestParam("question") String question) {
		List<Document> documents = embeddingService.searchDocument1(question);

		String text = "";
		for (Document document : documents) {
			text += "<div class='mb-2'>";
			text += "  <span class='me-2'>유사도 점수: %f,</span>".formatted(document.getScore());
			text += "  <span>%s(%s)</span>".formatted(document.getText(),
				document.getMetadata().get("year"));
			text += "</div>";
		}
		return text;
	}

	@PostMapping(
		value = "/search-document-2",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String searchDocument2(@RequestParam("question") String question) {
		List<Document> documents = embeddingService.searchDocument2(question);

		String text = "";
		for (Document document : documents) {
			text += "<div class='mb-2'>";
			text += "  <span class='me-2'>유사도 점수: %f,</span>".formatted(document.getScore());
			text += "  <span>%s(%s)</span>".formatted(document.getText(),
				document.getMetadata().get("year"));
			text += "</div>";
		}
		return text;
	}

	@PostMapping(
		value = "/delete-document",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String deleteDocument(@RequestParam("question") String question) {
		embeddingService.deleteDocument();
		return "Document가 삭제되었습니다.";
	}
}