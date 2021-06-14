package com.github.Volod878.volod_telegrambot.service;

import com.github.Volod878.volod_telegrambot.javarushclient.dto.GroupDiscussionInfo;
import com.github.Volod878.volod_telegrambot.repository.entity.GroupSub;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {

    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
}