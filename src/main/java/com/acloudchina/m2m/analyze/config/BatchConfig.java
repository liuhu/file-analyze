package com.acloudchina.m2m.analyze.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.BatchConfigurer;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;

/**
 * Created by liuhu on 23/05/2017.
 */
@Configuration
@Slf4j
@EnableBatchProcessing
@Import(BatchDataSouceConfig.class)
public class BatchConfig {

    @Autowired
    @Qualifier("mainDataSource")
    private DataSource mainDataSource;

    @Autowired
    private JobRepository jobRepository;

    @Bean
    public BatchConfigurer configurer(){
        return new DefaultBatchConfigurer(mainDataSource);
    }

    @Bean
    public JobRepository jobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(mainDataSource);
        factory.setTransactionManager(new DataSourceTransactionManager(mainDataSource));
        factory.afterPropertiesSet();
        return factory.getObject();
    }

    @Bean
    public JobLauncher jobLauncher() throws Exception {
        SimpleJobLauncher jobLauncher = new SimpleJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.setTaskExecutor(jobAsyncTaskExecutor());
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }

    @Bean
    public ThreadPoolTaskExecutor batchStepThreadPoolTaskExecutor() {
        ThreadPoolTaskExecutor poolTaskExecutor = new ThreadPoolTaskExecutor();
        poolTaskExecutor.setCorePoolSize(5);
        poolTaskExecutor.setMaxPoolSize(40);
        poolTaskExecutor.setThreadNamePrefix("Batch-Step-");
        poolTaskExecutor.afterPropertiesSet();
        return poolTaskExecutor;
    }

    @Bean
    public SimpleAsyncTaskExecutor jobAsyncTaskExecutor() {
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setThreadNamePrefix("Batch-Job-");
        return asyncTaskExecutor;
    }
}
