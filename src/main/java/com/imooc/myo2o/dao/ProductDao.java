package com.imooc.myo2o.dao;

import com.imooc.myo2o.entity.Product;
import com.imooc.myo2o.entity.ProductImg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Title: ProductDao
 * @Author 林广华
 * @Package com.imooc.myo2o.dao
 * @Date 2024/8/3 20:59
 * @description:
 */
public interface ProductDao {

    public Product getProductById(long productId);

    /**
     * 修改商品
     * @param product
     * @return
     */
    public int updateProduct(Product product);

    /**
     * 分页查询商品
     * @param productCodition
     * @param pageSize
     * @return
     */
    public List<Product> queryProductList(@Param("productCondition")Product productCodition,
                                          @Param("pageSize")int pageSize);

    /**
     * 添加商品
     * @param product
     * @return
     */
    public int insertProduct(Product product);

}
