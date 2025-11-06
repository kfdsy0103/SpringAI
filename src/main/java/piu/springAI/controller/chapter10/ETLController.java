package piu.springAI.controller.chapter10;

import java.io.IOException;
import java.net.MalformedURLException;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import piu.springAI.service.chapter10.ETLService;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/ai")
public class ETLController {

	private final ETLService etlService;

	@PostMapping(
		value = "/txt-pdf-docx-etl",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String txtPdfDocxEtl(
		@RequestParam("title") String title,
		@RequestParam("author") String author,
		@RequestParam("attach") MultipartFile attachFile
	) throws IOException {
		String result = etlService.etlFromFile(title, author, attachFile);
		return result;
	}

	@PostMapping(
		value = "/html-etl",
		consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
		produces = MediaType.TEXT_PLAIN_VALUE
	)
	public String htmlEtl(
		@RequestParam("title") String title,
		@RequestParam("author") String author,
		@RequestParam("url") String url
	) throws MalformedURLException {
		String result = etlService.etlFromHtml(title, author, url);
		return url;
	}
}
