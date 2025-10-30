package piu.springAI;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AiController {

	@PostMapping(value = "/chat", produces = MediaType.TEXT_PLAIN_VALUE)
	public String chat(@RequestParam("question") String question) {
		return "아직 모델이 연결되지 않았습니다.";
	}
}
