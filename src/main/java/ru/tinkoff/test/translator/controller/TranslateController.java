package ru.tinkoff.test.translator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
