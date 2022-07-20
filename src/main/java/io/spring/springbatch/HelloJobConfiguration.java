package io.spring.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HelloJobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job helloJob() {
        return this.jobBuilderFactory.get("helloJob")
                .start(helloFirstStep())
                .next(helloSecondStep())
                .build();
    }

    @Bean
    public Step helloSecondStep() {
        return this.stepBuilderFactory.get("helloFirstStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("====================");
                    log.info("Hello, Spring Batch!");
                    log.info("====================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }

    @Bean
    public Step helloFirstStep() {
        return this.stepBuilderFactory.get("helloSecondStep")
                .tasklet((contribution, chunkContext) -> {
                    log.info("=========================");
                    log.info("Second Step was executed!");
                    log.info("=========================");
                    return RepeatStatus.FINISHED;
                })
                .build();
    }
}
