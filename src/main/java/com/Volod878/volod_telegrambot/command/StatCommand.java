package com.Volod878.volod_telegrambot.command;

import com.Volod878.volod_telegrambot.command.annotation.AdminCommand;
import com.Volod878.volod_telegrambot.dto.StatisticDTO;
import com.Volod878.volod_telegrambot.service.SendBotMessageService;
import com.Volod878.volod_telegrambot.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

/**
 * Statistics {@link Command}.
 */
@AdminCommand
public class StatCommand implements Command {

    private final StatisticsService statisticsService;
    private final SendBotMessageService sendBotMessageService;

    public final static String STAT_MESSAGE = "✨<b>Подготовил статистику</b>✨\n" +
            "- Количество активных пользователей: %s\n" +
            "- Количество неактивных пользователей: %s\n" +
            "- Среднее количество групп на одного пользователя: %s\n\n" +
            "<b>Информация по активным группам</b>:\n" +
            "%s";

    @Autowired
    public StatCommand(SendBotMessageService sendBotMessageService, StatisticsService statisticsService) {
        this.sendBotMessageService = sendBotMessageService;
        this.statisticsService = statisticsService;
    }

    @Override
    public void execute(Update update) {
        StatisticDTO statisticDTO = statisticsService.countBotStatistic();

        String collectedGroups = statisticDTO.getGroupStatDTOs().stream()
                .map(it -> String.format("%s (id = %s) - %s подписчиков", it.getTitle(), it.getId(), it.getActiveUserCount()))
                .collect(Collectors.joining("\n"));

        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), String.format(STAT_MESSAGE,
                statisticDTO.getActiveUserCount(),
                statisticDTO.getInactiveUserCount(),
                statisticDTO.getAverageGroupCountByUser(),
                collectedGroups));
    }
}
