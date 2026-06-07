package com.zopchat;

import com.google.firebase.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {
    public static String normalizeMobile(String raw) {
        if (raw == null) return "";
        String d = raw.replaceAll("[^0-9]", "");
        if (d.startsWith("91") && d.length() == 12) d = d.substring(2);
        return d;
    }
    public static boolean isValidIndianMobile(String m) { return m != null && m.matches("[6-9][0-9]{9}"); }
    public static String mobileAuthEmail(String mobile) { return normalizeMobile(mobile) + "@mobile.zopchat.app"; }
    public static String deterministicChatId(String a, String b) { return a.compareTo(b) < 0 ? a + "_" + b : b + "_" + a; }
    public static String firstLetter(String s) { return s == null || s.trim().isEmpty() ? "U" : s.trim().substring(0,1).toUpperCase(Locale.ROOT); }
    public static String timeShort(Timestamp t) {
        if (t == null) return "";
        return new SimpleDateFormat("h:mm a", Locale.getDefault()).format(t.toDate());
    }
    public static String lastSeenText(Object onlineObj, Timestamp lastSeen) {
        boolean online = onlineObj instanceof Boolean && (Boolean) onlineObj;
        if (online) return "online";
        if (lastSeen == null) return "last seen recently";
        long diff = System.currentTimeMillis() - lastSeen.toDate().getTime();
        if (diff < 60_000) return "last seen just now";
        if (diff < 3_600_000) return "last seen " + (diff / 60_000) + " min ago";
        SimpleDateFormat sameDay = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        if (sameDay.format(new Date()).equals(sameDay.format(lastSeen.toDate()))) return "last seen today at " + new SimpleDateFormat("h:mm a", Locale.getDefault()).format(lastSeen.toDate());
        return "last seen " + new SimpleDateFormat("dd MMM, h:mm a", Locale.getDefault()).format(lastSeen.toDate());
    }
}
