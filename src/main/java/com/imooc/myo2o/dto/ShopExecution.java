package com.imooc.myo2o.dto;

import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.enums.ShopStateEnum;

import java.util.List;

/**
 * @Title: ShopExecution
 * @Author 林广华
 * @Package com.imooc.myo2o.dto
 * @Date 2024/7/29 19:35
 * @description: shop返回模板
 */
public class ShopExecution {

//    结果状态
    private int state;
//    状态标识
    private String stateInfo;
//    店铺数量
    private int count;
//    操作的shop（增删改的时候用到）
    private Shop shop;
//    shop列表（查询店铺的时候用到)
    private List<Shop> shopList;

    public ShopExecution() {
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public List<Shop> getShopList() {
        return shopList;
    }

    public void setShopList(List<Shop> shopList) {
        this.shopList = shopList;
    }

    //  失败的构造器
    public ShopExecution(ShopStateEnum stateEnum) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
    }

//    成功的构造器
    public ShopExecution(ShopStateEnum stateEnum, Shop shop) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shop = shop;
    }

    //    成功的构造器 返回列表
    public ShopExecution(ShopStateEnum stateEnum, List<Shop> shopList) {
        this.state = stateEnum.getState();
        this.stateInfo = stateEnum.getStateInfo();
        this.shopList = shopList;
    }

    @Override
    public String toString() {
        return "ShopExecution{" +
                "state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", count=" + count +
                ", shop=" + shop +
                ", shopList=" + shopList +
                '}';
    }
}
