package com.spongeapi.tutorial.commandsexample;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class PlayerKillCommand implements CommandExecutor {
    private final CommandsExample plugin;

    public PlayerKillCommand(CommandsExample plugin) {
        this.plugin = plugin;
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Player player = args.<Player>getOne("player").get();
        Optional<Integer> timeOpt = args.getOne("time");
        src.sendMessage(Text.of(player.getName() + " умрет через " + timeOpt.orElse(0) + " секунд"));
        Task.builder().delay(timeOpt.orElse(0), TimeUnit.SECONDS)
                .execute(() -> player.offer(Keys.HEALTH, 0d))
                .submit(plugin);
        return CommandResult.empty();
    }
}
