<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>

<%@include file="../include/admin/adminHeader.jsp" %>
<%@include file="../include/admin/adminNavigator.jsp" %>
<title>编辑产品</title>

<script>
$(function(){
	$("editForm").submit(function(){
		if(!checkEmpty("name","产品名称"))
		   return false;
		if(!checkNumber("promotePrice","原价格"))
		   return false;
		if(!checkNumber("originalPrice","优惠价格"))
		   return false;
		if(!checkInt("stock","库存"))
		   return false;
		
		return true;
	}) ;
 
 })
</script>

  <ol class="breadcrumb">
    <li><a href="admin_category_list">所有分类</a></li>
    <li><a href="admin_product_list?cid=${p.category.id}">${p.category.name}</a></li>
    <li class="active">编辑产品</li>
  </ol>
  
  <div class="panel panel-info editDiv">
    <div class="panel-heading">编辑产品</div>
    <div class="panel-body">
      <form method="post" id="editForm" action="admin_property_update">
        <table class="editTable">
         <tr>
           <td>属性名称</td>
           <td><input name="name" id="name" type="text" class="form-control" value="${p.name}"/></td>
          </tr>
          <tr>
           <td>产品小标题</td>
           <td><input name="subTitle" id="subTitle" type="text" class="form-control" value="${p.subTitle}"/></td>
          </tr>
          <tr>
           <td>原价格</td>
           <td><input name="promotePrice" id="promotePrice" type="text" class="form-control" value="${p.promotePrice}"/></td>
          </tr>
          <tr>
           <td>优惠价格</td>
           <td><input name="originalPrice" id="originalPrice" type="text" class="form-control" value="${p.originalPrice}"/></td>
          </tr>
          <tr>
           <td>库存</td>
           <td><input name="stock" id="stock" type="text" class="form-control" value="${p.stock}"/></td>
          </tr>
          
          <tr class="submitTR">
           <td colspan="2" align="center">
             <input type="hidden" name="cid" id="cid" value="${p.category.id }" />
             <input type="hidden" name="id" id="id" value="${p.id}" />
             <button type="submit" class="btn btn-success">提交</button>
           </td>
           </tr>
           </table>
           </form>
           </div>
           </div>
           
           <%@include file="../include/admin/adminFooter.jsp" %>
           
         