package piu.springAI.service.chapter11;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.tool.chapter11.HeatingSystemTools;

@Slf4j
@Service
@RequiredArgsConstructor
public class HeatingSystemService {

	private final ChatClient chatClient;
	private final HeatingSystemTools heatingSystemTools;

	public String chat(String question) {
		String answer = chatClient.prompt()
			.system("""
				현재 온도가 사용자가 원하는 온도 이상이라면 난방 시스템을 중지하세요.
				현재 온도가 사용자가 원하는 온도 이하라면 난방 시스템을 가동시켜주세요.
				""")
			.user(question)
			.tools(heatingSystemTools)
			.toolContext(Map.of("controlKey", "heatingSystemKey"))
			.call()
			.content();

		return answer;
	}
}
