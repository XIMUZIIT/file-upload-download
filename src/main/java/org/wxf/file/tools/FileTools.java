package org.wxf.file.tools;

import java.io.File;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Random;
import org.springframework.stereotype.Component;


/**
 * 文件相关工具类
 *
 * @author wxf
 */
@Component
public class FileTools {

	public boolean aCreateDir(String s) {

		for (int i = s.indexOf(File.separatorChar); i != -1; i =
				s.indexOf(File.separatorChar, i + 1)) {
			String s1 = s.substring(0, i + 1);
			File file = new File(s1);
			if (!file.exists()) {
				file.mkdir();
			}
		}

		return true;
	}

	private boolean aCreateNewFile(File file) {
		try {
			return file.createNewFile();
		} catch (Exception ex) {
			return false;
		}
	}

	private String aGetCharString(int i) {
		int j = i + 64;
		String s = "";
		if (j > 90) {
			int k = j - 90;
			int l = k % 26;
			int i1 = k / 26;
			if (l != 0) {
				i1++;
			}
			char c = (char) (i1 + 64);
			char c1 = (char) (j - i1 * 26);
			s = "" + c + "" + c1;
		} else {
			s = "" + (char) j;
		}
		return s;
	}

	/**
	 * 文件路径工具(时间)
	 *
	 * @return
	 */
	public String aGetCreateDir() {
		//当前时间
		LocalDate calendar = LocalDate.now();
		String s1 = String.valueOf(calendar.getYear());
		String s2 = calendar.getMonthValue() < 10 ? "0" + calendar.getMonthValue()
				: String.valueOf(calendar.getMonthValue());
		String s = s1 + s2 + File.separatorChar;
		return s;
	}

	public String aGetRandom() {
		Calendar calendar = Calendar.getInstance();
		Random random = new Random();
		long l = calendar.getTime().getTime();
		int i;
		for (i = random.nextInt(50); i == 0; i = random.nextInt(50)) {
			;
		}
		int j = Math.abs((int) (l / i));
		return "" + j;
	}

	public File aPolicyRename(File file) {
		if (aCreateNewFile(file)) {
			return file;
		}
		String s = file.getName();
		String s1 = null;
		String s2 = null;
		int i = s.lastIndexOf(".");
		if (i != -1) {
			s1 = s.substring(0, i);
			s2 = s.substring(i);
		} else {
			s1 = s;
			s2 = "";
		}
		String s3;
		for (int j = 0; !aCreateNewFile(file) && j < 9999; file = new File(file.getParent(), s3)) {
			j++;
			s3 = s1 + aGetRandom() + s2;
		}
		return file;
	}

}
