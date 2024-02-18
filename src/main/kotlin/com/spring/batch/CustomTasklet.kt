package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus

class CustomTasklet : Tasklet, NoCoLogging {
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        this.logger.info { "Custom Tasklet was executed" }
        return RepeatStatus.FINISHED
    }
}
