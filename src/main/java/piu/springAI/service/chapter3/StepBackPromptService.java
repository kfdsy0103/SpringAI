package piu.springAI.service.chapter3;

import java.util.List;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class StepBackPromptService {

	private final ChatClient chatClient;

	public String stepBackPrompt(String question) throws JsonProcessingException {

		String questions = chatClient.prompt()
			.user("""
				사용자 질문을 처리하기 위해 Step-Back 기법을 사용하려고 합니다.
				사용자 질문을 단계별 질문들로 재구성해주세요.
				맨 마지막 질문은 사용자 질문과 일치해야 합니다.
				단계별 질문을 항목으로 하는 JSON 배열로 출력해 주세요.
				출력 예시: ["...", "...", "...", ...]
				사용자 질문: %s
				""".formatted(question))
			.call()
			.content();

		// 응답 불순물 제거
		String json = questions.substring(questions.indexOf("["), questions.indexOf("]") + 1);
		log.info(json);

		// List<String>으로 파싱
		ObjectMapper objectMapper = new ObjectMapper();
		List<String> listQuestion = objectMapper.readValue(
			json,
			new TypeReference<List<String>>() {
			}
		);

		String[] answerArray = new String[listQuestion.size()];
		for (int i = 0; i < listQuestion.size(); i++) {
			String stepQuestion = listQuestion.get(i);
			String stepAnswer = getStepAnswer(stepQuestion, answerArray);// 단계별 질문에 대한 답변 획득
			answerArray[i] = stepAnswer;
			log.info("단계{}, 질문: {}, 답변: {}", i + 1, stepQuestion, stepAnswer);
		}

		return answerArray[answerArray.length - 1];
	}

	private String getStepAnswer(String question, String... prevStepAnswer) {
		String context = "";

		for (String str : prevStepAnswer) {
			context += str;
		}

		String answer = chatClient.prompt()
			.user("""
				%s
				문맥: %s
				""".formatted(question, context))
			.call()
			.content();

		return answer;
	}
}
