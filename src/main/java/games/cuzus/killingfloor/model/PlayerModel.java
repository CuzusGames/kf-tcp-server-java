package games.cuzus.killingfloor.model;

import games.cuzus.killingfloor.Util;

/**
 * @author geekrainian
 *
 * @see /sql/players.sql
 * @see /src/main/java/games/cuzus/killingfloor/model/SQLFieldMapper.java
 * @see /src/main/java/games/cuzus/killingfloor/model/RequestFieldMapping.java
 */
public class PlayerModel {
	// region Fields
	private String id;
	private String name;

	private String selectedVeterancy;
	private String selectedChar;

	private int healPoints;
	private int weldPoints;
	private int headshots;
	private int shotgunDamage;
	private int bullpupDamage;
	private int meleeDamage;
	private int fireDamage;
	private int explosiveDamage;

	private int wins;
	private int losts;
	private long mapScore;
	private int onlineTime;

	private int killsTotal;
	private long wavesTotal;
	private int teamKills;

	private int killedStalkers;
	private int killedScrakes;
	private int killedFleshpounds;
	private int killedPatriarchsMain;
	private int killedPartiarchsWeak;

	private int killedMinibosses;

	private int lang;
	private String accountKey;

	private String titleText;

	private String regIp; // ip
	private String lastIp; // ip
	private String firstVisit; // date
	private String lastVisit; // date
	private String lastMap;
	private int firstEnter;

	private String lastWinDate;
	private String lastWinMap;

	private int prestige;
	private String inventory;

	private int clanId;
	private String clanName;
	private String clanAbbr;
	private String clanIcon;
	private String clanJoinDate;
	private byte isClanLeader;
	private long clanZombieKills;
	private long clanPatriarchKills;

	private byte premiumType;
	private String premiumExpirationDate;

	private int inactive;

	private long gunslingerDamage;
	// endregion

	public PlayerModel(String id, boolean neverUsed) {
		setId(id);
	}

