package io.spring.springbatch;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobParameterConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("Job").start(this.step1()).next(this.step2()).build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1").tasklet((contribution, chunkContext) -> {
            final ObjectMapper mapper = new ObjectMapper();
            log.info(mapper.writeValueAsString(contribution.getStepExecution().getJobExecution().getJobParameters()));
            log.info(mapper.writeValueAsString(chunkContext.getStepContext().getJobParameters()));
            log.info("Step1 was Executed");
            return FINISHED;
        }).build();
    }

    @Bean
    public Step step2() {
        return this.stepBuilderFactory.get("step2").tasklet((contribution, chunkContext) -> {
            log.info("Step2 was Executed");
            return FINISHED;
        }).build();
    }
}
