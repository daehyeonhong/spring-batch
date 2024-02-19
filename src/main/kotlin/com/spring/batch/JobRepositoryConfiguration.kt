package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class JobRepositoryConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
    private val jobExecutionListener: JobExecutionListener,
) : NoCoLogging {
    private val executionContextTasklet1: Tasklet =
        Tasklet { _, _ ->
            logger.info { "Hello, World!" }
            RepeatStatus.FINISHED
        }

    @Bean
    fun job(): Job {
        return JobBuilder("job", this.jobRepository)
            .start(step1())
            .next(step2())
            .listener(this.jobExecutionListener)
            .build()
    }

    @Bean
    fun step1(): Step {
        return StepBuilder("step1", this.jobRepository)
            .tasklet(this.executionContextTasklet1, this.platformTransactionManager)
            .build()
    }

    @Bean
    fun step2(): Step {
        return StepBuilder("step2", this.jobRepository)
            .tasklet(this.executionContextTasklet1, this.platformTransactionManager)
            .build()
    }

    companion object {
        private const val welcomeMessage = "Hello, World!"
    }
}
