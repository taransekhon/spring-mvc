package com.reactivestax.spring5mvc.utils;

import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static com.reactivestax.spring5mvc.constants.CommonConstants.ENV_PROD;

/**
 * this class has various general purpose utility methods that can be used by
 * app developers
 */
public class MessageUtils {
	private static final Logger loggerObj = LoggerFactory.getLogger(MessageUtils.class);
	private MessageUtils() {
		throw new IllegalAccessError("MessageUtils class");
	}

	/**
	 * returns a printStackTrace as String 
	 * @param exception
	 * @return String
	 */
	public static String printStackTrace(final Throwable exception) {
		final Writer writer = new StringWriter();
		final PrintWriter printWriter = new PrintWriter(writer);
		exception.printStackTrace(printWriter);
		return writer.toString();
	}

	/**
	 * General purpose utility method to print the contents of map in JSON format
	 * @param map
	 * @return String
	 */
	public static String printMapInJSONFormat(Map<String,String> map) {
		return new Gson().toJson(map);
	}
	/**
	 * decrypts a base64 encoded string
	 * 
	 * @return String
	 * @throws UnsupportedEncodingException
	 */

    public static String getFullStackDetailsAsString(Throwable ex) {
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append(ex.getMessage());
        strBuilder.append("---");
        strBuilder.append(printStackTrace(ex));
        return strBuilder.toString();
    }

	public static boolean isEnvProd(Environment environment) {
		boolean isProdEnv = false;
		List<String> activeProfileList = Arrays.asList(environment.getActiveProfiles());
		for (String activeProfile : activeProfileList) {
			if(StringUtils.equalsIgnoreCase(activeProfile, ENV_PROD) ){
				isProdEnv = true;
			}
		}
		return isProdEnv;
	}
}
