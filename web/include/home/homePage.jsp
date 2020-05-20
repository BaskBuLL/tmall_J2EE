<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<title>模仿天猫首页</title>

<div class="homepageDiv">
 <%@ include file="categoryAndcarousel.jsp" %>
 <div style="height:10px"></div>
  <div style="margin:0px auto;max-width:1013px;background-color:#F5F5F5;">
    <div style="height:30px"></div>
 <%@ include file="homepageCategoryProducts.jsp" %>
 </div>
</div>