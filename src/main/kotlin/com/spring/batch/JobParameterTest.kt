package com.spring.batch

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.time.Instant
import java.util.*

@Component
class JobParameterTest(
    private val jobLauncher: JobLauncher,
    private val job: Job,
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        val parameters = JobParametersBuilder()
            .addString("name", "user1")
            .addLong("seq", 2L)
            .addDate("date", Date.from(Instant.now()))
            .addDouble("height", 184.5)
            .toJobParameters()

        this.jobLauncher.run(this.job, parameters)
    }
}
