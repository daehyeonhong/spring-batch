package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.job.builder.JobBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class ExecutionContextConfiguration(
    private val jobRepository: JobRepository,
    private val platformTransactionManager: PlatformTransactionManager,
    private val executionContextTasklet1: ExecutionContextTasklet1,
    private val executionContextTasklet2: ExecutionContextTasklet2,
    private val executionContextTasklet3: ExecutionContextTasklet3,
    private val executionContextTasklet4: ExecutionContextTasklet4,
) : NoCoLogging {
    @Bean
    fun job(): Job {
        return JobBuilder("job", this.jobRepository)
            .start(step1())
            .next(step2())
            .next(step3())
            .next(step4())
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
            .tasklet(this.executionContextTasklet2, this.platformTransactionManager)
            .build()
    }

    @Bean
    fun step3(): Step {
        return StepBuilder("step3", this.jobRepository)
            .tasklet(this.executionContextTasklet3, this.platformTransactionManager)
            .build()
    }

    @Bean
    fun step4(): Step {
        return StepBuilder("step4", this.jobRepository)
            .tasklet(this.executionContextTasklet4, this.platformTransactionManager)
            .build()
    }

    companion object {
        private const val welcomeMessage = "Hello, World!"
    }
}
