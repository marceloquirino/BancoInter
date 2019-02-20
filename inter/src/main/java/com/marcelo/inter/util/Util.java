package com.marcelo.inter.util;

import org.springframework.util.StringUtils;

public class Util {
	public static boolean IsNullOrEmpty(String value) {
		if(value != null && !StringUtils.isEmpty(value))
			return false;
		return true;
	}
	
	public static boolean IsBiggerThen(Integer small, Integer number) {
		if(number != null && number > small)
			return true;
		return false;
	}
}
