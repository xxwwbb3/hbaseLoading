package com.dinglicom.clouder.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class StringUtil {
	public static Locale getLocale(final String localeString) {
		Locale locale = null;
		if (localeString == null) {
			locale = Locale.getDefault();
		} else {
			try {
				final int idxOfUS = localeString.indexOf("_");
				if (idxOfUS != -1) {
					final String language = localeString.substring(0, idxOfUS);
					final String country = localeString.substring(idxOfUS + 1);
					locale = new Locale(language, country);
				} else {
					locale = new Locale(localeString);
				}
			} catch (Exception ex) {
				return Locale.getDefault();
			}
		}
		return locale;
	}

	/**
	 * check Object objs is null
	 * 
	 * @param objs
	 * @return
	 */
	public static boolean isEmpty(final Object... objs) {
		if (objs == null) {
			return true;
		}
		for (int i = 0; i < objs.length; i++) {
			if (null == objs[i] || "".equals(objs[i].toString().trim())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * check Object obj sis not null
	 * 
	 * @param objs
	 * @return
	 */
	public static boolean isNotEmpty(final Object... objs) {
		if (objs == null) {
			return false;
		}
		for (int i = 0; i < objs.length; i++) {
			if (null == objs[i] || "".equals(objs[i].toString().trim())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Find Collection duplicated elements,get duplicated elements zxb
	 * 
	 * @param elements
	 * @return Collection
	 */
	public static Collection<?> findCollectionDuplicateElements(final Collection<?> elements) {
		final Set<Object> set = new HashSet<Object>();
		final List<Object> list = new ArrayList<Object>();
		for (Object object : elements) {
			set.add(object);
		}
		if (elements.size() == set.size()) {
			return list;
		} else {
			for (Object object : set) {
				elements.remove(object);
			}
		}
		for (Object object : elements) {
			list.add(object);
		}
		return list;
	}

	/**
	 * 判断是否为0
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isZero(String str) {
		if (isEmpty(str)) {
			return true;
		}
		if ("0".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 正则表达式匹配
	 * 
	 * @param pStr
	 * @param mStr
	 * @return
	 */
	public static boolean isMatch(String pStr, String mStr) {
		Pattern p = Pattern.compile(pStr);
		Matcher matcher = p.matcher(mStr);
		if (matcher.find()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 字节数组字符串格式
	 * @param bytes
	 * @return
	 */
	public static String toString(byte[] bytes)
	{
		return Arrays.toString(bytes);
	}

}
