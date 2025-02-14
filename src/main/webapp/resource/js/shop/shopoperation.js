/**
 *
 */
console.log("引入shopoperation.js")
$(function(){
    // 从url中取出shopId
    const shopId = getQueryString("shopId");
    console.log("shopId",shopId)
    // 只有修改需要传shopId true:修改 false：注册
    const isEdit = shopId ? true : false;

    var initUrl = '/shopadmin/getshopinitinfo';
    var registerShopUrl = '/shopadmin/registershop';
    var shopInfoUrl = '/shopadmin/getshopbyid?shopId=' + shopId ;
    var editShopUrl = '/shopadmin/modifyshop';

    if(isEdit) {
        console.log("修改")
        getShopInfo(shopId);
    } else {
        console.log("注册")
        getShopInitInfo();
    }

    /**
     * 注册用
     */
    function getShopInitInfo() {
        /**
         * 获取分类区域下拉列表的值
         */
        $.getJSON(initUrl,function(data) {
            if (data.success) {
                var tempHtml = "";
                var tempAreaHtml = "";
                if (data.shopCategoryList != undefined) {
                    // console.log(data.shopCategoryList)
                    data.shopCategoryList.map(function(data){
                        tempHtml += '<option data-id="' + data.shopCategoryId + '">'
                            + data.shopCategoryName + '</option>'
                    })
                }
                if (data.areaList != undefined) {
                    // console.log(data.areaList);
                    data.areaList.map(function (data) {
                        tempAreaHtml += '<option data-id="' + data.areaId + '">'
                            + data.areaName + '</option>';
                    })
                }
                $('#shop-category').html(tempHtml);
                $('#shop-area').html(tempAreaHtml);
            }
        })

        /**
         * 点击提交获取表单参数并提交
         */
        $('#submit').click(function () {
            var shop = {};
            shop.shopName = $('#shop-name').val();
            shop.shopAddr = $('#shop-addr').val();
            shop.phone = $('#shop-phone').val();
            shop.shopDesc = $('#shop-desc').val();
            shop.shopCategory = {
                shopCategoryId:$('#shop-category').find('option').not(function() {
                    return !this.selected;
                }).data('id')
            };
            shop.area = {
                areaId:$('#shop-area').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            }
            var shopImg = $('#shop-img')[0].files[0];
            var formDate = new FormData();
            formDate.append('shopImg',shopImg);
            formDate.append('shopStr',JSON.stringify(shop));
            var verifyCodeActual = $('#j_captch').val()
            if (!verifyCodeActual) {
                alert("请输入验证码")
                return;
            }
            formDate.append("verifyCodeActual",verifyCodeActual)
            $.ajax({
                url:registerShopUrl,
                type:'POST',
                data:formDate,
                contentType:false,
                processData: false,
                cache:false,
                success: function (data) {
                    if (data.success) {
                        alert("提交成功")
                    } else{
                        alert("提交失败:" + data.errotMsg);
                    }
                    $('#j_captch').click()
                }
            })
        });
    }
    /**
     * 传入shopId获取要修改的店铺的信息 修改时回显用
     * @param shopId
     */
    function getShopInfo(shopId) {
        /**
         * 获取分类区域下拉列表的值
         */
        $.getJSON(shopInfoUrl,function(data) {
            console.log("根据id获取shop信息")
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                // 类别不可更改直接显示
                var shopCategory = '<option data-id="' +
                    shop.shopCategory.shopCategoryId + '">' +
                    shop.shopCategory.shopCategoryName + '</option>'
                // 区域可以更改
                var tempAreaHtml = "";
                if (data.areaList != undefined) {
                    data.areaList.map(function (data) {
                        tempAreaHtml += '<option data-id="' + data.areaId + '">'
                            + data.areaName + '</option>';
                    })
                }
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled','disabled');
                $('#shop-area').html(tempAreaHtml);
                $('#shop-area option[data-id="' + shop.area.areaId + '"]')
                    .attr("selected","selected");
            }
        })

        $('#submit').click(function () {
            var shop = {};
            shop.shopId = shopId;
            shop.shopName = $('#shop-name').val();
            shop.shopAddr = $('#shop-addr').val();
            shop.phone = $('#shop-phone').val();
            shop.shopDesc = $('#shop-desc').val();
            shop.shopCategory = {
                shopCategoryId:$('#shop-category').find('option').not(function() {
                    return !this.selected;
                }).data('id')
            };
            shop.area = {
                areaId:$('#shop-area').find('option').not(function () {
                    return !this.selected;
                }).data('id')
            }
            var shopImg = $('#shop-img')[0].files[0];
            var formDate = new FormData();
            formDate.append('shopImg',shopImg);
            formDate.append('shopStr',JSON.stringify(shop));
            var verifyCodeActual = $('#j_captch').val()
            if (!verifyCodeActual) {
                alert("请输入验证码")
                return;
            }
            formDate.append("verifyCodeActual",verifyCodeActual)
            $.ajax({
                url:editShopUrl,
                type:'POST',
                data:formDate,
                contentType:false,
                processData: false,
                cache:false,
                success: function (data) {
                    if (data.success) {
                        alert("提交成功")
                    } else{
                        alert("提交失败:" + data.errotMsg);
                    }
                    $('#captch_image').click()
                }
            })
        });
    }


})
