package me.fan87.plugindevkit.utils;

import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class LineWrapper {


    public static List<String> wrapLine(String input, String linePrefix, String lineSuffix, int width) {
        char[] array = input.toCharArray();
        List<String> out = new ArrayList<>();
        String currentColor = "";
        String cachedColor = "";
        boolean wasColorChar = false;
        String currentLine = linePrefix;
        String currentWord = "";
        for (int i = 0; i < array.length; i++) {
            char c = array[i];
            if (wasColorChar) {
                wasColorChar = false;
                cachedColor = currentColor;
                Pattern pattern = Pattern.compile("[0-9a-fkmolnr]");
                if (pattern.matcher(c + "").matches()) {
                    if (c == 'r') {
                        currentColor = ChatColor.COLOR_CHAR + "r";
                    } else {
                        currentColor += ChatColor.COLOR_CHAR + "" + c;
                    }
                }
                currentWord += ChatColor.COLOR_CHAR + "" + c;
                continue;
            }
            if (c == '\n') {
                currentLine += currentWord;
                currentWord = "";
                out.add(currentLine + lineSuffix);
                currentLine = linePrefix + cachedColor + currentWord;
                cachedColor = currentColor;
                continue;
            }
            if (c == ' ') {
                if ((currentLine + currentWord).replaceAll("§[0-9a-fklmnor]", "").length() > width) {
                    out.add(currentLine + lineSuffix);
                    currentLine = linePrefix + cachedColor + currentWord + " ";
                } else {
                    currentLine += currentWord + " ";
                }
                cachedColor = currentColor;
                currentWord = "";
                continue;
            }
            if (c == ChatColor.COLOR_CHAR) {
                wasColorChar = true;
                continue;
            }
            currentWord += c;
        }
        currentLine += currentWord;
        out.add(currentLine + lineSuffix);
        return out;
    }

    public static List<String> wrapLine(String input) {
        return wrapLine(input, "", "", 32);
    }

}