/**
 *
 */

/**
 * 点击改变验证码
 * @param img
 */
function changeVerifyCode(img) {
    img.src="../Kaptcha?" + Math.floor(Math.random() * 100);
}

/**
 * 从url中取出name对应的值
 * @param name
 * @returns {string}
 */
function getQueryString(name) {
    var reg = RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substring(1).match(reg);
    if (r != null) {
        return decodeURIComponent(r[2]);
    }
    return "";
}



