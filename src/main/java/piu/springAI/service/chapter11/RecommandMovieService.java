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
}
