package piu.springAI.controller.chapter10;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter10.ETLService;
import piu.springAI.service.chapter10.RAGService1;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class ETLController {

	private final ETLService etlService;
	private final RAGService1 ragService1;

	@PostMapping(
		value = "/txt-pdf-docx-etl",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String txtPdfDocxEtl(
		@RequestParam("title") String title,
		@RequestParam("author") String author,
		@RequestParam("attach") MultipartFile attachFile
	) throws IOException {
		String result = etlService.etlFromFile(title, author, attachFile);
		return result;
	}

	@PostMapping(
		value = "/html-etl",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String htmlEtl(
		@RequestParam("title") String title,
		@RequestParam("author") String author,
		@RequestParam("url") String url
	) throws MalformedURLException {
		String result = etlService.etlFromHtml(title, author, url);
		return result;
	}

	@PostMapping(
		value = "/json-etl",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String jsonEtl(@RequestParam("url") String url) throws MalformedURLException {
		String result = etlService.etlFromJson(url);
		return result;
	}

	@PostMapping(
		value = "/rag-etl",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String ragEtl(
		@RequestParam("attach") MultipartFile attachFile,
		@RequestParam("source") String source,
		@RequestParam(value = "chunkSize", defaultValue = "200") int chunkSize,
		@RequestParam(value = "minChunkChars", defaultValue = "100") int minChunkChars
	) throws IOException {
		ragService1.ragEtl(attachFile, source, chunkSize, minChunkChars);
		return "PDF ETL 과정을 성공적으로 처리하였습니다.";
	}

	@PostMapping(
		value = "/rag-chat",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String ragChat(
		@RequestParam("question") String question,
		@RequestParam(value = "score", defaultValue = "0.0") double score,
		@RequestParam("source") String source
	) {
		String answer = ragService1.ragChat(question, score, source);
		return answer;
	}

	@PostMapping(
		value = "/rag-clear",
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String ragClear() {
		ragService1.clearVectorStore();
		return "벡터 저장소의 데이터를 모두 삭제하였습니다.";
	}
}
