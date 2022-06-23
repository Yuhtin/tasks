package com.yuhtin.commissions.tasks.sql.provider.document.parser.impl;

import com.yuhtin.commissions.tasks.model.User;
import com.yuhtin.commissions.tasks.sql.provider.document.Document;
import com.yuhtin.commissions.tasks.sql.provider.document.parser.DocumentParser;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TasksDocumentParser implements DocumentParser<User> {

    @Getter private static final TasksDocumentParser instance = new TasksDocumentParser();

    @Override
    public User parse(Document document) {
        String data = document.getString("data");

        User user = new User(document.getString("owner"));
        for (String taskId : data.split("@")) {
            user.getCompletedTasksIdList().add(taskId);
        }

        return user;
    }

}
