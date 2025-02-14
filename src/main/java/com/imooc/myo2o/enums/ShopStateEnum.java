package com.imooc.myo2o.enums;

/**
 * @Title: ShopStateEnum
 * @Author 林广华
 * @Package com.imooc.myo2o.enums
 * @Date 2024/7/29 19:41
 * @description:
 */
public enum ShopStateEnum {

    CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),
        PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误"),
    NULL_SHOPID(-1002,"shopId为空"),NULL_SHOP(-1003,"shop信息为空");
    private int state;
    private String stateInfo;

    private ShopStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }
    /**
     * 依据传入的state返回相应的enum值
     */
    public static ShopStateEnum stateOf(int state) {
        for (ShopStateEnum s : values()) {
            if(s.getState() == state) {
                return s;
            }
        }
        return null;
    }

    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
}
