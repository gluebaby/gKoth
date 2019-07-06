package me.undeadguppy.gKoth.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import me.undeadguppy.gKoth.Main;
import net.md_5.bungee.api.ChatColor;

public class Game {

	public enum GameState {
		NO_GAME, COUNTDOWN, IN_GAME
	}

	private GameState state;
	private Location spawn1, spawn2, spawn3;
	private ArrayList<UUID> onDiamond;
	private HashMap<UUID, Integer> cappers;
	FileConfiguration file = Main.getInstance().getConfig();
	BukkitTask runCount, gameCount, check;
	int runCountId, gameCountId, checkId;

	public void runCountdown() {
		runCount = new CountDown(this).runTaskTimer(Main.getInstance(), 0L, 20L);
		runCountId = runCount.getTaskId();
	}

	public void runGameCount() {
		gameCount = new GameCountDown(this).runTaskTimer(Main.getInstance(), 0L, 20L * 60L);
		gameCountId = gameCount.getTaskId();
		check = new CaptureCheck(this).runTaskTimer(Main.getInstance(), 0L, 5L);
		checkId = check.getTaskId();
	}

	public int getCountTask() {
		return runCountId;
	}

	public int getGameTask() {
		return gameCountId;
	}

	public int getCheckTask() {
		return checkId;
	}

	public Game() {
		this.onDiamond = new ArrayList<UUID>();
		this.cappers = new HashMap<UUID, Integer>();
		this.state = GameState.NO_GAME;
		this.spawn1 = new Location(Bukkit.getServer().getWorld(file.getString("SpawnPoint1.World")),
				file.getDouble("SpawnPoint1.X"), file.getDouble("SpawnPoint1.Y"), file.getDouble("SpawnPoint1.Z"));
		this.spawn2 = new Location(Bukkit.getServer().getWorld(file.getString("SpawnPoint2.World")),
				file.getDouble("SpawnPoint2.X"), file.getDouble("SpawnPoint2.Y"), file.getDouble("SpawnPoint2.Z"));
		this.spawn3 = new Location(Bukkit.getServer().getWorld(file.getString("SpawnPoint3.World")),
				file.getDouble("SpawnPoint3.X"), file.getDouble("SpawnPoint3.Y"), file.getDouble("SpawnPoint3.Z"));

	}

	public void addOnHill(Player p) {
		UUID id = p.getUniqueId();
		this.onDiamond.add(id);
	}

	public void removeOnHill(Player p) {
		UUID id = p.getUniqueId();
		this.onDiamond.remove(id);
	}

	public boolean isOnHill(Player p) {
		UUID id = p.getUniqueId();
		return this.onDiamond.contains(id);
	}

	public ArrayList<UUID> onHill() {
		return this.onDiamond;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public GameState getState() {
		return this.state;
	}

	public boolean isState(GameState state) {
		return state == this.state;
	}

	public Location getSpawns(int id) {
		if (id == 1) {
			return spawn1;
		} else if (id == 2) {
			return spawn2;
		} else if (id == 3) {
			return spawn3;
		} else {
			return null;
		}

	}

	public HashMap<UUID, Integer> getHashMap() {
		return cappers;
	}

	public Integer getPoints(Player p) {
		UUID id = p.getUniqueId();
		return cappers.get(id);
	}

	public void start() {
		Bukkit.getServer().broadcastMessage(ChatColor.RED + "KoTH has started! Use /koth join to join the fight!");
		this.state = GameState.IN_GAME;
		runGameCount();

	}

	public void stop() {
		Bukkit.getServer().broadcastMessage(ChatColor.RED + "KoTH has ended!");
		this.state = GameState.NO_GAME;
		this.cappers.clear();
		this.onDiamond.clear();
		Bukkit.getScheduler().cancelAllTasks();
	}

}
