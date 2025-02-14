package com.imooc.myo2o.dao;

import com.imooc.myo2o.entity.Shop;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: ShopDao
 * @Author 林广华
 * @Package com.imooc.myo2o.dao
 * @Date 2024/7/29 14:39
 * @description:
 */
@Repository
public interface ShopDao {

    /**
     * 分页查询店铺 可以根据店铺名（模糊查询），状态，类别，区域Id，owner
     * @param shopCondition
     * @param rowIndex 第几行开始取数据
     * @param pageSize 返回的行数
     * @return
     */
    public List<Shop> queryShopList(@Param("shopCondition")Shop shopCondition,
                                    @Param("rowIndex")int rowIndex,
                                    @Param("pageSize")int pageSize);

    /**
     *  返回queryShopList总数
     */
    public int queryShopCount(@Param("shopCondition")Shop shopCondition);

    /**
     * 根据店铺id返回店铺信息
     * @param shopId
     * @return
     */
    public Shop queryByShopId(long shopId);

    /**
     * 注册店铺
     * @param shop
     * @return 1表示成功
     */
    public int insertShop(Shop shop);

    /**
     * 更新店铺信息
     * @param shop
     * @return
     */
    public int updateShop(Shop shop);









}
