package com.imooc.myo2o.service.serviceImpl;

import com.imooc.myo2o.dao.ProductCategoryDao;
import com.imooc.myo2o.entity.ProductCategory;
import com.imooc.myo2o.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Title: ProductCategoryServiceImpl
 * @Author 林广华
 * @Package com.imooc.myo2o.service.serviceImpl
 * @Date 2024/8/2 15:33
 * @description:
 */
@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryDao productCategoryDao;

    private Map<String,Object> modelMap = new HashMap<String, Object>();


    @Override
    public List<ProductCategory> getProductCategoryList(long shopId) {
        return productCategoryDao.queryProductCategoryList(shopId);
    }

    @Override
    @Transactional
    public Map<String, Object> batchAddProductCategory(List<ProductCategory> productCategoryList) {
        int count = 0;
        if (productCategoryList != null && productCategoryList.size() > 0) {
            try {
                count = productCategoryDao.batchInsertProductCategory(productCategoryList);
                if (count <= 0) {
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                modelMap.put("success",false);
                modelMap.put("errorMsg",e.getMessage());
                return modelMap;
            }
        } else {
            modelMap.put("success",false);
            modelMap.put("errorMsg","添加列表为空");
            return modelMap;
        }
        modelMap.put("count",count);
        modelMap.put("success",true);
        return modelMap;
    }

    @Override
    @Transactional
    public Map<String, Object> deleteProductCategory(long productCategoryId, long shopId) {
        // TODO 将此商品类别下的商品的类别Id置为空
        try {
            int effectedNum = productCategoryDao.deleteShopProductCategory(productCategoryId,
                    shopId);
            if (effectedNum <= 0 ) {
                throw new RuntimeException();
            } else {
                modelMap.put("success",true);
                modelMap.put("count",effectedNum);
                return modelMap;
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
