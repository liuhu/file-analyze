package com.acloudchina.m2m.analyze.flow;

import com.acloudchina.m2m.analyze.domain.SimBaseInfoDomain;
import com.acloudchina.m2m.analyze.domain.SimInfoDomain;
import com.acloudchina.m2m.analyze.repository.SimBaseInfoRepository;
import com.acloudchina.m2m.analyze.service.SimService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by liuhu on 24/05/2017.
 */
@StepScope
@Component("fileAnalyzeProcessor")
@Slf4j
public class FileAnalyzeProcessor implements ItemProcessor<String, SimInfoDomain> {

    @Autowired
    private SimService simService;

    @Override
    public SimInfoDomain process(String item) throws Exception {

        if (StringUtils.isBlank(item)) {
            return null;
        }
        if (!item.contains("c.acloudchina.m2m.service.SpiderService")) {
            return null;
        }
        if (item.length() < 138) {
            return null;
        }
        String newItem = item.substring(138, item.length()).trim();
        log.info(newItem);

        SimBaseInfoDomain baseInfoDomain = new SimBaseInfoDomain();
        baseInfoDomain.setJson(newItem);
        simService.saveBaseInfo(baseInfoDomain);

        try {
            JSONObject object = JSON.parseObject(newItem);
            String error = object.getString("error");
            if ("1".equals(error)) {
                log.info("Invalid item, item = {}", item);
                return null;
            }

            SimInfoDomain domain = new SimInfoDomain();

            JSONObject result = object.getJSONObject("result");
            domain.setSimId(result.getString("simId"));
            domain.setSimNo(result.getString("simNo"));
            domain.setIccid(result.getString("iccid"));
            domain.setImei(result.getString("imei"));
            domain.setIccidFull(result.getString("iccidFull"));
            domain.setSimStateSrc(result.getString("simStateSrc"));
            domain.setPackageName(result.getString("packageName"));
            domain.setPackageInfo(result.getString("packageInfo"));
            domain.setDeviceType(result.getString("deviceType"));
            domain.setApiAccount(result.getString("apiAccount"));

            domain.setUsageToPeriod(result.getBigDecimal("usageToPeriod"));
            domain.setAmountUsageData(result.getBigDecimal("usageToPeriod"));
            domain.setMonthUsageData(result.getBigDecimal("monthUsageData"));
            domain.setTotalMonthUsage(result.getBigDecimal("totalMonthUsage"));

            JSONObject memberInfo = result.getJSONObject("memberInfo");
            if (null != memberInfo) {
                domain.setMUserName(memberInfo.getString("userName"));
                domain.setMIdCard(memberInfo.getString("idCard"));
                domain.setMTrueName(memberInfo.getString("trueName"));
            }
            JSONObject realNameInfo = result.getJSONObject("realNameInfo");
            if (null != realNameInfo) {
                domain.setRUserName(realNameInfo.getString("userName"));
                domain.setRIdCard(realNameInfo.getString("idCard"));
                domain.setRTrueName(realNameInfo.getString("trueName"));
            }
            domain.setJson(item);
            return domain;
        } catch (Exception e) {
            log.error("format to json ERROR, item = {}", item, e);
            return null;
        }
    }
}
