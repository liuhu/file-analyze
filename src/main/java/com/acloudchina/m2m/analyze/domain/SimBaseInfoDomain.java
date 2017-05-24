package com.acloudchina.m2m.analyze.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by liuhu on 24/05/2017.
 */
@Data
@Entity
@Table(name = "AC_SIM_BASE")
public class SimBaseInfoDomain implements Persistable<String> {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

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
