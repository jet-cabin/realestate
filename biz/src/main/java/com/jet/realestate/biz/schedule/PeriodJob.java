//package com.jet.realestate.biz.schedule;
//
//import com.glodon.morrow.schedule.ScheduleJob;
//import com.glodon.morrow.schedule.SingleJobProcessor;
//import com.glodon.morrow.schedule.internal.JobDesc;
//import com.jet.realestate.biz.model.House;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Collections;
//import java.util.List;
//
//@Slf4j
//@ScheduleJob(jobName = "housejob", cron = "1 * * * * ?", parameter = "anything", description = "job for house")
//public class PeriodJob implements SingleJobProcessor<House> {
//    @Override
//    public void process(House house) {
//        log.info("process {}", house == null ? "" : house);
//    }
//
//    @Override
//    public List<House> selectTasks(JobDesc jobDesc) {
////        return Arrays.asList(new House());
//        return Collections.emptyList();
//    }
//}
