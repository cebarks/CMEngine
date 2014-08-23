package net.cme.engine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import net.cme.util.ResourceLoader;

public class Version {

	public static final int MAJOR;
	public static final int MINOR;
	public static final int REVISION;
	public static final int BUILD;

	static {
		BufferedReader in = null;
		try {
			in = new BufferedReader(new FileReader(ResourceLoader.getResource("VERSION")));

		} catch (FileNotFoundException e) {
			CMEngine.LOGGER.error("Couldn't find version information: ", e);
		}

		int major = 0;
		int minor = 0;
		int revision = 0;
		int build = 0;

		try {
			String line;
			while ((line = in.readLine()) != null) {
				String[] tokens = line.split("=");

				if (tokens[0].equalsIgnoreCase("MAJOR")) {
					major = Integer.parseInt(tokens[1]);
				}

				if (tokens[0].equalsIgnoreCase("MINOR")) {
					minor = Integer.parseInt(tokens[1]);
				}

				if (tokens[0].equalsIgnoreCase("REVISION")) {
					revision = Integer.parseInt(tokens[1]);
				}

				if (tokens[0].equalsIgnoreCase("BUILD")) {
					build = Integer.parseInt(tokens[1]);
				}
			}
		} catch (IOException e) {
			CMEngine.LOGGER.error("Something went wrong when parsing VERSION: ", e);
		} finally {
			MAJOR = major;
			MINOR = minor;
			REVISION = revision;
			BUILD = build;
		}
	}

	public static String getFullVersion() {
		return new StringBuilder().append(MAJOR).append('.').append(MINOR).append('.').append(REVISION).toString();
	}
}
