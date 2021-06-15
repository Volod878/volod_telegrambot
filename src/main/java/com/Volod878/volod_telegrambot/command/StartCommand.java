package com.Volod878.volod_telegrambot.command;

import com.Volod878.volod_telegrambot.repository.entity.TelegramUser;
import com.Volod878.volod_telegrambot.service.SendBotMessageService;
import com.Volod878.volod_telegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Start {@link Command}.
 */
public class StartCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public final static String START_MESSAGE =
            "Привет. Меня немного научили общяться\uD83D\uDE0E\nНо я всеравно дико влюблен в Викусичку:)\n"+
                    "К тому же я помогу тебе быть в курсе последних статей на JavaRush тех авторов, которые тебе интересны.\n\n" +
                    "Нажимай /addGroupSub чтобы подписаться на группу статей в JavaRush.\n" +
                    "Не знаешь о чем я? Напиши /help, чтобы узнать что я умею.";
            /*"Привет. Я Javarush Telegram Bot. Я помогу тебе быть в курсе последних " +
            "статей тех авторов, котрые тебе интересны. Я еще маленький и только учусь.";*/

    // Здесь не добавляем сервис через получение из Application Context.
    // Потому что если это сделать так, то будет циклическая зависимость, которая
    // ломает работу приложения.
    public StartCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        String chatId = update.getMessage().getChatId().toString();

        telegramUserService.findByChatId(chatId).ifPresentOrElse(
                user -> {
                    user.setActive(true);
                    telegramUserService.save(user);
                },
                () -> {
                    TelegramUser telegramUser = new TelegramUser();
                    telegramUser.setActive(true);
                    telegramUser.setChatId(chatId);
                    telegramUserService.save(telegramUser);
                });

        sendBotMessageService.sendMessage(chatId, START_MESSAGE);
    }
}
