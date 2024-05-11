package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
class JobLauncherController(
    @Qualifier(value = "defaultJob") private val job: Job,
    private val defaultBatchConfiguration: DefaultBatchConfiguration,
) : NoCoLogging {
    @PostMapping("/launch")
    fun launch(@RequestBody member: Member): String {
        logger.info { "batch completed" }
        val jobParameters = JobParametersBuilder().addLong("id", member.id)
            .addLocalDate("date", LocalDate.now())
            .toJobParameters()
        val taskExecutorJobLauncher = this.defaultBatchConfiguration.jobLauncher() as TaskExecutorJobLauncher
        taskExecutorJobLauncher.setTaskExecutor(SimpleAsyncTaskExecutor())
        taskExecutorJobLauncher.run(job, jobParameters)
        logger.info { "batch completed1" }
        return "batch completed"
    }

    @GetMapping("/")
    fun index() {
        println("Hello, World!")
    }
}

data class Member(
    val id: Long,
)
