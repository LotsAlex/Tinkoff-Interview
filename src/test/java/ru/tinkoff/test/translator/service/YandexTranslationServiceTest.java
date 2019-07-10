package ru.tinkoff.test.translator.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import ru.tinkoff.test.translator.dao.InvoiceInfoDao;
import ru.tinkoff.test.translator.dao.entity.InvoiceInfoEntity;
import ru.tinkoff.test.translator.model.TranslationRequest;

@RunWith(MockitoJUnitRunner.class)
public class YandexTranslationServiceTest {
	@Mock
	private InvoiceInfoDao invoiceInfoDao;
	@Mock
	private HttpServletRequest request;
	private YandexTranslationService service;

	@Before
	public void setUp() throws Exception {
		this.service = new YandexTranslationService(invoiceInfoDao, request);
	}

	@After
	public void tearDown() throws Exception {
		this.service = null;
	}

	@Test
	public void testYandexTranslationService() {
		assertNotNull(
				"YandexTranslationService must be initialised by 'invoiceInfoDao' parameter after created object.",
				service.invoiceInfoDao);
		assertNotNull("YandexTranslationService must be initialised by 'request' parameter after created object.",
				service.request);
	}

	@Test
	public void testTranslate() throws Exception {
		String from = "en";
		String to = "ru";
		String word1 = "Hi";
		String word2 = "baby";
		String inputText = String.format("%s, %s!", word1, word2);
		TranslationRequest translationRequest = new TranslationRequest(inputText, from, to);

		String translateByYandex1 = TranslationUtil.translateByYandex(word1, from, to);
		String translateByYandex2 = TranslationUtil.translateByYandex(word2, from, to);
		String expected = service.translate(translationRequest);
		String actual = String.format("%s %s", translateByYandex1, translateByYandex2);

		assertEquals(expected, actual);

	}

	@Test
	public void testLogInvoiceSaveToDb() throws SQLException {
		InvoiceInfoEntity invoiceInfoEntityMock = mock(InvoiceInfoEntity.class);
		service.logInvoice(invoiceInfoEntityMock);
		verify(invoiceInfoDao, times(1)).save(invoiceInfoEntityMock);
	}

}
