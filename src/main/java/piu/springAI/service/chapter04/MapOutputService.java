package piu.springAI.service.chapter04;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.MapOutputConverter;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class MapOutputService {

	private final ChatClient chatClient;

	public Map<String, Object> MapOutputConverterLowLevel(String hotel) {
		MapOutputConverter mapOutputConverter = new MapOutputConverter();

		PromptTemplate promptTemplate = PromptTemplate.builder()
			.template("""
				"호텔 {hotel}에 대한 정보를 알려주세요 {format}
				""")
			.build();

		Prompt prompt = promptTemplate.create(Map.of("hotel", hotel, "format", mapOutputConverter));

		String json = chatClient.prompt(prompt)
			.call()
			.content();

		Map<String, Object> hotelInfo = mapOutputConverter.convert(json);
		return hotelInfo;
	}

	public Map<String, Object> MapOutputConverterHighLevel(String hotel) {
		Map<String, Object> hotelInfo = chatClient.prompt()
			.user("""
				호텔 {hotel}에 대한 정보를 알려주세요.
				""".formatted(hotel))
			.call()
			.entity(new MapOutputConverter());

		return hotelInfo;
	}
}
