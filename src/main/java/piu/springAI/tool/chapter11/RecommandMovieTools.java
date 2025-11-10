package piu.springAI.tool.chapter11;

import java.util.List;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RecommandMovieTools {

	@Tool(description = "사용자가 관람한 영화 목록을 제공합니다.")
	public List<String> getMovieListByUserId(
		@ToolParam(description = "사용자 ID") String userId
	) {
		// 실제로는 DB에서 조회
		// List<String> movies = List.of("엣지오브투모로우", "투모로우", "아이언맨", "혹성탈출", "타이타닉", "엘리시움");
		// return movies;
		throw new RuntimeException("사용자 ID가 존재하지 않습니다.");
	}
}
