<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    
<title>模仿天猫官网产品${p.name}</title>
 <div class="categoryPictureInProductPageDiv">
  <img class="categoryPictureInproductPage" src="img/category/${p.category.id}.jpg">
 </div>
 
 <div class="productPageDiv">
   <%@include file="product/imgAndInfo.jsp" %> 
   <%@include file="product/productDetail.jsp" %>
   <%@include file="product/productReview.jsp" %> 
</div>