package io.spring.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;

@Slf4j
@RestController
@RequiredArgsConstructor
public class JobLauncherController {
    private final Job job;
    private final BasicBatchConfigurer basicBatchConfigurer;

    @PostMapping(value = "/batch")
    public String launch(@RequestBody final Member member) throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        final JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", member.getId())
                .addDate("date", new Date())
                .toJobParameters();

        final SimpleJobLauncher jobLauncher = (SimpleJobLauncher) this.basicBatchConfigurer.getJobLauncher();
        jobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor());
        jobLauncher.run(this.job, jobParameters);

        return "batch completed";
    }
}
