package ru.tinkoff.test.translator.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TranslationUtilTest {

	@Test(expected = RuntimeException.class)
	public void testTranslateByYandexOnlyMoreThatOneWordException() throws Exception {
		TranslationUtil.translateByYandex("more that one word", "en", "ru");
	}

	@Test
	public void testTranslateByYandex() throws Exception {
		assertEquals("больше", TranslationUtil.translateByYandex("more", "en", "ru"));
	}

}
