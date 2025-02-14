package com.imooc.myo2o.web.superadmin;

import com.imooc.myo2o.entity.Area;
import com.imooc.myo2o.service.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LoggerFactoryBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * @Title: AreaController
 * @Author 林广华
 * @Package com.imooc.myo2o.superadmin
 * @Date 2024/7/28 23:03
 * @description:
 */
@Controller
@RequestMapping("/superadmin")
public class AreaController {

    Logger logger = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    @RequestMapping(value = "/listarea",method = RequestMethod.GET)
//    将返回值转为JSON
    @ResponseBody
    public Map<String, Object> listArea() {
        logger.info("=========/superadminn/listarea is start============");
        long startTime = System.currentTimeMillis();
        Map<String,Object> modelMap = new HashMap<String, Object>();
        List<Area> list = new ArrayList<Area>();
        try{
            list = areaService.getAreaList();
            modelMap.put("rows", list);
            modelMap.put("total", list.size());
        } catch (Exception e) {
            e.printStackTrace();
            modelMap.put("success",false);
            modelMap.put("errMsg",e.toString());
        }
        logger.error("test error");
        long endTime = System.currentTimeMillis();
        logger.debug("用时：[{}ms]",endTime - startTime);
        logger.info("=========/superadminn/listarea is end============");
        return modelMap;
    }






}
