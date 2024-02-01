<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<script src="https://d3js.org/d3.v4.js"></script>
<script type="text/javascript" src="http://code.jquery.com/jquery-3.5.1.min.js"></script>
<title>Image Match</title>
<style>
#nav{
width:30%; height:100px;
float:left;/*레이아웃을 왼쪽에 배치*/
}
#content{
width:70%; height:100px; float:right;/*레이아웃을 오른쪽에 배치*/
}
</style>
</head>
<body>
<div id="navd">
	<H1>소스 Image</H1>
	<img src ='http://localhost:8080/img/Samsung_Orig_Wordmark_BLUE_RGB.jpg'/>
</div>
<div id="nav">
	<H1>유사도 Chart</H1>
	<div id="my_dataviz"></div>
</div>
<div id="content">
	<H1>Image List</H1>
	<div id="images"></div>
</div>
</body>
<script>

// set the dimensions and margins of the graph
var margin = {top: 10, right: 30, bottom: 30, left: 60},
    width = 460 - margin.left - margin.right,
    height = 400 - margin.top - margin.bottom;

// append the svg object to the body of the page
var svg = d3.select("#my_dataviz")
  .append("svg")
    .attr("width", width + margin.left + margin.right)
    .attr("height", height + margin.top + margin.bottom)
  .append("g")
    .attr("transform",
          "translate(" + margin.left + "," + margin.top + ")");
$(document).ready(function(){
	let randomTargetList = [];
	for(let i = 0 ; i < 30 ; i++ ){
		randomTargetList.push('https://source.unsplash.com/featured/300x202?sig='+i);
	}
 	let sampleList = [
			"http://localhost:8080/img/samlogo1small.png",
			"http://localhost:8080/img/samlogo1.png",
			"http://localhost:8080/img/samlogo1big.png",
			"http://localhost:8080/img/samlogo2.png",
			"http://localhost:8080/img/samlogo2small.png",
			"http://localhost:8080/img/Samsung_Orig_Wordmark_BLACK_RGB.jpg",
			"http://localhost:8080/img/Samsung_Orig_Wordmark_WHITE_RGB.jpg",
			"http://localhost:8080/img/Samsung_Orig_Wordmark_BLUE_RGB.jpg"
			
	]
	const targetList = [...sampleList,...randomTargetList];
	$.ajax({
		type:'post',   //post 방식으로 전송
		url:'/sampleData',   //데이터를 주고받을 파일 주소
	   	headers: { 
	        'Accept': 'application/json',
	        'Content-Type': 'application/json' 
	    },
		data:JSON.stringify({	
			"source":"http://localhost:8080/img/Samsung_Orig_Wordmark_BLUE_RGB.jpg",
			"targetList":targetList
		}),   //위의 변수에 담긴 데이터를 전송해준다.
		datatype: "JSON",
		success : function(data){   //파일 주고받기가 성공했을 경우. data 변수 안에 값을 담아온다.
			console.log(data)
			var img= "";
			$.each(data.result, function () {
			 img += '<img src= "' + this.target+ '" width ="100">';
			});
			$('#images').append(img);
		  	// Add X axis
		  	var x = d3.scaleLinear()
			    .domain([0, data.result.length])
			    .range([ 0, width ]);
		  	svg.append("g")
			    .attr("transform", "translate(0," + height + ")")
			    .call(d3.axisBottom(x));
		  	// Add Y axis
		  	var y = d3.scaleLinear()
			    .domain([0, 100])
			    .range([ height, 0]);
		  	svg.append("g")
			    .call(d3.axisLeft(y));
		  	// Add dots
		  	svg.append('g')
			    .selectAll("dot")
			    .data(data.result)
			    .enter()
			    .append("circle")
			      .attr("cx", function (d) { return x(d.targetIndex); } )
			      .attr("cy", function (d) { return y(d.compareMethod_0*100); } )
			      .attr("r", 4)
			      .style("fill", function (d) { console.log( d ); return d.compareMethod_0 === 1 ?  'red' : "#69b3a2" })

		}
	});
});
</script>
</html>