package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class ExecutionContextTasklet2 : Tasklet, NoCoLogging {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        logger.info { "Hello, World!" }

        val executionContext = chunkContext.stepContext.stepExecution.jobExecution.executionContext
        val stepExecutionContext = chunkContext.stepContext.stepExecution.executionContext

        logger.info { "Job Name: ${executionContext["jobName"]}" }
        logger.info { "Step Name: ${stepExecutionContext["stepName"]}" }

        val stepName = chunkContext.stepContext.stepExecution.stepName

        if (!executionContext.containsKey("stepName")) {
            executionContext.putString("stepName", stepName)
        }

        return RepeatStatus.FINISHED
    }
}
