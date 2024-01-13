package games.cuzus.killingfloor.model;

/**
 * Mapping for StatsObject fields
 *
 * @author geekrainian
 *
 * @see /sql/players.sql
 * @see /src/main/java/games/cuzus/killingfloor/model/SQLFieldMapper.java
 * @see /src/main/java/games/cuzus/killingfloor/model/PlayerModel.java
 */
public class RequestFieldMapping {
	public static final String ID = "id";
	public static final String NAME = "name";

	public static final String SELECTED_VETERANCY = "vet";
	public static final String SELECTED_CHARACTER = "char";

	public static final String HEAL_POINTS = "healpo";
	public static final String WELD_POINTS = "weldpo";
	public static final String HEADSHOTS = "heads";
	public static final String SHOTGUN_DAMAGE = "sgdam";
	public static final String BULLPUP_DAMAGE = "bpdam";
	public static final String MELEE_DAMAGE = "meldam";
	public static final String FIRE_DAMAGE = "firedam";
	public static final String EXPLOSIVE_DAMAGE = "expdam";

	public static final String WINS = "wins";
	public static final String LOSTS = "losts";
	public static final String MAP_SCORE = "mapsc";
	public static final String ONLINE_TIME = "onl";

	public static final String TOTAL_KILLS = "totalki";
	public static final String WAVES_TOTAL = "wavttl";
	public static final String TEAM_KILLS = "teamki";

	public static final String STALKER_KILLS = "stalkki";
	public static final String SCRAKE_KILLS = "scrki";
	public static final String FLESHPOUND_KILLS = "fpki";
	public static final String PATRIARCH_MAIN_KILLS = "patmki";
	public static final String PATRIARCH_WEAK_KILLS = "patwki";
	public static final String MINIBOSS_KILLS = "minbki";

	public static final String ACCOUNT_KEY = "acckey";

	public static final String LAST_MAP = "lastmap";
	public static final String REG_IP = "regip";
	public static final String LAST_IP = "lastip";
	public static final String FIRST_VISIT = "fvis";
	public static final String LAST_VISIT = "lvis";
	public static final String FIRST_ENTER = "fentr";

	public static final String LAST_WIN_DATE = "lwindt";
	public static final String LAST_WIN_MAP = "lwinmp";

	public static final String PRESTIGE = "prestige";

	public static final String CLAN_ID = "clanid";
	public static final String CLAN_NAME = "clanname";
	public static final String CLAN_ABBR = "clanabbr";
	public static final String CLAN_ICON = "clanicon";
	public static final String CLAN_LEADER = "clanlead";
	public static final String CLAN_JOIN_DATE = "clandate";
	public static final String CLAN_ZOMBIE_KILLS = "clanzki";
	public static final String CLAN_PATRIARCH_KILLS = "clanpki";

	public static final String TITLE_TEXT = "tittext";

	public static final String INVENTORY = "inv";
	public static final String LANG = "lang";

	public static final String GUNSLINGER_DAMAGE = "gunsdam";

	public static final String PREMIUM_TYPE = "premtype";
	public static final String PREMIUM_EXPIRATION_DATE = "premdate";

	public static final String INACTIVE = "inact";
}
