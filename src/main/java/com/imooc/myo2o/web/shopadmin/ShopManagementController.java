package com.imooc.myo2o.web.shopadmin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.myo2o.dto.ImageHolder;
import com.imooc.myo2o.dto.ShopExecution;
import com.imooc.myo2o.entity.Area;
import com.imooc.myo2o.entity.PersonInfo;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.entity.ShopCategory;
import com.imooc.myo2o.enums.ShopStateEnum;
import com.imooc.myo2o.service.AreaService;
import com.imooc.myo2o.service.ShopCategoryService;
import com.imooc.myo2o.service.ShopService;
import com.imooc.myo2o.util.CodeUtil;
import com.imooc.myo2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ShopManagementController
 * @Author 林广华
 * @Package com.imooc.myo2o.web.shopadmin
 * @Date 2024/7/29 23:38
 * @description:
 */
@Controller
@RequestMapping("/shopadmin")
public class ShopManagementController {
    @Autowired
    private ShopService shopService;
    @Autowired
    private ShopCategoryService shopCategoryService;
    @Autowired
    private AreaService areaService;

    Map<String,Object> modelMap = new HashMap<String, Object>();

    /**
     * 权限验证，验证是否有权限操作店铺
     * @param request
     * @return
     */
    @RequestMapping(value = "/getshopmanagementinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopManagementInfo(HttpServletRequest request) {
        Long shopId = HttpServletRequestUtil.getLong(request, "shopId");
        if (shopId <= 0) {
            Object currentShopObj = request.getSession().getAttribute("currentShop");
            if (currentShopObj == null) {
                modelMap.put("redirect",true);
                modelMap.put("url","/shopadmin/shoplist");
            } else {
                Shop currentShop = (Shop) currentShopObj;
                modelMap.put("redirect",false);
                modelMap.put("shopId",currentShop.getShopId());
            }
        } else {
            Shop currentShop = new Shop();
            currentShop.setShopId(shopId);
            request.getSession().setAttribute("currentShop", currentShop);
            modelMap.put("redirect",false);
        }
        return modelMap;
    }


    /**
     * 获取商铺列表
     * @param request
     * @return
     */
    @GetMapping("/getshoplist")
    @ResponseBody
    public Map<String, Object> getShopList(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        PersonInfo personInfo = new PersonInfo();
        personInfo.setUserId(1L);
        request.getSession().setAttribute("user",personInfo);
        personInfo = (PersonInfo) request.getSession().getAttribute("user");
        try {
            Shop shopCondition = new Shop();
            shopCondition.setOwner(personInfo);
            ShopExecution se = shopService.getShopList(shopCondition, 0, 100);
//            将user_id对应的username赋给personInfo
            personInfo.setName(se.getShopList().get(0).getOwner().getName());
            modelMap.put("success", true);
            modelMap.put("user",personInfo);
            modelMap.put("shopList",se.getShopList());
        } catch (Exception e) {
            System.out.println("com.imooc.myo2o.web.shopadmin." +
                    "ShopManagementController.getShopList出错了：" + e.getMessage());
            modelMap.put("success", false);
            modelMap.put("errorMsg", e.getMessage());
        }
        return modelMap;
    }

