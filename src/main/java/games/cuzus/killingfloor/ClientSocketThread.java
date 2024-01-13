package games.cuzus.killingfloor;

import games.cuzus.killingfloor.model.LogType;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import java.net.Socket;
import java.net.SocketException;

/**
 * @author geekrainian
 */
public class ClientSocketThread extends Thread {
	Socket _clientSocket;

	public ClientSocketThread(Socket clientSocket) {
		_clientSocket = clientSocket;
	}

	public void run() {
		try {
			Util.echo("Running thread for client socket: " + _clientSocket.getInetAddress().getHostAddress() + ":"
					+ _clientSocket.getPort(), LogType.FILE);

			BufferedReader bufReader;
			BufferedWriter bufWriter;

			bufReader = new BufferedReader(new InputStreamReader(_clientSocket.getInputStream()));
			bufWriter = new BufferedWriter(new OutputStreamWriter(_clientSocket.getOutputStream()));

			String input;

			while ((input = bufReader.readLine()) != null) {
				String answer = RequestHandler.getInstance().parse(input, _clientSocket.hashCode());
				bufWriter.write(answer);
				bufWriter.flush();
				break;
			}

			_clientSocket.close();
		} catch (SocketException se) {
			Util.echo("SocketException: " + se.getMessage(), LogType.BOTH);
			se.printStackTrace();
		} catch (Exception e) {
			Util.echo("Exception: " + e.getMessage(), LogType.BOTH);
			e.printStackTrace();
		}
	}
}
