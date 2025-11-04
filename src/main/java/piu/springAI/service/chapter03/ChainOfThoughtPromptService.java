package piu.springAI.service.chapter03;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChainOfThoughtPromptService {

	private final ChatClient chatClient;

	public Flux<String> chainOfThought(String question) {
		Flux<String> response = chatClient.prompt()
			.user("""
				%s
				한 걸음씩 생각해 봅시다.
				
				[예시]
				질문: 제 동생이 2살일 때, 저는 그의 나이의 두 배였어요.
				지금 저는 40살인데, 제 동생은 몇 살일까요? 한 걸음씩 생각해 봅시다.
				
				답변: 제 동생이 2살일 때, 저는 2*2=4살이었이요.
				2년이 차이나고, 지금 저는 40살이니, 제 동생은 40-2=38살이에요. 정답은 38살입니다.
				""".formatted(question))
			.stream()
			.content();

		return response;
	}
}
