package com.spring.batch.hello

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
class HelloJobConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
) : NoCoLogging {
    @Bean
    fun helloJob(): Job {
        return JobBuilder("helloJob", this.jobRepository)
            .start(helloStep())
            .next(helloStep2())
            .build()
    }

    @Bean
    fun helloStep(): Step {
        return StepBuilder("helloStep", this.jobRepository)
            .tasklet({ _, _ ->
                logger.info { "Hello, World!" }
                RepeatStatus.FINISHED
            }, this.platformTransactionManager)
            .build()
    }

    @Bean
    fun helloStep2(): Step {
        return StepBuilder("helloStep2", this.jobRepository)
            .tasklet({ _, _ ->
                logger.info { "Step 2 was executed!" }
                RepeatStatus.FINISHED
            }, this.platformTransactionManager)
            .build()
    }
}
