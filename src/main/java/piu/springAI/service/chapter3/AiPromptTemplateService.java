package piu.springAI.service.chapter3;

import java.util.Map;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class AiPromptTemplateService {

	private final ChatClient chatClient;

	private PromptTemplate systemTemplate = SystemPromptTemplate.builder()
		.template("""
			답변을 생성할 때 HTML과 CCS를 사용해서 파란 글자로 출력하세요.
			<span> 태그 안에 들어갈 내용만 출력하세요.
			""")
		.build();

	private PromptTemplate userTemplate = PromptTemplate.builder()
		.template("다음 한국어 문장을 {language}로 번역해 주세요.\n 문장: {statement}")
		.build();

	// create()는 Prompt 반환
	public Flux<String> promptTemplate1(String statement, String language) {

		Prompt prompt = userTemplate.create(
			Map.of("statement", statement, "language", language)
		);

		Flux<String> response = chatClient.prompt(prompt)
			.stream()
			.content();

		return response;
	}

	// 시스템 메시지도 필요한 경우 (createMessage()는 Message 반환)
	public Flux<String> promptTemplate2(String statement, String language) {

		Flux<String> response = chatClient.prompt()
			.messages(
				systemTemplate.createMessage(),
				userTemplate.createMessage(Map.of("statement", statement, "language", language))
			)
			.stream()
			.content();

		return response;
	}

	// render()는 String 반환
	public Flux<String> promptTemplate3(String statement, String language) {
		Flux<String> response = chatClient.prompt()
			.system(systemTemplate.render())
			.user(userTemplate.render(Map.of("statement", statement, "language", language)))
			.stream()
			.content();

		return response;
	}

	// Template 없이 자바 String 포맷 이용 (내부에서만 필요한 경우 이렇게 쓰면 됨)
	public Flux<String> promptTemplate4(String statement, String language) {
		String systemText = """
			답변을 생성할 때 HTML과 CSS를 사용해서 파란 글자로 출력하세요.
			<span> 태그 안에 들어갈 내용만 출력하세요.
			""";

		String userText = """
			다음 한국어 문장을 %s로 번역해주세요.\n 문장: %s
			""".formatted(language, statement);

		Flux<String> response = chatClient.prompt()
			.system(systemText)
			.user(userText)
			.stream()
			.content();

		return response;
	}
}
