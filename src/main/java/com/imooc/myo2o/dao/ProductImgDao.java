package com.imooc.myo2o.dao;

import com.imooc.myo2o.entity.ProductImg;

import java.util.List;

/**
 * @Title: ProductImgDao
 * @Author 林广华
 * @Package com.imooc.myo2o.dao
 * @Date 2024/8/3 21:03
 * @description:
 */
public interface ProductImgDao {

    /**
     * 返回productId对应的所有图片
     * @param productId
     * @return
     */
    public List<ProductImg> queryProductImgList(long productId);

    /**
     * 批量添加图片
     * @param productImgList
     * @return
     */
    public int batchInsertProductImg(List<ProductImg> productImgList);

    /**
     * 根据商品Id删除所有详情图
     * @param productId
     * @return
     */
    public int deleteProductImg(long productId);

}
