package com.Volod878.volod_telegrambot.command;

import com.Volod878.volod_telegrambot.repository.entity.GroupSub;
import com.Volod878.volod_telegrambot.repository.entity.TelegramUser;
import com.Volod878.volod_telegrambot.service.SendBotMessageService;
import com.Volod878.volod_telegrambot.service.TelegramUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.Volod878.volod_telegrambot.command.AbstractCommandTest.prepareUpdate;

@DisplayName("Unit-level testing for ListGroupSubCommand")
public class ListGroupSubCommandTest {

    @Test
    public void shouldProperlyShowsListGroupSub() {
        //given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setActive(true);
        telegramUser.setChatId("1");

        List<GroupSub> groupSubList = new ArrayList<>();
        groupSubList.add(populateGroupSub(1, "gs1"));
        groupSubList.add(populateGroupSub(2, "gs2"));
        groupSubList.add(populateGroupSub(3, "gs3"));
        groupSubList.add(populateGroupSub(4, "gs4"));

        telegramUser.setGroupSubs(groupSubList);

        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        Mockito.when(telegramUserService.findByChatId(telegramUser.getChatId())).thenReturn(Optional.of(telegramUser));

        ListGroupSubCommand command = new ListGroupSubCommand(sendBotMessageService, telegramUserService);

        Update update = prepareUpdate(Long.valueOf(telegramUser.getChatId()), CommandName.LIST_GROUP_SUB.getCommandName());

        String joinedGroups = telegramUser.getGroupSubs().stream()
                .map(it -> "Группа: " + it.getTitle() + " , ID = " + it.getId() + " \n")
                .collect(Collectors.joining());
        String collectedGroups = String.format("Я нашел все подписки на группы: \n\n %s", joinedGroups);

        //when
        command.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(), collectedGroups);
    }

    private GroupSub populateGroupSub(Integer id, String title) {
        GroupSub gs = new GroupSub();
        gs.setId(id);
        gs.setTitle(title);
        return gs;
    }
}