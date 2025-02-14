package com.imooc.myo2o.service;

import com.imooc.myo2o.dto.ImageHolder;
import com.imooc.myo2o.dto.ShopExecution;
import com.imooc.myo2o.entity.Shop;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.InputStream;

/**
 * @Title: ShopService
 * @Author 林广华
 * @Package com.imooc.myo2o.service
 * @Date 2024/7/29 19:59
 * @description:
 */
@Service
public interface ShopService {

    /**
     * 分页获取商铺列表
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);

    /**
     * 更新店铺信息，包括文件的保存
     * @param shop 商店信息
     * @param thumbnail 商铺缩略图的输入流和文件名
     * @return
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws RuntimeException;

    /**
     * 根据商铺id获取商铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(Long shopId);

    /**
     * 添加店铺
     * @param shop 商店信息
     * @param thumbnail 商铺缩略图的输入流和文件名
     * @return 返回执行结果
     * @throws Exception
     */
    ShopExecution addShop(Shop shop, ImageHolder thumbnail) throws Exception;

}
