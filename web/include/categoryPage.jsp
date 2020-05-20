<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<title>模仿天猫官网分类${c.name}</title>

<div id="category">
  <div class="categortPageDiv" >
    <div style="max-width:1013px;margin:0px auto">
    <img src="img/category/${c.id}.jpg">

    <%@include file="category/sortBar.jsp" %>

    <%@include file="category/productsByCategory.jsp" %>
       </div>
  </div>
</div>