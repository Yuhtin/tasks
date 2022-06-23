package com.yuhtin.commissions.tasks.sql;

import com.yuhtin.commissions.tasks.model.User;
import com.yuhtin.commissions.tasks.sql.provider.DatabaseProvider;
import com.yuhtin.commissions.tasks.sql.provider.document.Document;
import com.yuhtin.commissions.tasks.sql.provider.document.parser.impl.TasksDocumentParser;

import javax.annotation.Nullable;

public final class TasksDAO extends DatabaseProvider {

    private static final String TABLE = "tasks_data";

    public void createTable() {
        update("CREATE TABLE IF NOT EXISTS " + TABLE + "(" +
                "owner CHAR(36) NOT NULL PRIMARY KEY," +
                "data LONGTEXT NOT NULL DEFAULT 0" +
                ");");
    }

    @Nullable
    public User find(String nick) {
        Document document = query("select * from " + TABLE + " where owner = '" + nick + "';");
        if (document == null) return null;

        return document.parse(TasksDocumentParser.getInstance());
    }

    public void save(String nick, User user) {
        update("replace into " + TABLE + " values (?, ?);", nick, user.toString());
    }

}