package piu.springAI.service.chapter07.customAdvisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.CallAdvisor;
import org.springframework.ai.chat.client.advisor.api.CallAdvisorChain;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MaxCharLengthAdvisor implements CallAdvisor {

	public static final String MAX_CHAR_LENGTH = "maxCharLength";
	private int maxCharLength = 100;
	private int order;

	public MaxCharLengthAdvisor(int order) {
		this.order = order;
	}

	@Override
	public ChatClientResponse adviseCall(ChatClientRequest chatClientRequest, CallAdvisorChain callAdvisorChain) {
		// 강화된 프롬프트
		log.info("전처리");
		ChatClientRequest mutatedRequest = augmentPrompt(chatClientRequest);
		ChatClientResponse response = callAdvisorChain.nextCall(mutatedRequest);
		log.info("후처리");
		return response;
	}

	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}

	@Override
	public int getOrder() {
		return order;
	}

	private ChatClientRequest augmentPrompt(ChatClientRequest request) {
		String userText = this.maxCharLength + "자 이내로 답변해 주세요.";
		Integer maxCharLength = (Integer)request.context().get(MAX_CHAR_LENGTH);

		if (maxCharLength != null) {
			userText = maxCharLength + "자 이내로 답변해 주세요.";
		}
		String finalText = userText;

		// 기존 프롬프트를 얻어 강화
		Prompt originalPrompt = request.prompt();
		Prompt augmentedPrompt = originalPrompt.augmentUserMessage(userMessage -> UserMessage.builder()
			.text(userMessage.getText() + " " + finalText)
			.build());

		// 강화된 프롬프트로 ChatClientRequest 반환
		ChatClientRequest mutatedRequest = request.mutate()
			.prompt(augmentedPrompt)
			.build();

		return mutatedRequest;
	}
}
