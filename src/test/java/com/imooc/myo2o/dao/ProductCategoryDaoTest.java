package com.imooc.myo2o.dao;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.ProductCategory;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Title: ProductCategoryDaoTest
 * @Author 林广华
 * @Package com.imooc.myo2o.dao
 * @Date 2024/8/2 15:15
 * @description:
 */
// 控制方法顺序 JVM:按照定义顺序,NAME_ASCENDING:按照方法名字顺序
//@FixMethodOrder(MethodSorters.JVM)
public class ProductCategoryDaoTest extends BaseTest {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    @Test
    public void testbatchInsertProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setProductCategoryName("商品类别4");
        productCategory.setPriority(2);
        productCategory.setCreateTime(new Date());
        productCategory.setShopId(19L);
        ProductCategory productCategory2 = new ProductCategory();
        productCategory2.setProductCategoryName("商品类别5");
        productCategory2.setPriority(5);
        productCategory2.setCreateTime(new Date());
        productCategory2.setShopId(19L);
        List<ProductCategory> productCategoryList = new ArrayList<ProductCategory>();
        productCategoryList.add(productCategory);
        productCategoryList.add(productCategory2);
        int count = productCategoryDao.batchInsertProductCategory(productCategoryList);
        System.out.println(count);
    }

    @Test
    @Ignore
    public void testQueryProductCategoryList() {
        long shopId = 19L;
        List<ProductCategory> productCategories = productCategoryDao.queryProductCategoryList(shopId);
        System.out.println("productCategories = " + productCategories.toString());
    }


}
