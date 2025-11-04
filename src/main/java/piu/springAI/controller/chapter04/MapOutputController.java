package piu.springAI.controller.chapter04;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter04.MapOutputService;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class MapOutputController {

	private final MapOutputService mapOutputService;

	@PostMapping(
		value = "/map-output-converter",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Map<String, Object> mapOutput(@RequestParam("hotel") String hotel) {
		Map<String, Object> response = mapOutputService.MapOutputConverterLowLevel(hotel);
		// Map<String, Object> response = mapOutputService.MapOutputConverterHighLevel(hotel);
		return response;
	}
}
