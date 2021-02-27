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
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.fabricmc.loader.api.metadata.Person;
import net.minecraft.command.CommandSource;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.LiteralText;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static com.mojang.brigadier.arguments.StringArgumentType.word;
import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class CommandMods extends EssentialsCommand {
    private static final String name = "mods";
    private static final List<String> aliases = null;

    public CommandMods() {
        super(name, aliases);
    }

    @Override
    public void registerMain(CommandDispatcher<ServerCommandSource> dispatcher, String name) {
        dispatcher.register(
                literal(name)
                        .executes(this::mainCommandExecutor)
                        .then(argument("modid", word())
                                .suggests(this::modSubSuggest)
                                .executes(this::modidSubExecutor)));
    }

    private int mainCommandExecutor(CommandContext<ServerCommandSource> ctx) {
        Collection<ModContainer> mods = FabricLoader.getInstance().getAllMods();
        int modCount = mods.size();
        StringBuilder feedback = new StringBuilder();
        feedback.append(String.format("Mods (%d): ", modCount));
        int i = 0;
        for (ModContainer mod : mods) {
            ModMetadata modMeta = mod.getMetadata();
            feedback.append(String.format("§a%s§r", modMeta.getName()));
            i++;
            if (i < mods.size())
                feedback.append("\n");
        }
        ctx.getSource().sendFeedback(new LiteralText(feedback.toString()), false);
        return Command.SINGLE_SUCCESS;
    }

    private int modidSubExecutor(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        String inputId = getString(ctx, "modid");

        if (!FabricLoader.getInstance().getModContainer(inputId).isPresent())
            throw new SimpleCommandExceptionType(new LiteralText(String.format("无法找到ModID为%s的Mod!", inputId))).create();

        ModMetadata modMeta = FabricLoader.getInstance().getModContainer(inputId).get().getMetadata();

        StringBuilder modInfo = new StringBuilder();
        modInfo.append(String.format("Mod: §a%s§r (§a%s§r)\n", modMeta.getName(), modMeta.getId()));
        modInfo.append(String.format("版本: §a%s§r\n", modMeta.getVersion()));
        modInfo.append("作者: ");
        Collection<Person> authors = modMeta.getAuthors();
        int i = 0;
        for (Person author : authors) {
            modInfo.append(String.format("§a%s§r", author.getName()));
            i++;
            if (i < authors.size())
                modInfo.append("§r , ");
        }
        modInfo.append("\n");
        modInfo.append(String.format("简介: §a%s§r\n", modMeta.getDescription()));
        modInfo.append(String.format("官网: §a%s§r", modMeta.getContact().get("homepage").isPresent() ? modMeta.getContact().get("homepage").get() : ""));

        ctx.getSource().sendFeedback(new LiteralText(modInfo.toString()), false);
        return Command.SINGLE_SUCCESS;
    }

    private CompletableFuture<Suggestions> modSubSuggest(CommandContext<ServerCommandSource> ctx, SuggestionsBuilder builder) {
        return CommandSource.suggestMatching(FabricLoader.getInstance().getAllMods().stream().map(mod -> mod.getMetadata().getId()), builder);
    }
}
