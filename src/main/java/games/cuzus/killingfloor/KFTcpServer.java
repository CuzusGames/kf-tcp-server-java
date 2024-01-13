package games.cuzus.killingfloor;

import games.cuzus.killingfloor.model.LogType;

import java.io.File;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author geekrainian
 */
public class KFTcpServer {
	public static void main(String[] args) {
		String configPath = args[0];

		if (configPath.isEmpty()) {
			configPath = "kftcpserver.properties";
		}

		File f = new File(configPath);
		if (!f.exists() || f.isDirectory()) {
			Util.echo("WARNING: Configuration file does not exist, using defults...", LogType.BOTH);
		}

		Config.load(configPath);

		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(Config.SERVER_PORT, 0, InetAddress.getByName(Config.SERVER_HOST));

			Util.echo("Server socket opened at: " + serverSocket.getInetAddress().getHostAddress() + ":"
					+ serverSocket.getLocalPort(), LogType.BOTH);

			while (true) {
				final Socket activeSocket = serverSocket.accept();

				Util.echo(
						"Connected client socket: " + activeSocket.getInetAddress().getHostAddress() + ":"
								+ activeSocket.getPort(),
						LogType.FILE);

				ClientSocketThread thread = new ClientSocketThread(activeSocket);
				thread.start();
			}
		} catch (Exception e) {
			Util.echo("Server socket error: " + e.getMessage(), LogType.BOTH);
			e.printStackTrace();
		} finally {
			try {
				if (serverSocket != null) {
					serverSocket.close();
				}
			} catch (Exception e) {
			}
		}
	}
}
