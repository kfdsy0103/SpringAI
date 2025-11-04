package piu.springAI.service.chapter03;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SelfConsistencyService {

	private final ChatClient chatClient;

	private PromptTemplate promptTemplate = PromptTemplate.builder()
		.template("""
			다음 내용을 [IMPORTANT, NOT_IMPORTANT] 둘 중 하나로 분류해 주세요.
			
			레이블만 반환하세요.
			내용: {content}
			""")
		.build();

	public String selfConsistency(String content) {
		int importantCount = 0;
		int notImportantCount = 0;

		String userText = promptTemplate.render(Map.of("content", content));

		// 5번에 걸쳐 받아보기
		for (int i = 0; i < 5; i++) {
			String output = chatClient.prompt()
				.user(userText)
				.options(ChatOptions.builder()
					.temperature(1.0)
					.build())
				.call()
				.content();

			log.info("{}: {}", i, output);

			if (output.equals("IMPORTANT")) {
				importantCount++;
			} else {
				notImportantCount++;
			}
		}

		// 최종 결정
		String finalClassification = importantCount > notImportantCount ? "중요함" : "중요하지 않음";
		return finalClassification;
	}
}
