package games.cuzus.killingfloor;

import games.cuzus.killingfloor.model.LogType;

import java.io.*;
import java.util.Calendar;
import java.text.SimpleDateFormat;

/**
 * @author geekrainian
 */
public class Util {
	private static SimpleDateFormat _formatter = new SimpleDateFormat("HH:mm:ss");

	public static void echo(String message, LogType type) {
		if (type == LogType.CONSOLE || type == LogType.BOTH) {
			System.out.println(_formatter.format(Calendar.getInstance().getTime()) + " " + message);
		}

		if (type == LogType.FILE || type == LogType.BOTH) {
			writeToFile(Config.LOG_FILE_NAME, Config.LOG_FILE_MAX_SIZE_MB * 1024, message);
		}
	}

	public static void echoWithInstigator(String message, LogType type, int instigatorId) {
		echo("(" + instigatorId + ") " + message, type);
	}

	public static boolean writeToFile(String path, int maxFileSize, String line) {
		if (isNullOrEmpty(path) || isNullOrEmpty(line) || maxFileSize == 0)
			return false;

		File file = new File(path);

		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		} else if (file.length() / 1024 > maxFileSize) {
			String basicName = file.getName();

			String name = basicName;
			String ext = "";

			int pos = basicName.lastIndexOf(".");

			if (pos > 0) {
				name = basicName.substring(0, pos);
				ext = basicName.substring(pos, basicName.length());
			}

			String suffix = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(Calendar.getInstance().getTime());

			if (!ext.isEmpty()) {
				basicName = name + "_" + suffix + ext;
			} else {
				basicName += "_" + suffix;
			}

			File file2 = new File(basicName);

			file.renameTo(file2);
		}

		try {
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);

			bw.write(
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()) + " " + line
							+ "\n");

			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public static boolean isNullOrEmpty(String str) {
		if (str == null || str.isEmpty())
			return true;
		return false;
	}

	public static String getKeyValueString(String key, String value) {
		if (isNullOrEmpty(key))
			return "";

		if (isNullOrEmpty(value))
			value = "";

		return "&" + key + "=" + value;
	}

	public static String getKeyValueString(String key, int value) {
		return getKeyValueString(key, String.valueOf(value));
	}

	public static String getKeyValueString(String key, long value) {
		return getKeyValueString(key, String.valueOf(value));
	}

	public static boolean isValidPlayerId(String id) {
		if (!isNullOrEmpty(id) && id.length() == 17 && !id.equalsIgnoreCase("76561197960265728"))
			return true;
		return false;
	}
}
