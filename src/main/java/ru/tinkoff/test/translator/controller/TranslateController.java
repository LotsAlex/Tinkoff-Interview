package ru.tinkoff.test.translator.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.tinkoff.test.translator.model.TranslationRequest;
import ru.tinkoff.test.translator.service.TranslationService;

@RestController
public class TranslateController {

	private TranslationService translationService;

	public TranslateController(TranslationService translationService) {
		this.translationService = translationService;
	}

	@PostMapping(path = "/translate")
	public ResponseEntity<String> translate(@RequestBody TranslationRequest translateRequest, Model model)
			throws Exception {

		String translatedText = translationService.translate(translateRequest);

		return ResponseEntity.ok(translatedText);
	}

	@GetMapping(path = "/translate")
	public ResponseEntity<String> translateRedirect(@RequestParam String text, @RequestParam String from,
			@RequestParam String to) throws Exception {
		ObjectMapper om = new ObjectMapper();
		TranslationRequest translationRequest = new TranslationRequest(text, from, to);
		// set headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<String>(om.writeValueAsString(translationRequest), headers);

		// send request and parse result
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> translateResponse = restTemplate.exchange("http://localhost:8080/translate",
				HttpMethod.POST, entity, String.class);
		return translateResponse;
	}
}
