package com.chathead;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Objects;

public class Command implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {


        Player player = (Player)sender;

        if (args.length == 1) {
            if (PlayerUtils.isValidPlayer(args[0])) {
                OfflinePlayer headPlayer = Bukkit.getOfflinePlayer(Objects.requireNonNull(PlayerUtils.offlinePlayerNameUuid(args[0])));
                HeadLinkGrabber.sendHeadMessage(headPlayer, player, 0);
            } else {
                player.sendMessage(ChatHead.cmsg("&cThe player '" + args[0] + "' does not exist."));
            }
            return true;
        } else if (args.length >= 2 && args.length <= 3) {
            if (PlayerUtils.isValidPlayer(args[0])) {
                if (PlayerUtils.isValidPlayer(args[1])) {
                    OfflinePlayer headPlayer = Bukkit.getOfflinePlayer(Objects.requireNonNull(PlayerUtils.offlinePlayerNameUuid(args[0])));
                    Player sendPlayer = Bukkit.getPlayerExact(args[1]);
                    if (sendPlayer != null) {
                        int indent = 0;
                        if (args.length == 3) {
                            try {
                                indent = (int)Math.floor(Double.parseDouble(args[2]));
                            } catch (NumberFormatException e) {
                                player.sendMessage(ChatHead.cmsg("&cThe number '" + args[2] + "' is not valid."));
                                return true;
                            }
                        }
                        HeadLinkGrabber.sendHeadMessage(headPlayer, sendPlayer, 0);
                    } else {
                        player.sendMessage(ChatHead.cmsg("&cThe player '" + args[1] + "' is not online."));
                    }
                } else {
                    player.sendMessage(ChatHead.cmsg("&cThe player '" + args[1] + "' does not exist."));
                }
            } else {
                player.sendMessage(ChatHead.cmsg("&cThe player '" + args[0] + "' does not exist."));
            }
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command cmd, String s, String[] args) {
        return null;
    }
}
