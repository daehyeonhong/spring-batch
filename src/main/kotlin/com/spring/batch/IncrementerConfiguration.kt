package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.*
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import java.time.LocalDateTime

@Configuration
class IncrementerConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
) : NoCoLogging {
    @Bean
    fun job(
        step1: Step,
        step2: Step,
    ): Job = JobBuilder("batchJob", this.jobRepository)
        .start(step1)
        .next(step2)
        .preventRestart()
        .incrementer(CustomJobParametersIncrementer())
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
}

class CustomJobParametersIncrementer : JobParametersIncrementer {
    override fun getNext(parameters: JobParameters?): JobParameters {
        val id = LocalDateTime.now()
        return JobParametersBuilder().addLocalDateTime("run.id", id).toJobParameters()
    }
}