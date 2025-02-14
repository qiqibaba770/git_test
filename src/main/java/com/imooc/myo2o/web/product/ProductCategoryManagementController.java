package com.imooc.myo2o.web.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.myo2o.dto.ImageHolder;
import com.imooc.myo2o.dto.Result;
import com.imooc.myo2o.entity.Product;
import com.imooc.myo2o.entity.ProductCategory;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.service.ProductCategoryService;
import com.imooc.myo2o.service.ProductService;
import com.imooc.myo2o.util.CodeUtil;
import com.imooc.myo2o.util.HttpServletRequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * @Title: ProductCategoryManagementController
 * @Author 林广华
 * @Package com.imooc.myo2o.web.product
 * @Date 2024/8/2 15:38
 * @description:
 */
@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {

    @Autowired
    private ProductCategoryService productCategoryService;
    @Autowired
    private ProductService productService;

    // 支持上传商品详情图的最大数量
    private static final int IMAGEMAXCOUNT = 6;
    @Autowired
    private CommonsMultipartResolver multipartResolver;

    /**
     * 添加商品
     * @param request
     * @return
     */
    @RequestMapping(value = "/addproduct",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> addProduct(HttpServletRequest request){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        System.out.println("接收到请求");
        // 验证码校验
        if (!CodeUtil.checkifyVerifyCode(request)) {
            modelMap.put("success",false);
            modelMap.put("errorMsg","验证码错误");
        }
        // 接受前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
        ObjectMapper mapper = new ObjectMapper();
        Product product = null;
        String productStr = HttpServletRequestUtil.getString(request, "productStr");
        // 从请求中接收文件流
        MultipartHttpServletRequest multipartRequest = null;
        // 接受缩略图
        ImageHolder thumbnail = null;
        // 接受详情图
        List<ImageHolder> imageHolderList = new ArrayList<ImageHolder>();
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
                request.getSession().getServletContext()
        );
        try {
            // 若请求中存在文件流,则取出相关文件（缩略图和详情图)
            if (multipartResolver.isMultipart(request)) {
                multipartRequest = (MultipartHttpServletRequest) request;
                // 取出缩略图并构造ImageHolder对象
                CommonsMultipartFile thumbnailfile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
                thumbnail = new ImageHolder(thumbnailfile.getOriginalFilename(),thumbnailfile.getInputStream());
                // 取出详情图
                for (int i = 0; i < IMAGEMAXCOUNT; i++) {
                    CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg" + i);
                    if (productImgFile != null) {
                        ImageHolder productImgHolder = new ImageHolder(productImgFile.getOriginalFilename(),productImgFile.getInputStream());
                        imageHolderList.add(productImgHolder);
                    } else {
                        break;
                    }
                }
            } else {
                modelMap.put("success",false);
                modelMap.put("errorMsg","上传文件不能为空");
                return modelMap;
            }
        } catch (Exception e) {
            System.out.println("添加商品错误:" + e.getMessage());
            modelMap.put("success",false);
            modelMap.put("errorMsg",e.getMessage());
            return modelMap;
        }
        try {
            product = mapper.readValue(productStr,Product.class);
        } catch (Exception e) {
            System.out.println("出错了：:" + e.getMessage());
            modelMap.put("success",false);
            modelMap.put("errorMsg",e.getMessage());
            return modelMap;
        }
        // 若Product信息，缩略图，详情图都不为空则开始进行商品添加
        if (product != null && thumbnail != null && imageHolderList.size() > 0) {
            try{
                // 从session中获取当前店铺的Id并赋值给productId，减少对前端数据的依赖
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                Shop shop = new Shop();
                shop.setShopId(currentShop.getShopId());
                product.setShop(shop);
                // 执行添加操作
                Map<String, Object> serviceResultMap = productService.addProduct(product, thumbnail, imageHolderList);
                if (Boolean.TRUE.equals(serviceResultMap.get("success"))) {
                    modelMap.put("success",true);
                } else {
                    modelMap.put("success",false);
                    modelMap.put("errorMsg",serviceResultMap.get("errorMsg"));
                }
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errorMsg",e.getMessage());
                return modelMap;
            }
        }
        System.out.println(modelMap.toString());
        return modelMap;
    }

    /**
     * 删除商品类别
     * @param productCategoryId
     * @param request
     * @return
     */
    @RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> removeProductCategory(Long productCategoryId,
                                                     HttpServletRequest request ){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        if (productCategoryId != null && productCategoryId > 0) {
            try {
                Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
                modelMap = productCategoryService.deleteProductCategory(productCategoryId,
                        currentShop.getShopId());
            } catch (Exception e) {
                modelMap.put("success", false);
                modelMap.put("msg", e.getMessage());
                return modelMap;
            }
        }else {
            modelMap.put("success", false);
            modelMap.put("errorMsg","请至少选择一个商品类别");
        }
        return modelMap;
    }

    /**
     * 批量添加商品类别
     * @param request
     * @param productCategoryList
     * @return
     */
    @RequestMapping(value = "/addproductcategorys",method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> addProductCategorys(HttpServletRequest request,
                                                   @RequestBody List<ProductCategory> productCategoryList){
        Map<String,Object> modelMap = new HashMap<String,Object>();
        Shop shop = (Shop) request.getSession().getAttribute("currentShop");
        // 将前端传过来的商铺类别设置对应的Id
        for (ProductCategory pc : productCategoryList) {
            pc.setShopId(shop.getShopId());
            pc.setCreateTime(new Date());
        }
        modelMap = productCategoryService.batchAddProductCategory(productCategoryList);
        return modelMap;
    }

    /**
     * 获取所有商品类别
     * @param request
     * @return
     */
    @RequestMapping(value = "/getproductcategorylist",method = RequestMethod.GET)
    @ResponseBody
    public Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
//        Shop shop = new Shop();
//        shop.setShopId(19L);
//        request.getSession().setAttribute("currentShop",shop);
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        List<ProductCategory> list = null;
        if (currentShop != null && currentShop.getShopId() > 0) {
            list = productCategoryService.getProductCategoryList(currentShop.getShopId());
            return new Result<List<ProductCategory>>(true,list);
        }else {
            return new Result<List<ProductCategory>>(false,1001,"缺少操作权限");
        }


    }


}
