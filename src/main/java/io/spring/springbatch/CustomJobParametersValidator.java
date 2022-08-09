package io.spring.springbatch;

import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.JobParametersValidator;

public class CustomJobParametersValidator implements JobParametersValidator {
    @Override
    public void validate(final JobParameters parameters) throws JobParametersInvalidException {
        assert parameters != null;
        if (parameters.getString("name") == null)
            throw new JobParametersInvalidException("name parameters is not found");
    }
}
