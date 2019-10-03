package com.cmpp.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tbl_pro_secret")
public class ProSecret {
    /**
     * id
     */
    private String id;

    /**
     * app_id
     */
    private String appId;

    /**
     * app_secret
     */
    private String appSecret;

    /**
     * 创建时间
     */
    private String createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

    /**
     * order_id
     *
     * @return id order_id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public String getId() {
        return id;
    }

    /**
     * order_id
     *
     * @param id order_id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * app_id
     *
     * @return app_id app_id
     */
    @Column(name = "app_id")
    public String getAppId() {
        return appId;
    }

    /**
     * app_id
     *
     * @param appId app_id
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    @Column(name = "app_secret")
    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    @Column(name = "created_date", insertable = false, updatable = false)
    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "updated_date", insertable = false, updatable = false)
    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }
}