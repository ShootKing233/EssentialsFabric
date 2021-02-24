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

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;

import cn.vancraft.shoot.essentials.utils.FormatUtil;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.ArrayList;
import java.util.List;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.greedyString;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandPing extends EssentialsCommand {
    private static final String name = "ping";
    private static final List<String> aliases = new ArrayList<String>() {
        {
            add("pong");
            add("echo");
        }
    };

    public CommandPing() {
        super(name, aliases);
    }

    @Override
    public void registerMain(CommandDispatcher<ServerCommandSource> dispatcher, String name) {
        dispatcher.register(literal(name)
                .executes(this::mainCommandExecutor)
                .then(argument("text", greedyString())
                        .executes(this::textSubExecutor)));
    }

    private int mainCommandExecutor(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(new LiteralText("Pong!"), false);
        return Command.SINGLE_SUCCESS;
    }

    private int textSubExecutor(CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(new LiteralText(FormatUtil.replaceFormat(getString(ctx, "text"))), false);
        return Command.SINGLE_SUCCESS;
    }
}