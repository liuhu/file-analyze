package com.acloudchina.m2m.analyze.service;

import com.acloudchina.m2m.analyze.constant.BatchConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by liuhu on 24/05/2017.
 */
@Service
@Slf4j
public class LaunchBatchService {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("fileAnalyzeJob")
    private Job fileAnalyzeJob;

    public void launchJob() {
        try {
            List<String> filePatchList = new ArrayList<>();
            readAllFilePath("/Users/liuhu/Downloads/Doc/A01", filePatchList);
            if (null != filePatchList && !filePatchList.isEmpty()) {
                for (String filePath : filePatchList) {
                    JobParametersBuilder parametersBuilder = new JobParametersBuilder();
                    parametersBuilder.addString("jobId", UUID.randomUUID().toString());
                    parametersBuilder.addString(BatchConstants.FILE_PATH, filePath);
                    jobLauncher.run(fileAnalyzeJob, parametersBuilder.toJobParameters());
                }
            }
        } catch (Exception e) {
            log.error("launch batch job ERROR", e);
        }
    }


    public void readAllFilePath(String filePath, List<String> filePathList) {
        File f = null;
        f = new File(filePath);
        File[] files = f.listFiles(); // 得到f文件夹下面的所有文件。
        List<File> list = new ArrayList<File>();
        for (File file : files) {
            if(file.isDirectory()) {
                //如何当前路劲是文件夹，则循环读取这个文件夹下的所有文件
                readAllFilePath(file.getAbsolutePath(), filePathList);
            } else {
                list.add(file);
            }
        }
        list.stream().forEach(x -> {
            filePathList.add(x.getAbsolutePath());
        });
    }
}
