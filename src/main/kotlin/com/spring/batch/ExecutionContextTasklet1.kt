package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class ExecutionContextTasklet1 : Tasklet, NoCoLogging {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        logger.info { "Hello, World!" }

        val jobExecutionContext: ExecutionContext = contribution.stepExecution.jobExecution.executionContext
        val stepExecutionContext = contribution.stepExecution.executionContext


        val jobName = chunkContext.stepContext.stepExecution.jobExecution.jobInstance.jobName
        val stepName = chunkContext.stepContext.stepName

        if (!jobExecutionContext.containsKey("jobName")) {
            jobExecutionContext.putString("jobName", jobName)
        }

        if (!jobExecutionContext.containsKey("stepName")) {
            stepExecutionContext.putString("stepName", stepName)
        }

        logger.info { "Job Name: ${jobExecutionContext.getString("jobName")}" }
        logger.info { "Step Name: ${stepExecutionContext.getString("stepName")}" }

        return RepeatStatus.FINISHED
    }
}
