package com.cmpp.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "tbl_app")
public class App {
    /**
     * 主键自增Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * 接入号
     */
    @Column(name = "app_id")
    private String appId;

    /**
     * 产品名称
     */
    @Column(name = "app_name")
    private String appName;

    /**
     * 用户Id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 所属领域
     */
    @Column(name = "field")
    private String field;

    /**
     * 协议类型
     */
    @Column(name = "protocol_type")
    private int protocolType;

    /**
     * 回调地址
     */
    @Column(name = "callback_url")
    private String callbackUrl;

    /**
     * 状态，0：启用 1：禁用
     */
    @Column(name = "app_status")
    private Integer appStatus;

    /**
     * 扩展码
     */
    @Column(name = "extend_code")
    private String extendCode;

    /**
     * 流速
     */
    @Column(name = "speed_limit")
    private Integer speedLimit;

    /**
     * 开始发送时间
     */
    @Column(name = "send_begin_time")
    private Date sendBeginTime;

    /**
     * 结束发送时间
     */
    @Column(name = "send_end_time")
    private Date sendEndTime;

    /**
     * 单条短信价格
     */
    @Column(name = "price")
    private BigDecimal price;

    /**
     * 支付类型 0 为预付款 1为后付款
     */
    @Column(name = "pay_type")
    private Integer payType;

    /**
     * 通道
     */

    @Column(name = "channel")
    private String channel;

    @Column(name = "max_connection")
    private int maxConnection;

    /**
     * 创建时间
     */
    @Column(name = "created_date", insertable = false, updatable = false)
    private Date createdDate;

    /**
     * 更新时间
     */
    @Column(name = "updated_date", insertable = false, updatable = false)
    private Date updatedDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field == null ? null : field.trim();
    }

    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl == null ? null : callbackUrl.trim();
    }

    public Integer getAppStatus() {
        return appStatus;
    }

    public void setAppStatus(Integer appStatus) {
        this.appStatus = appStatus;
    }

    public Integer getSpeedLimit() {
        return speedLimit;
    }

    public void setSpeedLimit(Integer speedLimit) {
        this.speedLimit = speedLimit;
    }

    public Date getSendBeginTime() {
        return sendBeginTime;
    }

    public void setSendBeginTime(Date sendBeginTime) {
        this.sendBeginTime = sendBeginTime;
    }

    public Date getSendEndTime() {
        return sendEndTime;
    }

    public void setSendEndTime(Date sendEndTime) {
        this.sendEndTime = sendEndTime;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
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

    public String getExtendCode() {
        return extendCode;
    }

    public void setExtendCode(String extendCode) {
        this.extendCode = extendCode;
    }

    public int getMaxConnection() {
        return maxConnection;
    }

    public void setMaxConnection(int maxConnection) {
        this.maxConnection = maxConnection;
    }
}