	public PlayerModel(String query) {
		if (Util.isNullOrEmpty(query)) {
			return;
		}

		String[] params = query.split("&");

		if (params.length < 1) {
			return;
		}

		for (String param : params) {
			if (Util.isNullOrEmpty(param))
				continue;

			String[] keyValue = param.split("=");
			String key, value;

			try {
				key = keyValue[0];
				value = keyValue[1];
			} catch (ArrayIndexOutOfBoundsException e) {
				// Ignore bad format
				continue;
			}

			if (Util.isNullOrEmpty(key) || Util.isNullOrEmpty(value)) {
				continue;
			}

			switch (key) {
				case RequestFieldMapping.ID:
					setId(value);
					break;
				case RequestFieldMapping.NAME:
					setName(value);
					break;
				case RequestFieldMapping.SELECTED_VETERANCY:
					setSelectedVeterancy(value);
					break;
				case RequestFieldMapping.SELECTED_CHARACTER:
					setSelectedCharacter(value);
					break;
				case RequestFieldMapping.HEAL_POINTS:
					setHealpoints(value);
					break;
				case RequestFieldMapping.WELD_POINTS:
					setWeldPoints(value);
					break;
				case RequestFieldMapping.HEADSHOTS:
					setHeadshots(value);
					break;
				case RequestFieldMapping.SHOTGUN_DAMAGE:
					setShotgunDamage(value);
					break;
				case RequestFieldMapping.BULLPUP_DAMAGE:
					setBullpupDamage(value);
					break;
				case RequestFieldMapping.MELEE_DAMAGE:
					setMeleeDamage(value);
					break;
				case RequestFieldMapping.FIRE_DAMAGE:
					setFireDamage(value);
					break;
				case RequestFieldMapping.EXPLOSIVE_DAMAGE:
					setExplosiveDamage(value);
					break;
				case RequestFieldMapping.WINS:
					setWins(value);
					break;
				case RequestFieldMapping.LOSTS:
					setLosts(value);
					break;
				case RequestFieldMapping.MAP_SCORE:
					setMapScore(value);
					break;
				case RequestFieldMapping.ONLINE_TIME:
					setOnlineTime(value);
					break;
				case RequestFieldMapping.TOTAL_KILLS:
					setTotalKills(value);
					break;
				case RequestFieldMapping.WAVES_TOTAL:
					setWavesTotal(value);
					break;
				case RequestFieldMapping.TEAM_KILLS:
					setTeamKills(value);
					break;
				case RequestFieldMapping.STALKER_KILLS:
					setStalkerKills(value);
					break;
				case RequestFieldMapping.SCRAKE_KILLS:
					setScrakeKills(value);
					break;
				case RequestFieldMapping.FLESHPOUND_KILLS:
					setFleshpoundKills(value);
					break;
				case RequestFieldMapping.PATRIARCH_MAIN_KILLS:
					setPatriarchKillsMain(value);
					break;
				case RequestFieldMapping.PATRIARCH_WEAK_KILLS:
					setPatriarchKillsWeak(value);
					break;
				case RequestFieldMapping.MINIBOSS_KILLS:
					setMinibossKills(value);
					break;
				case RequestFieldMapping.ACCOUNT_KEY:
					setAccountKey(value);
					break;
				case RequestFieldMapping.LAST_MAP:
					setLastMap(value);
					break;
				case RequestFieldMapping.REG_IP:
					setRegIp(value);
					break;
				case RequestFieldMapping.LAST_IP:
					setLastIp(value);
					break;
				case RequestFieldMapping.FIRST_VISIT:
					setFirstVisit(value);
					break;
				case RequestFieldMapping.LAST_VISIT:
					setLastVisit(value);
					break;
				case RequestFieldMapping.FIRST_ENTER:
					setFirstEnter(value);
					break;
				case RequestFieldMapping.LAST_WIN_DATE:
					setLastWinDate(value);
					break;
				case RequestFieldMapping.LAST_WIN_MAP:
					setLastWinMap(value);
					break;
				case RequestFieldMapping.PRESTIGE:
					setPrestige(value);
					break;

				// Clans

				case RequestFieldMapping.CLAN_ID:
					setClanId(value);
					break;
				case RequestFieldMapping.CLAN_NAME:
					setClanName(value);
					break;
				case RequestFieldMapping.CLAN_ABBR:
					setClanAbbr(value);
					break;
				case RequestFieldMapping.CLAN_ICON:
					setClanIcon(value);
					break;
				case RequestFieldMapping.CLAN_LEADER:
					setIsClanLeader(value);
					break;
				case RequestFieldMapping.CLAN_JOIN_DATE:
					setClanJoinDate(value);
					break;
				case RequestFieldMapping.CLAN_ZOMBIE_KILLS:
					setClanZombieKills(value);
					break;
				case RequestFieldMapping.CLAN_PATRIARCH_KILLS:
					setClanPatriarchKills(value);
					break;

				case RequestFieldMapping.TITLE_TEXT:
					setTitleText(value);
					break;
				case RequestFieldMapping.INVENTORY:
					setInventory(value);
					break;
				case RequestFieldMapping.LANG:
					setLang(value);
					break;

				case RequestFieldMapping.GUNSLINGER_DAMAGE:
					setGunslingerDamage(value);
					break;

				case RequestFieldMapping.PREMIUM_TYPE:
					setPremiumType(value);
					break;
				case RequestFieldMapping.PREMIUM_EXPIRATION_DATE:
					setPremiumExpirationDate(value);
					break;

				case RequestFieldMapping.INACTIVE:
					setInactive(value);
					break;
			}
		}
	}

