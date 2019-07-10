package ru.tinkoff.test.translator.service;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public final class TranslationUtil {
	private TranslationUtil() {
	}

	public static String translateByYandex(String word, String from, String to) throws Exception {
		if (word.split(TranslationUtil.getSplitRegexp()).length > 1) {
			throw new RuntimeException("Must be only oner word.");
		}
		String urlStr = "https://translate.yandex.net/api/v1.5/tr.json/translate?key=trnsl.1.1.20190708T155120Z.9cc49cc79b16ac65.5a29e26a6154690314a322631373fca1b6bcd1e9";
		URL urlObj = new URL(urlStr);
		HttpsURLConnection connection = (HttpsURLConnection) urlObj.openConnection();
		connection.setRequestMethod("POST");
		connection.setDoOutput(true);
		DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
		dataOutputStream.writeBytes("text=" + URLEncoder.encode(word, "UTF-8") + "&lang=" + from + "-" + to);

		InputStream response = connection.getInputStream();
		try (Scanner scanner = new Scanner(response)) {
			String json = scanner.nextLine();
			int start = json.indexOf("[");
			int end = json.indexOf("]");
			String translated = json.substring(start + 2, end - 1);
			return translated;
		}
	}

	public static String getSplitRegexp() {
		return "[\\W^ ] *";
	}
}
