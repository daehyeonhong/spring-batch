package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class JobRepositoryConfiguration(
) : NoCoLogging {
    @Bean(name = ["defaultJob"])
    fun job(
        jobRepository: JobRepository,
        @Qualifier(value = "step1") step1: Step,
        @Qualifier(value = "step2") step2: Step,
    ): Job {
        logger.info { "Creating job" }
        return JobBuilder("job", jobRepository)
            .start(step1)
            .next(step2)
            .build()
    }

    @JobScope
    @Bean(name = ["step1"])
    fun step1(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
    ): Step {
        logger.info { "Creating step1" }
        return StepBuilder("step1", jobRepository)
            .tasklet(printGreeting(), platformTransactionManager)
            .build()
    }

    @JobScope
    @Bean(name = ["step2"])
    fun step2(
        jobRepository: JobRepository,
        platformTransactionManager: PlatformTransactionManager,
    ): Step {
        logger.info { "Creating step2" }
        return StepBuilder("step2", jobRepository)
            .tasklet(printGreeting(), platformTransactionManager)
            .build()
    }

    private fun printGreeting() = Tasklet { _, _ ->
        logger.info { "Hello, World!" }
        RepeatStatus.FINISHED
    }
}
