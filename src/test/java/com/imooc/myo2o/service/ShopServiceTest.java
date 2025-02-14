package com.imooc.myo2o.service;

import com.imooc.myo2o.BaseTest;
import com.imooc.myo2o.dto.ImageHolder;
import com.imooc.myo2o.dto.ShopExecution;
import com.imooc.myo2o.entity.Area;
import com.imooc.myo2o.entity.PersonInfo;
import com.imooc.myo2o.entity.Shop;
import com.imooc.myo2o.entity.ShopCategory;
import com.imooc.myo2o.enums.ShopStateEnum;
import net.coobird.thumbnailator.Thumbnails;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

/**
 * @Title: ShopServiceTest
 * @Author 林广华
 * @Package com.imooc.myo2o.service
 * @Date 2024/7/29 20:37
 * @description:
 */
public class ShopServiceTest extends BaseTest {

    @Autowired
    private ShopService shopService;

    @Test
    public void testGetShopList() {
        Shop shop = new Shop();
        ShopCategory shopCategory = new ShopCategory();
        shopCategory.setShopCategoryId(33L);
        shop.setShopCategory(shopCategory);
        ShopExecution shopList = shopService.getShopList(shop, 0, 2);
        System.out.println("shopList = " + shopList.toString());
    }

    @Test
    @Ignore
    public void testModifyShop() throws FileNotFoundException {
        Shop shop = shopService.getByShopId(19L);
        shop.setShopName("修改后的店铺名");
        File shopImg = new File("C:\\Users\\七七八八\\Desktop\\Image\\1.png");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(),is);
        ShopExecution shopExecution = shopService.modifyShop(shop, imageHolder);
        System.out.println("新的图片地址 = " + shopExecution.getShop().getShopImg());
    }

    @Test
    @Ignore
    public void testAddShop() throws Exception {
        Shop shop = new Shop();
        Area area = new Area();
        PersonInfo owner = new PersonInfo();
        ShopCategory sc = new ShopCategory();

        area.setAreaId(1);
        sc.setShopCategoryId(33L);
        owner.setUserId(1L);

        shop.setOwner(owner);
        shop.setArea(area);
        shop.setShopCategory(sc);
        shop.setShopName("测试店铺3");
        shop.setShopDesc("测试店铺3");
        shop.setShopAddr("测试店铺3");
        shop.setPhone("13810524526");
        shop.setCreateTime(new Date());
        shop.setEnableStatus(ShopStateEnum.CHECK.getState());
        shop.setAdvice("审核中");
        File shopImg = new File("C:\\Users\\七七八八\\Desktop\\Image\\aa.jpg");
        InputStream is = new FileInputStream(shopImg);
        ImageHolder imageHolder = new ImageHolder(shopImg.getName(),is);
        ShopExecution shopExecution = shopService.addShop(shop, imageHolder);
        System.out.println("shopExecution = " + shopExecution);


    }




}
