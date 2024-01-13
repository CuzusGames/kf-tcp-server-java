package games.cuzus.killingfloor.model;

/**
 * @author geekrainian
 */
public class RequestResultMessage {
	public static final String SUCCESS_NO_MESSAGE = "ok=1";
	public static final String SUCCESS_NO_DATA_FOR_PLAYER = "ok=1&message=no data";
	public static final String ERROR_BAD_PLAYER_ID = "error=1&message=bad id";
	public static final String ERROR_WRONG_QUERY = "error=1&message=wrong query";
	public static final String ERROR_WRONG_COMMAND = "error=1&message=wrong command";
	public static final String ERROR_SQL = "error=1&message=sql error";
}
