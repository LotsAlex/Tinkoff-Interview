package ru.tinkoff.test.translator.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import ru.tinkoff.test.translator.model.TranslationRequest;
import ru.tinkoff.test.translator.service.TranslationService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class TranslateControllerTest {
	private static final ObjectMapper om = new ObjectMapper();
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private TranslationService translationService;

	@Test
	public void test() throws Exception {
		String text = "Hi, all!";
		String from = "en";
		String to = "ru";
		TranslationRequest translationRequest = new TranslationRequest(text, from, to);

		when(translationService.translate(any(TranslationRequest.class))).thenReturn("Переведено");

		mockMvc.perform(post("/translate").content(om.writeValueAsString(translationRequest))
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk())
				.andExpect(content().string(containsString("Переведено")));
	}

}
