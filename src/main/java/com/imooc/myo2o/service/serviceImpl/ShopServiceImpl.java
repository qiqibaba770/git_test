package com.imooc.myo2o.service.serviceImpl;

import com.imooc.myo2o.dao.ShopDao;
import com.imooc.myo2o.dto.ImageHolder;
import com.imooc.myo2o.dto.ShopExecution;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.enums.ShopStateEnum;
import com.imooc.myo2o.service.ShopService;
import com.imooc.myo2o.util.ImageUtil;
import com.imooc.myo2o.util.PageUtil;
import com.imooc.myo2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.File;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @Title: ShopServiceImpl
 * @Author 林广华
 * @Package com.imooc.myo2o.service.serviceImpl
 * @Date 2024/7/29 20:00
 * @description:
 */
@Service
public class ShopServiceImpl  implements ShopService {
    @Autowired
    private ShopDao shopDao;

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {

        int rowIndex = PageUtil.calculateRowIndex(pageIndex, pageSize);
        List<Shop> shops = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();
        if (shops != null) {
            se.setShopList(shops);
            se.setCount(count);
        } else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    @Transactional
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws RuntimeException {
        if (shop == null || shop.getShopId() == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
//        判断是否需要修改图片信息
            try{
                if(thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())) {
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if(tempShop != null) {
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop,thumbnail);
                }
//        更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0) {
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }
            } catch (Exception e) {
                System.out.println("modifyShop出错："  + e.getMessage());
                throw new RuntimeException();
            }
        }
    }

    @Override
    public Shop getByShopId(Long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
        try{
//            状态为审核中
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
//            添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new RuntimeException("店铺创建失败");
            } else {
                if (thumbnail.getImage() != null) {
                    try{
                        // 存储图片并更新shop中图片的地址
                        addShopImg(shop,thumbnail);
                    } catch (Exception e) {
                        System.out.println("e = " + e.getMessage());
                        System.out.println("+++++++++++++++++++++++++++++++++++");
                        throw new RuntimeException("addShopImg error:" + e.getMessage());
                    }
//                    更新店铺的图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0) {
                        throw new RuntimeException("更新图片地址失败");
                    }
                }
            }
        } catch (Exception e){
            throw new RuntimeException("addShopError:"+e.getMessage());
        }
        return new ShopExecution(ShopStateEnum.CHECK);
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
//      获取shop图片目录的相对值路径
        String dest = null;
        try {
            dest = PathUtil.getShopImagePath(shop.getShopId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        shop.setShopImg(shopImgAddr);
    }
}
