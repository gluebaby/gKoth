package me.undeadguppy.gKoth;

import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.undeadguppy.gKoth.Game.Game;
import me.undeadguppy.gKoth.Game.Game.GameState;
import me.undeadguppy.gKoth.Utils.Messages;

public class Koth implements CommandExecutor, Listener {

	Game game = new Game();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String l, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(ChatColor.RED + "Only players can use KoTH commands!");
			return true;
		}
		// Sender is a player
		Player p = (Player) sender;
		if (cmd.getName().equalsIgnoreCase("koth")) {
			if (args.length == 0) {
				// No args entered.
				p.sendMessage(Messages.getInstance().getHelpMessage());
				return true;
			}
			if (args[0].equalsIgnoreCase("join")) {
				// Teleport to a random spawnpoint
				if (game.isState(GameState.IN_GAME) || game.isState(GameState.COUNTDOWN)) {
					Random r = new Random();
					int id = 1 + r.nextInt(3);
					Location tp = game.getSpawns(id);
					p.teleport(tp);
					return true;
				} else {
					p.sendMessage(ChatColor.RED + "There is no KoTH in progress!");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("start")) {
				if (p.hasPermission("gkoth.host")) {
					// Start game
					if (game.isState(GameState.NO_GAME)) {
						// Start countdown
						game.setState(GameState.COUNTDOWN);
						game.runCountdown();
						return true;
					} else {
						// Stop code
						p.sendMessage(ChatColor.RED + "There is already a KoTH in progress!");
						return true;
					}
				} else {
					p.sendMessage(ChatColor.RED + "You do not have access to this command!");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("stop")) {
				if (p.hasPermission("gkoth.host")) {
					// Stop game
					if (game.isState(GameState.IN_GAME) || game.isState(GameState.COUNTDOWN)) {
						// Stop game
						game.stop();
						return true;
					} else { // Stop code
						p.sendMessage(ChatColor.RED + "There is no KoTH in progress!");
						return true;
					}
				} else {
					p.sendMessage(ChatColor.RED + "You do not have access to this command!");
					return true;
				}
			} else if (args[0].equalsIgnoreCase("loot")) {
				p.sendMessage(ChatColor.RED + "KoTH Loot:");
				p.sendMessage(ChatColor.RED + "- 1.5k or 5k");
				p.sendMessage(ChatColor.RED + "- Either:");
				p.sendMessage(ChatColor.RED + "- 1x Kit VIP");
				p.sendMessage(ChatColor.RED + "- 1x Kit MVP");
				p.sendMessage(ChatColor.RED + "- 1x Kit PRO");
				p.sendMessage(ChatColor.RED + "- 1x Kit LORD");
				p.sendMessage(ChatColor.RED + "- 1x Kit GOD");
				p.sendMessage(ChatColor.GRAY + "Please make sure you have inventory space before entering KoTH!");
			} else { // Invalid arg
				p.sendMessage(Messages.getInstance().getHelpMessage());
				return true;

			}
		}
		return true;
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if (game.isState(GameState.COUNTDOWN) || game.isState(GameState.IN_GAME)) {
			e.getPlayer().sendMessage(ChatColor.RED + "There is currently an active KoTH! Use /koth join to join!");
		}
	}

}
