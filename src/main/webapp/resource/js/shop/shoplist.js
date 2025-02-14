/**
 *
 */
$(function (){

    getlist();

    /**
     * 获取店铺列表信息并调用渲染方法
     * @param e
     */
    function getlist(e) {
        $.ajax({
            url:"/shopadmin/getshoplist",
            type:"get",
            dataType:"json",
            success:function (data) {
                if (data.success) {
                    // 渲染商铺列表
                    handleList(data.shopList);
                    // 显示用户名
                    handleUser(data.user);
                }
            }
        })
    }

    /**
     * 显示用户信息
     * @param data 用户信息
     */
    function handleUser(data) {
        $('#user-name').text(data.name);
    }

    /**
     * 渲染商铺列表
     */
    function handleList(data) {
        var html = "";
        data.map(function(item,index) {
            html += '<div class="row row-shop"> <div class="col-40">'
                + item.shopName + '</div><div class="col-40">'
                + shopStatus(item.enableStatus)
                + '</div><div class="col-20">'
                + goShop(item.enableStatus,item.shopId) + '</div></div>'
        });
        $('.shop-wrap').html(html)
    }

    /**\
     * 根据店铺状态id渲染对应的结果
     * @param status
     * @returns {string}
     */
    function shopStatus(status) {
        if (status == 0) {
            return '审核中';
        }else if (status == -1) {
            return '店铺非法';
        }else if (status == 1) {
            return '审核通过';
        }
    }

    function goShop(status,id) {
        if (status == 1) {
            return '<a href="/shopadmin/shopmanagement?shopId=' + id + '">进入</a>';
        }
        else {
            return '';
        }
    }









})




