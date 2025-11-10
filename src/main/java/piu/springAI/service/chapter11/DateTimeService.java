package piu.springAI.service.chapter11;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.tool.chapter11.DateTimeTools;

@Slf4j
@Service
@RequiredArgsConstructor
public class DateTimeService {

	private final ChatClient chatClient;
	private final DateTimeTools dateTimeTools;

	public String chat(String question) {
		String answer = chatClient.prompt()
			.user(question)
			.tools(dateTimeTools)
			.call()
			.content();
		
		return answer;
	}
}
