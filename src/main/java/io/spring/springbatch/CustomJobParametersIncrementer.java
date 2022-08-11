package io.spring.springbatch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersIncrementer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import static java.time.format.DateTimeFormatter.ISO_DATE;

public class CustomJobParametersIncrementer implements JobParametersIncrementer {
    static final DateTimeFormatter FORMAT = ISO_DATE;

    @Override
    public JobParameters getNext(final JobParameters parameters) {
        final String id = FORMAT.format(LocalDateTime.now());
        return new JobParametersBuilder().addString("run.id", id).toJobParameters();
    }
}
