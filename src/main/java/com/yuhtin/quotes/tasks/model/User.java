package com.yuhtin.quotes.tasks.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Data
public class User {

    private final String nickname;
    private final List<String> completedTasksIdList = new ArrayList<>();

    @Override
    public String toString() {
        return String.join(",", completedTasksIdList);
    }


}
