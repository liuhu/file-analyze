package com.acloudchina.m2m.analyze.repository;

import com.acloudchina.m2m.analyze.domain.SimInfoDomain;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by liuhu on 24/05/2017.
 */
public interface SimInfoRepository extends JpaRepository<SimInfoDomain, String> {
}
