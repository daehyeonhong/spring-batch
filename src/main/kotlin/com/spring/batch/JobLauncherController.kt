package com.spring.batch

import io.klogging.NoCoLogging
import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
class JobLauncherController(
    @Qualifier(value = "job") private val job: Job,
    private val jobLauncher: JobLauncher,
) : NoCoLogging {
    @PostMapping("/launch")
    fun launch(@RequestBody member: Member): String {
        logger.info { "batch completed" }
        val jobParameters = JobParametersBuilder().addLong("id", member.id)
            .addLocalDate("date", LocalDate.now())
            .toJobParameters()

        this.jobLauncher.run(job, jobParameters)
        return "batch completed"
    }
}

data class Member(
    val id: Long,
)
