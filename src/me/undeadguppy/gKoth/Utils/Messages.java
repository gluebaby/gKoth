package me.undeadguppy.gKoth.Utils;

import net.md_5.bungee.api.ChatColor;

public class Messages {

	public Messages() {
	};

	public static Messages msg = new Messages();

	public static Messages getInstance() {
		return msg;
	}

	public String getHelpMessage() {
		return ChatColor.RED + "*****KoTH*****\n" + ChatColor.RED
				+ "- /koth join\n- /koth start\n- /koth stop\n- /koth loot\n" + ChatColor.GRAY
				+ "Please make sure you have inventory space before entering KoTH!";

	}

}
