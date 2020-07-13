package com.example.common.utils;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class WorkTeamUtils {

    public static String convertWorkTeams(ArrayList<String> workTeamList) {
        if (CollectionUtils.isEmpty(workTeamList)) {
            return null;
        }
        return workTeamList.stream().collect(Collectors.joining(","));
    }

    public static ArrayList<String> convertWorkTeams(String workTeamIds) {
        return workTeamIds == null ? null : new ArrayList<>(Arrays.stream(workTeamIds.split(",")).collect(Collectors.toList()));
    }
}
