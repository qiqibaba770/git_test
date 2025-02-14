package com.imooc.myo2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Title: ShopAdminController
 * @Author 林广华
 * @Package com.imooc.myo2o.web.shopadmin
 * @Date 2024/7/30 14:12
 * @description:
 */
@Controller
@RequestMapping(value = "/shopadmin",method = {RequestMethod.GET})
public class ShopAdminController {

    @RequestMapping("/shopoperation")
    public String shopOperation() {
        return "shop/shopoperation";
    }

    @RequestMapping("/shoplist")
    public String shopList() {
        return "shop/shoplist";
    }

    @RequestMapping("/shopmanagement")
    public String shopmanagement() {
        return "shop/shopmanagement";
    }

    /**
     * 商品类别管理页面
     * @return
     */
    @RequestMapping(value = "/productcategorymanagement",method = RequestMethod.GET)
    public String productcategorymanagement() {
        return "shop/productcategorymanagement";
    }

    // 商品添加/编辑页面
    @RequestMapping("/productoperation")
    public String productOperation() {
        return "shop/productoperation";
    }















}
