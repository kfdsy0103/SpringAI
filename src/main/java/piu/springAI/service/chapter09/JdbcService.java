package piu.springAI.service.chapter09;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.PromptChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class JdbcService {

	private final ChatClient chatClient;

	public JdbcService(
		JdbcChatMemoryRepository jdbcChatMemoryRepository,
		ChatClient.Builder chatClientBuilder
	) {
		ChatMemory chatMemory = MessageWindowChatMemory.builder()
			.chatMemoryRepository(jdbcChatMemoryRepository)
			.maxMessages(20)
			.build();

		this.chatClient = chatClientBuilder
			.defaultAdvisors(
				PromptChatMemoryAdvisor.builder(chatMemory).build(),
				new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE - 1)
			)
			.build();
	}

	public String chat(String userText, String conversationId) {
		String content = chatClient.prompt()
			.user(userText)
			// 어드바이저가 내부적으로 conversationId 값을 이용함 (검색 조건 및 대화 기억 저장)
			.advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
			.call()
			.content();
		return content;
	}
}
