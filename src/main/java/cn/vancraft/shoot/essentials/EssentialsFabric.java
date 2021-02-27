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

package cn.vancraft.shoot.essentials;

import cn.vancraft.shoot.essentials.commands.CommandMods;
import cn.vancraft.shoot.essentials.commands.CommandPing;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EssentialsFabric implements ModInitializer {
    public static final Logger LOGGER = LogManager.getLogger();

    @Override
    public void onInitialize() {
        LOGGER.info("正在加载Essentials VERSION");
        CommandRegistrationCallback.EVENT.register((dispatcher, dedicated) -> {
            LOGGER.debug("正在注册命令ping");
            new CommandPing().registerCommand(dispatcher);
            LOGGER.debug("正在注册命令mods");
            new CommandMods().registerCommand(dispatcher);
        });
    }
}
