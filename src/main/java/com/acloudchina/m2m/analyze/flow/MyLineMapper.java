package com.acloudchina.m2m.analyze.flow;

import org.springframework.batch.item.file.LineMapper;

/**
 * Created by liuhu on 24/05/2017.
 */
public class MyLineMapper implements LineMapper<String> {

    @Override
    public String mapLine(String line, int lineNumber) throws Exception {
        return line;
    }
}
