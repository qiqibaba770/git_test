package com.imooc.myo2o.entity;

import java.util.Date;

/**
 * @Title: ProductImg
 * @Author 林广华
 * @Package back.model
 * @Date 2024/7/28 20:18
 * @description: 详情图片
 */
public class ProductImg {
    private Long productImgId;
//    图片地址
    private String imgAddr;
    private String imgDesc;
    private Integer priority;
    private Date createTime;
    private Long productId;

    public ProductImg(Long productImgId, String imgAddr, String imgDesc, Integer priority, Date createTime, Long productId) {
        this.productImgId = productImgId;
        this.imgAddr = imgAddr;
        this.imgDesc = imgDesc;
        this.priority = priority;
        this.createTime = createTime;
        this.productId = productId;
    }

    public ProductImg() {
    }

    @Override
    public String toString() {
        return "ProductImg{" +
                "productImgId=" + productImgId +
                ", imgAddr='" + imgAddr + '\'' +
                ", imgDesc='" + imgDesc + '\'' +
                ", priority=" + priority +
                ", createTime=" + createTime +
                ", productId=" + productId +
                '}';
    }

    public Long getProductImgId() {
        return productImgId;
    }

    public void setProductImgId(Long productImgId) {
        this.productImgId = productImgId;
    }

    public String getImgAddr() {
        return imgAddr;
    }

    public void setImgAddr(String imgAddr) {
        this.imgAddr = imgAddr;
    }

    public String getImgDesc() {
        return imgDesc;
    }

    public void setImgDesc(String imgDesc) {
        this.imgDesc = imgDesc;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
