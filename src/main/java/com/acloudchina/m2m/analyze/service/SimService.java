package com.acloudchina.m2m.analyze.service;

import com.acloudchina.m2m.analyze.domain.SimBaseInfoDomain;
import com.acloudchina.m2m.analyze.domain.SimInfoDomain;
import com.acloudchina.m2m.analyze.repository.SimBaseInfoRepository;
import com.acloudchina.m2m.analyze.repository.SimInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by liuhu on 24/05/2017.
 */
@Service
@Transactional
public class SimService {

    @Autowired
    private SimBaseInfoRepository simBaseInfoRepository;

    @Autowired
    private SimInfoRepository simInfoRepository;

    @Transactional
    public void saveBaseInfo(SimBaseInfoDomain simBaseInfoDomain) {
        simBaseInfoRepository.saveAndFlush(simBaseInfoDomain);
    }

    @Transactional
    public void saveSimInfo(List<SimInfoDomain> simInfoDomains) {
        simInfoRepository.save(simInfoDomains);
    }
}
