package io.spring.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static java.time.LocalDateTime.now;
import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class IncrementerConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob() {
        return this.jobBuilderFactory.get("batchJob")
                .start(this.step1())
                .next(this.step2())
                .incrementer(new CustomJobParametersIncrementer())
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("step1 has executed");
                    return FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return this.stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    if (now().getHour() % 2 == 0)
                        throw new RuntimeException();
                    log.info("step2 has executed");
                    return FINISHED;
                })
                .build();
    }
}
