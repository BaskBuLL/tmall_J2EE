<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<script>
function showProductsAsideCategorys(cid){
	$("div.eachCategory[cid="+cid+"]").css("background-color","white");
	$("div.eachCategory[cid="+cid+"] a").css("color","#87CEFA");
	$("div.productsAsideCategorys[cid="+cid+"]").show();
}

function hideProductsAsideCategorys(cid){
	$("div.eachCategory[cid="+cid+"]").css("background-color","#e2e2e3");
	$("div.eachCategory[cid="+cid+"] a").css("color","#000");
	$("div.productsAsideCategorys[cid="+cid+"]").hide();
}

$(function(){
	  
	  $("div.eachCategory").mouseenter(function(){
	   var cid =$(this).attr("cid");
	   showProductsAsideCategorys(cid);
	  });
	  $("div.eachCategory").mouseleave(function(){
	   var cid =$(this).attr("cid");
	   hideProductsAsideCategorys(cid);
	  });
	  $("div.productsAsideCategorys").mouseenter(function(){
	   var cid =$(this).attr("cid");
	   showProductsAsideCategorys(cid);
	  });
	  $("div.productsAsideCategorys").mouseleave(function(){
	   var cid =$(this).attr("cid");
	   hideProductsAsideCategorys(cid);
	  });
	  
	  $("div.rightMenu span").mouseenter(function(){
		    var left = $(this).position().left;
			var top  = $(this).position().top;
			var width= $(this).css("width");
	        var destLeft=parseInt(left)+parseInt(width)/2;
			$("img#catear").css("left",destLeft+5);
			$("img#catear").css("top",top-20);
			$("img#catear").fadeIn(500);
	  });
	  
	    $("div.rightMenu span").mouseleave(function(){
	    $("img#catear").hide();
	  });
	  
	   
})
</script>

<img src="img/site/catear.png" id="catear" class="catear" />
<div class="categoryWithCarousel">
  
    <div class="headbar show1">
    <div style="max-width:1013px;margin:0px auto;">
      <div class="head">
        <span style="margin-left:10px" class="glyphicon glyphicon-th-list"></span>
        <span style="margin-left:10px">商品分类</span>
      </div>
      
      <div class="rightMenu">
        <span><a href=""><span class="glyphicon glyphicon-shopping-cart" style="margin-right:3px"></span> 天猫超市</a></span>
        <span><a href=""><span class="glyphicon glyphicon-globe" style="margin-right:3px"></span>天猫国际</a></span>
        
        <c:forEach items="${cs}" var="c" varStatus="st">
          <c:if test="${st.count<=4}">
            <span><a href="forecaregory?cid=${c.id}">${c.name}</a></span>
          </c:if>
        </c:forEach>
      </div>
      
    </div>
    </div>
    <div style="max-width:1013px;margin:0px auto;">
    <div style="position:relative">
      <%@include file="categoryMenu.jsp" %>
    </div>
    
    <div style="position:relative;left:0;top:0">
     <%@include file="productsAsideCategorys.jsp" %>
    </div>
    </div>
    
    <%@include file="carousel.jsp" %>
    
    <div class="carouselBackgroundDiv">
    </div>
    </div>
    
    
    
    