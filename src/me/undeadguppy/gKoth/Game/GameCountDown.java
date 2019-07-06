package me.undeadguppy.gKoth.Game;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class GameCountDown extends BukkitRunnable {

	private Game game;
	private int i;

	public GameCountDown(Game game) {
		this.game = game;
		this.i = 10;
	}

	@Override
	public void run() {
		if (this.i == 0) {
			this.cancel();
			game.stop();
		} else {
			if (i == 10 || i == 5 || i == 1) {
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "" + i + " minutes remaining in KoTH!");
			}
			i--;
		}
	}

}
