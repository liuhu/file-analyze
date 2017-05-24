package com.acloudchina.m2m.analyze.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by liuhu on 24/05/2017.
 */
@Data
@Entity
@Table(name = "AC_SIM")
public class SimInfoDomain implements Persistable<String> {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    private String simId;

    private String simNo;

    private String iccid;

    private String imei;

    private String iccidFull;

    private String simStateSrc;

    private String packageName;

    private String packageInfo;

    private String deviceType;

    private String apiAccount;

    @Column(precision = 15, scale = 3)
    private BigDecimal usageToPeriod;

    @Column(precision = 15, scale = 3)
    private BigDecimal amountUsageData;

    @Column(precision = 15, scale = 3)
    private BigDecimal monthUsageData;

    @Column(precision = 15, scale = 3)
    private BigDecimal totalMonthUsage;


    private String mUserName;

    private String mIdCard;

    private String mTrueName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date mAuthTime;



    private String rUserName;

    private String rIdCard;

    private String rTrueName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date rAuthTime;

    @Column(length = 6000)
    private String json;


    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_time", updatable = false)
    @CreationTimestamp
    private Date createdTime;

    @JsonIgnore
    @Override
    public boolean isNew() {
        return id == null || "".equals(id);
    }
}
