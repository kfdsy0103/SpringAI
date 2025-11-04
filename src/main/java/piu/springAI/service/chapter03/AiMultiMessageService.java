package piu.springAI.service.chapter03;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AiMultiMessageService {

	private final ChatClient chatClient;

	public String multiMessage(String question, List<Message> chatMemory) {

		SystemMessage systemMessage = SystemMessage.builder()
			.text("""
					당신은 AI 비서입니다.
					제공되는 지난 대화 내용을 보고 우선적으로 답변해 주세요.
				""")
			.build();

		// 대화를 처음 시작하는 경우
		if (chatMemory.isEmpty()) {
			chatMemory.add(systemMessage);
		}

		// 로깅
		log.info(chatMemory.toString());

		// LLM 동기 호출, chatResponse 응답받기
		ChatResponse chatResponse = chatClient.prompt()
			.messages(chatMemory)
			.user(question)
			.call()
			.chatResponse();

		// LLM 응답 결과 담기
		UserMessage userMessage = UserMessage.builder().text(question).build();
		chatMemory.add(userMessage);

		// LLM 응답(AssistantMessage)은 대화 기억 유지를 위해 마찬가지로 저장
		AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
		chatMemory.add(assistantMessage);

		// 응답 String은 반환
		String text = assistantMessage.getText();
		return text;
	}
}
