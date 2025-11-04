package piu.springAI.service.chapter04;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ListOutputService {

	private final ChatClient chatClient;

	public List<String> listOutputLowLevel(String question) {
		ListOutputConverter listOutputConverter = new ListOutputConverter();

		PromptTemplate promptTemplate = PromptTemplate.builder()
			.template("{city}에서 유명한 호텔 목록 5개를 출력하세요. {format}")
			.build();

		Prompt prompt = promptTemplate.create(Map.of("city", question, "format", listOutputConverter.getFormat()));

		String content = chatClient.prompt(prompt)
			.call()
			.content();

		List<String> hotelList = listOutputConverter.convert(content);
		return hotelList;
	}

	public List<String> listOutputHighLevel(String question) {
		List<String> hotelList = chatClient.prompt()
			.user("""
				{city}에서 유명한 호텔 목록 5개를 출력하세요.
				""".formatted(question))
			.call()
			.entity(new ListOutputConverter());

		return hotelList;
	}
}
