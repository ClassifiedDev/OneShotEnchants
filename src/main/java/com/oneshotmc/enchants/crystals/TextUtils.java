package com.oneshotmc.enchants.crystals;

import java.util.concurrent.TimeUnit;
import org.bukkit.ChatColor;

public class TextUtils {
    public static String getFormattedCooldown(long time) {
        return getCooldownString((int)(time - System.currentTimeMillis()) / 1000);
    }

    public static String getCooldownString(int seconds) {
        int day = (int)TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.SECONDS.toHours(seconds) * 60L;
        long second = TimeUnit.SECONDS.toSeconds(seconds) - TimeUnit.SECONDS.toMinutes(seconds) * 60L;
        String s = "";
        if (day != 0)
            s = String.valueOf(s) + day + "d ";
        if (hours != 0L)
            s = String.valueOf(s) + hours + "h ";
        if (minute != 0L)
            s = String.valueOf(s) + minute + "m ";
        if (second != 0L)
            s = String.valueOf(s) + second + "s ";
        if (s.isEmpty())
            return String.valueOf(seconds) + "s";
        return s.trim();
    }

    public static String getProgressBar(int lines, int current, int needed) {
        StringBuilder builder = new StringBuilder();
        lines = 30;
        double percentage = (current / needed);
        int goodLines = (int)Math.floor(lines * percentage);
        builder.append(ChatColor.GREEN.toString());
        for (int i = 0; i < goodLines; i++)
            builder.append("|");
        int redLines = lines - goodLines;
        if (redLines > 0) {
            builder.append(ChatColor.RED.toString());
            for (int j = 0; j < redLines; j++)
                builder.append("|");
        }
        return builder.toString();
    }
}
