package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class JobConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
) : NoCoLogging {
    @Bean
    fun job(
        flow: Flow,
        step5: Step,
    ): Job = JobBuilder("flow", this.jobRepository).start(flow).next(step5).end().build()

    @Bean
    fun step1(
    ): Step = StepBuilder("step1", this.jobRepository).tasklet({ _, _ ->
        logger.info { "Hello, World!" }
        RepeatStatus.FINISHED
    }, this.platformTransactionManager).build()

    @Bean
    fun step2(
    ): Step = StepBuilder("step2", this.jobRepository).tasklet({ _, _ ->
        logger.info { "Hello, World!" }
        RepeatStatus.FINISHED
    }, this.platformTransactionManager).build()

    @Bean
    fun step3(
    ): Step = StepBuilder("step3", this.jobRepository).tasklet({ _, _ ->
        logger.info { "Hello, World!" }
        RepeatStatus.FINISHED
    }, this.platformTransactionManager).build()

    @Bean
    fun step4(
    ): Step = StepBuilder("step4", this.jobRepository).tasklet({ _, _ ->
        logger.info { "Hello, World!" }
        RepeatStatus.FINISHED
    }, this.platformTransactionManager).build()

    @Bean
    fun step5(
    ): Step = StepBuilder("step5", this.jobRepository).tasklet({ _, _ ->
        logger.info { "Hello, World!" }
        RepeatStatus.FINISHED
    }, this.platformTransactionManager).build()

    @Bean
    fun flow(
        step3: Step,
        step4: Step,
    ): Flow = FlowBuilder<Flow>("flow")
        .start(step3)
        .next(step4)
        .end()

}
