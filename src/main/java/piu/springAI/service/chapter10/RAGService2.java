package piu.springAI.service.chapter10;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.preretrieval.query.transformation.CompressionQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.RewriteQueryTransformer;
import org.springframework.ai.rag.preretrieval.query.transformation.TranslationQueryTransformer;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.filter.FilterExpressionBuilder;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RAGService2 {

	private final ChatClient chatClient;
	private final ChatModel chatModel;
	private final VectorStore vectorStore;
	private final ChatMemory chatMemory;

	public RAGService2(ChatClient.Builder chatClientBuilder, ChatModel chatModel, VectorStore vectorStore,
		ChatMemory chatMemory) {
		this.chatClient = chatClientBuilder
			.defaultAdvisors(
				new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE - 1)
			)
			.build();
		this.chatModel = chatModel;
		this.vectorStore = vectorStore;
		this.chatMemory = chatMemory;
	}

	public String chatWithTranslation(String question, double score, String source) {
		RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
			.queryTransformers(createTranslationQueryTransformer())
			.documentRetriever(createVectorStoreDocumentRetriever(score, source))
			.build();

		String answer = chatClient.prompt()
			.user(question)
			.advisors(retrievalAugmentationAdvisor)
			.call()
			.content();
		
		return answer;
	}

	public String chatWithCompression(
		String question, double score, String source, String conversationId
	) {
		RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
			.queryTransformers(createCompressionQueryTransformer()) // 검색 전
			.documentRetriever(createVectorStoreDocumentRetriever(score, source)) // 유사도 검색 모듈
			.build();

		String answer = chatClient.prompt()
			.user(question)
			.advisors(
				MessageChatMemoryAdvisor.builder(chatMemory).build(),
				retrievalAugmentationAdvisor
			)
			.advisors(advisorSpec -> advisorSpec.param(
				ChatMemory.CONVERSATION_ID, conversationId
			))
			.call()
			.content();

		return answer;
	}

	public String chatWithRewriteQuery(String question, double score, String source) {
		RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
			.queryTransformers(createRewriteQueryTransformer())
			.documentRetriever(createVectorStoreDocumentRetriever(score, source))
			.build();

		String answer = chatClient.prompt()
			.user(question)
			.advisors(retrievalAugmentationAdvisor)
			.call()
			.content();
		return answer;
	}

	// 따로 빈으로 빼도 좋겠다.
	// 원래 사용자 질문을 위한 ChatClient X, 별도의 새로운 ChatClient를 사용하여 명확한 질문을 한번 더 함
	private CompressionQueryTransformer createCompressionQueryTransformer() {

		ChatClient.Builder builder = ChatClient.builder(chatModel)
			.defaultAdvisors(
				new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE - 1)
			);

		CompressionQueryTransformer compressionQueryTransformer = CompressionQueryTransformer.builder()
			.chatClientBuilder(builder)
			.build();

		return compressionQueryTransformer;
	}

	private VectorStoreDocumentRetriever createVectorStoreDocumentRetriever(double score, String source) {
		VectorStoreDocumentRetriever vectorStoreDocumentRetriever = VectorStoreDocumentRetriever.builder()
			.vectorStore(vectorStore)
			.similarityThreshold(score)
			.topK(3)
			.filterExpression(() -> {
				FilterExpressionBuilder filterExpressionBuilder = new FilterExpressionBuilder();
				if (StringUtils.hasText(source)) {
					return filterExpressionBuilder.eq("source", source).build();
				}
				return null;
			})
			.build();
		return vectorStoreDocumentRetriever;
	}

	private RewriteQueryTransformer createRewriteQueryTransformer() {

		ChatClient.Builder build = ChatClient.builder(chatModel)
			.defaultAdvisors(
				new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE - 1)
			);

		RewriteQueryTransformer rewriteQueryTransformer = RewriteQueryTransformer.builder()
			.chatClientBuilder(build)
			.build();

		return rewriteQueryTransformer;
	}

	private TranslationQueryTransformer createTranslationQueryTransformer() {
		ChatClient.Builder builder = ChatClient.builder(chatModel)
			.defaultAdvisors(
				new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE - 1)
			);

		TranslationQueryTransformer translationQueryTransformer = TranslationQueryTransformer.builder()
			.chatClientBuilder(builder)
			.targetLanguage("korean")
			.build();

		return translationQueryTransformer;
	}
}
