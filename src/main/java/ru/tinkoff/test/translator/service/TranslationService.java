package ru.tinkoff.test.translator.service;

import ru.tinkoff.test.translator.model.TranslationRequest;

public interface TranslationService {

	String translate(TranslationRequest translateRequest) throws Exception;

}
