package games.cuzus.killingfloor.model;

import games.cuzus.killingfloor.Config;

/**
 * This class is designed for building SQL queries based on the database schema
 * and URL mapping.
 *
 * @see /sql/players.sql
 * @see /src/main/java/games/cuzus/killingfloor/model/RequestFieldMapping.java
 * @see /src/main/java/games/cuzus/killingfloor/model/PlayerModel.java
 *
 * @author geekrainian
 */
public class SQLFieldMapper {
	private static SQLFieldMapper _instance;

	public static SQLFieldMapper getInstance() {
		if (_instance == null) {
			_instance = new SQLFieldMapper();
		}

		return _instance;
	}

	// NOTE: Order is important here!
	public final String[] FIELDS = new String[] {
			"id",

			"steam_id",
			"`name`",
			"veterancy",
			"`character`",

			"heal_points",
			"weld_points",
			"headshots",
			"shotgun_damage",
			"bullpup_damage",
			"melee_damage",
			"fire_damage",
			"explosive_damage",

			"wins",
			"losts",
			"map_score",
			"online_time",
			"total_kills",
			"waves_total",
			"team_kills",
			"stalker_kills",
			"scrake_kills",
			"fleshpound_kills",
			"patriarch_kills_main",
			"patriarch_kills_weak",
			"miniboss_kills",
			"account_key",
			"last_map",
			"reg_ip",
			"last_ip",
			"first_visit",
			"last_visit",
			"first_enter",
			"last_win_date",
			"last_win_map",
			"prestige",

			"clan_id",
			"clan_name",
			"clan_abbr",
			"clan_icon",
			"clan_leader",
			"clan_join_date",
			"clan_zombie_kills",
			"clan_patriarch_kills",

			"title_text",
			"inventory",
			"`lang`",

			"gunslinger_damage",

			"premium_type",
			"premium_expiration_date",

			"inactive"
	};

	public String buildIdSelectQuery() {
		return "SELECT id FROM " + Config.MYSQL_TABLE + " WHERE " + FIELDS[1] + "=?;"; // FIELDS[1] = steam_id
	}

	public String buildFullSelectQuery() {
		String query = "SELECT ";

		for (int i = 0; i < FIELDS.length; i++) {
			query += FIELDS[i] + (i < FIELDS.length - 1 ? "," : "");
		}

		query += " FROM " + Config.MYSQL_TABLE;
		query += " WHERE " + FIELDS[1] + "=?;"; // FIELDS[1] = steam_id

		return query;
	}

	public String buildUpdateQuery(int startFromIndex) {
		String query = "UPDATE " + Config.MYSQL_TABLE + " SET ";

		for (int i = startFromIndex; i < FIELDS.length; i++) {
			query += FIELDS[i] + "=?" + (i < FIELDS.length - 1 ? "," : "");
		}

		query += " WHERE id=?;";

		return query;
	}

	public String buildInsertQuery(int startFromIndex) {
		String query = "INSERT INTO " + Config.MYSQL_TABLE + " (";

		for (int i = startFromIndex; i < FIELDS.length; i++) {
			query += FIELDS[i] + (i < FIELDS.length - 1 ? "," : "");
		}

		query += ") VALUES (";

		for (int i = startFromIndex; i < FIELDS.length; i++) {
			query += "?" + (i < FIELDS.length - 1 ? "," : "");
		}

		query += ");";

		return query;
	}

	public String buildClansListSelectQuery() {
		return "SELECT clan_id, clan_name FROM " + Config.MYSQL_TABLE
				+ " WHERE clan_id > 0 AND clan_name IS NOT NULL GROUP BY(clan_id);";
	}
}