	// NOTE: Order is important here!
	public String toStringModel() {
		String model = "";

		model += Util.getKeyValueString(RequestFieldMapping.ID, getId());
		// model += Util.getKeyValueString(RequestFieldMapping.NAME, getName());

		model += Util.getKeyValueString(RequestFieldMapping.SELECTED_VETERANCY, getSelectedVeterancy());
		model += Util.getKeyValueString(RequestFieldMapping.SELECTED_CHARACTER, getSelectedCharacter());

		model += Util.getKeyValueString(RequestFieldMapping.HEAL_POINTS, getHealPoints());
		model += Util.getKeyValueString(RequestFieldMapping.WELD_POINTS, getWeldPoints());
		model += Util.getKeyValueString(RequestFieldMapping.HEADSHOTS, getHeadshots());
		model += Util.getKeyValueString(RequestFieldMapping.SHOTGUN_DAMAGE, getShotgunDamage());
		model += Util.getKeyValueString(RequestFieldMapping.BULLPUP_DAMAGE, getBullpupDamage());
		model += Util.getKeyValueString(RequestFieldMapping.MELEE_DAMAGE, getMeleeDamage());
		model += Util.getKeyValueString(RequestFieldMapping.FIRE_DAMAGE, getFireDamage());
		model += Util.getKeyValueString(RequestFieldMapping.EXPLOSIVE_DAMAGE, getExplosiveDamage());

		model += Util.getKeyValueString(RequestFieldMapping.WINS, getWins());
		model += Util.getKeyValueString(RequestFieldMapping.LOSTS, getLosts());
		model += Util.getKeyValueString(RequestFieldMapping.MAP_SCORE, getMapScore());
		model += Util.getKeyValueString(RequestFieldMapping.ONLINE_TIME, getOnlineTime());

		model += Util.getKeyValueString(RequestFieldMapping.TOTAL_KILLS, getTotalKills());
		model += Util.getKeyValueString(RequestFieldMapping.WAVES_TOTAL, getWavesTotal());
		model += Util.getKeyValueString(RequestFieldMapping.TEAM_KILLS, getTeamKills());

		model += Util.getKeyValueString(RequestFieldMapping.STALKER_KILLS, getStalkerKills());
		model += Util.getKeyValueString(RequestFieldMapping.SCRAKE_KILLS, getScrakeKills());
		model += Util.getKeyValueString(RequestFieldMapping.FLESHPOUND_KILLS, getFleshpoundKills());
		model += Util.getKeyValueString(RequestFieldMapping.PATRIARCH_MAIN_KILLS, getPatriarchKillsMain());
		model += Util.getKeyValueString(RequestFieldMapping.PATRIARCH_WEAK_KILLS, getPatriarchKillsWeak());
		model += Util.getKeyValueString(RequestFieldMapping.MINIBOSS_KILLS, getMinibossKills());

		model += Util.getKeyValueString(RequestFieldMapping.ACCOUNT_KEY, getAccountKey());

		model += Util.getKeyValueString(RequestFieldMapping.LAST_MAP, getLastMap());
		model += Util.getKeyValueString(RequestFieldMapping.REG_IP, getRegIp());
		model += Util.getKeyValueString(RequestFieldMapping.LAST_IP, getLastIp());
		model += Util.getKeyValueString(RequestFieldMapping.FIRST_VISIT, getFirstVisit());
		model += Util.getKeyValueString(RequestFieldMapping.LAST_VISIT, getLastVisit());
		model += Util.getKeyValueString(RequestFieldMapping.FIRST_ENTER, getFirstEnter());
		model += Util.getKeyValueString(RequestFieldMapping.LAST_WIN_DATE, getLastWinDate());
		model += Util.getKeyValueString(RequestFieldMapping.LAST_WIN_MAP, getLastWinMap());

		model += Util.getKeyValueString(RequestFieldMapping.PRESTIGE, getPrestige());

		model += Util.getKeyValueString(RequestFieldMapping.CLAN_ID, getClanId());
		model += Util.getKeyValueString(RequestFieldMapping.CLAN_NAME, getClanName());
		model += Util.getKeyValueString(RequestFieldMapping.CLAN_ABBR, getClanAbbr());
		model += Util.getKeyValueString(RequestFieldMapping.CLAN_ICON, getClanIcon());
		model += Util.getKeyValueString(RequestFieldMapping.CLAN_LEADER, getIsClanLeader());
		model += Util.getKeyValueString(RequestFieldMapping.CLAN_JOIN_DATE, getClanJoinDate());
		model += Util.getKeyValueString(RequestFieldMapping.CLAN_ZOMBIE_KILLS, getClanZombieKills());
		model += Util.getKeyValueString(RequestFieldMapping.CLAN_PATRIARCH_KILLS, getClanPatriarchKills());

		model += Util.getKeyValueString(RequestFieldMapping.TITLE_TEXT, getTitleText());
		model += Util.getKeyValueString(RequestFieldMapping.INVENTORY, getInventory());
		model += Util.getKeyValueString(RequestFieldMapping.LANG, getLang());

		model += Util.getKeyValueString(RequestFieldMapping.GUNSLINGER_DAMAGE, getGunslingerDamage());

		model += Util.getKeyValueString(RequestFieldMapping.PREMIUM_TYPE, getPremiumType());
		model += Util.getKeyValueString(RequestFieldMapping.PREMIUM_EXPIRATION_DATE, getPremiumExpirationDate());

		model += Util.getKeyValueString(RequestFieldMapping.INACTIVE, getInactive());

		model = model.substring(1); // remove first "&"

		Util.echo("PlayerModel->toStringModel() model: " + model, LogType.FILE);

		return model;
	}

