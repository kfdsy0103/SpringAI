package piu.springAI.service.chapter10;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.model.transformer.KeywordMetadataEnricher;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.reader.jsoup.JsoupDocumentReader;
import org.springframework.ai.reader.jsoup.config.JsoupDocumentReaderConfig;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.tika.TikaDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ETLService {

	private final ChatModel chatModel;
	private final VectorStore vectorStore;

	public String etlFromFile(String title, String author, MultipartFile attachFile) throws IOException {

		// 1. Extract
		List<Document> documents = extractFromFile(attachFile);
		if (documents == null) {
			return ".txt, .pdf, .doc, .docx 파일 중 하나를 올려주세요.";
		}
		log.info("추출된 Document 수: {} 개", documents.size());

		// 2. Transform
		for (Document document : documents) {
			Map<String, Object> metadata = document.getMetadata();
			// 공통 메타데이터는 청크 분할이 되어도 유지된다.
			metadata.putAll(Map.of(
				"title", title,
				"author", author,
				"source", attachFile.getOriginalFilename()
			));
		}
		documents = transform(documents); // 청크 단위 분할
		log.info("변환된 Document 수: {} 개", documents.size());

		// 3. Load
		vectorStore.add(documents);

		return "올린 문서를 추출-변환-적재 완료하였습니다.";
	}

	// Resource -> Reader -> List<Document>
	private List<Document> extractFromFile(MultipartFile attachFile) throws IOException {
		ByteArrayResource resource = new ByteArrayResource(attachFile.getBytes());

		// 텍스트 파일 1개를 1개의 Document
		if (attachFile.getContentType().equals("text/plain")) {
			TextReader reader = new TextReader(resource);
			return reader.read();
		}
		// PDF 페이지별로 Document
		if (attachFile.getContentType().equals("application/pdf")) {
			PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
			return reader.read();
		}
		// Word 파일 1개를 1개의 Document
		if (attachFile.getContentType().contains("wordprocessingml")) {
			TikaDocumentReader reader = new TikaDocumentReader(resource);
			return reader.read();
		}

		return null;
	}

	// 메타데이터에 키워드 추가
	private List<Document> transform(List<Document> documents) {

		List<Document> transformedDocuments = null;

		// 작게 분할
		TokenTextSplitter tokenTextSplitter = new TokenTextSplitter();
		transformedDocuments = tokenTextSplitter.apply(documents);

		// 메타데이터에 키워드 추가 (각 청크 Document 마다 실행하므로 오랜 시간이 걸림)
		KeywordMetadataEnricher keywordMetadataEnricher = new KeywordMetadataEnricher(chatModel, 5);
		transformedDocuments = keywordMetadataEnricher.apply(transformedDocuments);

		return transformedDocuments;
	}

	public String etlFromHtml(String title, String author, String url) throws MalformedURLException {
		Resource resource = new UrlResource(url);

		// 1. Extract
		JsoupDocumentReader reader = new JsoupDocumentReader(
			resource,
			JsoupDocumentReaderConfig.builder()
				.charset("UTF-8") // 인코딩 정보
				.selector("#content") // 선택자로 읽을 텍스트 위치 지정
				.additionalMetadata(Map.of(
					"title", title,
					"author", author,
					"url", url
				))
				.build()
		);
		List<Document> documents = reader.read();
		log.info("추출된 Document 수: {} 개", documents.size());

		// 2. Transform
		TokenTextSplitter transformer = new TokenTextSplitter();
		List<Document> transformedDocuments = transformer.apply(documents);
		log.info("변환된 Document 수: {} 개", transformedDocuments.size());

		// 3. Load
		vectorStore.add(transformedDocuments);

		return "HTML에서 추출-변환-적재 완료하였습니다.";
	}
}