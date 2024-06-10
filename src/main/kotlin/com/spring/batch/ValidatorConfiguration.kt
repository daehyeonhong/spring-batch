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

@Configuration
class ValidatorConfiguration(
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
        .validator(CustomJobParametersValidator())
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
    ): Step = StepBuilder("step3", this.jobRepository).tasklet({ _, _ ->
        logger.info { "Hello, World!" }
        RepeatStatus.FINISHED
    }, this.platformTransactionManager).build()
}

class CustomJobParametersValidator : JobParametersValidator {
    override fun validate(parameters: JobParameters?) {
        if (parameters?.getString("name") == null) {
            throw JobParametersInvalidException("`name` parameter is missing")
        }
    }
}