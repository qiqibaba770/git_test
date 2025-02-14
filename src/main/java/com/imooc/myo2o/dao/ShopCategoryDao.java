package com.imooc.myo2o.dao;

import com.imooc.myo2o.entity.ShopCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Title: ShopCategoryDao
 * @Author 林广华
 * @Package com.imooc.myo2o.dao
 * @Date 2024/7/30 20:22
 * @description: 获取店铺类别信息
 */
public interface ShopCategoryDao {
    /**
     * 获取所有商品类别
     * @param shopCategoryCondition
     * @return
     */
    List<ShopCategory> queryShopCategory(@Param("shopCategoryCondition")
                                         ShopCategory shopCategoryCondition);




}
