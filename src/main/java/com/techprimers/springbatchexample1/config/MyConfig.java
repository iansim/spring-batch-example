package com.techprimers.springbatchexample1.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import com.techprimers.springbatchexample1.batch.MyJobExecutionDecider;
import com.techprimers.springbatchexample1.batch.MyStep;
import com.techprimers.springbatchexample1.batch.MyTask;
import com.techprimers.springbatchexample1.model.MyTable;
import com.techprimers.springbatchexample1.model.User;

@Configuration
public class MyConfig {
	
	@Autowired
	MyTask myTask;
    @Autowired
    MyStep myStep;
    
    @Autowired
    StepBuilderFactory stepBuilderFactory;	
    @Autowired
    ItemProcessor<User, User> itemProcessor;
    @Autowired
    ItemWriter<User> itemWriter;
    @Autowired
    ItemReader<User> itemReader;
	
    @Autowired
    MyJobExecutionDecider myJobExecutionDecider;
    
	@Bean(name = "coreJob")
	public Job coreJob(JobBuilderFactory jobBuilderFactory,StepBuilderFactory stepBuilderFactory,Step myStep2) {
		Step step = stepBuilderFactory.get("Core Task").tasklet(myTask).build();
        return jobBuilderFactory.get("ETL-LoadZ")
        .incrementer(new RunIdIncrementer())
        .start(step).next(myJobExecutionDecider).on("1").to(myStep2)
        .from(myJobExecutionDecider).on("2").to(myStep)
        .end()
        .build();
	}

	
	@Bean 
	public Step myStep2() {
		
		
		return stepBuilderFactory.get("ETL-file-load1")
                .<User, User>chunk(100)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
	}
//	   @Bean(name = "joba")
//	    public Job joba(JobBuilderFactory jobBuilderFactory,
//	                   StepBuilderFactory stepBuilderFactory,
//	                   ItemReader<MyTable> itemReader,
//	                   ItemProcessor<MyTable, MyTable> itemProcessor,
//	                   ItemWriter<MyTable> itemWriter
//	    ) {
//
//	        Step step = stepBuilderFactory.get("ETL-file-load")
//	                .<MyTable, MyTable>chunk(100)
//	                .reader(itemReader)
//	                .processor(itemProcessor)
//	                .writer(itemWriter)
//	                .build();
//
//
//	        return jobBuilderFactory.get("ETL-Load")
//	                .incrementer(new RunIdIncrementer())
//	                .start(step)
//	                .build();
//	    }
//
//	    @Bean
//	    public FlatFileItemReader<MyTable> itemReadera() {
//
//	        FlatFileItemReader<MyTable> flatFileItemReader = new FlatFileItemReader<>();
//	        flatFileItemReader.setResource(new FileSystemResource("src/main/resources/users.csv"));
//	        flatFileItemReader.setName("CSV-Reader");
//	        flatFileItemReader.setLinesToSkip(1);
//	        flatFileItemReader.setLineMapper(lineMappera());
//	        return flatFileItemReader;
//	    }
//
//	    @Bean
//	    public LineMapper<MyTable> lineMappera() {
//
//	        DefaultLineMapper<MyTable> defaultLineMapper = new DefaultLineMapper<>();
//	        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
//
//	        lineTokenizer.setDelimiter(",");
//	        lineTokenizer.setStrict(false);
//	        lineTokenizer.setNames("id", "name", "dept", "salary");
//
//	        BeanWrapperFieldSetMapper<MyTable> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
//	        fieldSetMapper.setTargetType(MyTable.class);
//
//	        defaultLineMapper.setLineTokenizer(lineTokenizer);
//	        defaultLineMapper.setFieldSetMapper(fieldSetMapper);
//
//	        return defaultLineMapper;
//	    }
	
}
