package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class ExecutionContextTasklet3 : Tasklet, NoCoLogging {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        logger.info { "Hello, World!" }

        val name = chunkContext.stepContext.stepExecution.jobExecution.executionContext["name"]

        if (name == null) {
            chunkContext.stepContext.stepExecution.jobExecution.executionContext.put("name", "John Doe")
        }

        return RepeatStatus.FINISHED
    }
}
