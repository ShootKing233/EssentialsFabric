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

public interface IEssentialsCommand {
    String getName();

    List<String> getAliases();

    void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher);

    void registerMain(CommandDispatcher<ServerCommandSource> dispatcher, String name);

    void registerAliases(CommandDispatcher<ServerCommandSource> dispatcher);
}
