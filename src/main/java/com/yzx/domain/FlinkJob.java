package com.yzx.domain;

import lombok.Data;

@Data
public class FlinkJob {
    String jid;
    String jobName;
    String startTime;
    String duration;
    String endTime;
    Task tasks;
    String status;

    @Data
    public static class Task{
        int total;
        int created;
        int scheduled;
        int deploying;
        int running;
        int finished;
        int canceling;
        int canceled;
        int failed;
        int reconciling;
        int initializing;
    }
}
