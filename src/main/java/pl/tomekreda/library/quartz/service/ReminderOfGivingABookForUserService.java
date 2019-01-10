package pl.tomekreda.library.quartz.service;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.quartz.configuration.ConfigureQuartz;
import pl.tomekreda.library.quartz.job.JobReminderOfGivingABookForUser;

import java.util.Date;
import java.util.UUID;

@Service
public class ReminderOfGivingABookForUserService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void reminderOfGivingABookForUser(Date date, UUID bookId, UUID taskForUserId) {
        JobDetailFactoryBean jobDetailFactoryBean= ConfigureQuartz.createJobDetail(JobReminderOfGivingABookForUser.class);
        jobDetailFactoryBean.getJobDataMap().put("bookId",bookId);
        jobDetailFactoryBean.getJobDataMap().put("taskForUserId",taskForUserId);
        jobDetailFactoryBean.setGroup("reminder_of_giving_a_book_for_user");
        jobDetailFactoryBean.setBeanName("reminder_of_giving_a_book_for_user"+taskForUserId);
        jobDetailFactoryBean.afterPropertiesSet();

        SimpleTriggerFactoryBean simpleTriggerFactoryBean=ConfigureQuartz.createTrigger(jobDetailFactoryBean.getObject(),date);
        simpleTriggerFactoryBean.setGroup("reminder_of_giving_a_book_for_user");
        simpleTriggerFactoryBean.setBeanName("reminder_of_giving_a_book_for_user"+taskForUserId);
        simpleTriggerFactoryBean.afterPropertiesSet();

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.scheduleJob(jobDetailFactoryBean.getObject(),simpleTriggerFactoryBean.getObject());
        } catch (SchedulerException e) {
        }
    }


    public void deleteJob(UUID taskForUserId){
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey=new JobKey("reminder_of_giving_a_book_for_user"+taskForUserId,"reminder_of_giving_a_book_for_user");
            scheduler.deleteJob(jobKey);

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
