package com.astar.ots.quartz;

import com.astar.ots.service.PollingService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SimpleJob implements Job {

    @Autowired
    private PollingService pollingService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        log.info("Calling Polling-Service - Start");
        pollingService.getOvenData();
        log.info("Calling Polling-Service - End");
    }
}
