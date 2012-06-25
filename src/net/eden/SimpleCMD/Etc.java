package net.eden.SimpleCMD;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.bukkit.Material;

public class Etc {

    public static double formatAmount(double amount, final boolean usingiConomy) {

        if (usingiConomy) {
            return Math.floor(amount * 100) / 100;
        } else {
            return Math.floor(amount);
        } //test
    }

    public static String formatMaterialName(final int materialId) {
        String returnMaterialName = "";
        String rawMaterialName = Material.getMaterial(materialId).toString();
        rawMaterialName = rawMaterialName.toLowerCase(Locale.ENGLISH);
        // Large first letter.
        final String firstLetterCapital = rawMaterialName.substring(0, 1).toUpperCase();
        rawMaterialName = firstLetterCapital
                + rawMaterialName.substring(1, rawMaterialName.length());
        returnMaterialName = rawMaterialName.replace("_", " ");

        return returnMaterialName;
    }

    public static String pluralWording(final String word, final Integer number) {
        // Start
        if (word.equalsIgnoreCase("Ticket")) {
            if (number == 1) {
                return "Ticket";
            } else {
                return "Tickets";
            }
        }
        // Next
        if (word.equalsIgnoreCase("ticket")) {
            if (number == 1) {
                return "Ticket";
            } else {
                return "Tickets";
            }
        }
        // Next
        if (word.equalsIgnoreCase("player")) {
            if (number == 1) {
                return "Player";
            } else {
                return "Players";
            }
        }
        // Next
        if (word.equalsIgnoreCase("day")) {
            if (number == 1) {
                return "Tag";
            } else {
                return "Tage";
            }
        }
        // Next
        if (word.equalsIgnoreCase("hour")) {
            if (number == 1) {
                return "Stunde";
            } else {
                return "Stunden";
            }
        }
        // Next
        if (word.equalsIgnoreCase("minute")) {
            if (number == 1) {
                return "Minute";
            } else {
                return "Minuten";
            }
        }
        // Next
        if (word.equalsIgnoreCase("second")) {
            if (number == 1) {
                return "Sekunde";
            } else {
                return "Sekunden";
            }
        }
        // Next
        if(number==1){
            return word;
        }
        else
        {
            return word+"s";
        }
    }

    public static String timeUntil(final long time, final boolean mini) {

        long timeLeft = time;
        // How many days left?
        String stringTimeLeft = "";

        if (timeLeft >= 60 * 60 * 24) {
            final int days = (int) Math.floor(timeLeft / (60 * 60 * 24));
            timeLeft -= 60 * 60 * 24 * days;
            if (mini) {
                stringTimeLeft += Integer.toString(days) + "d ";
            } else {
                stringTimeLeft += Integer.toString(days) + " " + Etc.pluralWording("day", days) + ", ";
            }
        }
        if (timeLeft >= 60 * 60) {
            final int hours = (int) Math.floor(timeLeft / (60 * 60));
            timeLeft -= 60 * 60 * hours;
            if (mini) {
                stringTimeLeft += Integer.toString(hours) + "h ";
            } else {
                stringTimeLeft += Integer.toString(hours) + " " + Etc.pluralWording("hour", hours) + ", ";
            }
        }
        if (timeLeft >= 60) {
            final int minutes = (int) Math.floor(timeLeft / (60));
            timeLeft -= 60 * minutes;
            if (mini) {
                stringTimeLeft += Integer.toString(minutes) + "m ";

            } else {
                stringTimeLeft += Integer.toString(minutes) + " " + Etc.pluralWording("minute", minutes) + ", ";
            }
        } else {
            // Lets remove the last comma, since it will look bad with 2 days, 3
            // hours, and 14 seconds.
            if (stringTimeLeft.equalsIgnoreCase("") == false && !mini) {
                stringTimeLeft = stringTimeLeft.substring(0,
                        stringTimeLeft.length() - 1);
            }
        }
        final int secs = (int) timeLeft;
        if (mini) {
            stringTimeLeft += secs + "s";
        } else {
            if (!stringTimeLeft.equalsIgnoreCase("")) {
                stringTimeLeft += "and ";
            }
            stringTimeLeft += Integer.toString(secs) + " " + Etc.pluralWording("second", secs);
        }

        return stringTimeLeft;
    }

    public static Map<String, Integer> realPlayersFromList(final List<String> ticketList) {
        final Map<String, Integer> playerList = new HashMap<String, Integer>();
        int value;
        for (String check : ticketList) {
            if (playerList.containsKey(check)) {
                value = Integer.parseInt(playerList.get(check).toString()) + 1;
            } else {
                value = 1;
            }
            playerList.put(check, value);
        }
        return playerList;
    }

    public static int parseInt(final String arg) {
        int newInt = 0;
        try {
            newInt = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
        }
        return newInt > 0 ? newInt : 0;
    }

    public static int parseDouble(final String arg) {
        int newInt = 0;
        try {
            newInt = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
        }
        return newInt > 0 ? newInt : 0;
    }
}
