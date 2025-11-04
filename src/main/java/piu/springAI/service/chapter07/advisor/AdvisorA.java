package piu.springAI.service.chapter07.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.core.Ordered;

import lombok.extern.slf4j.Slf4j;

// Spring MVC 환경에서는 CallAdvisor를 구현해야함
@Slf4j
public class AdvisorA implements CallAdvisor {

	@Override
	public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
		log.info("전처리");
		ChatClientResponse response = callAdvisorChain.nextCall(chatClientRequest);
		log.info("후처리");
		return response;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public int getOrder() {
		return Ordered.HIGHEST_PRECEDENCE + 1;
	}
}
