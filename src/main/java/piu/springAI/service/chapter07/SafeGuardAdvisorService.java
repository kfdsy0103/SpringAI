package piu.springAI.service.chapter07;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SafeGuardAdvisorService {

	private final ChatClient chatClient;

	public SafeGuardAdvisorService(ChatClient.Builder chatClientBuilder) {
		SafeGuardAdvisor safeGuardAdvisor = new SafeGuardAdvisor(
			List.of("욕설", "계좌번호", "폭력", "폭탄"),
			"해당 질문은 민감한 콘텐츠 요청이므로 응답할 수 없습니다.",
			Ordered.HIGHEST_PRECEDENCE
		);

		this.chatClient = chatClientBuilder
			.defaultAdvisors(safeGuardAdvisor)
			.build();
	}

	public String safeGuardService(String question) {
		String response = chatClient.prompt()
			.user(question)
			.call()
			.content();
		return response;
	}
}