    /**
     * 修改商铺信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> modifyShop(HttpServletRequest request) throws IOException {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if(!CodeUtil.checkifyVerifyCode(request)) {
            modelMap.put("success",false);
            modelMap.put("errorMsg","验证码错误");
        }
//        接受并转化相应的参数（包括店铺信息以及图片信息）
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = objectMapper.readValue(shopStr,Shop.class);
            System.out.println("shop"+shop.toString());
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errorMsg",e.getMessage());
            System.out.println("1" + e.getMessage());
            return modelMap;
        }
        //        用于存储上传的文件
        CommonsMultipartFile shopImg = null;
        // 用于解析multipart请求
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        // 检查请求是否为multipart类型
        if (resolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multiRequest.getFile("shopImg");
        }
        ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),shopImg.getInputStream());
//        修改店铺信息
        if (shop != null && shop.getShopId() != null) {
            ShopExecution shopExecution = null;
            try {
                if (shopImg != null) {
                    shopExecution = shopService.modifyShop(shop,imageHolder);
                } else {
                    shopExecution = shopService.modifyShop(shop,imageHolder);
                }
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errorMsg:",e.getMessage());
                System.out.println("2" + e.getMessage());
                return modelMap;
            }
            if (shopExecution.getState() == ShopStateEnum.SUCCESS.getState()) {
                modelMap.put("success",true);
            }else {
                modelMap.put("success",false);
                modelMap.put("errorMsg",shopExecution.getStateInfo());
            }
            return modelMap;
        } else {
            modelMap.put("success",false);
            modelMap.put("errorMsg","请输入店铺Id");
            return modelMap;
        }
    }

    /**
     * 根据商铺Id获取商铺信息，并将区域列表返回（用于修改时显示）
     * @param request
     * @return
     */
    @RequestMapping(value = "getshopbyid",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopById(HttpServletRequest request) {
        Map<String,Object> modelMap = new HashMap<String,Object>();
        Long shopId = HttpServletRequestUtil.getLong(request,"shopId");
        if(shopId > -1) {
            try {
                Shop shop = shopService.getByShopId(shopId);
                if (shop != null) {
                    List<Area> areaList = areaService.getAreaList();
                    modelMap.put("shop",shop);
                    modelMap.put("areaList",areaList);
                    modelMap.put("success",true);
                }else {
                    modelMap.put("error","根据id未查找到商铺信息");
                    modelMap.put("success",false);
                }
            } catch (Exception e) {
                System.out.println("com.imooc.myo2o.web.shopadmin." +
                        "ShopManagementController.getShopById出错:" + e.getMessage());
                modelMap.put("success",false);
                modelMap.put("errorMsg",e.getMessage());
            }
        }else {
            modelMap.put("success",false);
            modelMap.put("errorMsg","shopId为空");
        }

    return modelMap;

    }

    /**
     * 获取店铺类型列表和区域列表
     * @return
     */
    @RequestMapping(value = "/getshopinitinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getShopInitInfo() {
        Map<String,Object> modelMap = new HashMap<String,Object>();
        List<ShopCategory> shopCategoriesList = new ArrayList<ShopCategory>();
        List<Area> areaList = new ArrayList<Area>();
        try {
            shopCategoriesList = shopCategoryService.getShopCategoryList(new ShopCategory());
            areaList = areaService.getAreaList();
            modelMap.put("shopCategoryList", shopCategoriesList);
            modelMap.put("areaList", areaList);
            modelMap.put("success", true);
        }catch (Exception e){
            modelMap.put("success",false);
            modelMap.put("errorMsg",e.getMessage());
        }
        System.out.println(modelMap.toString());
        return modelMap;
    }


    /**
     * 店铺注册
     * @param request
     * @return
     */
    @RequestMapping(value = "/registershop",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> registerShop(HttpServletRequest request) throws IOException {
        Map<String,Object> modelMap = new HashMap<String, Object>();
        if(!CodeUtil.checkifyVerifyCode(request)) {
            modelMap.put("success",false);
            modelMap.put("errorMsg","验证码错误");
        }
//        接受并转化相应的参数（包括店铺信息以及图片信息）
        String shopStr = HttpServletRequestUtil.getString(request, "shopStr");
        ObjectMapper objectMapper = new ObjectMapper();
        Shop shop = null;
        try {
            shop = objectMapper.readValue(shopStr,Shop.class);
        } catch (Exception e) {
            modelMap.put("success",false);
            modelMap.put("errorMsg",e.getMessage());
            System.out.println("1" + e.getMessage());
            return modelMap;
        }
        //        用于存储上传的文件
        CommonsMultipartFile shopImg = null;
        // 用于解析multipart请求
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        // 检查请求是否为multipart类型
        if (resolver.isMultipart(request)) {
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            shopImg = (CommonsMultipartFile) multiRequest.getFile("shopImg");
        } else {
            modelMap.put("success",false);
            modelMap.put("errorMsg","上传图片不能为空");
            return modelMap;
        }
//        注册店铺
        if (shop != null && shopImg != null) {
            PersonInfo personInfo = (PersonInfo) request.getSession().getAttribute("user");
            shop.setOwner(personInfo);
            ShopExecution shopExecution = null;
            ImageHolder imageHolder = new ImageHolder(shopImg.getOriginalFilename(),
                                                        shopImg.getInputStream());
            try {
                shopExecution = shopService.addShop(shop, imageHolder);
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errorMsg:",e.getMessage());
                System.out.println("2" + e.getMessage());
                return modelMap;
            }
            if (shopExecution.getState() == ShopStateEnum.CHECK.getState()) {
                modelMap.put("success",true);
//                该用户可以操作的店铺列表
                List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
//                如果创建的是第一个店铺
                if (shopList != null || shopList.size() == 0) {
                    shopList = new ArrayList<Shop>();
                }
                shopList.add(shopExecution.getShop());
                request.getSession().setAttribute("shopList",shopList);

            }else {
                modelMap.put("success",false);
                modelMap.put("errorMsg",shopExecution.getStateInfo());
            }
            return modelMap;
        } else {
            modelMap.put("success",false);
            modelMap.put("errorMsg","请输入店铺信息");
            return modelMap;
        }
    }
    public ShopManagementController() {
    }



//    private static void inputStreamToFile(InputStream inputStream, File file) {
//        FileOutputStream os = null;
//        try{
//            os = new FileOutputStream(file);
//            int bytesRead = 0;
//            byte[] buffer = new byte[1024];
//            while((bytesRead = inputStream.read(buffer)) != -1) {
//                os.write(buffer, 0, bytesRead);
//            }
//        }catch(Exception e){
//            throw new RuntimeException("调用inputStreamToFile异常:"+e.getMessage());
//        }finally {
//            try {
//                if(os != null){
//                    os.close();
//                }
//                if(inputStream != null){
//                    inputStream.close();
//                }
//            } catch (IOException e) {
//                throw new RuntimeException("inputStreamToFile关闭IO异常:"+e.getMessage());
//            }
//        }
//    }


}
