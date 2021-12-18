<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/view/include/link.jsp" %>
<link rel="stylesheet" href="/css/admin/sales.css" >
<script src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.29.1/moment.min.js" integrity="sha512-qTXRIMyZIFb8iQcfjXWCO8+M5Tbc38Qi5WzdPOYZHIlZpzBHG3L3by84BBBOiRGiEb7KKtAOAs5qYdUiZiQNNQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<%@ include file="/WEB-INF/view/include/header.jsp" %>
  
  
    
	<main>
		<section>
  			<div class="today">
  				<span>
  					<span>오늘 매출</span>
  					<span id="today"></span>
  				</span>
  				
  				<button>상세보기</button>
  				
  			</div>
  			
  		</section>
  		
		<section class="detail">
  			<div>
  				<h3 class="sales_today_detail_title">매출 상세</h3>
  				<div class="sales_today_detail">
  					<div>메뉴</div>
  					<div>수량</div>
  					<div>가격</div>
  				</div>
  			</div>
  		</section>
	
	
		<section>
			<div class="box">
				<button class="year_btn">연 매출</button>
				<button class="month_btn">월 매출</button>
				<button class="week_btn">주간 매출</button>
				<input type="month"name="date" id="date">
				<button class="other_month_search">검색</button>
			
				<h1>1월 총 합계 </h1>
				
				<div>(단위 : 만원)</div>
				<div class="graph_box">
					<ul>
						<li>
							<div class="graph">
								<span class="sales"></span>
								<span class="graph_date"></span>
							</div>
						</li>
						
					</ul>
				
					<div class="graph_background">
						<div></div>
						<div></div>
						<div></div>
						<div></div>
						<div></div>
					</div>
				</div>
			
			</div>
		</section>
	</main>

<script src="/js/util/util.js"></script>
<script src="/js/admin/sales.js"></script> 


</body>
</html>