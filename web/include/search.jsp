<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

    <a href="${contextPath}">
     <img id="logo" src="img/site/logo.gif" class="logo">
    </a>
    
    <form action="foresearch" method="post">
      <div class="searchDiv">
        <input name="keyword" type="text" placeholder="搜索 天猫 商品/品牌/店铺" style="padding-left:5px" value="${param.keyword }">
        <button type="submit" class="searchButton">搜索</button>
        <div class="searchBelow">
          <c:forEach items="${cs}" var="c" varStatus="st">
           <c:if test="${st.count>=6 and st.count<=9}">
            <span>
              <a href="forecategory?cid=${c.id}">
                ${c.name}
              </a>
              <c:if test="${st.count!=9}">
               <span>|</span>
              </c:if>
            </span>
           </c:if>
          </c:forEach>
          </div>
          </div>
          </form>