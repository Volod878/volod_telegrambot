package com.Volod878.volod_telegrambot.bot;

import com.Volod878.volod_telegrambot.command.CommandContainer;
import com.Volod878.volod_telegrambot.command.CommandName;
import com.Volod878.volod_telegrambot.service.GroupSubService;
import com.Volod878.volod_telegrambot.service.SendBotMessageServiceImpl;
import com.Volod878.volod_telegrambot.service.TelegramUserService;
import com.Volod878.volod_telegrambot.javarushclient.JavaRushGroupClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

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
    public VolodTelegrambot(TelegramUserService telegramUserService, JavaRushGroupClient groupClient, GroupSubService groupSubService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this), telegramUserService, groupClient, groupSubService);
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();

                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(CommandName.NO.getCommandName()).execute(update);
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
