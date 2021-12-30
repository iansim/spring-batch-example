package com.techprimers.springbatchexample1.batch;

import java.util.Date;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.techprimers.springbatchexample1.model.User;

@JobScope
@Component 
public class MyTask implements Tasklet{
    @Autowired
	JobBuilderFactory jobBuilderFactory;
	
    @Autowired
    StepBuilderFactory stepBuilderFactory;	
    
    @Autowired
    ItemProcessor<User, User> itemProcessor;
    @Autowired
    ItemWriter<User> itemWriter;
    @Autowired
    ItemReader<User> itemReader;
    @Autowired
    JobLauncher jobLauncher;



	@Override
	public RepeatStatus execute(StepContribution arg0, ChunkContext arg1) throws Exception {
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+new Date());
		JobParameters maps = arg0.getStepExecution().getJobParameters();
		System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@:"+maps.getString("time"));
//		
//        Step step = stepBuilderFactory.get("XETL-file-load1")
//                .<User, User>chunk(100)
//                .reader(itemReader)
//                .processor(itemProcessor)
//                .writer(itemWriter)
//                .build();
//
//
//        Job job = jobBuilderFactory.get("XETL-Load")
//                .incrementer(new RunIdIncrementer())
//                .start(step)
//                .build();
//        
//        JobExecution jobExecution = jobLauncher.run(job, maps);
//        
//
//        System.out.println("JobExecution: " + jobExecution.getStatus());

//        System.out.println("Batch is Running...");
//        while (jobExecution.isRunning()) {
//            System.out.println("...");
//        }

		return RepeatStatus.FINISHED;
	}

}
