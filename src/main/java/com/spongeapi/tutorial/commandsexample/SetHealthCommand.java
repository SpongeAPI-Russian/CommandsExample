package com.spongeapi.tutorial.commandsexample;

import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.Optional;

public class SetHealthCommand implements CommandExecutor {
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Player> player = args.getOne("player");
        if (player.isPresent()) {
            Optional<Double> newHealthOpt = args.getOne(Text.of("health"));
            if (newHealthOpt.isPresent()) {
                double newHealth = newHealthOpt.get();
                player.get().offer(Keys.HEALTH, newHealth);
                return CommandResult.success();
            }
        }
        return CommandResult.empty();
    }
}
