package io.spring.springbatch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import static org.springframework.batch.repeat.RepeatStatus.FINISHED;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JobConfiguration {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job batchJob1() {
        return this.jobBuilderFactory.get("batchJob1")
                .start(this.step1())
                .next(this.step2())
                .build();
    }

    @Bean
    public Job batchJob2() {
        return this.jobBuilderFactory.get("batchJob2")
                .start(this.flow())
                .next(this.step5())
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return this.stepBuilderFactory.get("step1")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step1 has executed");
                    return FINISHED;
                })
                .build();
    }

    @Bean
    public Step step2() {
        return this.stepBuilderFactory.get("step2")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step2 has executed");
                    return FINISHED;
                })
                .build();
    }

    @Bean
    public Flow flow() {
        final FlowBuilder<Flow> flowFlowBuild = new FlowBuilder<>("flow");

        flowFlowBuild
                .start(this.step3())
                .next(this.step4())
                .end();

        return flowFlowBuild.build();
    }

    private Step step3() {
        return this.stepBuilderFactory.get("step3")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step3 has executed");
                    return FINISHED;
                })
                .build();
    }

    private Step step4() {
        return this.stepBuilderFactory.get("step4")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step4 has executed");
                    return FINISHED;
                })
                .build();
    }

    private Step step5() {
        return this.stepBuilderFactory.get("step5")
                .tasklet((contribution, chunkContext) -> {
                    log.info("Step5 has executed");
                    return FINISHED;
                })
                .build();
    }

}
