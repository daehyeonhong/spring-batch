package io.spring.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static java.lang.Thread.sleep;
import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobLauncherConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return this.jobBuilderFactory.get("Job")
                .start(this.step1())
                .next(this.step2())
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    sleep(3000);
                    return FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return this.stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> null)
                .build();
    }
}
