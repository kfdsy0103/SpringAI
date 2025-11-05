package piu.springAI.service.chapter07;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter07.customAdvisor.AdvisorStream;
import piu.springAI.service.chapter07.customAdvisor.MaxCharLengthAdvisor;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class AiAdvisorShareDataService {

	private final ChatClient chatClient;

	public AiAdvisorShareDataService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder
			.defaultAdvisors(new MaxCharLengthAdvisor(Ordered.HIGHEST_PRECEDENCE))
			.build();
	}

	public Flux<String> advisorContext(String question) {
		Flux<String> content = chatClient.prompt()
			.advisors(advisorSpec ->
				advisorSpec.param(MaxCharLengthAdvisor.MAX_CHAR_LENGTH, 100)) // 100자
			.advisors(new AdvisorStream())
			.user(question)
			.stream()
			.content();

		return content;
	}
}
