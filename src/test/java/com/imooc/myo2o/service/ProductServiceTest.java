package com.imooc.myo2o.service;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.dto.ImageHolder;
import com.imooc.myo2o.entity.Product;
import com.imooc.myo2o.entity.ProductCategory;
import com.imooc.myo2o.entity.Shop;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Title: ProductServiceTest
 * @Author 林广华
 * @Package com.imooc.myo2o.service
 * @Date 2024/8/3 22:20
 * @description:
 */
public class ProductServiceTest extends BaseTest {

    @Autowired
    private ProductService productService;

    @Test
    public void testAddProduct() throws FileNotFoundException {
        Product product = new Product();
        Shop shop = new Shop();
        shop.setShopId(19L);
        ProductCategory pc = new ProductCategory();
        pc.setProductCategoryId(11L);
        product.setShop(shop);
        product.setProductCategory(pc);
        product.setProductName("测试商品一");
        product.setProductDesc("测试商品一简介");
        product.setPriority(10);
        product.setCreateTime(new Date());
        product.setEnableStatus(1);
        // 创建缩略图文件流
        File thumbnailFile = new File("C:\\Users\\七七八八\\Desktop\\Image\\3.jpg");
        FileInputStream is = new FileInputStream(thumbnailFile);
        ImageHolder imageHolder = new ImageHolder(thumbnailFile.getName(), is);
        // 创建两个商品详情图
        File pro1 = new File("C:\\Users\\七七八八\\Desktop\\Image\\4.jpg");
        File pro2 = new File("C:\\Users\\七七八八\\Desktop\\Image\\5.jpg");
        FileInputStream is1 = new FileInputStream(pro1);
        FileInputStream is2 = new FileInputStream(pro2);
        ImageHolder ih1 = new ImageHolder(pro1.getName(), is1);
        ImageHolder ih2 = new ImageHolder(pro2.getName(), is2);
        List<ImageHolder> productImagaeList = new ArrayList<ImageHolder>();
        productImagaeList.add(ih1);
        productImagaeList.add(ih2);
        // 添加商品并验证
        Map<String, Object> stringObjectMap = productService.addProduct(product, imageHolder, productImagaeList);
        System.out.println("stringObjectMap = " + stringObjectMap.toString());
    }


}
