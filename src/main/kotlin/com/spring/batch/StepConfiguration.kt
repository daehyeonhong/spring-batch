package com.spring.batch

import io.klogging.NoCoLogging
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
class StepConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
) : NoCoLogging {
    @Bean
    fun job(): Job {
        return JobBuilder("job", this.jobRepository)
            .start(step1())
            .next(step2())
            .build()
    }

    @Bean
    fun step1(): Step {
        return StepBuilder("step1", this.jobRepository)
            .tasklet({ _, _ ->
                logger.info { "Hello, World!" }
                RepeatStatus.FINISHED
            }, this.platformTransactionManager)
            .build()
    }

    @Bean
    fun step2(): Step {
        return StepBuilder("step2", this.jobRepository)
            .tasklet(
                CustomTasklet(),
                this.platformTransactionManager
            )
            .build()
    }
}
