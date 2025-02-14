package com.imooc.myo2o.dao;

import com.imooc.myo2o.entity.ProductCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @Title: ProductCategory
 * @Author 林广华
 * @Package com.imooc.myo2o.dao
 * @Date 2024/8/2 15:02
 * @description: 商铺类别
 */
public interface ProductCategoryDao {

    /**
     * 通过shopId查询店铺商铺类别
     * @param shopId
     * @return
     */
    public List<ProductCategory> queryProductCategoryList(long shopId);

    /**
     * 批量新增商品类别
     * @param productList
     * @return 受影响行数
     */
    public int batchInsertProductCategory(List<ProductCategory>productList);

    /**
     * 删除商品类别
     */
    public int deleteShopProductCategory(@Param("productCategoryId")long productCategoryId,
                                         @Param("shopId")long shopId);

}
