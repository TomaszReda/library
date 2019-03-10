package pl.tomekreda.library.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomekreda.library.service.QuartzService;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class JobReminderOfGivingABookForLibrary implements Job {

    @Autowired
    private QuartzService quartzService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("[Quartz JobReminderOfGivingABookForLibrary run with time]" + LocalDateTime.now().toString());
        JobDataMap detail = context.getJobDetail().getJobDataMap();
        UUID bookId = (UUID) detail.get("bookId");
        UUID taskForUserId = (UUID) detail.get("taskForLibraryId");
        quartzService.quartzJobReminderOfGivingABookForLibrary(bookId, taskForUserId);

    }
}
