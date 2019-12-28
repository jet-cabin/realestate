//package com.jet.realestate.biz.schedule;
//
//import com.glodon.morrow.schedule.ScheduleJob;
//import com.glodon.morrow.schedule.SingleJobProcessor;
//import com.glodon.morrow.schedule.internal.JobDesc;
//import com.jet.realestate.biz.model.Rent;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.List;
//
//@Slf4j
//@ScheduleJob(jobName = "rentJob", cron = "0 * * * * ?",description = "rent job",parameter = "nothing")
//public class RentJob implements SingleJobProcessor<Rent> {
//    @Override
//    public void process(Rent rent) {
//
//    }
//
//    @Override
//    public List<Rent> selectTasks(JobDesc jobDesc) {
//        return null;
//    }
//}