	public String toStringAsEmpty() {
		return RequestResultMessage.SUCCESS_NO_DATA_FOR_PLAYER;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealPoints() {
		return this.healPoints;
	}

	public void setHealPoints(int value) {
		this.healPoints = value;
	}

	public void setHealpoints(String value) {
		this.healPoints = Integer.parseInt(value);
	}

	public int getWeldPoints() {
		return this.weldPoints;
	}

	public void setWeldPoints(int value) {
		this.weldPoints = value;
	}

	public void setWeldPoints(String value) {
		this.weldPoints = Integer.parseInt(value);
	}

	public int getShotgunDamage() {
		return this.shotgunDamage;
	}

	public void setShotgunDamage(int value) {
		this.shotgunDamage = value;
	}

	public void setShotgunDamage(String value) {
		this.shotgunDamage = Integer.parseInt(value);
	}

	public int getHeadshots() {
		return this.headshots;
	}

	public void setHeadshots(int value) {
		this.headshots = value;
	}

	public void setHeadshots(String value) {
		this.headshots = Integer.parseInt(value);
	}

	public int getStalkerKills() {
		return this.killedStalkers;
	}

	public void setStalkerKills(int value) {
		this.killedStalkers = value;
	}

	public void setStalkerKills(String value) {
		this.killedStalkers = Integer.parseInt(value);
	}

	public int getBullpupDamage() {
		return this.bullpupDamage;
	}

	public void setBullpupDamage(int value) {
		this.bullpupDamage = value;
	}

	public void setBullpupDamage(String value) {
		this.bullpupDamage = Integer.parseInt(value);
	}

	public int getMeleeDamage() {
		return this.meleeDamage;
	}

	public void setMeleeDamage(int value) {
		this.meleeDamage = value;
	}

	public void setMeleeDamage(String value) {
		this.meleeDamage = Integer.parseInt(value);
	}

	public int getFireDamage() {
		return this.fireDamage;
	}

	public void setFireDamage(int value) {
		this.fireDamage = value;
	}

	public void setFireDamage(String value) {
		this.fireDamage = Integer.parseInt(value);
	}

	public int getExplosiveDamage() {
		return this.explosiveDamage;
	}

	public void setExplosiveDamage(int value) {
		this.explosiveDamage = value;
	}

	public void setExplosiveDamage(String value) {
		this.explosiveDamage = Integer.parseInt(value);
	}

	public int getWins() {
		return this.wins;
	}

	public void setWins(int value) {
		this.wins = value;
	}

	public void setWins(String value) {
		this.wins = Integer.parseInt(value);
	}

	public int getLosts() {
		return this.losts;
	}

	public void setLosts(int value) {
		this.losts = value;
	}

	public void setLosts(String value) {
		this.losts = Integer.parseInt(value);
	}

	public long getMapScore() {
		return this.mapScore;
	}

	public void setMapScore(long value) {
		this.mapScore = value;
	}

	public void setMapScore(String value) {
		this.mapScore = Long.parseLong(value);
	}

	public int getOnlineTime() {
		return this.onlineTime;
	}

	public void setOnlineTime(int value) {
		this.onlineTime = value;
	}

	public void setOnlineTime(String value) {
		this.onlineTime = Integer.parseInt(value);
	}

	public int getTotalKills() {
		return this.killsTotal;
	}

	public void setTotalKills(int value) {
		this.killsTotal = value;
	}

	public void setTotalKills(String value) {
		this.killsTotal = Integer.parseInt(value);
	}

	public long getWavesTotal() {
		return this.wavesTotal;
	}

	public void setWavesTotal(long value) {
		this.wavesTotal = value;
	}

	public void setWavesTotal(String value) {
		this.wavesTotal = Long.parseLong(value);
	}

	public int getTeamKills() {
		return this.teamKills;
	}

	public void setTeamKills(int value) {
		this.teamKills = value;
	}

	public void setTeamKills(String value) {
		this.teamKills = Integer.parseInt(value);
	}

	public int getScrakeKills() {
		return this.killedScrakes;
	}

	public void setScrakeKills(int value) {
		this.killedScrakes = value;
	}

	public void setScrakeKills(String value) {
		this.killedScrakes = Integer.parseInt(value);
	}

	public int getFleshpoundKills() {
		return this.killedFleshpounds;
	}

	public void setFleshpoundKills(int value) {
		this.killedFleshpounds = value;
	}

	public void setFleshpoundKills(String value) {
		this.killedFleshpounds = Integer.parseInt(value);
	}

	public int getPatriarchKillsMain() {
		return this.killedPatriarchsMain;
	}

	public void setPatriarchKillsMain(int value) {
		this.killedPatriarchsMain = value;
	}

	public void setPatriarchKillsMain(String value) {
		this.killedPatriarchsMain = Integer.parseInt(value);
	}

	public int getPatriarchKillsWeak() {
		return this.killedPartiarchsWeak;
	}

	public void setPatriarchKillsWeak(int value) {
		this.killedPartiarchsWeak = value;
	}

	public void setPatriarchKillsWeak(String value) {
		this.killedPartiarchsWeak = Integer.parseInt(value);
	}

	public int getMinibossKills() {
		return this.killedMinibosses;
	}

	public void setMinibossKills(int value) {
		this.killedMinibosses = value;
	}

	public void setMinibossKills(String value) {
		this.killedMinibosses = Integer.parseInt(value);
	}

	public String getAccountKey() {
		return this.accountKey;
	}

	public void setAccountKey(String key) {
		this.accountKey = key;
	}

	public String getTitleText() {
		return this.titleText;
	}

	public void setTitleText(String title) {
		this.titleText = title;
	}

	public String getSelectedVeterancy() {
		return this.selectedVeterancy;
	}

	public void setSelectedVeterancy(String veterancy) {
		this.selectedVeterancy = veterancy;
	}

	public String getSelectedCharacter() {
		return this.selectedChar;
	}

	public void setSelectedCharacter(String selectedChar) {
		this.selectedChar = selectedChar;
	}

	public String getLastMap() {
		return this.lastMap;
	}

	public void setLastMap(String map) {
		final String suf = ".rom";

		if (!Util.isNullOrEmpty(map) && map.endsWith(suf)) {
			map = map.substring(0, map.length() - suf.length());
		}

		this.lastMap = map;
	}

	public String getLastIp() {
		return this.lastIp;
	}

	public void setLastIp(String ip) {
		this.lastIp = ip;
	}

	public String getRegIp() {
		return this.regIp;
	}

	public void setRegIp(String ip) {
		this.regIp = ip;
	}

	public String getFirstVisit() {
		return this.firstVisit;
	}

	public void setFirstVisit(String time) {
		this.firstVisit = time;
	}

	public String getLastVisit() {
		return this.lastVisit;
	}

	public void setLastVisit(String dateStr) {
		this.lastVisit = dateStr;
	}

	public int getFirstEnter() {
		return this.firstEnter;
	}

	public void setFirstEnter(String value) {
		this.firstEnter = Integer.parseInt(value);
	}

	public void setFirstEnter(int value) {
		this.firstEnter = value;
	}

	public String getLastWinDate() {
		return this.lastWinDate;
	}

	public void setLastWinDate(String dateStr) {
		this.lastWinDate = dateStr;
	}

	public String getLastWinMap() {
		return this.lastWinMap;
	}

	public void setLastWinMap(String map) {
		this.lastWinMap = map;
	}

	public int getPrestige() {
		return this.prestige;
	}

	public void setPrestige(int value) {
		this.prestige = value;
	}

	public void setPrestige(String value) {
		this.prestige = Integer.parseInt(value);
	}

	// Clans

	public int getClanId() {
		return this.clanId;
	}

	public void setClanId(String id) {
		this.clanId = Integer.parseInt(id);
	}

	public void setClanId(int id) {
		this.clanId = id;
	}

	public String getClanName() {
		return this.clanName;
	}

	public void setClanName(String value) {
		this.clanName = value;
	}

	public String getClanAbbr() {
		return this.clanAbbr;
	}

	public void setClanAbbr(String value) {
		this.clanAbbr = value;
	}

	public String getClanIcon() {
		return this.clanIcon;
	}

	public void setClanIcon(String value) {
		this.clanIcon = value;
	}

	public String getClanJoinDate() {
		return this.clanJoinDate;
	}

	public void setClanJoinDate(String value) {
		this.clanJoinDate = value;
	}

	public byte getIsClanLeader() {
		return this.isClanLeader;
	}

	public void setIsClanLeader(String value) {
		this.isClanLeader = Byte.parseByte(value);
	}

	public void setIsClanLeader(byte value) {
		this.isClanLeader = value;
	}

	public long getClanZombieKills() {
		return this.clanZombieKills;
	}

	public void setClanZombieKills(int value) {
		this.clanZombieKills = value;
	}

	public void setClanZombieKills(String value) {
		this.clanZombieKills = Long.parseLong(value);
	}

	public long getClanPatriarchKills() {
		return this.clanPatriarchKills;
	}

	public void setClanPatriarchKills(int value) {
		this.clanPatriarchKills = value;
	}

	public void setClanPatriarchKills(String value) {
		this.clanPatriarchKills = Long.parseLong(value);
	}

	// Other

	public String getInventory() {
		return this.inventory;
	}

	public void setInventory(String value) {
		this.inventory = value;
	}

	public int getLang() {
		return this.lang;
	}

	public void setLang(int lang) {
		this.lang = lang;
	}

	public void setLang(String value) {
		try {
			this.lang = Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			this.lang = 0;
		}
	}

	public long getGunslingerDamage() {
		return this.gunslingerDamage;
	}

	public void setGunslingerDamage(long value) {
		this.gunslingerDamage = value;
	}

	public void setGunslingerDamage(String value) {
		this.gunslingerDamage = Long.parseLong(value);
	}

	public int getPremiumType() {
		return this.premiumType;
	}

	public void setPremiumType(byte value) {
		this.premiumType = value;
	}

	public void setPremiumType(String value) {
		this.premiumType = Byte.parseByte(value);
	}

	public String getPremiumExpirationDate() {
		return this.premiumExpirationDate;
	}

	public void setPremiumExpirationDate(String value) {
		this.premiumExpirationDate = value;
	}

	public int getInactive() {
		return this.inactive;
	}

	public void setInactive(String value) {
		try {
			this.inactive = Integer.parseInt(value);
		} catch (NumberFormatException nfe) {
			this.inactive = 0;
		}
	}

	public void setInactive(int value) {
		this.inactive = value;
	}
}
