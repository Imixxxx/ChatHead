package com.chathead;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerUtils {

    private static Map<UUID, JsonObject> uuidMap = new HashMap<>();
    private static Map<String, JsonObject> nameMap = new HashMap<>();
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public static boolean isValidPlayer(String name) {
        try {
            JsonObject playerJson;
            if (nameMap.containsKey(name)) {
                playerJson = nameMap.get(name);
            } else {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                playerJson = JsonParser.parseReader(reader).getAsJsonObject();
                nameMap.put(name, playerJson);

                scheduler.schedule(() -> nameMap.remove(name), 1, TimeUnit.HOURS);
            }
            if (playerJson.has("id")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isValidPlayer(UUID uuid) {
        try {
            JsonObject playerJson;
            if (uuidMap.containsKey(uuid)) {
                playerJson = uuidMap.get(uuid);
            } else {
                URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                playerJson = JsonParser.parseReader(reader).getAsJsonObject();
                uuidMap.put(uuid, playerJson);

                scheduler.schedule(() -> uuidMap.remove(uuid), 1, TimeUnit.HOURS);
            }
            if (playerJson.has("id")) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public static String offlinePlayerUuidName(UUID uuid) {
        try {
            JsonObject playerJson;
            if (uuidMap.containsKey(uuid)) {
                playerJson = uuidMap.get(uuid);
            } else {
                URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                playerJson = JsonParser.parseReader(reader).getAsJsonObject();
                uuidMap.put(uuid, playerJson);

                scheduler.schedule(() -> uuidMap.remove(uuid), 1, TimeUnit.HOURS);
            }
            if (playerJson.has("name")) {
                return playerJson.get("name").getAsString();
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static UUID offlinePlayerNameUuid(String name) {
        try {
            JsonObject playerJson;
            if (nameMap.containsKey(name)) {
                playerJson = nameMap.get(name);
            } else {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
                InputStreamReader reader = new InputStreamReader(url.openStream());
                playerJson = JsonParser.parseReader(reader).getAsJsonObject();
                nameMap.put(name, playerJson);

                scheduler.schedule(() -> nameMap.remove(name), 1, TimeUnit.HOURS);
            }

            if (playerJson.has("id")) {
                String trimmedUuid = playerJson.get("id").getAsString();
                String fullUuid = trimmedUuid.substring(0, 8) + "-" +
                        trimmedUuid.substring(8, 12) + "-" +
                        trimmedUuid.substring(12, 16) + "-" +
                        trimmedUuid.substring(16, 20) + "-" +
                        trimmedUuid.substring(20);
                return UUID.fromString(fullUuid);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
