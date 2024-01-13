package games.cuzus.killingfloor;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import java.util.Properties;

import games.cuzus.killingfloor.model.LogType;

/**
 * @author geekrainian
 */
public final class Config {
	public static String SERVER_HOST;
	public static int SERVER_PORT;

	public static String LOG_FILE_NAME;
	public static int LOG_FILE_MAX_SIZE_MB;

	public static String MYSQL_URL;
	public static String MYSQL_USER;
	public static String MYSQL_PASSWORD;
	public static String MYSQL_TABLE;

	public static boolean load(final String configPath) {
		try {
			Properties p = new Properties();
			InputStream is = new FileInputStream(new File(configPath));
			p.load(is);
			is.close();

			SERVER_HOST = p.getProperty("ServerHost", "127.0.0.1");
			SERVER_PORT = Integer.parseInt(p.getProperty("ServerPort", "53495"));

			LOG_FILE_NAME = p.getProperty("LogFileName", "kftcp.log");
			LOG_FILE_MAX_SIZE_MB = Integer.parseInt(p.getProperty("LogFileMaxSizeMb", "2"));

			MYSQL_URL = p.getProperty("MySqlUrl", "jdbc:mysql://localhost:3306/killingfloor");
			MYSQL_USER = p.getProperty("MySqlUser", "root");
			MYSQL_PASSWORD = p.getProperty("MySqlPassword", "root");
			MYSQL_TABLE = p.getProperty("MySqlTable", "players");

			return true;
		} catch (Exception e) {
			Util.echo("Failed to load config: " + e.getMessage(), LogType.BOTH);
			e.printStackTrace();
		}

		return false;
	}
}
