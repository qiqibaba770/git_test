package com.imooc.myo2o.util;

/**
 * @Title: PageUtil
 * @Author 林广华
 * @Package com.imooc.myo2o.util
 * @Date 2024/8/1 14:37
 * @description:
 */
public class PageUtil {

    /**
     * 将页数转为行数
     * @param pageIndex
     * @param PageSize
     * @return
     */
    public static int calculateRowIndex(int pageIndex,int PageSize) {
        return (pageIndex > 0) ? (pageIndex - 1) * PageSize : 0;
    }



}
