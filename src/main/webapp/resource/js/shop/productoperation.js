/**
 *
 */

$(function() {
  // 从url获取商品id
  var productId = getQueryString('productId');
  var shopId = 1;
  var infoUrl = '/shopadmin/getproductbyid?productId=' + productId;
  var categoryUrl = '/shopadmin/getproductcategorylist';
  // 更新商品信息的url
  var productPostUrl = 'shopadmin/modifyproduct';
  // 标识是添加还是编辑
  var isEdit = false;
  if (productId) {  // 有productId则为编辑操作
    // 将要编辑的商品信息显示在页面
    getInfo(productId);
    isEdit = true;
  } else {    // 添加
    getCategory(shopId);
    productPostUrl = '/shopadmin/addproduct';
  }

  /**
   * 获取需要编辑的商品的信息并显示在页面
   * @param id
   */
  function getInfo(id) {
    $.getJSON(infoUrl, function(data) {
              if (data.success) {
                var product = data.product;
                $('#product-name').val(product.productName);
                $('#product-desc').val(product.productDesc);
                $('#priority').val(product.priority);
                $('#normal-price').val(product.normalPrice);
                $('#promotion-price').val(
                    product.promotionPrice);

                var optionHtml = '';
                var optionArr = data.productCategoryList;
                var optionSelected = product.productCategory.productCategoryId;
                optionArr
                    .map(function(item, index) {
                      var isSelect = optionSelected === item.productCategoryId ? 'selected'
                          : '';
                      optionHtml += '<option data-value="'
                          + item.productCategoryId
                          + '"'
                          + isSelect
                          + '>'
                          + item.productCategoryName
                          + '</option>';
                    });
                $('#category').html(optionHtml);
              }
            });
  }

  /**
   * 新增商品时获取商品类别信息
   */
  function getCategory() {
    $.getJSON(categoryUrl, function(data) {
      if (data.success) {
        var productCategoryList = data.data;
        var optionHtml = '';
        productCategoryList.map(function(item, index) {
          optionHtml += '<option data-value="'
              + item.productCategoryId + '">'
              + item.productCategoryName + '</option>';
        });
        $('#category').html(optionHtml);
      }
    });
  }

  $('.detail-img-div').on('change', '.detail-img:last-child', function() {
    if ($('.detail-img').length < 6) {
      $('#detail-img').append('<input type="file" class="detail-img">');
    }
  });

  $('#submit').click(
      function() {
        var product = {};
        product.productName = $('#product-name').val();
        product.productDesc = $('#product-desc').val();
        product.priority = $('#priority').val();
        product.normalPrice = $('#normal-price').val();
        product.promotionPrice = $('#promotion-price').val();
        product.productCategory = {
          productCategoryId : $('#category').find('option').not(
              function() {
                return !this.selected;
              }).data('value')
        };
        product.productId = productId;
        // 获取缩略图文件流
        var thumbnail = $('#small-img')[0].files[0];
        console.log(thumbnail);
        var formData = new FormData();
        formData.append('thumbnail', thumbnail);
        // 遍历商品详情图控件
        $('.detail-img').map(
            function(index, item) {
              if ($('.detail-img')[index].files.length > 0) {
                formData.append('productImg' + index,
                    $('.detail-img')[index].files[0]);
              }
            });
        formData.append('productStr', JSON.stringify(product));
        var verifyCodeActual = $('#j_captcha').val();
        if (!verifyCodeActual) {
          alert('请输入验证码！');
          return;
        }
        formData.append("verifyCodeActual", verifyCodeActual);
        $.ajax({
          url : productPostUrl,
          type : 'POST',
          data : formData,
          contentType : false,
          processData : false,
          cache : false,
          success : function(data) {
            if (data.success) {
              alert('提交成功！');
              $('#captcha_img').click();
            } else {
              alert('提交失败！');
              $('#captcha_img').click();
            }
          }
        });
      });

});