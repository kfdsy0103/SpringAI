package piu.springAI.controller.chapter07;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter07.SafeGuardAdvisorService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class SafeGuardAdvisorController {

	private final SafeGuardAdvisorService safeGuardAdvisorService;

	@PostMapping(
		value = "/advisor-safe-guard",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String safeGuard(@RequestParam("question") String question) {
		String response = safeGuardAdvisorService.safeGuardService(question);
		return response;
	}
}
