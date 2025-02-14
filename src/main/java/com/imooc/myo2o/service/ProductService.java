package com.imooc.myo2o.service;

import com.imooc.myo2o.dto.ImageHolder;
import com.imooc.myo2o.entity.Product;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @Title: ProductService
 * @Author 林广华
 * @Package com.imooc.myo2o.service
 * @Date 2024/8/3 21:20
 * @description:
 */
public interface ProductService {

    /**
     * 根据productId获取要查询的商品
     * @param id
     * @return
     */
    public Product getProductById(long id);

    /**
     * 更新商品
     * @param product
     * @return
     */
    public Map<String,Object> updateProduct(Product product);

    /**
     * 添加图片
     * @param product
     * @param thumbnail 缩略图的
     * @param productImgList    详情图的
     * @return
     * @throws RuntimeException
     */
    public Map<String,Object> addProduct(Product product,
                                         ImageHolder thumbnail,
                                         List<ImageHolder> productImgList) throws RuntimeException;

}
