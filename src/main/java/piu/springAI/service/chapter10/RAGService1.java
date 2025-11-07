package piu.springAI.service.chapter10;

import java.io.IOException;
import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.core.Ordered;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RAGService1 {

	private final ChatClient chatClient;
	private final JdbcTemplate jdbcTemplate;
	private final VectorStore vectorStore;

	public RAGService1(ChatClient.Builder chatClientBuilder, JdbcTemplate jdbcTemplate, VectorStore vectorStore) {
		this.chatClient = chatClientBuilder
			.defaultAdvisors(
				new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE - 1)
			)
			.build();
		this.jdbcTemplate = jdbcTemplate;
		this.vectorStore = vectorStore;
	}

	public void clearVectorStore() {
		jdbcTemplate.update("TRAUNCATE TABLE vector_store");
	}

	public void ragEtl(MultipartFile attachFile, String source, int chunkSize, int minChunkSizeChars) throws
		IOException {

		// 1. Extract 메타데이터 추가
		Resource resource = new ByteArrayResource(attachFile.getBytes());
		PagePdfDocumentReader reader = new PagePdfDocumentReader(resource);
		List<Document> documents = reader.read();

		for (Document document : documents) {
			document.getMetadata().put("source", source);
		}

		// 2. Transform
		TokenTextSplitter tokenTextSplitter = new TokenTextSplitter(
			chunkSize, minChunkSizeChars, 5, 10000, true
		);
		List<Document> applied = tokenTextSplitter.apply(documents);

		// 3. Store
		vectorStore.add(applied);
	}

	public String ragChat(String question, double score, String source) {

		// 1. 검색 조건
		SearchRequest.Builder searchRequestBuilder = SearchRequest.builder()
			.similarityThreshold(0.5)
			.topK(3);
		if (StringUtils.hasText(question)) {
			searchRequestBuilder.filterExpression("source == '%s'".formatted(source));
		}
		SearchRequest searchRequest = searchRequestBuilder.build();

		// 2. QuestionAnswerAdvisor 생성
		QuestionAnswerAdvisor questionAnswerAdvisor = QuestionAnswerAdvisor.builder(vectorStore)
			.searchRequest(searchRequest)
			.build();

		// 3. 어드바이저 등록 및 응답
		String content = chatClient.prompt()
			.user(question)
			.advisors(questionAnswerAdvisor)
			.call()
			.content();
		
		return content;
	}
}
