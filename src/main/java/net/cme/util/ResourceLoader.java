package net.cme.util;

import java.io.InputStream;

public class ResourceLoader {
	public static InputStream getResourceAsInputStream(String string) {
		return ClassLoader.getSystemResourceAsStream(string);
	}

	public static String getResource(String string) {
		return ClassLoader.getSystemResource(string).getPath();
	}
}
