package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.BatchStatus
import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager

@Configuration
class StartNextConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
) : NoCoLogging {
    @Bean
    fun job(
        step1: Step,
        step2: Step,
        step3: Step,
    ): Job = JobBuilder("batchJob", this.jobRepository)
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
    ): Step = StepBuilder("step2", this.jobRepository).tasklet({ _, _ ->
        logger.info { "Hello, World!" }
        RepeatStatus.FINISHED
    }, this.platformTransactionManager).build()

    @Bean
    fun step3(
    ): Step = StepBuilder("step3", this.jobRepository).tasklet(
        { contribution, chunkContext ->
            chunkContext.stepContext.stepExecution.status = BatchStatus.FAILED
            contribution.exitStatus = ExitStatus.STOPPED
            this.logger.info { "step3 has executed" }
            RepeatStatus.FINISHED
        }, this.platformTransactionManager
    ).build()
}