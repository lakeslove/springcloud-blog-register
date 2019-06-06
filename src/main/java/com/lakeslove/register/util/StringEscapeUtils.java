package com.lakeslove.register.util;

public class StringEscapeUtils {

	/**
	 * 转换一个字符串里的特殊字符
	 * 这个方法有待改善
	 * @param string
	 * @return
	 */
	public static String escapeHtml(String string) {
		//TODO 此方法待改进
		if (string != null) {
			string = string.replaceAll("&", "&amp;");
			string = string.replaceAll(" ", "&nbsp;");
			string = string.replaceAll("<", "&lt;");
			string = string.replaceAll(">", "&gt;");
			string = string.replaceAll("\"", "&quot;");
			string = string.replaceAll("\\\\", "&#92;");
			string = string.replaceAll("(\r\n|\r|\n|\n\r)", "<br>");
		}
		return string;
	}
}
