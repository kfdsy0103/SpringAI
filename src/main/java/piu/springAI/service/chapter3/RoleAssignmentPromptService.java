package piu.springAI.service.chapter3;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleAssignmentPromptService {

	private final ChatClient chatClient;

	public Flux<String> roleAssignment(String requirements) {

		Flux<String> travelSuggestions = chatClient.prompt()
			.system("""
				당신은 여행 가이드 역할을 해야합니다.
				아래 요청사항에서 위치를 알려주면 근처에 있는 3곳을 제안해주고,
				이유를 달아주세요. 경우에 따라서 방문하고 싶은 장소 유형을 제공할 수도 있습니다.
				출력 형식은 <ul> 태그이고, 장소는 굵게 표시해주세요.
				""")
			.user("요청사항: %s".formatted(requirements))
			.options(ChatOptions.builder()
				.temperature(1.0)
				.maxTokens(1000)
				.build()
			)
			.stream()
			.content();

		return travelSuggestions;
	}
}
