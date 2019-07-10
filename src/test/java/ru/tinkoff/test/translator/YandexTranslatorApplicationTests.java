package ru.tinkoff.test.translator;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ru.tinkoff.test.translator.controller.TranslateController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class YandexTranslatorApplicationTests {
	@Autowired
	private TranslateController translateController;

	@Test
	public void contextLoads() {
		assertThat(translateController).isNotNull();
	}

}
