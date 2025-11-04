package piu.springAI.service.chapter04;

import java.util.List;
import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.converter.BeanOutputConverter;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter04.dto.Hotel;

@Service
@Slf4j
@RequiredArgsConstructor
public class BeanOutputService {

	private final ChatClient chatClient;

	public Hotel beanOutputLowLevel(String city) {
		BeanOutputConverter<Hotel> hotelBeanOutputConverter = new BeanOutputConverter<>(Hotel.class);

		PromptTemplate promptTemplate = PromptTemplate.builder()
			.template("""
				{city}에서 유명한 호텔 목록 5개를 출력하세요. {format}
				""")
			.build();

		Prompt prompt = promptTemplate.create(Map.of("city", city, "format", hotelBeanOutputConverter.getFormat()));

		String json = chatClient.prompt(prompt)
			.call()
			.content();

		Hotel hotel = hotelBeanOutputConverter.convert(json);
		return hotel;
	}

	public Hotel beanOutputHighLevel(String city) {
		Hotel hotel = chatClient.prompt()
			.user("""
				{city}에서 유명한 호텔 목록 5개를 출력하세요.
				""".formatted(city))
			.call()
			.entity(Hotel.class);
		// .entity(new BeanOutputConverter<>(Hotel.class));

		return hotel;
	}

	public List<Hotel> genericBeanOutputConverterLowLevel(String cities) {
		BeanOutputConverter<List<Hotel>> listBeanOutputConverter = new BeanOutputConverter<>(
			new ParameterizedTypeReference<List<Hotel>>() {
			});

		PromptTemplate promptTemplate = PromptTemplate.builder()
			.template("""
				다음 도시들에서 유명한 호텔 3개를 출력하세요.
				
				{cities}
				
				{format}
				""")
			.build();

		Prompt prompt = promptTemplate.create(Map.of("cities", cities, "format", listBeanOutputConverter.getFormat()));

		String json = chatClient.prompt(prompt)
			.call()
			.content();

		List<Hotel> hotels = listBeanOutputConverter.convert(json);
		return hotels;
	}

	public List<Hotel> genericBeanOutputConverterHighLevel(String cities) {
		List<Hotel> hotels = chatClient.prompt()
			.user("""
				다음 도시들에서 유명한 호텔 3개를 출력하세요.
				
				{cities}
				""".formatted(cities))
			.call()
			.entity(new ParameterizedTypeReference<List<Hotel>>() {
			});

		return hotels;
	}
}
