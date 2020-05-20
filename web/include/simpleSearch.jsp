<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    
   <div>
    <a href="${contextPath}">
     <img id="simpleLogo" class="simpleLogo" src="img/site/simpleLogo.png">
    </a>
    
    <form action="foresearch" method="post">
      <div class="simpleSearchDiv pull-right" style="margin-right:50px">
        <input type="text" placeholder="平衡车 原汁机" name="keyword" style="padding-left:3px" value="${param.keyword }">
        <button class="searchButton" type="button">搜天猫</button>
        <div class="searchBelow" style="margin-left:-5px">
          <c:forEach items="${cs}" var="c" varStatus="st">
            <c:if test="${st.count>=10 and st.count<=13}">
              <span >
                <a href="forecategory?cid=${c.id }" style="padding:0px 10px 0px 10px">
                  ${c.name}
                </a>
                <c:if test="${st.count!=13}">
                  <span>|</span>
                </c:if>
              </span>
            </c:if>
           </c:forEach>
         </div>
         </div>
         </form>
         <div style="clear:both"></div>
       </div>
              