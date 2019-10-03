package com.cmpp.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 作者： chengli
 * 日期： 2019/10/3 10:26
 */
@Entity
@Table(name = "tbl_consumption")
public class Consumption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "app_id")
    private String appId;
    @Column(name = "total_money")
    private BigDecimal totalMoney;
    @Column(name = "used_money")
    private BigDecimal usedMoney;
    @Column(name = "total_num")
    private long totalNum;
    @Column(name = "used_num")
    private long usedNum;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updated_date")
    private Date updatedDate;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getUsedMoney() {
        return usedMoney;
    }

    public void setUsedMoney(BigDecimal usedMoney) {
        this.usedMoney = usedMoney;
    }

    public long getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(long totalNum) {
        this.totalNum = totalNum;
    }

    public long getUsedNum() {
        return usedNum;
    }

    public void setUsedNum(long usedNum) {
        this.usedNum = usedNum;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}
