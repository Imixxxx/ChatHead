package com.chathead;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class HeadLinkGrabber {


    public static void sendHeadMessage(OfflinePlayer headPlayer, Player sendPlayer, int indent) {
        try {
            BufferedImage image = ImageIO.read(new URL("https://crafatar.com/avatars/" + headPlayer.getUniqueId() + "?size=8"));

            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            Graphics2D g2 = newImage.createGraphics();
            g2.rotate(Math.toRadians(-90), (double) image.getWidth() / 2, (double) image.getHeight() / 2);
            g2.drawImage(image, null, 0, 0);

            int width = newImage.getWidth();
            int height = newImage.getHeight();

            int[][] pixels = new int[height][width];
            List<String> pixelHex = new ArrayList<>();

            for (int row = 0; row < height; row++) {
                StringBuilder lineMessage = new StringBuilder();
                for (int i = 0; i < indent; i++) { lineMessage.append(" "); }
                for (int col = 0; col < width; col++) {
                    int rgb = newImage.getRGB(row, col);
                    String hex = "#" + Integer.toHexString(rgb).substring(2);
                    pixels[row][col] = rgb;
                    pixelHex.add(hex);
                    lineMessage.append(ChatHead.hexChatColor(hex)).append("█");
                }
                sendPlayer.sendMessage(lineMessage.toString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
