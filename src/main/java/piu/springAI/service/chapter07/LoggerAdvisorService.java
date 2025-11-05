package piu.springAI.service.chapter07;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter07.customAdvisor.MaxCharLengthAdvisor;

@Slf4j
@Service
public class LoggerAdvisorService {

	private final ChatClient chatClient;

	public LoggerAdvisorService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder
			.defaultAdvisors(
				new MaxCharLengthAdvisor(Ordered.HIGHEST_PRECEDENCE),
				new SimpleLoggerAdvisor(Ordered.LOWEST_PRECEDENCE - 1))
			.build();
	}

	public String advisorLogging(String question) {
		String response = chatClient.prompt()
			.advisors(advisorSpec -> advisorSpec.param("maxCharLength", 100))
			.user(question)
			.call()
			.content();
		return response;
	}
}

