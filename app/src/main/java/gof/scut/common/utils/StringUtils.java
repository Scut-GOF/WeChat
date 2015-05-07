package gof.scut.common.utils;

/**
 * Created by Administrator on 2015/4/16.
 */

import android.text.Html;
import android.text.Spanned;

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class StringUtils {
	public static String addBrackets(String rawString) {
		return "(" + rawString + ")";
	}

	public static int SHOW_LENGTH = 50;
	public static ArrayList<String> splitWithWord(String str, String word) {
		ArrayList<String> splits = new ArrayList<>();
		if (!str.contains(word)) {
			splits.add(0, str);
			return splits;
		}
		if (str.equals(word)) {
			splits.add(word);
			return splits;
		}
		StringTokenizer stringTokenizer = new StringTokenizer(str, word);
		String headChecker = "";
		while (stringTokenizer.hasMoreTokens()) {
			String splitStr = stringTokenizer.nextToken();
			splits.add(splitStr);
			headChecker += splitStr;
			if (stringTokenizer.hasMoreTokens()) {
				splits.add(word);
				headChecker += word;
			}
		}
		if (!headChecker.equals(str)) splits.add(0, word);
		return splits;
	}
//example
//	ArrayList<String> splitResults = StringUtils.splitWithWord("好像很厉害的厉害的样子", "厉害");
//	for (int i = 0; i < splitResults.size(); i++)
//			Log.d("SPLIT", splitResults.get(i));

	public static String splitChinese(String str) {
		StringReader re = new StringReader(str);
		IKSegmenter ik = new IKSegmenter(re, true);
		Lexeme lex = null;
		String phrase = "";
		try {
			while ((lex = ik.next()) != null) {
				phrase += (lex.getLexemeText() + " ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return phrase;
	}

	//	example
//	Log.d("SPLIT",StringUtils.splitChinese("IK Analyzer是一个开源的，基于java语言" );
	public static final boolean isChinese(char c) {
		Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
		if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
				|| ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
				|| ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
				|| ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
				|| ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
			return true;
		}
		return false;
	}

	public static boolean containChinese(String str) {

		return str.getBytes().length != str.length();
	}

	public static String cutString(String rawStr, String keyword, int showLength) {
		String startStr = rawStr.substring(0, keyword.length()).toLowerCase();
		//keyword in start
		if (keyword.length() > showLength || rawStr.length() < showLength) return keyword;
		if (startStr.equals(keyword)) {
			return rawStr.substring(0, showLength);
		}
		int otherLength = showLength - keyword.length();


		int indexTag = 0;
		for (int i = 0; i < rawStr.length() - keyword.length() + 1; i++) {
			if (rawStr.substring(i, keyword.length() + i).toLowerCase().equals(keyword)) {
				indexTag = i;
				break;
			}
		}
		int startIndex = 0;
		if (indexTag - otherLength > -1) startIndex = indexTag - otherLength;
		int endIndex = rawStr.length();
		if (indexTag + otherLength < rawStr.length()) endIndex = indexTag + otherLength;
		String resultString = rawStr.substring(startIndex, endIndex + 1);
		if (startIndex != 0) resultString = "..." + resultString;
		return resultString;
	}

	public static String splitChineseSingly(String str) {
		if (str == null) return "";
		if (str.equals("")) return str;
		StringBuilder sb = new StringBuilder();
		str = str.replace(" ", "龘");
		//avoid check chinese every time
		sb.append(str.charAt(0));
		if (isChinese(str.charAt(0))) {
			sb.append(" ");
		}
		for (int i = 1; i < str.length(); i++) {
			if (isChinese(str.charAt(i))) {
				sb.append(" ");
				sb.append(str.charAt(i));
				sb.append(" ");
			} else sb.append(str.charAt(i));
		}
		return sb.toString();
	}

	public static String splitNoBlankChineseSingly(String str) {
		StringBuilder sb = new StringBuilder();
		//avoid check chinese every time
		sb.append(str.charAt(0));
		if (isChinese(str.charAt(0))) {
			sb.append(" ");
		}
		for (int i = 1; i < str.length(); i++) {
			if (isChinese(str.charAt(i))) {
				sb.append(" ");
				sb.append(str.charAt(i));
				sb.append(" ");
			} else sb.append(str.charAt(i));
		}
		return sb.toString();
	}

	public static String recoverWordFromDB(String str) {
		if (str == null) return "";
		str = str.replace(" ", "");
		str = str.replace("龘", " ");
		return str;
	}

	public static boolean isNumber(String str) {
		char[] s = str.toCharArray();
		for (char value : s) {
			if (!Character.isDigit(value))
				return false;
		}
		return true;
	}


	public static boolean StringCmp(String str1, String str2, int str1FromIndex) {
		//both arg lower case
		for (int i = 0; i < str2.length(); i++) {
			if (str1FromIndex + i >= str1.length()) return false;
			if (str1.charAt(str1FromIndex + i) != str2.charAt(i)) return false;

		}
		return true;
	}

	public static Spanned simpleHighLightByPinyin(String keyword, String str, String highColor, String generalColor) {
		//if (keyword == null)
		StringBuilder result = new StringBuilder();
		//keyword should be lower case
		int kwLength = keyword.length();
		if (kwLength == 0)
			return Html.fromHtml(str);
		for (int i = 0; i < str.length(); i++) {
			if ((str.charAt(i) == keyword.charAt(0) || str.charAt(i) == (keyword.charAt(0) - 32)) && (StringCmp(str.toLowerCase(), keyword, i))) {

				for (int j = 0; j < kwLength; j++) {
					result.append("<font color=\"#");
					result.append(highColor);
					result.append("\">");
					result.append(str.charAt(i + j));
					result.append("</font>");
				}

				i += (kwLength - 1);


			} else {
				if (StringUtils.isChinese(str.charAt(i))) {
					String pinyin = new PinyinUtils().getCharacterPinYin(str.charAt(i));
					if (pinyin == null) {
//						Log.d("NULLPINYIN",str.charAt(i)+"");
//						Log.d("NULLPINYIN",str);
//						result.append("<font color=\"#");
//						result.append(generalColor);
//						result.append("\">");
						result.append(str.charAt(i));
//						result.append("</font>");
					} else if (pinyin.contains(keyword) || keyword.contains(pinyin)
							|| keyword.contains(pinyin.charAt(0) + "")) {
						result.append("<font color=\"#");
						result.append(highColor);
						result.append("\">");
						result.append(str.charAt(i));
						result.append("</font>");
					} else {
//						result.append("<font color=\"#");
//						result.append(generalColor);
//						result.append("\">");
						result.append(str.charAt(i));
//						result.append("</font>");
					}
				} else {
//					result.append("<font color=\"#");
//					result.append(generalColor);
//					result.append("\">");
					result.append(str.charAt(i));
//					result.append("</font>");
				}
			}
		}
		return Html.fromHtml(result.toString());
	}

	public static Spanned simpleHighLight(String keyword, String str, String highColor, String generalColor) {
		StringBuilder result = new StringBuilder();
		//keyword should be lower case
		int kwLength = keyword.length();
		if (kwLength == 0)
			return Html.fromHtml(str);
		for (int i = 0; i < str.length(); i++) {
			if ((str.charAt(i) == keyword.charAt(0) || str.charAt(i) == (keyword.charAt(0) - 32)) && (StringCmp(str.toLowerCase(), keyword, i))) {

				for (int j = 0; j < kwLength; j++) {
					result.append("<font color=\"#");
					result.append(highColor);
					result.append("\">");
					result.append(str.charAt(i + j));
					result.append("</font>");
				}

				i += (kwLength - 1);


			} else {
//				result.append("<font color=\"#");
//				result.append(generalColor);
//				result.append("\">");
				result.append(str.charAt(i));
//				result.append("</font>");

			}
		}
		return Html.fromHtml(result.toString());
	}

	public static String getStringPinYin(String str) {
		PinyinUtils pinyin = new PinyinUtils();
		return pinyin.getStringPinYin(str);
	}

	public static String getPurePinYinBlankLy(String str) {
		PinyinUtils pinyin = new PinyinUtils();
		return pinyin.getPurePinYinBlankLy(str);
	}

	public static String getPureSPinYinBlankLy(String str) {
		PinyinUtils pinyin = new PinyinUtils();
		return pinyin.getPureSPinYinBlankLy(str);
	}

	public static ArrayList<String> splitToArray(String str) {
		StringTokenizer stringTokenizer = new StringTokenizer(str, " ");
		ArrayList<String> strings = new ArrayList<>();
		while (stringTokenizer.hasMoreTokens()) {
			String splitStr = stringTokenizer.nextToken();
			strings.add(splitStr);
		}
		return strings;
	}
}
