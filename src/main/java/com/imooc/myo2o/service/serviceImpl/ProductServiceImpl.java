package com.imooc.myo2o.service.serviceImpl;

import com.imooc.myo2o.dao.ProductDao;
import com.imooc.myo2o.dao.ProductImgDao;
import com.imooc.myo2o.dto.ImageHolder;
import com.imooc.myo2o.entity.Product;
import com.imooc.myo2o.entity.ProductImg;
import com.imooc.myo2o.service.ProductService;
import com.imooc.myo2o.util.ImageUtil;
import com.imooc.myo2o.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @Title: ProductServiceImpl
 * @Author 林广华
 * @Package com.imooc.myo2o.service.serviceImpl
 * @Date 2024/8/3 21:44
 * @description:
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;
    @Autowired
    private ProductImgDao productImgDao;

    private Map<String,Object> modelMap = new HashMap<>();

    /**
     * 根据Id获取商品
     * @param id
     * @return
     */
    @Override
    public Product getProductById(long id) {

    }

    /**
     * 修改商品
     * @param product
     * @return
     */
    @Override
    public Map<String, Object> updateProduct(Product product) {
        return Collections.emptyMap();
    }

    /**
     * 处理缩略图，获取缩略图的相对路径并赋值给product
     * 往tb_product 写入商品信息并获取productId
     * 结合productId批量处理详情图
     * 将商品详情图列表批量添加
     * @param product
     * @param thumbnail 缩略图的
     * @param productImgList    详情图的
     * @return
     * @throws RuntimeException
     */
    @Override
    @Transactional
    public Map<String, Object> addProduct(Product product,
                                          ImageHolder thumbnail,
                                          List<ImageHolder> productImgList) throws RuntimeException {

        if (product != null && product.getShop() != null && product.getShop().getShopId() > 0){
            // 给商品设置默认值
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            // 默认为上架的状态
            product.setEnableStatus(1);
            // 若商品缩略图不为空则添加
            if (thumbnail != null) {
                addThumbnail(product,thumbnail);
            }
            try {
                // 添加商品信息
                int effectedNum = productDao.insertProduct(product);
                if (effectedNum <= 0) {
                    System.out.println("创建商品失败");
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                System.out.println("添加商品信息失败" + e.getMessage());
                throw new RuntimeException();
            }
            // 详情图不为空则添加
            if (productImgList != null && productImgList.size() > 0) {
                addProductImgList(product,productImgList);
            }
            modelMap.put("success",true);
            modelMap.put("successMsg","添加成功");
        } else {
            modelMap.put("success",false);
            modelMap.put("errorMsg","商品不能为空");
        }
        return modelMap;
    }

    /**
     * 批量添加详情图片
     * @param product
     * @param productImgList
     */
    private void addProductImgList(Product product, List<ImageHolder> productImgList) {
        // 获取图片存储路径
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        ArrayList<ProductImg> productImgs = new ArrayList<ProductImg>();
        // 遍历图片依次去处理，并添加进productImg实体类型
        for (ImageHolder productImgHolder : productImgList) {
            String imgAddr = ImageUtil.generateThumbnail(productImgHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImg.setProductId(product.getProductId());
            productImgs.add(productImg);
        }
        // 如果确实有图片需要添加就执行添加操作
        if (productImgs.size() > 0) {
            try {
                int effectedNum = productImgDao.batchInsertProductImg(productImgs);
                if (effectedNum <= 0) {
                    throw new RuntimeException();
                }
            } catch (Exception e) {
                System.out.println("添加详情图失败" + e.getMessage());
                throw new RuntimeException();
            }
        }


    }

    /**
     * 添加商品图片
     * @param product
     * @param thumbnail
     */
    private void addThumbnail(Product product, ImageHolder thumbnail) {
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(thumbnail, dest);
        product.setImgAddr(thumbnailAddr);
    }
}
