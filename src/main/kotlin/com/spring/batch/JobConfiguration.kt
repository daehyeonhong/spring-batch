package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDate

@Configuration
class JobConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
) : NoCoLogging {
    @Bean
    fun job(
        @Qualifier(value = "step1") step1: Step,
        @Qualifier(value = "step2") step2: Step,
    ): Job = JobBuilder("batchJob1", this.jobRepository).start(step1).next(step2).build()

    @Bean
    fun job2(
        @Qualifier(value = "step1") step1: Step,
        @Qualifier(value = "step2") step2: Step,
    ): Job = JobBuilder("batchJob2", this.jobRepository).start(step1).next(step2).build()

    @Bean
    fun job3(
        @Qualifier(value = "step1") step1: Step,
        @Qualifier(value = "step2") step2: Step,
    ): Job = JobBuilder("batchJob3", this.jobRepository).start(step1).next(step2).build()

    @Bean
    @JobScope
    fun step1(
        @Value("#{jobParameters['targetDate']}") targetDate: LocalDate
    ): Step = StepBuilder("step1", this.jobRepository).tasklet({ _, _ ->
            logger.info { "Hello, World!" }
            logger.info { "targetDate: $targetDate" }
            RepeatStatus.FINISHED
        }, this.platformTransactionManager).build()

    @Bean
    fun step2(
    ): Step = StepBuilder("step2", this.jobRepository).tasklet({ _, _ ->
            logger.info { "Hello, World!" }
            RepeatStatus.FINISHED
        }, this.platformTransactionManager).build()
}
