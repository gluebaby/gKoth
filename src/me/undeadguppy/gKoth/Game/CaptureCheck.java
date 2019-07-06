package me.undeadguppy.gKoth.Game;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.undeadguppy.gKoth.Main;
import me.undeadguppy.gKoth.Game.Game.GameState;

public class CaptureCheck extends BukkitRunnable {

	private Game game;

	public CaptureCheck(Game game) {
		this.game = game;

	}

	@Override
	public void run() {
		if (game.isState(GameState.IN_GAME)) {
			for (Player p : Bukkit.getServer().getOnlinePlayers()) {
				int y = p.getLocation().getBlockY() - 1;
				Block block = p.getWorld().getBlockAt(p.getLocation().getBlockX(), y, p.getLocation().getBlockZ());
				if (block.getType() == Material.DIAMOND_BLOCK) {
					if (!game.isOnHill(p)) {
						if (!(game.onHill().size() == 1)) {
							if (game.getHashMap().containsKey(p.getUniqueId())) {
								// Player is in hashmap
							} else {
								// Player isn't
								game.getHashMap().put(p.getUniqueId(), 0);
							}
							game.addOnHill(p);
							new Points(game).runTaskTimer(Main.getInstance(), 0L, 20L * 7);
						} else {
							return;
						}

					}
				} else {
					if (game.isOnHill(p)) {
						game.removeOnHill(p);
					}
				}
			}
		}
	}

}
