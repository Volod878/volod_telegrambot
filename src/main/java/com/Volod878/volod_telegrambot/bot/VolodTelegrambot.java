package com.Volod878.volod_telegrambot.bot;

import com.Volod878.volod_telegrambot.command.CommandContainer;
import com.Volod878.volod_telegrambot.command.CommandName;
import com.Volod878.volod_telegrambot.service.GroupSubService;
import com.Volod878.volod_telegrambot.service.SendBotMessageServiceImpl;
import com.Volod878.volod_telegrambot.service.StatisticsService;
import com.Volod878.volod_telegrambot.service.TelegramUserService;
import com.Volod878.volod_telegrambot.javarushclient.JavaRushGroupClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

/**
 * Telegram bot for Javarush Community from Javarush community.
 */
@Component
public class VolodTelegrambot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    @Autowired
    public VolodTelegrambot(TelegramUserService telegramUserService, JavaRushGroupClient groupClient, GroupSubService groupSubService,
                               @Value("#{'${bot.admins}'.split(',')}") List<String> admins, StatisticsService statisticsService) {
        this.commandContainer =
                new CommandContainer(new SendBotMessageServiceImpl(this),
                        telegramUserService, groupClient, groupSubService, admins, statisticsService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String username = update.getMessage().getFrom().getUserName();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier, username).execute(update);
            } else {
                commandContainer.retrieveCommand(CommandName.NO.getCommandName(), username).execute(update);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }
}
