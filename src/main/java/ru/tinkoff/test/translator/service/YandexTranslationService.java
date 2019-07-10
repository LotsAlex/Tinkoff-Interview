package ru.tinkoff.test.translator.service;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import ru.tinkoff.test.translator.dao.InvoiceInfoDao;
import ru.tinkoff.test.translator.dao.entity.InvoiceInfoEntity;
import ru.tinkoff.test.translator.model.TranslationRequest;

import static ru.tinkoff.test.translator.service.TranslationUtil.translateByYandex;

@Service
public class YandexTranslationService implements TranslationService {

	private static final int THREAD_POOL_SIZE = 5;
	InvoiceInfoDao invoiceInfoDao;
	HttpServletRequest request;

	public YandexTranslationService(InvoiceInfoDao invoiceInfoDao, HttpServletRequest request) {
		this.invoiceInfoDao = invoiceInfoDao;
		this.request = request;
	}

	@Override
	public String translate(TranslationRequest translationRequest) throws Exception {

		InvoiceInfoEntity invoiceInfoEntity = new InvoiceInfoEntity();
		invoiceInfoEntity.setClientIp(request.getRemoteAddr());
		invoiceInfoEntity.setInvoiceTime(LocalDateTime.now());
		invoiceInfoEntity.setInputParams(translationRequest.getText(), translationRequest.getFrom(),
				translationRequest.getTo());

		logInvoice(invoiceInfoEntity);
		String translatedText = getTranslatedText(translationRequest);
		return translatedText.trim();
	}

	private String getTranslatedText(TranslationRequest translationRequest)
			throws InterruptedException, ExecutionException {
		String[] translatedWords = executeTranslation(translationRequest);
		StringBuilder sb = new StringBuilder();
		Arrays.asList(translatedWords).stream().forEach(w -> sb.append(w).append(" "));
		return sb.toString();
	}

	private String[] executeTranslation(TranslationRequest translationRequest) throws InterruptedException {
		String realText = translationRequest.getText();
		String[] words = realText.split(TranslationUtil.getSplitRegexp());
		String[] translatedWords = new String[words.length];
		ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_POOL_SIZE);
		for (int i = 0; i < words.length; i++) {
			final int index = i;
			String word = words[i];
			forkJoinPool.submit(() -> {
				String translated = "";
				try {
					System.out.println(Thread.currentThread());
					translated = translateByYandex(word, translationRequest.getFrom(), translationRequest.getTo());
					synchronized (this) {
						translatedWords[index] = translated;
					}

				} catch (Exception e) {
					translated = e.getMessage();
					e.printStackTrace();
				}
			});
		}
		forkJoinPool.shutdown();
		forkJoinPool.awaitTermination(1, TimeUnit.MINUTES);
		return translatedWords;
	}

	void logInvoice(InvoiceInfoEntity invoiceInfoEntity) throws SQLException {
		invoiceInfoDao.save(invoiceInfoEntity);
	}

}
