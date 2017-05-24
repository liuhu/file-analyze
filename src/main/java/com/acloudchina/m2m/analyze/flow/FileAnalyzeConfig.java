package com.acloudchina.m2m.analyze.flow;

import com.acloudchina.m2m.analyze.domain.SimInfoDomain;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;


/**
 * Created by liuhu on 24/05/2017.
 */
@Configuration
public class FileAnalyzeConfig {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean(name = "itemReader")
    @StepScope
    public FlatFileItemReader<String> itemReader(@Value("#{jobParameters[filePath]}") String filePath) {
        FlatFileItemReader<String> itemReader = new FlatFileItemReader<>();
        itemReader.setResource(new FileSystemResource(filePath));
        itemReader.setLineMapper(new MyLineMapper());
        return itemReader;
    }

    @Bean
    public Job fileAnalyzeJob(@Qualifier("fileAnalyzeStep") Step fileAnalyzeStep) {
        return jobBuilderFactory.get("fileAnalyzeJob")
                .incrementer(new RunIdIncrementer())
                .flow(fileAnalyzeStep)
                .end()
                .build();
    }

    @Bean
    public Step fileAnalyzeStep(@Qualifier("itemReader")ItemReader<String> reader,
                                @Qualifier("fileAnalyzeProcessor") ItemProcessor<String, SimInfoDomain> processor,
                                @Qualifier("fileAnalyzeWriter")ItemWriter<SimInfoDomain> writer) {
        return stepBuilderFactory.get("fileAnalyzeStep")
                .<String, SimInfoDomain> chunk(20)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
