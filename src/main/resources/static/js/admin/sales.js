
$(document).ready(function(){
	
const pathArr = location.pathname.split("/");
const storeId = pathArr[pathArr.length-1];


const dateInput = document.getElementById("date");
dateInput.valueAsDate = new Date();



function sales(time,date){

	let title = "";
	let format = "";
	let dateArr = [];
	
	switch(time) {
		case "week": {
			dateArr = getDate(time);
			title = "이번 주";
			format = "MM월 DD일";
			break;
		} 
		case "month": {
			if(!date) {
				dateArr = getDate(time);
			} else {
				dateArr = getDate(time, date);
			}
			title = moment(dateArr[0]).format("M") + "월";
			format = "D";
			break;
		} 
		case "year": {
			dateArr = getDate(time);
			title = moment(dateArr[0]).format("YYYY") + "년";
			format = "MM월"
		}
	}
	const data = {
		time : time,
		month : date
	}
	
	$.ajax({
		url: "/admin/sales",
		type: "GET",
		data: data
	})
	.done(function(result) {
		console.log(result);
		let html = "";
		
		for(var i=0;i<dateArr.length;i++) {
			html +=  
			`<li>
				<div class="graph">
					<span class="sales"></span>
					<span class="graph_date">${moment(dateArr[i]).format(format) }</span>
				</div>
			</li>`
		}
		
		$(".graph_box ul").html(html);
		
		if(result == "") {
			$("main h1").text(title +" 총 합계 0원");
			return;
		}
		
		const total = result[result.length-1]["total"];
		
		$("main h1").text(title +" 총 합계 " + total.toLocaleString() + "원");
		
		const maxIndex = result.length -1;
		
		for(var i=0,j=0;i<dateArr.length;i++) {
			
			const dt = moment(dateArr[i]).format(format);
			const odt = moment(result[j]["orderDate"]).format(format);

			if(dt == odt ) {
				const sum = result[j]["total"];
				const avg = sum / total * 100;
				
				$(".graph_box li").eq(i).find(".graph").css("height", avg +"%");
				$(".graph_box li").eq(i).find(".sales").text((sum/10000).toFixed(1));
				j++;
				
				if(maxIndex <= j) {
					break;
				}
			}
		}
	})
	.fail(function(data, textStatus, errorThrown){
		swal("에러");
	})
}






function getDate(time, date){
		
	const d = new Date();
	
	const year = d.getFullYear();
	let month = d.getMonth() + 1;
	const dt = d.getDate();
	const day =  d.getDay();
	
	const dateArr = [];
	
	switch(time) {
		case "week": {
			let monday = dt-day+1;
			const lastDate = new Date(year, month, 0).getDate();
			for(var i=0,j=1;i<7;i++) {
				if(monday > lastDate) {
					month++;
					monday = 1;
				}
				if(month > 12) {
					month = 1;
				}
				let yoil = String(monday).padStart("2", 0);
				dateArr[i] = year + "-" + month + "-" + yoil;
				monday++; 
			}
			break;
		}
		
		case "month": {
			if(!date) {
				const lastDate = new Date(year, month, 0).getDate();
				for(var i=0;i<lastDate;i++) {
					dateArr[i] = year + "-" + month + "-" + (i+1);
				}
				break;
			} else {
				date = String(date);
				const arr = String(date).split("-");
				
				const lastDate = new Date(arr[0], arr[1], 0).getDate();
				for(var i=0;i<lastDate;i++) {
					dateArr[i] = year + "-" + arr[1] + "-" + (i+1);
				}
				break;
			}
		}
		case "year": {
			for(var i=0;i<12;i++) {
				dateArr[i] = year + "-" + (i+1) + "-" + 1;
			}
			break;
		}
	}
	
	return dateArr;
	
}



// 기본페이지 주 매출
sales("week");

// 월 매출
$(".month_btn").click(function(){
	sales("month");
})

// 다른 달 일 매출
$(".other_month_search").click(function(){
	const date = $("#date").val();
	sales("month", date);
})

// 이번주 매출
$(".week_btn").click(function(){
	sales("week");
})

// 올해 매출
$(".year_btn").click(function(){
	sales("year");
})



$(".today button").click(function(){
	$(".detail").fadeToggle(200);
})



$.ajax({
	url: "/admin/management/salesToday",
	type: "GET",
	data: {storeId : storeId}
})
.done(function(result){
	$("#today").text(result.total.toLocaleString() +"원");
	
	let html = "";
	for(i=0;i<result.menuList.length;i++) {
		const menu = result.menuList[i];
		const option = menu.optionName == null ? "" : "[" + menu.optionName + "]";
		
		html +=
			`<div>${menu.foodName}${option }</div>
			 <div>${menu.amount}</div>
			 <div>${menu.totalPrice.toLocaleString()}</div>`;
	}
	
	$(".sales_today_detail").append(html);
	
	
	
})
.fail(function(){
	
})




})


