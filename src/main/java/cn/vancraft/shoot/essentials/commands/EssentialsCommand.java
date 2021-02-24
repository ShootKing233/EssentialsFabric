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

package cn.vancraft.shoot.essentials.commands;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.server.command.ServerCommandSource;

import java.util.List;

public abstract class EssentialsCommand implements IEssentialsCommand {
    private final String name;
    private final List<String> aliases;

    protected EssentialsCommand(String name, List<String> aliases) {
        this.name = name;
        this.aliases = aliases;
    }

    public void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher) {
        registerMain(dispatcher, this.name);
        registerAliases(dispatcher);
    }

    public abstract void registerMain(CommandDispatcher<ServerCommandSource> dispatcher, String name);

    public void registerAliases(CommandDispatcher<ServerCommandSource> dispatcher) {
        if (aliases != null && !aliases.isEmpty()) {
            for (String alias : aliases) {
                registerMain(dispatcher, alias);
            }
        }
    }

    public String getName() {
        return this.name;
    }

    public List<String> getAliases() {
        return this.aliases;
    }
}
