package com.acloudchina.m2m.analyze.flow;

import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


/**
 * Created by liuhu on 24/05/2017.
 */
@StepScope
@Component("fileAnalyzeProcessor")
public class FileAnalyzeProcessor implements ItemProcessor<String, String> {
    @Override
    public String process(String item) throws Exception {
        System.out.println(item);
        return null;
    }
}
