package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.stereotype.Component

@Component
class JobRepositoryListener(
    private val jobRepository: JobRepository,
) : JobExecutionListener, NoCoLogging {
    override fun beforeJob(jobExecution: JobExecution) {
        super.beforeJob(jobExecution)
    }

    override fun afterJob(jobExecution: JobExecution) {
        val jobName = jobExecution.jobInstance.jobName
        val jobParameters = JobParametersBuilder()
            .addString("targetDate", "20210102")
            .toJobParameters()

        val lastJobExecution = this.jobRepository.getLastJobExecution(jobName, jobParameters)


        lastJobExecution?.let {
            for (stepExecution in lastJobExecution.stepExecutions) {
                val status = stepExecution.status
                logger.info { "status = ${status}" }
                val exitStatus = stepExecution.exitStatus
                logger.info { "exitStatus = ${exitStatus}" }
                val stepName = stepExecution.stepName
                logger.info { "stepName = ${stepName}" }
            }
        }
    }
}
