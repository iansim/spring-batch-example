package com.techprimers.springbatchexample1.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.stereotype.Component;


@Component 
public class MyJobExecutionDecider implements JobExecutionDecider{

	@Override
	public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
		JobParameters maps = jobExecution.getJobParameters();
		System.out.println("MyJobExecutionDecider @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+maps.getString("id"));
		return new FlowExecutionStatus(maps.getString("id"));
	}

}
