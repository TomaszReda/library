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
import pl.tomekreda.library.quartz.job.JobReminderOfGivingABookForUser;
import pl.tomekreda.library.quartz.utils.QuartzUtils;

import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class ReminderOfGivingABookForUserService {

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    public void reminderOfGivingABookForUser(Date date, UUID bookId, UUID taskForUserId) {
        JobDetailFactoryBean jobDetailFactoryBean = ConfigureQuartz.createJobDetail(JobReminderOfGivingABookForUser.class);
        jobDetailFactoryBean.getJobDataMap().put("bookId", bookId);
        jobDetailFactoryBean.getJobDataMap().put("taskForUserId", taskForUserId);
        jobDetailFactoryBean.setGroup(QuartzUtils.QUARTZ_GROUP_REMINER_USER);
        jobDetailFactoryBean.setBeanName(QuartzUtils.QUARTZ_GROUP_REMINER_USER + taskForUserId);
        jobDetailFactoryBean.afterPropertiesSet();

        SimpleTriggerFactoryBean simpleTriggerFactoryBean = ConfigureQuartz.createTrigger(jobDetailFactoryBean.getObject(), date);
        simpleTriggerFactoryBean.setGroup(QuartzUtils.QUARTZ_GROUP_REMINER_USER);
        simpleTriggerFactoryBean.setBeanName(QuartzUtils.QUARTZ_GROUP_REMINER_USER + taskForUserId);
        simpleTriggerFactoryBean.afterPropertiesSet();

        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            scheduler.scheduleJob(jobDetailFactoryBean.getObject(), simpleTriggerFactoryBean.getObject());
        } catch (SchedulerException e) {
//not cale
        }
    }


    public void deleteJob(UUID taskForUserId) {
        try {
            Scheduler scheduler = schedulerFactoryBean.getScheduler();
            JobKey jobKey = new JobKey(QuartzUtils.QUARTZ_GROUP_REMINER_USER + taskForUserId, QuartzUtils.QUARTZ_GROUP_REMINER_USER);
            scheduler.deleteJob(jobKey);

        } catch (SchedulerException e) {
            log.error("[Quartz]=deleteJob");
        }
    }
}
