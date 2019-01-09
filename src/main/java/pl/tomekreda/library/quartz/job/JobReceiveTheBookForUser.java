package pl.tomekreda.library.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomekreda.library.quartz.service.ReceiveTheBookForUserService;

public class JobReceiveTheBookForUser implements Job {

    @Autowired
    private ReceiveTheBookForUserService receiveTheBookForUserService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.err.println("cosss");
    }
}
