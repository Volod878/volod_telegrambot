package com.github.Volod878.volod_telegrambot.command;

import com.github.Volod878.volod_telegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.Volod878.volod_telegrambot.command.CommandName.*;

/**
 * Help {@link Command}.
 */
public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format("✨<b>Дотупные команды</b>✨\n\n"

                    + "<b>Начать\\закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - приостановить работу со мной\n"
                    + "%s - получить помощь в работе со мной\n"
                    + "%s - узнать количество подписчиков\n"
                    + "%s [n] - сгенерировать пароль, где [n] это количество символов в пароле\n\n"
                    + "Если вам нужно добавить текст или число к команде, нажмите на имя команды и "
                    + "удерживайте пока она не появится в вашем сообщении, либо напечаиайте команду"
                    + " самостоятельно\n",
            START.getCommandName(), STOP.getCommandName(), HELP.getCommandName(), STAT.getCommandName(),
            PASSWORD.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}