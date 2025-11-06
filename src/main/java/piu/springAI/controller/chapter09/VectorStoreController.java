package piu.springAI.controller.chapter09;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter09.VectorStoreService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class VectorStoreController {

	private final VectorStoreService vectorStoreService;

	@PostMapping(
		value = "/chat",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
}
