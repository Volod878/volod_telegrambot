package com.Volod878.volod_telegrambot.command;

import com.Volod878.volod_telegrambot.service.GroupSubService;
import com.Volod878.volod_telegrambot.service.SendBotMessageService;
import com.Volod878.volod_telegrambot.javarushclient.JavaRushGroupClient;
import com.Volod878.volod_telegrambot.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            JavaRushGroupClient javaRushGroupClient, GroupSubService groupSubService) {

        commandMap = ImmutableMap.<String, Command>builder()
                .put(CommandName.START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(CommandName.STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(CommandName.HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(CommandName.STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(CommandName.PASSWORD.getCommandName(), new PasswordCommand(sendBotMessageService))
                .put(CommandName.NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(CommandName.ADD_GROUP_SUB.getCommandName(), new AddGroupSubCommand(sendBotMessageService, javaRushGroupClient, groupSubService))
                .put(CommandName.LIST_GROUP_SUB.getCommandName(), new ListGroupSubCommand(sendBotMessageService, telegramUserService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }

}