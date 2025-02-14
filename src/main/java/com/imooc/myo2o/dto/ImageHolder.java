package com.imooc.myo2o.dto;

import java.io.InputStream;

/**
 * @Title: ImageHolder
 * @Author 林广华
 * @Package com.imooc.myo2o.dto
 * @Date 2024/8/3 21:28
 * @description:
 */
public class ImageHolder {
    private String imageName;
    private InputStream image;

    public ImageHolder() {
    }

    public ImageHolder(String imageName, InputStream image) {
        this.imageName = imageName;
        this.image = image;
    }

    @Override
    public String toString() {
        return "ImageHolder{" +
                "imageName='" + imageName + '\'' +
                ", image=" + image +
                '}';
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public InputStream getImage() {
        return image;
    }

    public void setImage(InputStream image) {
        this.image = image;
    }
}
