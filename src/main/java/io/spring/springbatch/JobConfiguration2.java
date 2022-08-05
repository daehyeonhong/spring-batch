package io.spring.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfiguration2 {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob2() {
        return this.jobBuilderFactory.get("batchJob2")
                .incrementer(new RunIdIncrementer())
                .start(this.step3())
                .next(this.step4())
                .build();
    }

    @Bean
    public Step step3() {
        return this.stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("step3 has executed");
                    return FINISHED;
                })
                .build();
    }

    @Bean
    public Step step4() {
        return this.stepBuilderFactory.get("step4")
                .tasklet((contribution, chunkContext) -> {
                    log.info("step4 has executed");
                    return FINISHED;
                })
                .build();
    }
}
