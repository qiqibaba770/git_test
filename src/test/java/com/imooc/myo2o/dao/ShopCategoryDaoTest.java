package com.imooc.myo2o.dao;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.ShopCategory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Title: ShopCategoryDaoTest
 * @Author 林广华
 * @Package com.imooc.myo2o.dao
 * @Date 2024/7/30 20:39
 * @description:
 */
public class ShopCategoryDaoTest extends BaseTest {

    @Autowired
    private ShopCategoryDao shopCategoryDao;

    @Test
    public void testQueryShopCategory() {
        List<ShopCategory> list = shopCategoryDao.queryShopCategory(new ShopCategory());
        System.out.println(list);
    }





}
