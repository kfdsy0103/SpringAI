package piu.springAI.service;

import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
public class AiService {

	private final ChatModel chatModel;

	public AiService(ChatModel chatModel) {
		this.chatModel = chatModel;
	}

	public String generateText(String question) {

		// 시스템 메시지 생성
		SystemMessage systemMessage = SystemMessage.builder()
			.text("사용자 질문에 대해 한국어로 답변 해야 합니다.")
			.build();

		// 사용자 메시지 생성
		UserMessage userMessage = UserMessage.builder()
			.text(question)
			.build();

		// 대화 옵션 생성
		ChatOptions chatOptions = ChatOptions.builder()
			.model("gpt-4o-mini") // 기본이므로 생략 가능
			.temperature(0.3) // 기본 값은 0.7 (온도 : 높을수록 창의적인 답변, 낮을수록 일관적)
			.maxTokens(1000)
			.build();

		// 프롬프트 생성
		Prompt prompt = Prompt.builder()
			.messages(systemMessage, userMessage)
			.chatOptions(chatOptions)
			.build();

		// LLM에 요청하고 응답받기
		ChatResponse chatResponse = chatModel.call(prompt);
		AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
		String answer = assistantMessage.getText();

		return answer;
	}

	public Flux<String> generateStreamText(String question) {

		// 시스템 메시지 생성
		SystemMessage systemMessage = SystemMessage.builder()
			.text("사용자 질문에 대해 한국어로 답변을 해야 합니다.")
			.build();

		// 사용자 메시지 생성
		UserMessage userMessage = UserMessage.builder()
			.text(question)
			.build();

		// 대화 옵션 설정
		ChatOptions chatOptions = ChatOptions.builder()
			.model("gpt-4o")
			.temperature(0.3)
			.maxTokens(1000)
			.build();

		// 프롬프트 생성
		Prompt prompt = Prompt.builder()
			.messages(systemMessage, userMessage)
			.chatOptions(chatOptions)
			.build();

		// LLM에게 요청하고 응답받기
		Flux<ChatResponse> fluxResponse = chatModel.stream(prompt);
		Flux<String> fluxString = fluxResponse.map(chatResponse -> {
			AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
			String chunk = assistantMessage.getText();
			if (chunk == null)
				chunk = "";
			return chunk;
		});

		return fluxString;
	}
}
