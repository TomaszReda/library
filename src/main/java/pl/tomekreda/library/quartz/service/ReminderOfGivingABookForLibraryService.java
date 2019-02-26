package pl.tomekreda.library.quartz.service;

import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
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
import java.util.UUID;

@Service
@Slf4j
public class ReminderOfGivingABookForLibraryService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void reminderOfGivingABookForLibrary(Date date, UUID bookId, UUID taskForLibraryId) {
        JobDetailFactoryBean jobDetailFactoryBean = ConfigureQuartz.createJobDetail(JobReminderOfGivingABookForLibrary.class);
        jobDetailFactoryBean.getJobDataMap().put("bookId", bookId);
        jobDetailFactoryBean.getJobDataMap().put("taskForLibraryId", taskForLibraryId);

        jobDetailFactoryBean.setGroup("reminder_of_giving_a_book_for_library");
        jobDetailFactoryBean.setBeanName("reminder_of_giving_a_book_for_library" + taskForLibraryId);
        jobDetailFactoryBean.afterPropertiesSet();

        SimpleTriggerFactoryBean simpleTriggerFactoryBean = ConfigureQuartz.createTrigger(jobDetailFactoryBean.getObject(), date);
        simpleTriggerFactoryBean.setGroup("reminder_of_giving_a_book_for_library");
        simpleTriggerFactoryBean.setBeanName("reminder_of_giving_a_book_for_library" + taskForLibraryId);
        simpleTriggerFactoryBean.afterPropertiesSet();

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.scheduleJob(jobDetailFactoryBean.getObject(), simpleTriggerFactoryBean.getObject());
        } catch (SchedulerException e) {
        }
    }

    public void deleteJob(UUID taskForLibraryId) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = new JobKey("reminder_of_giving_a_book_for_library" + taskForLibraryId, "reminder_of_giving_a_book_for_library");
            scheduler.deleteJob(jobKey);

        } catch (SchedulerException e) {
            log.error("[Quartz]=deleteJob");

        }
    }
}
