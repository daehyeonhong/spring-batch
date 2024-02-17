package com.spring.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component

@Component
class JobRunner(
    private val jobLauncher: JobLauncher,
    private val job: Job,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val jobParameters = JobParametersBuilder()
            .addString("name", "user2")
            .toJobParameters()

        this.jobLauncher.run(job, jobParameters)
    }
}
