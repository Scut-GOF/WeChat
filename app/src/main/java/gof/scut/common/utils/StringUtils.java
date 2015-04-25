package gof.scut.common.utils;

/**
 * Created by Administrator on 2015/4/16.
 */

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;

public class StringUtils {
	public static String addBrackets(String rawString) {
		return "(" + rawString + ")";
	}

	public static String[] splitWithWord(String word) {
		return null;
	}

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
}
