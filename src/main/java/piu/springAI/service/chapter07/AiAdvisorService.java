package piu.springAI.service.chapter07;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter07.advisor.AdvisorA;
import piu.springAI.service.chapter07.advisor.AdvisorB;
import piu.springAI.service.chapter07.advisor.AdvisorC;

@Server
@Slf4j
@Service
public class AiAdvisorService {

	private final ChatClient chatClient;

	public AiAdvisorService(ChatClient.Builder chatClientBuilder) {
		this.chatClient = chatClientBuilder
			.defaultAdvisors(
				new AdvisorA(),
				new AdvisorB()
			)
			.build();
	}

	public String advisorChain1(String question) {
		String response = chatClient.prompt()
			.advisors(new AdvisorC())
			.user(question)
			.call()
			.content();

		return response;
	}
}
