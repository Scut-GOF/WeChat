package gof.scut.common.utils;

/**
 * Created by Administrator on 2015/4/16.
 */

import org.wltea.analyzer.core.IKSegmenter;
import org.wltea.analyzer.core.Lexeme;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class StringUtils {
	public static String addBrackets(String rawString) {
		return "(" + rawString + ")";
	}

	public static ArrayList<String> splitWithWord(String str, String word) {

		StringTokenizer stringTokenizer = new StringTokenizer(str, word);
		ArrayList<String> splits = new ArrayList();
		while (stringTokenizer.hasMoreTokens()) {
			String splitStr = stringTokenizer.nextToken();
			splits.add(splitStr);
			if (stringTokenizer.hasMoreTokens())
				splits.add(word);
		}
		return splits;
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
//	example
//	Log.d("SPLIT",StringUtils.splitChinese("IK Analyzer是一个开源的，基于java语言" );
}
