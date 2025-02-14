package com.imooc.myo2o.service;

import com.imooc.myo2o.entity.Area;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Title: AreaService
 * @Author 林广华
 * @Package com.imooc.myo2o.service.serviceImpl
 * @Date 2024/7/28 22:39
 * @description:
 */
@Service
public interface AreaService {

    List<Area> getAreaList();


}
