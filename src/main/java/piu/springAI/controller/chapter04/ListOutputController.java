package piu.springAI.controller.chapter04;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter04.ListOutputService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class ListOutputController {

	private final ListOutputService listOutputService;

	@PostMapping(
		value = "/list-output-converter",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<String> listOutputConverter(@RequestParam("city") String city) {
		List<String> response = listOutputService.listOutputLowLevel(city);
		// List<String> response = listOutputService.listOutputHighLevel(city);
		return response;
	}
}
