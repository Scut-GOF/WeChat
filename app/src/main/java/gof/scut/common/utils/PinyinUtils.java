package gof.scut.common.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class PinyinUtils {
	private HanyuPinyinOutputFormat format = null;
	private String[] pinyin;

	public PinyinUtils() {
		format = new HanyuPinyinOutputFormat();
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		pinyin = null;
	}

	//转换单个字符
	public String getCharacterPinYin(char c) {
		try {
			pinyin = PinyinHelper.toHanyuPinyinStringArray(c, format);
		} catch (BadHanyuPinyinOutputFormatCombination e) {
			e.printStackTrace();
		}

		// 如果c不是汉字，toHanyuPinyinStringArray会返回null
		if (pinyin == null) return null;

		// 只取一个发音，如果是多音字，仅取第一个发音
		return pinyin[0];
	}

	//转换一个字符串
	public String getStringPinYin(String str) {
		StringBuilder sb = new StringBuilder();
		String tempPinyin = null;
		for (int i = 0; i < str.length(); ++i) {
			tempPinyin = getCharacterPinYin(str.charAt(i));
			if (tempPinyin == null) {
				// 如果str.charAt(i)非汉字，则保持原样
				sb.append(str.charAt(i));
			} else {
				sb.append(tempPinyin);
			}
		}
		return sb.toString();
	}

	//转换一个字符串
	public String getPurePinYinBlankLy(String str) {
		StringBuilder sb = new StringBuilder();
		String tempPinyin = null;
		for (int i = 0; i < str.length(); ++i) {
			tempPinyin = getCharacterPinYin(str.charAt(i));
			if (tempPinyin != null) {
				sb.append(tempPinyin);
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	//转换一个字符串
	public String getPureSPinYinBlankLy(String str) {
		StringBuilder sb = new StringBuilder();
		String tempPinyin = null;
		for (int i = 0; i < str.length(); ++i) {
			tempPinyin = getCharacterPinYin(str.charAt(i));
			if (tempPinyin != null) {
				sb.append(tempPinyin.charAt(0));
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	public static String testStringPinYin(String str) {
		PinyinUtils pinyin = new PinyinUtils();
		return pinyin.getStringPinYin(str);
	}

	public static String testPurePinYinBlankLy(String str) {
		PinyinUtils pinyin = new PinyinUtils();
		return pinyin.getPurePinYinBlankLy(str);
	}

	public static String testPureSPinYinBlankLy(String str) {
		PinyinUtils pinyin = new PinyinUtils();
		return pinyin.getPureSPinYinBlankLy(str);
	}
}
