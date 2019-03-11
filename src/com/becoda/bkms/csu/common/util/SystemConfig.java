package com.becoda.bkms.csu.common.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class SystemConfig {

	private static final String BUNDLE_NAME = "SystemConfig";

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private SystemConfig() {

	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			e.printStackTrace();
			return "no " + key + " key!";
		}
	}

	public static void main(String[] args) {
		System.out.println(SystemConfig.getString("saveUrl"));
	}

}
