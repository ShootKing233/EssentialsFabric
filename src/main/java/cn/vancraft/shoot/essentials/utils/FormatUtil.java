/*
 * A Fabric Mod to add some commands of the Spigot plugin Essentials.
 * Copyright (C) 2021  ShootKing233
 *
 * EssentialsFabric is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EssentialsFabric is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EssentialsFabric.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.vancraft.shoot.essentials.utils;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class FormatUtil {
    public static final Set<Character> ALL_SET = new HashSet<Character>() {
        {
            add('0');
            add('1');
            add('2');
            add('3');
            add('4');
            add('5');
            add('6');
            add('7');
            add('8');
            add('9');
            add('a');
            add('b');
            add('c');
            add('d');
            add('e');
            add('f');
            add('g');
            add('k');
            add('l');
            add('m');
            add('n');
            add('o');
            add('r');
        }
    };
    public static final Pattern REPLACE_ALL = Pattern.compile("(&)?&([0-9a-fk-orA-FK-OR])");

    public static String replaceColor(final String raw, final Set<Character> supported) {
        final StringBuffer buffer = new StringBuffer();
        final Matcher matcher = REPLACE_ALL.matcher(raw);
        loop: while (matcher.find()) {
            final boolean isEscaped = matcher.group(1) != null;
            if (!isEscaped) {
                final char code = matcher.group(2).toLowerCase(Locale.ROOT).charAt(0);
                for (final char color : supported) {
                    if (color == code) {
                        matcher.appendReplacement(buffer, "§" + "$2");
                        continue loop;
                    }
                }
            }
            matcher.appendReplacement(buffer, "&$2");
        }
        matcher.appendTail(buffer);
        return matcher.toString();
    }

    public static String replaceFormat(final String raw) {
        if (raw == null)
            return null;
        return replaceColor(raw, ALL_SET);
    }
}
