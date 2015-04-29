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

	public static boolean isNumber(String str) {
		char[] s = str.toCharArray();
		for (int i = 0; i < s.length; i++) {
			if (!Character.isDigit(s[i]))
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

	public static Spanned simpleHighLight(String keyword, String str, String highColor, String generalColor) {
		String result = "";
		//keyword should be lower case
		int kwLength = keyword.length();
		for (int i = 0; i < str.length(); i++) {
			if ((str.charAt(i) == keyword.charAt(0) || str.charAt(i) == (keyword.charAt(0) - 32)) && (StringCmp(str.toLowerCase(), keyword, i))) {

				for (int j = 0; j < kwLength; j++)
					result += ("<font color=\"#" + highColor + "\">" + str.charAt(i + j) + "</font>");
				i += (kwLength - 1);


			} else result += ("<font color=\"#" + generalColor + "\">" + str.charAt(i) + "</font>");
		}
		return Html.fromHtml(result);
	}
}
