package org.silpa.soundex;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created by sujith on 19/5/14.
 */
public class Soundex {

    private static Map<String, List<Character>> soundexMap = new HashMap<String, List<Character>>();

    static {
        soundexMap.put("soundex_en", Arrays.asList('0', '1', '2', '3', '0', '1', '2', '0', '0', '2', '2', '4', '5', '5', '0', '1', '2', '6', '2', '3', '0', '1', '0', '2', '0', '2'));
        soundexMap.put("soundex", Arrays.asList('0', '1', '2', '3', '0', '1', '2', '0', '0', '2', '2', '4', '5', '5', '0', '1', '2', '6', '2', '3', '0', '1', '0', '2', '0', '2'));
    }

    public char soundexCode(char ch) {
        String lang = CharacterMap.getLanguage(ch);
        try {
            if (lang.equals("en_US")) {
                return Soundex.soundexMap.get("soundex_en").get(CharacterMap.charmap.get(lang).indexOf(ch));
            } else {
                return Soundex.soundexMap.get("soundex").get(CharacterMap.charmap.get(lang).indexOf(ch));
            }
        } catch (Exception e) {
            return '0';
        }
    }

    public String soundex(String name) {
        return soundex(name, 8);
    }

    public String soundex(String name, int length) {

        if (name == null || name.length() == 0) return null;

        StringBuffer sndx = new StringBuffer("");
        char fc = name.charAt(0);

        for (char c : name.toLowerCase(Locale.getDefault()).toCharArray()) {
            char d = soundexCode(c);

            if (d == '0') {
                continue;
            }

            if (sndx.length() == 0) {
                sndx.append(d);
            } else if (d != sndx.charAt(sndx.length() - 1)) {
                sndx.append(d);
            }
        }
        sndx.insert(0, fc);

        if (CharacterMap.getLanguage(name.charAt(0)) == "en_US") {
            return sndx.toString();
        }

        if (sndx.length() < length) {
            while (sndx.length() != length) {
                sndx.append('0');
            }
            return sndx.substring(0, length);
        }
        return sndx.substring(0, length);
    }

    public int compare(String string1, String string2) {
        if (string1 == null || string2 == null) {
            return -1;
        }

        if (string1.equals(string2)) {
            return 0;
        }

        String stringLang1 = CharacterMap.getLanguage(string1.charAt(0));
        String stringLang2 = CharacterMap.getLanguage(string2.charAt(0));

        if ((stringLang1.equals("en_US") && !stringLang2.equals("en_US")) ||
                (!stringLang1.equals("en_US") && stringLang2.equals("en_US"))) {
            return -1;
        }

        String soundex1 = soundex(string1);
        String soundex2 = soundex(string2);

        if (soundex1.substring(1).equals(soundex2.substring(1))) {
            return 1;
        }
        return 2;
    }
}
