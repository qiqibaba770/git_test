package com.imooc.myo2o.util;

import com.google.code.kaptcha.Constants;

import javax.servlet.http.HttpServletRequest;

/**
 * @Title: CodeUtil
 * @Author 林广华
 * @Package com.imooc.myo2o.util
 * @Date 2024/7/30 22:21
 * @description: 判断验证码
 */
public class CodeUtil {

    // 判断验证码是否正确
    public static boolean checkifyVerifyCode(HttpServletRequest request) {
        String verifyCode = (String) request.getSession().getAttribute(
                Constants.KAPTCHA_SESSION_KEY
        );
         HttpServletRequestUtil.getString(request,"verifyCodeActual");
         if (verifyCode == null || !verifyCode.equals(verifyCode)) {
             return false;
         }
//         第一次修改
//        第二次修改
//        master也修改这行
//        test分支修改的内容
//        ssh推送
//        整合
//        idea中处理冲突
//        master分支添加
//        dev分支添加
         return true;
    }


}
