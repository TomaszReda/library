package pl.tomekreda.library.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomekreda.library.quartz.service.ReceiveTheBookForUserService;
import pl.tomekreda.library.quartz.service.ReminderOfGivingABookForUserService;

public class JobReminderOfGivingABookForUser implements Job {

    @Autowired
    private ReminderOfGivingABookForUserService reminderOfGivingABookForUserService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
    }
}
