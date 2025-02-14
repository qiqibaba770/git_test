package com.imooc.myo2o.util;

/**
 * @Title: PathUtil
 * @Author 林广华
 * @Package com.imooc.myo2o.util
 * @Date 2024/7/29 16:40
 * @description:
 */
public class PathUtil {
    private static final String seperator = System.getProperty("file.separator");

    /**
     *
     * @return 返回项目图片根路径
     */
    public static String getImgBasePath() {
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = "E:/projectdev/image/";
        }else {
            basePath = "/home/user/image";
        }
        basePath = basePath.replace("/",seperator);
        return basePath;
    }

    /**
     *
     * @param shopId 店铺id
     * @return 返回项目图片的子路径
     */
    public static String getShopImagePath(long shopId){
        String imagePath = "/upload/item/shop/" + shopId + "/";
        return imagePath.replace("/",seperator);
    }


}
