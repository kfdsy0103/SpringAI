package piu.springAI.service.chapter08;

import java.util.List;
import java.util.Map;

import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.Embedding;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.embedding.EmbeddingResponseMetadata;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmbeddingService {

	// 구현체가 자동 주입 됨
	private final EmbeddingModel embeddingModel;
	private final VectorStore vectorStore;

	public void textEmbedding(String question) {

		// 임베딩 하기
		EmbeddingResponse response = embeddingModel.embedForResponse(List.of(question));

		// 임베딩 모델 정보 얻기 (모델명 + 사용 토큰 정보)
		EmbeddingResponseMetadata metadata = response.getMetadata();
		log.info("모델 이름: {}", metadata.getModel());
		log.info("모델의 임베딩 차원: {}", embeddingModel.dimensions());

		// 임베딩 결과 얻기
		Embedding embedding = response.getResults().get(0);
		log.info("벡터 차원: {}", embedding.getOutput().length);
		log.info("벡터: {}", embedding.getOutput());
	}

	public void addDocument() {
		List<Document> documents = List.of(
			new Document("대통령 선거는 5년마다 있습니다.", Map.of("source", "헌법", "year", 1987)),
			new Document("대통령 임기는 4년입니다.", Map.of("source", "헌법", "year", 1980)),
			new Document("국회의원은 법률안을 심의·의결합니다.", Map.of("source", "헌법", "year", 1987)),
			new Document("자동차를 사용하려면 등록을 해야합니다.", Map.of("source", "자동차관리법")),
			new Document("대통령은 행정부의 수반입니다.", Map.of("source", "헌법", "year", 1987)),
			new Document("국회의원은 4년마다 투표로 뽑습니다.", Map.of("source", "헌법", "year", 1987)),
			new Document("승용차는 정규적인 점검이 필요합니다.", Map.of("source", "자동차관리법")));

		// 벡터 DB 저장
		vectorStore.add(documents);
	}

	public List<Document> searchDocument1(String question) {
		List<Document> documents = vectorStore.similaritySearch(question);
		return documents;
	}

	public List<Document> searchDocument2(String question) {
		List<Document> documents = vectorStore.similaritySearch(
			SearchRequest.builder()
				.query(question) // 쿼리 내용
				.topK(1) // 최상위 1개만 가져옴
				.similarityThreshold(0.4) // 유사성 점수가 0.4 이상
				.filterExpression("source == '헌법' && year >= 1987") // 메타데이터 조건
				.build()
		);
		return documents;
	}
}
