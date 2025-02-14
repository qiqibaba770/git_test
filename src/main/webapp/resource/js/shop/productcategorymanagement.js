/**
 *
 */
$(function () {
    var listUrl = '/shopadmin/getproductcategorylist';
    var addUrl = '/shopadmin/addproductcategorys';
    var deleteUrl = '/shopadmin/removeproductcategory';

    getList();

    /**
     * 获取所有商铺类别数据并渲染
     */
    function getList() {
        $.getJSON(listUrl,function(data) {
            if (data.success) {
                var dataList = data.data;
                $('.category-wrap').html('');
                var tempHtml = "";
                dataList.map(function(item,index) {
                    tempHtml += ''
                        + '<div class="row row-product-category now">'
                        + '<div class="col-33 product-category-name">'
                        + item.productCategoryName
                        + '</div>'
                        + '<div class="col-33">'
                        + item.priority
                        + '</div>'
                        + '<div class="col-33"><a href="#" class="button delete" data-id="'
                        + item.productCategoryId
                        + '">删除</a></div>' + '</div>';
                })
                $('.category-wrap').append(tempHtml)
            } else if (data.errorCode == 1001) {
                window.location.href = '/shopadmin/shoplist';
            }
        })
    }

    /**
     * 点击新增按钮后
     */
    $('#new').click(function (){
        var tempHtml ='<div class="row row-product-category temp">\n' +
            '        <div class="col-33"><input class="category-input category" type="text" placeholder="请输入类别名" /> </div>\n' +
            '        <div class="col-33"><input class="category-input priority" type="number" /> </div>\n' +
            '        <div class="col-33"><a href="#" class="button delete">删除</a> </div>\n' +
            '    </div>';
        $('.category-wrap').append(tempHtml);
    });

    /**
     * 点击提交按钮后
     */
    $('#submit').click(function (){
        var tempArr = $('.temp');
        var productCategoryList = [];
        tempArr.map(function (index,item){
            var tempObj = {};
            tempObj.productCategoryName = $(item).find('.category').val();
            tempObj.priority = $(item).find('.priority').val();
            if (tempObj.productCategoryName && tempObj.priority) {
                productCategoryList.push(tempObj);
            }
        });
        $.ajax({
            url:addUrl,
            type:'POST',
            data:JSON.stringify(productCategoryList),
            contentType:'application/json',
            success:function(data) {
                if (data.success) {
                    alert('添加成功');
                    getList();
                } else {
                    alert('添加失败'+data.errorMsg);
                }
            }
        })
    })

    $('.category-wrap').on('click','.row-product-category.now .delete',
        function (e) {
        var target = e.currentTarget;
        $.ajax({
            url:deleteUrl,
            type:'POST',
            data:{
                productCategoryId:target.dataset.id
            },
            dataType:'json',
            success:function(data) {
                if (data.success) {
                    alert('删除成功');
                    getList();
                }
            }
        })
    })

    $('.category-wrap').on('click','.row-product-category.temp .delete',
        function (e) {
            console.log($(this).parent().parent())
            $(this).parent().parent().remove();
            getList();
        })

})

