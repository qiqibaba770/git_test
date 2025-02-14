package com.imooc.myo2o.util;

import com.imooc.myo2o.dto.ImageHolder;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * @Title: ImageUtil
 * @Author 林广华
 * @Package com.imooc.myo2o.util
 * @Date 2024/7/29 15:29
 * @description: 图片处理相关
 */
public class ImageUtil {

    private static final String basePah = Thread.currentThread().getContextClassLoader()
            .getResource("").getPath();
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final Random r = new Random();

    /**
     * 如果storePath是文件路径则删除该文件，
     * 如果是目录路径则删除该目录下的所有文件
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) { // 如果是目录
                File[] files = fileOrPath.listFiles();
                for (File file : files) {
                    file.delete();
                }
            }
        }
        fileOrPath.delete();
    }


// 将图片保存并返回文件名
    public static String generateThumbnail(ImageHolder thumbnail, String targetAddr) {
//        获取随机名
        String realFileName = getRandomFileName();
//        获取文件后缀名
        String extension = getFileExtension(thumbnail.getImageName());
//        判断目标地址是否存在，不存在就创建
        makeDirPath(targetAddr);
//        保存的真实路径
        String relativeAddr = targetAddr + realFileName + extension;
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        try {
            System.out.println(basePah + "water.png");
            Thumbnails.of(thumbnail.getImage())
                    .size(200, 200)
                    .watermark(Positions.BOTTOM_RIGHT, ImageIO.read(new File(basePah + "water.png")), 0.25f)
                    .outputQuality(0.8f)
                    .toFile(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的路径
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取文件流扩展名
     * @param fileName:文件名
     * @return
     */
    private static String getFileExtension(String fileName) {
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日时分秒 + 五位随机数
     * @return
     */
    public static String getRandomFileName() {
//        获取随机五位数
        int rannum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + rannum;
    }
















    public static void main(String[] args) throws IOException {
        Thumbnails.of(new File("C:\\Users\\七七八八\\Desktop\\Image\\1.png"))
                .scale(1.0) // 不改变尺寸，size(200,200)
                .watermark(Positions.BOTTOM_RIGHT,
                        ImageIO.read(new File(basePah + "/water.png")),
                        0.2f)
                .outputQuality(0.8f)
                .toFile("C:\\Users\\七七八八\\Desktop\\Image\\1water.png");
    }






}
