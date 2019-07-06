package me.undeadguppy.gKoth.Game;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class CountDown extends BukkitRunnable {

	private Game game;
	private int i;

	public CountDown(Game game) {
		this.game = game;
		this.i = 30;
	}

	@Override
	public void run() {
		if (i == 0) {
			// Countdown ends
			this.cancel();
			game.start();
			return;
		} else {
			if (i % 5 == 0 || i < 5) {
				Bukkit.getServer().broadcastMessage(ChatColor.RED + "KoTH starting in " + i + " seconds!");
			}
			i--;
		}
	}

}
