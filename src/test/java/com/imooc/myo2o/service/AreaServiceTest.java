package com.imooc.myo2o.service;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.entity.Area;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @Title: AreaService
 * @Author 林广华
 * @Package com.imooc.myo2o.service
 * @Date 2024/7/28 22:48
 * @description:
 */
public class AreaServiceTest extends BaseTest {
    @Autowired
    private AreaService areaService;

    @Test
    public void testGetAreaList() {
        List<Area> areaList = areaService.getAreaList();
        for (Area area : areaList) {
            System.out.println(area);
        }
    }

}
