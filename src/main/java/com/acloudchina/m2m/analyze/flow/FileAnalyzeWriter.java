package com.acloudchina.m2m.analyze.flow;

import com.acloudchina.m2m.analyze.domain.SimInfoDomain;
import com.acloudchina.m2m.analyze.service.SimService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by liuhu on 24/05/2017.
 */
@Slf4j
@StepScope
@Component
@Transactional
public class FileAnalyzeWriter implements ItemWriter<SimInfoDomain> {

    @Autowired
    private SimService simService;

    @Override
    public void write(List<? extends SimInfoDomain> items) throws Exception {
        List<SimInfoDomain> simInfoDomains = items.stream().collect(Collectors.toList());
        simService.saveSimInfo(simInfoDomains);
    }
}
