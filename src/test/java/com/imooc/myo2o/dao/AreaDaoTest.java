package com.imooc.myo2o.dao;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @Title: AreaDao
 * @Author 林广华
 * @Package com.imooc.myo2o.dao
 * @Date 2024/7/28 21:43
 * @description: 测试dao层
 */
public class AreaDaoTest extends BaseTest {
    @Autowired
    private AreaDao areaDao;

    @Test
    public void testQueryArea(){
        List<Area> arealist = areaDao.queryArea();
        assertEquals(1, arealist.size());
        for(Area area:arealist){
            System.out.println(area);
        }
    }
}
