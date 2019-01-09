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
public class ReminderOfGivingABookForLibraryService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void reminderOfGivingABookForLibrary(Date date) {
        JobDetailFactoryBean jobDetailFactoryBean= ConfigureQuartz.createJobDetail(JobReminderOfGivingABookForLibrary.class);

        jobDetailFactoryBean.setGroup("reminder_of_giving_a_book_for_user");
        jobDetailFactoryBean.setBeanName("reminder_of_giving_a_book_for_user");
        jobDetailFactoryBean.afterPropertiesSet();

        SimpleTriggerFactoryBean simpleTriggerFactoryBean=ConfigureQuartz.createTrigger(jobDetailFactoryBean.getObject(),date);
        simpleTriggerFactoryBean.setGroup("reminder_of_giving_a_book_for_user");
        simpleTriggerFactoryBean.setBeanName("reminder_of_giving_a_book_for_user");
        simpleTriggerFactoryBean.afterPropertiesSet();

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.scheduleJob(jobDetailFactoryBean.getObject(),simpleTriggerFactoryBean.getObject());
        } catch (SchedulerException e) {
        }
    }
}
