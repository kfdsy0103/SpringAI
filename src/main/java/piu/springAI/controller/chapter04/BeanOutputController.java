package piu.springAI.controller.chapter04;

import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter04.BeanOutputService;
import piu.springAI.service.chapter04.dto.Hotel;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/ai")
public class BeanOutputController {

	private final BeanOutputService beanOutputService;

	@PostMapping(
		value = "/bean-output-converter",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public Hotel beanOutputConverter(@RequestParam("city") String city) {
		Hotel hotel = beanOutputService.beanOutputLowLevel(city);
		// Hotel hotel = beanOutputService.beanOutputHighLevel(city);
		return hotel;
	}

	@PostMapping(
		value = "/generic-bean-output-converter",
		consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE,
		produces = MediaType.APPLICATION_JSON_VALUE
	)
	public List<Hotel> genericBeanOutputConverter(@RequestParam("cities") String cities) {
		List<Hotel> hotels = beanOutputService.genericBeanOutputConverterLowLevel(cities);
		// List<Hotel> hotels = beanOutputService.genericBeanOutputConverterHighLevel(cities);
		return hotels;
	}
}
