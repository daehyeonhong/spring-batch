package io.spring.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobRepositoryListener implements JobExecutionListener {
    private final JobRepository jobRepository;

    @Override
    public void beforeJob(final JobExecution jobExecution) {

    }

    @Override
    public void afterJob(final JobExecution jobExecution) {
        final String jobName = jobExecution.getJobInstance().getJobName();
        final JobParameters jobParameters = new JobParametersBuilder().addString("name", "user1").toJobParameters();
        final JobExecution lastJobExecution = this.jobRepository.getLastJobExecution(jobName, jobParameters);
        if (lastJobExecution != null) for (final StepExecution execution : lastJobExecution.getStepExecutions()) {
            final BatchStatus status = execution.getStatus();
            log.info("status={}", status);
            final ExitStatus exitStatus = execution.getExitStatus();
            log.info("exitStatus={}", exitStatus);
            final String stepName = execution.getStepName();
            log.info("stepName={}", stepName);
        }

    }
}
