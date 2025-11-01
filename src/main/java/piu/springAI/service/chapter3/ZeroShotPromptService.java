package piu.springAI.service.chapter3;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ZeroShotPromptService {

	private final ChatClient chatClient;

	private PromptTemplate promptTemplate = PromptTemplate.builder()
		.template("""
			영화 리뷰를 [긍정적, 중립적, 부정적] 중에서 하나로 분류하세요.
			레이블만 반환하세요.
			리뷰: {review}
			""")
		.build();

	public ZeroShotPromptService(ChatClient.Builder clientBuilder) {
		this.chatClient = clientBuilder
			.defaultOptions(
				ChatOptions.builder()
					.temperature(0.0)    // 3가지 중 하나이니 일관적
					.maxTokens(4)    // 토큰 수 제한
					.build())
			.build();
	}

	public String zeroShotPrompt(String review) {
		String sentiment = chatClient.prompt()
			.user(promptTemplate.render(Map.of("review", review)))
			.call()
			.content();
		return sentiment;
	}
}
