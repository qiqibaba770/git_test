package com.imooc.myo2o.service;

import com.imooc.myo2o.dto.Result;
import com.imooc.myo2o.entity.Product;
import com.imooc.myo2o.entity.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Title: ProductCategoryService
 * @Author 林广华
 * @Package com.imooc.myo2o.service
 * @Date 2024/8/2 15:32
 * @description: 商品类别
 */
@Service
public interface ProductCategoryService {

    /**
     * 获取店铺对应的商品类别
     * @param shopId 店铺Id
     * @return
     */
    public List<ProductCategory> getProductCategoryList(long shopId);

    /**
     * 批量添加商品类型
     * @param productCategoryList 商品类型列表
     * @return
     */
    public Map<String,Object> batchAddProductCategory(List<ProductCategory> productCategoryList);

    /**
     * 将此类别下的商品id的类别id置为空在删除改商品类别
     * @param productCategoryId
     * @param shopId
     * @return
     */
    public Map<String,Object> deleteProductCategory(long productCategoryId,long shopId);

}
