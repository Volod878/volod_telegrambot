package com.Volod878.volod_telegrambot.service;

import com.Volod878.volod_telegrambot.repository.entity.GroupSub;
import com.Volod878.volod_telegrambot.javarushclient.dto.GroupDiscussionInfo;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {

    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);
}