package com.imooc.myo2o.dao;

import com.imooc.myo2o.entity.Area;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Title: AreaDao
 * @Author 林广华
 * @Package com.imooc.myo2o.dao
 * @Date 2024/7/28 21:35
 * @description:
 */
@Repository
public interface AreaDao {
    /**
     * 列出区域列表
     * @return areaList
     */
    List<Area> queryArea();
}
