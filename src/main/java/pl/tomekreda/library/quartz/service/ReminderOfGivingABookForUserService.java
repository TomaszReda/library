package pl.tomekreda.library.quartz.service;

import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.quartz.configuration.ConfigureQuartz;
import pl.tomekreda.library.quartz.job.JobReminderOfGivingABookForLibrary;

import java.util.Date;

@Service
public class ReminderOfGivingABookForUserService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void reminderOfGivingABookForUser(Date date) {
        JobDetailFactoryBean jobDetailFactoryBean= ConfigureQuartz.createJobDetail(JobReminderOfGivingABookForLibrary.class);

        jobDetailFactoryBean.setGroup("reminder_of_giving_a_book_for_library");
        jobDetailFactoryBean.setBeanName("reminder_of_giving_a_book_for_library");
        jobDetailFactoryBean.afterPropertiesSet();

        SimpleTriggerFactoryBean simpleTriggerFactoryBean=ConfigureQuartz.createTrigger(jobDetailFactoryBean.getObject(),date);
        simpleTriggerFactoryBean.setGroup("reminder_of_giving_a_book_for_library");
        simpleTriggerFactoryBean.setBeanName("reminder_of_giving_a_book_for_library");
        simpleTriggerFactoryBean.afterPropertiesSet();

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.scheduleJob(jobDetailFactoryBean.getObject(),simpleTriggerFactoryBean.getObject());
        } catch (SchedulerException e) {
        }
    }
}
