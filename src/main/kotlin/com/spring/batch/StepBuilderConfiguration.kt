package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class StepBuilderConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
) : NoCoLogging {
    @Bean
    fun batchJob(
        step1: Step,
        step2: Step,
        step3: Step,
    ): Job = JobBuilder("batchJob", this.jobRepository)
        .start(step1)
        .next(step2)
        .next(step3)
        .incrementer(RunIdIncrementer())
        .build()

    @Bean
    fun job(
        step1: Step,
        step2: Step,
        step3: Step,
    ): Job = JobBuilder("job", this.jobRepository)
        .start(step1)
        .next(step2)
        .next(step3)
        .build()

    @Bean
    fun step1(
    ): Step = StepBuilder("step1", this.jobRepository).tasklet({ _, _ ->
        logger.info { "Hello, World!" }
        RepeatStatus.FINISHED
    }, this.platformTransactionManager).build()

    @Bean
    fun step2(
    ): Step = StepBuilder("step2", this.jobRepository)
        .chunk<String, String>(10, platformTransactionManager)
        .reader { null }
        .processor { null }
        .writer {}
        .build()

    @Bean
    fun step3(
        step1: Step
    ): Step = StepBuilder("step3", this.jobRepository)
        .partitioner(step1)
        .gridSize(2)
        .build()

    @Bean
    fun step4(
        job: Job
    ): Step = StepBuilder("step4", this.jobRepository)
        .job(job)
        .build()

    @Bean
    fun step5(
        flow: Flow,
    ): Step = StepBuilder("step4", this.jobRepository)
        .flow(flow)
        .build()

    @Bean
    fun flow(
        step2: Step
    ): Flow {
        val flowBuilder = FlowBuilder<Flow>("flow")
        flowBuilder.start(step2).end()
        return flowBuilder.build()
    }

}