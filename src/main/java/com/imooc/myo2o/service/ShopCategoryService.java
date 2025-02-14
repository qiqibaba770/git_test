package com.imooc.myo2o.service;

import com.imooc.myo2o.entity.ShopCategory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: ShopCategoryService
 * @Author 林广华
 * @Package com.imooc.myo2o.service
 * @Date 2024/7/30 20:47
 * @description: 关于店铺类别的业务层
 */
@Service
public interface ShopCategoryService {

    List<ShopCategory> getShopCategoryList(ShopCategory shopCategoryCondition);



}
