/**
 *
 */
$(function(){
    var shopId = getQueryString("shopId");
    // 验证是否有权限操作店铺
    var shopInfoUrl = '/shopadmin/getshopmanagementinfo?shopId=' + shopId;
    $.getJSON(shopInfoUrl, function(data){
        if (data.redirect) {
            window.location.href = data.url;
        } else {
            if (data.shopId != undefined && data.shopId != null) {
                shopId = data.shopId;
            }
            $('#shopInfo').attr('href','/shopadmin/shopoperation?shopId=' + shopId);
        }
    })




})