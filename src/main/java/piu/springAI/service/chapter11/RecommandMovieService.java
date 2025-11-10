package piu.springAI.service.chapter11;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.tool.chapter11.RecommandMovieTools;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommandMovieService {

	private final ChatClient chatClient;
	private final RecommandMovieTools recommandMovieTools;

	public String chat(String question) {
		String answer = chatClient.prompt()
			.user(question)
			.tools(recommandMovieTools)
			.call()
			.content();

		return answer;
	}

	public String chatException(String question) {
		String answer = chatClient.prompt()
			.user("""
				질문에 대해 답변해주세요.
				사용자 ID가 존재하지 않는 경우, 진행을 멈추고
				'[LLM] 질문을 처리할 수 없습니다.'라고 답변해 주세요.
				
				질문: %s
				""".formatted(question))
			.tools(recommandMovieTools)
			.call()
			.content();

		return answer;
	}
}
