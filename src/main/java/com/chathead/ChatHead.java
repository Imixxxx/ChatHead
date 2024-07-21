package com.chathead;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class ChatHead extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("chathead").setExecutor(new Command());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }


    public static net.md_5.bungee.api.ChatColor hexChatColor(String hex) {
        try {
            return net.md_5.bungee.api.ChatColor.of(hex);
        } catch (NumberFormatException e) {
            return net.md_5.bungee.api.ChatColor.WHITE;
        }
    }


    public static String cmsg(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

}
