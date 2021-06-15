package com.Volod878.volod_telegrambot.service;

import com.Volod878.volod_telegrambot.repository.entity.GroupSub;
import com.Volod878.volod_telegrambot.javarushclient.dto.GroupDiscussionInfo;

import java.util.List;
import java.util.Optional;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {

    GroupSub save(String chatId, GroupDiscussionInfo groupDiscussionInfo);

    GroupSub save(GroupSub groupSub);

    Optional<GroupSub> findById(Integer id);

    List<GroupSub> findAll();
}
