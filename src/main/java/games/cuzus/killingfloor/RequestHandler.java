package games.cuzus.killingfloor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import games.cuzus.killingfloor.model.LogType;
import games.cuzus.killingfloor.model.PlayerModel;
import games.cuzus.killingfloor.model.RequestResultMessage;
import games.cuzus.killingfloor.model.SQLFieldMapper;

/**
 * @author geekrainian
 */
public class RequestHandler {
	private static RequestHandler _instance;

	public static RequestHandler getInstance() {
		if (_instance == null) {
			_instance = new RequestHandler();
		}

		return _instance;
	}

	private long _succeedRequests;
	private long _failedRequests;

	public RequestHandler() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			Util.echo("JDBC driver is not allowed: " + e.getMessage(), LogType.BOTH);
			e.printStackTrace();
		}
	}

	private String getUrl() {
		return Config.MYSQL_URL;
	}

	private String getUser() {
		return Config.MYSQL_USER;
	}

	private String getPass() {
		return Config.MYSQL_PASSWORD;
	}

	private void closeConnection(Connection con) {
		if (con == null)
			return;

		try {
			con.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	private void executed(String action, boolean succeed, int instigatorId) {
		if (succeed) {
			_succeedRequests++;
		} else {
			_failedRequests++;
		}

		if (!Util.isNullOrEmpty(action)) {
			Util.echoWithInstigator(action + " succeed", LogType.BOTH, instigatorId);
		}

		Util.echo("Succeed " + _succeedRequests + "/" + (_succeedRequests + _failedRequests) + " requests",
				LogType.BOTH);
	}

	public String parse(String request, int instigatorId) {
		Util.echoWithInstigator("Parsing request: " + request, LogType.FILE, instigatorId);

		String[] params = request.split("&");

		for (String param : params) {
			if (Util.isNullOrEmpty(param))
				continue;

			String[] keyValue = param.split("=");
			String key, value;

			try {
				key = keyValue[0];
				value = keyValue[1];
			} catch (ArrayIndexOutOfBoundsException e) {
				// Wrong key value
				continue;
			} catch (Exception e) {
				Util.echo("Unknown parsing exception: " + e.getMessage(), LogType.BOTH);
				e.printStackTrace();
				continue;
			}

			if (Util.isNullOrEmpty(key) || Util.isNullOrEmpty(value)) {
				continue;
			}

			Util.echoWithInstigator("key = " + key + ", value = " + value, LogType.FILE, instigatorId);

			if (key.equalsIgnoreCase("action")) {
				PlayerModel pm = new PlayerModel(request.substring(param.length()));

				if (!Util.isValidPlayerId(pm.getId())) {
					executed("Player model parsing", false, instigatorId);
					return RequestResultMessage.ERROR_BAD_PLAYER_ID;
				}

				switch (Integer.parseInt(value)) {
					// Load
					case 0: {
						String playerLoadResult = loadPlayer(pm.getId());

						if (playerLoadResult == null) {
							executed("Player loading", false, instigatorId);
							return RequestResultMessage.ERROR_SQL;
						} else {
							executed("Player loading", true, instigatorId);
							return playerLoadResult;
						}
					}
					// Save
					case 1: {
						if (savePlayer(pm)) {
							executed("Player saving", true, instigatorId);
							return RequestResultMessage.SUCCESS_NO_MESSAGE;
						} else {
							executed("Player saving", false, instigatorId);
							return RequestResultMessage.ERROR_SQL;
						}
					}
					// Get clans data
					case 5: {
						String data = getClanData();
						executed("Clan data", !Util.isNullOrEmpty(data), instigatorId);
						return data;
					}
					default:
						executed("Parsing command from query", false, instigatorId);
						return RequestResultMessage.ERROR_WRONG_COMMAND;
				}
			}
		}

		executed("Parsing query", false, instigatorId);
		return RequestResultMessage.ERROR_WRONG_QUERY;
	}

	private String getClanData() {
		Connection con = null;

		List<String> clansList = new ArrayList<>();

		try {
			con = DriverManager.getConnection(getUrl(), getUser(), getPass());
			PreparedStatement ps = con.prepareStatement(SQLFieldMapper.getInstance().buildClansListSelectQuery());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				clansList.add(rs.getString("clan_id") + "," + rs.getString("clan_name"));
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			Util.echo("Clans loading exception: " + e.getMessage(), LogType.BOTH);
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}

		if (clansList.size() > 0) {
			String model = String.join(";", clansList);

			return model;
		}

		return "1234,noclans"; // NOTE: Do not return an empty string, provide any fake data instead
	}

	private String loadPlayer(String id) {
		if (Util.isNullOrEmpty(id)) {
			return null;
		}

		PlayerModel pm = null;
		Connection con = null;
		boolean isPlayerExists = false;

		try {
			con = DriverManager.getConnection(getUrl(), getUser(), getPass());
			PreparedStatement ps = con.prepareStatement(SQLFieldMapper.getInstance().buildFullSelectQuery());
			ps.setString(1, id);
			ResultSet rs = ps.executeQuery();

			pm = new PlayerModel(id, true);

			if (rs.next()) {
				isPlayerExists = true;

				pm.setId(String.valueOf(rs.getInt("id")));

				pm.setName(rs.getString("name"));
				pm.setSelectedVeterancy(rs.getString("veterancy"));
				pm.setSelectedCharacter(rs.getString("character"));

				pm.setHealPoints(rs.getInt("heal_points"));
				pm.setWeldPoints(rs.getInt("weld_points"));
				pm.setHeadshots(rs.getInt("headshots"));
				pm.setShotgunDamage(rs.getInt("shotgun_damage"));
				pm.setBullpupDamage(rs.getInt("bullpup_damage"));
				pm.setMeleeDamage(rs.getInt("melee_damage"));
				pm.setFireDamage(rs.getInt("fire_damage"));
				pm.setExplosiveDamage(rs.getInt("explosive_damage"));

				pm.setWins(rs.getInt("wins"));
				pm.setLosts(rs.getInt("losts"));
				pm.setMapScore(rs.getLong("map_score"));
				pm.setOnlineTime(rs.getInt("online_time"));

				pm.setTotalKills(rs.getInt("total_kills"));
				pm.setWavesTotal(rs.getLong("waves_total"));
				pm.setTeamKills(rs.getInt("team_kills"));

				pm.setStalkerKills(rs.getInt("stalker_kills"));
				pm.setScrakeKills(rs.getInt("scrake_kills"));
				pm.setFleshpoundKills(rs.getInt("fleshpound_kills"));
				pm.setPatriarchKillsMain(rs.getInt("patriarch_kills_main"));
				pm.setPatriarchKillsWeak(rs.getInt("patriarch_kills_weak"));
				pm.setMinibossKills(rs.getInt("miniboss_kills"));

				pm.setAccountKey(rs.getString("account_key"));

				pm.setLastMap(rs.getString("last_map"));
				pm.setRegIp(rs.getString("reg_ip"));
				pm.setLastIp(rs.getString("last_ip"));
				pm.setFirstVisit(rs.getString("first_visit"));
				pm.setLastVisit(rs.getString("last_visit"));
				pm.setFirstEnter(rs.getInt("first_enter"));
				pm.setLastWinDate(rs.getString("last_win_date"));
				pm.setLastWinMap(rs.getString("last_win_map"));

				pm.setPrestige(rs.getInt("prestige"));

				pm.setClanId(rs.getInt("clan_id"));
				pm.setClanName(rs.getString("clan_name"));
				pm.setClanAbbr(rs.getString("clan_abbr"));
				pm.setClanIcon(rs.getString("clan_icon"));
				pm.setIsClanLeader(rs.getByte("clan_leader"));
				pm.setClanJoinDate(rs.getString("clan_join_date"));
				pm.setClanZombieKills(rs.getString("clan_zombie_kills"));
				pm.setClanPatriarchKills(rs.getString("clan_patriarch_kills"));

				pm.setTitleText(rs.getString("title_text"));

				pm.setInventory(rs.getString("inventory"));
				pm.setLang(rs.getInt("lang"));

				pm.setGunslingerDamage(rs.getLong("gunslinger_damage"));

				pm.setPremiumType(rs.getByte("premium_type"));
				pm.setPremiumExpirationDate(rs.getString("premium_expiration_date"));

				pm.setInactive(rs.getInt("inactive"));

				Util.echo("Loading player: " + id, LogType.FILE);
			} else {
				Util.echo("Player " + id + " does not exists in database", LogType.FILE);
			}

			rs.close();
			ps.close();
		} catch (Exception e) {
			Util.echo("Player loading exception: " + e.getMessage(), LogType.BOTH);
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}

		if (pm == null)
			return null;

		if (isPlayerExists)
			return pm.toStringModel();

		return pm.toStringAsEmpty();
	}

	private boolean savePlayer(PlayerModel pm) {
		if (pm == null) {
			return false;
		}

		boolean success = false;
		Connection con = null;

		try {
			con = DriverManager.getConnection(getUrl(), getUser(), getPass());
			PreparedStatement ps = con.prepareStatement(SQLFieldMapper.getInstance().buildIdSelectQuery());
			ps.setString(1, pm.getId());
			ResultSet rs = ps.executeQuery();

			int identifier;

			if (rs.next()) {
				// UPDATE
				identifier = rs.getInt("id");

				int i = 1;
				int startFromIndex = 2; // start index in SQLFieldMapper.FIELDS object, should start with 'name'

				ps = con.prepareStatement(SQLFieldMapper.getInstance().buildUpdateQuery(startFromIndex));

				ps.setString(i, pm.getName());
				ps.setString(++i, pm.getSelectedVeterancy());

				if (Util.isNullOrEmpty(pm.getSelectedCharacter()))
					ps.setNull(++i, Types.VARCHAR);
				else
					ps.setString(++i, pm.getSelectedCharacter());

				ps.setInt(++i, pm.getHealPoints());
				ps.setInt(++i, pm.getWeldPoints());
				ps.setInt(++i, pm.getHeadshots());
				ps.setInt(++i, pm.getShotgunDamage());
				ps.setInt(++i, pm.getBullpupDamage());
				ps.setInt(++i, pm.getMeleeDamage());
				ps.setInt(++i, pm.getFireDamage());
				ps.setInt(++i, pm.getExplosiveDamage());

				ps.setInt(++i, pm.getWins());
				ps.setInt(++i, pm.getLosts());
				ps.setLong(++i, pm.getMapScore());
				ps.setInt(++i, pm.getOnlineTime());

				ps.setInt(++i, pm.getTotalKills());
				ps.setLong(++i, pm.getWavesTotal());
				ps.setInt(++i, pm.getTeamKills());

				ps.setInt(++i, pm.getStalkerKills());
				ps.setInt(++i, pm.getScrakeKills());
				ps.setInt(++i, pm.getFleshpoundKills());
				ps.setInt(++i, pm.getPatriarchKillsMain());
				ps.setInt(++i, pm.getPatriarchKillsWeak());
				ps.setInt(++i, pm.getMinibossKills());

				if (Util.isNullOrEmpty(pm.getAccountKey()))
					ps.setNull(++i, Types.VARCHAR);
				else
					ps.setString(++i, pm.getAccountKey());

				ps.setString(++i, pm.getLastMap());
				ps.setString(++i, pm.getRegIp());
				ps.setString(++i, pm.getLastIp());
				ps.setString(++i, pm.getFirstVisit());
				ps.setString(++i, pm.getLastVisit());
				ps.setInt(++i, pm.getFirstEnter()); // TODO: setByte()
				ps.setString(++i, pm.getLastWinDate());
				ps.setString(++i, pm.getLastWinMap());

				ps.setInt(++i, pm.getPrestige()); // TODO: setByte()

				ps.setInt(++i, pm.getClanId());
				ps.setString(++i, pm.getClanName());
				ps.setString(++i, pm.getClanAbbr());
				ps.setString(++i, pm.getClanIcon());
				ps.setInt(++i, pm.getIsClanLeader());
				ps.setString(++i, pm.getClanJoinDate());
				ps.setLong(++i, pm.getClanZombieKills());
				ps.setLong(++i, pm.getClanPatriarchKills());

				if (Util.isNullOrEmpty(pm.getTitleText()))
					ps.setNull(++i, Types.VARCHAR);
				else
					ps.setString(++i, pm.getTitleText());

				if (Util.isNullOrEmpty(pm.getInventory()))
					ps.setNull(++i, Types.VARCHAR);
				else
					ps.setString(++i, pm.getInventory());

				ps.setInt(++i, pm.getLang());

				ps.setLong(++i, pm.getGunslingerDamage());

				ps.setInt(++i, pm.getPremiumType());
				ps.setString(++i, pm.getPremiumExpirationDate());

				ps.setInt(++i, pm.getInactive()); // TODO: setByte()

				ps.setInt(++i, identifier);

				Util.echo("Update player in database: " + pm.getId(), LogType.FILE);
			} else {
				// CREATE
				int i = 1;
				int startFromIndex = 1; // start index in SQLFieldMapper.FIELDS object, should start with 'steam_id'

				ps = con.prepareStatement(SQLFieldMapper.getInstance().buildInsertQuery(startFromIndex));

				ps.setString(i, pm.getId());
				ps.setString(++i, pm.getName());
				ps.setString(++i, pm.getSelectedVeterancy());

				if (Util.isNullOrEmpty(pm.getSelectedCharacter()))
					ps.setNull(++i, Types.VARCHAR);
				else
					ps.setString(++i, pm.getSelectedCharacter());

				ps.setInt(++i, pm.getHealPoints());
				ps.setInt(++i, pm.getWeldPoints());
				ps.setInt(++i, pm.getHeadshots());
				ps.setInt(++i, pm.getShotgunDamage());
				ps.setInt(++i, pm.getBullpupDamage());
				ps.setInt(++i, pm.getMeleeDamage());
				ps.setInt(++i, pm.getFireDamage());
				ps.setInt(++i, pm.getExplosiveDamage());

				ps.setInt(++i, pm.getWins());
				ps.setInt(++i, pm.getLosts());
				ps.setLong(++i, pm.getMapScore());
				ps.setInt(++i, pm.getOnlineTime());

				ps.setInt(++i, pm.getTotalKills());
				ps.setLong(++i, pm.getWavesTotal());
				ps.setInt(++i, pm.getTeamKills());

				ps.setInt(++i, pm.getStalkerKills());
				ps.setInt(++i, pm.getScrakeKills());
				ps.setInt(++i, pm.getFleshpoundKills());
				ps.setInt(++i, pm.getPatriarchKillsMain());
				ps.setInt(++i, pm.getPatriarchKillsWeak());
				ps.setInt(++i, pm.getMinibossKills());

				if (Util.isNullOrEmpty(pm.getAccountKey()))
					ps.setNull(++i, Types.VARCHAR);
				else
					ps.setString(++i, pm.getAccountKey());

				ps.setString(++i, pm.getLastMap());
				ps.setString(++i, pm.getRegIp());
				ps.setString(++i, pm.getLastIp());
				ps.setString(++i, pm.getFirstVisit());
				ps.setString(++i, pm.getLastVisit());
				ps.setInt(++i, pm.getFirstEnter()); // TODO: setByte()
				ps.setString(++i, pm.getLastWinDate());
				ps.setString(++i, pm.getLastWinMap());

				ps.setInt(++i, pm.getPrestige()); // TODO: setByte()

				ps.setInt(++i, pm.getClanId());
				ps.setString(++i, pm.getClanName());
				ps.setString(++i, pm.getClanAbbr());
				ps.setString(++i, pm.getClanIcon());
				ps.setInt(++i, pm.getIsClanLeader());
				ps.setString(++i, pm.getClanJoinDate());
				ps.setLong(++i, pm.getClanZombieKills());
				ps.setLong(++i, pm.getClanPatriarchKills());

				if (Util.isNullOrEmpty(pm.getTitleText()))
					ps.setNull(++i, Types.VARCHAR);
				else
					ps.setString(++i, pm.getTitleText());

				if (Util.isNullOrEmpty(pm.getInventory()))
					ps.setNull(++i, Types.VARCHAR);
				else
					ps.setString(++i, pm.getInventory());

				ps.setInt(++i, pm.getLang());

				ps.setLong(++i, pm.getGunslingerDamage());

				ps.setInt(++i, pm.getPremiumType());
				ps.setString(++i, pm.getPremiumExpirationDate());

				ps.setInt(++i, pm.getInactive()); // TODO: setByte()

				Util.echo("Insert new player to database: " + pm.getId(), LogType.FILE);
			}

			ps.execute();
			ps.close();

			success = true;
		} catch (Exception e) {
			Util.echo("Player saving exception: " + e.getMessage(), LogType.BOTH);
			e.printStackTrace();
		} finally {
			closeConnection(con);
		}

		return success;
	}
}
