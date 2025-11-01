package piu.springAI.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiDefaultService {

	private final ChatClient chatClient;

	public AiDefaultService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder
			.defaultSystem("적절한 감탄사, 웃음 등을 넣어서 친절하게 대화해 주세요.")
			.defaultOptions(ChatOptions.builder()
				.temperature(1.0)    // 창의적인 응답
				.maxTokens(300)      // 짧은 답변
				.build()
			)
			.build();
	}

	public Flux<String> defaultMethod(String question) {
		Flux<String> response = chatClient.prompt()
			.user(question)
			.stream()
			.content();

		return response;
	}
}
