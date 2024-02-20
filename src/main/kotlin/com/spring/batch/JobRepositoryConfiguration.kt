package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.DefaultJobKeyGenerator
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.database.support.DefaultDataFieldMaxValueIncrementerFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.Isolation
import javax.sql.DataSource


@Configuration
class JobRepositoryConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
    private val jobExecutionListener: JobExecutionListener,
    private val dataSource: DataSource,
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
}
