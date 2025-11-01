package piu.springAI.controller.chapter3;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter3.RoleAssignmentPromptService;
import reactor.core.publisher.Flux;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class RoleAssignmentPromptController {

	private final RoleAssignmentPromptService promptService;

	@PostMapping(
		value = "/role-assignment",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_NDJSON_VALUE
	)
	public Flux<String> roleAssignment(
		@RequestParam("requirements") String requirements
	) {
		Flux<String> stringFlux = promptService.roleAssignment(requirements);
		return stringFlux;
	}
}
