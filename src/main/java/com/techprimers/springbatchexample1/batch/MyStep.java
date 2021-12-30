package com.techprimers.springbatchexample1.batch;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.stereotype.Component;

@Component 
public class MyStep implements Step {

	@Override
	public void execute(StepExecution stepExecution) throws JobInterruptedException {
		// TODO Auto-generated method stub
		JobParameters maps = stepExecution.getJobParameters();
		System.out.println("1@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+maps.getString("time"));
//		stepExecution.setStatus(new BatchStatus("HAHAH"));
		stepExecution.setExitStatus(ExitStatus.COMPLETED);
		 
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "MyStep";
	}

	@Override
	public int getStartLimit() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public boolean isAllowStartIfComplete() {
		// TODO Auto-generated method stub
		return true;
	}

}
