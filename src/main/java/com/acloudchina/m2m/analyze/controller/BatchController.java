package com.acloudchina.m2m.analyze.controller;

import com.acloudchina.m2m.analyze.service.LaunchBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liuhu on 24/05/2017.
 */
@RestController
public class BatchController {

    @Autowired
    private LaunchBatchService launchBatchService;

    @RequestMapping(value = "/trigger" ,method = RequestMethod.GET)
    public void trigger() {
        launchBatchService.launchJob();
    }
}
