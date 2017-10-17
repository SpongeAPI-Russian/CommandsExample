package com.spongeapi.tutorial.commandsexample;

import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;

@Plugin(
        id = "commandsexample",
        name = "CommandsExample",
        version = "0.1-SNAPSHOT",
        description = "Примеры создания команд на Sponge",
        url = "https://spongeapi.com",
        authors = {
                "Xakep_SDK"
        }
)
public class CommandsExample {

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        // Просто команда
        CommandSpec whoAmI = CommandSpec.builder()
                .description(Text.of("Выводит в чат Ваш ник"))
                .permission("commandsexample.cmd.whoami")
                .executor((src, args) -> {
                    src.sendMessage(Text.of(src.getName()));
                    return CommandResult.empty();
                })
                .build();

        Sponge.getCommandManager().register(this, whoAmI, "whoami", "wami", "mynick");

        // Команда с аргументом и флагом
        CommandSpec kill = CommandSpec.builder()
                .description(Text.of("Убивает выбранного игрока"))
                .permission("commandsexample.cmd.kill")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.playerOrSource(Text.of("player"))),
                        GenericArguments.requiringPermission(
                                GenericArguments.flags()
                                        .valueFlag(GenericArguments.integer(Text.of("time")),"t")
                                        .buildWith(GenericArguments.none()), "commandsexample.cmd.kill.timed")
                )
                .executor(new PlayerKillCommand(this))
                .build();

        Sponge.getCommandManager().register(this, kill, "kill", "k");

        // Команда с аргументом и подкомандой
        CommandSpec getHealth = CommandSpec.builder()
                .description(Text.of("Показывает жизни игрока"))
                .permission("commandsexample.cmd.health.get")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.playerOrSource(Text.of("player")))
                )
                .executor(new GetHealthCommand())
                .build();
        CommandSpec setHealth = CommandSpec.builder()
                .description(Text.of("Устанавливает жизни игрока"))
                .permission("commandsexample.cmd.health.set")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.playerOrSource(Text.of("player"))),
                        GenericArguments.doubleNum(Text.of("health"))
                )
                .executor(new SetHealthCommand())
                .build();

        CommandSpec health = CommandSpec.builder()
                .description(Text.of("Управление жизнями игрока"))
                .child(getHealth, "get", "g")
                .child(setHealth, "set", "s")
                .arguments(
                        GenericArguments.onlyOne(GenericArguments.playerOrSource(Text.of("player")))
                )
                .executor(new GetHealthCommand())
                .build();


        Sponge.getCommandManager().register(this, health, "health", "h");
    }
}
