package pl.tomekreda.library.quartz.service;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.quartz.configuration.ConfigureQuartz;
import pl.tomekreda.library.quartz.job.JobReceiveTheBookForUser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

@Service
public class ReceiveTheBookForUserService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;


    public void receiveTheBook(Date date, UUID bookId,UUID taskForUserId) {

        JobDetailFactoryBean jobDetailFactoryBean=ConfigureQuartz.createJobDetail(JobReceiveTheBookForUser.class);

        jobDetailFactoryBean.setGroup("receive_the_book_for_user");
        jobDetailFactoryBean.getJobDataMap().put("bookId",bookId);
        jobDetailFactoryBean.getJobDataMap().put("taskForUserId",taskForUserId);
        jobDetailFactoryBean.setBeanName("receive_the_book_for_user "+taskForUserId);
        jobDetailFactoryBean.afterPropertiesSet();

        SimpleTriggerFactoryBean simpleTriggerFactoryBean=ConfigureQuartz.createTrigger(jobDetailFactoryBean.getObject(),date);
        simpleTriggerFactoryBean.setGroup("receive_the_book_for_user");
        simpleTriggerFactoryBean.setBeanName("receive_the_book_for_user "+taskForUserId);
        simpleTriggerFactoryBean.afterPropertiesSet();

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.scheduleJob(jobDetailFactoryBean.getObject(),simpleTriggerFactoryBean.getObject());
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

    }



    public void deleteJob(UUID taskForUserId){
        try {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        JobKey jobKey=new JobKey("receive_the_book_for_user "+taskForUserId,"receive_the_book_for_user");
        scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }


}
