package pl.tomekreda.library.quartz.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import pl.tomekreda.library.quartz.configuration.ConfigureQuartz;
import pl.tomekreda.library.quartz.service.ReceiveTheBookForUserService;
import pl.tomekreda.library.quartz.service.ReminderOfGivingABookForLibraryService;

public class JobReminderOfGivingABookForLibrary implements Job {

    @Autowired
    private ReminderOfGivingABookForLibraryService reminderOfGivingABookForLibraryService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {


    }
}
