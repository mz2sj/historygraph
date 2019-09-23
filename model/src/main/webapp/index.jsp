<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Echarts 关系图</title>
	<% 
	pageContext.setAttribute("APP_PATH", request.getContextPath());
	%>
    <!-- 引入 echarts.js -->
    <script type="text/javascript" src="${APP_PATH }/static/js/echarts.js"></script>
    <!-- 引入jquery.js -->
    <script type="text/javascript" src="${APP_PATH }/static/js/jquery-3.2.1.min.js"></script>
    <!--  引入bootstrap -->
    <script type="text/javascript" src="${APP_PATH }/static/bootstrap-3.3.7-dist/js/bootstrap.js"></script>
    <link href="${APP_PATH }/static/bootstrap-3.3.7-dist/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<div class="row">
	<div class="col-md-3 col-md-offset-2">
		<h2>Echarts关系图</h2>
	</div>	
	<div class="col-md-3 ">
		<input type="text"  id="searchNodeName" style="margin-top:15px" class="form-control" placeholder="请输入您要查找的内容">
	</div>	
	<div class="col-md-2 ">
		<button type="button" class="btn btn-primary" id="searchBtn" style="margin-top:15px">查询</button>
	</div>	
</div>
<div class="row">
	<div class="col-md-12  col-md-offset-2">
		<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
		<div id="main" style="width: 1200px;height:700px;"></div>
	</div>
</div>

<script type="text/javascript">
	var globaldata;
	$("#searchBtn").click(function(){
		var searchNodeName=$("#searchNodeName").val();
		$.ajax({
			url:"${APP_PATH}/search/",
			type:"GET",
			data:"searchNodeName="+searchNodeName,
			success:function(result){
				graphData=result;
				globaldata=result;
				console.log(result);
				initecharts(result);
			}
		});
	});
	function initecharts(graphdata){		
		//alert(graphData.extend.category[0].name);
		//图例数据
		var legenddata=[];
		graphdata.extend.category.forEach(function constructData(element, index) {
		    legenddata.push(element.name);
		});
		
		//catagory 数据
		var categorydata=[];
		graphdata.extend.category.forEach(function constructData(element, index) {
		    categorydata.push({'name':element.name});
		});
		
		//node 数据
		var nodedata=[];
		graphdata.extend.node.forEach(function constructData(element, index) {
			if(element.categories==1){
				nodedata.push({'name':element.name,'category':element.categories-1,symbolSize:130});
			}
			else if(element.categories==2){
				nodedata.push({'name':element.name,'category':element.categories-1,symbolSize:100});
			}
			else if(element.categories==3){
				nodedata.push({'name':element.name,'category':element.categories-1,symbolSize:100});
			}
			else if(element.categories==4){
				nodedata.push({'name':element.name,'category':element.categories-1,symbolSize:100});
			}
		});
		
		//link数据
		var linkdata=[];
		graphdata.extend.link.forEach(function constructData(link) {
		    sourceid=link.source;
		    targetid=link.target;
		    graphdata.extend.node.forEach(function matchNode(node){
		    	if(node.id==sourceid){
		    		sourcename=node.name;
		    	}
		    	if(node.id==targetid){
		    		targetname=node.name;
		    	}
		    });
		    linkdata.push({'source':sourcename,'target':targetname});
		    
		});
		
		//检查echartDom对象是否已经存在
		if (document.getElementById('main') == null) {
	        return
	      }
	    echarts.dispose(document.getElementById('main'));
	    
		var myChart=echarts.init(document.getElementById('main'));
		option={
				color:['#f69314', '#a2a8d3', '#5AB1EF', '#acc6aa'],
				textStyle: {
					color: '#000',
					fontSize: 35,
				},
				animationDuration: 1500,
				animationEasingUpdate: 'quinticInOut',
				legend: { 
					orient:'vertical',
					left: 'left', 
					top: 'middle',
					itemGap:20,
					padding:30,
					itemWidth:55,
					itemHeight:30,
					textStyle:{
						fontSize:35
					},
					data:legenddata//此处的数据必须和关系网类别中name相对应  ['事件','人物','地点','时间']
				},  
				title: { 
					text: '睢水之战关系图谱',
					left:'0',
					top:'5%',
					textStyle:{
						fontSize:40
					}
				},
	            tooltip: {
	                formatter: function (x) {
	                    return x.data.des;
	                },
					
	            },
	            series: [
	                {
	                    type: 'graph',
	                    layout: 'force',
	                   
	                    roam: true,
	                    edgeSymbol: ['circle', 'circle'],
	                    edgeSymbolSize: [4, 4],
						
						focusNodeAdjacency: true,
	                    force : { //力引导图基本配置
	                        //initLayout: ,//力引导的初始化布局，默认使用xy轴的标点
	                        repulsion : 1800,//节点之间的斥力因子。支持数组表达斥力范围，值越大斥力越大。
	                        gravity : 0.1,//节点受到的向中心的引力因子。该值越大节点越往中心点靠拢。
	                        edgeLength :[10, 10],//边的两个节点之间的距离，这个距离也会受 repulsion。[10, 50] 。值越小则长度越长
	           
	                    //因为力引导布局会在多次迭代后才会稳定，这个参数决定是否显示布局的迭代动画，在浏览器端节点数据较多（>100）的时候不建议关闭，布局过程会造成浏览器假死。                        
	                    },
	                    draggable: true,
						edgeLabel: {
							show: false,
						},
						itemStyle: {
						opacity: 0.8,
						borderColor: '#fff',
						borderWidth: 4,
						shadowBlur: 6,
						shadowColor: 'rgba(0, 0, 0, 0.5)',
						 },
						lineStyle: {
							color: 'target',
							opacity: 0.8,
							width: 5,
							shadowColor: 'rgba(0, 0, 0, 0.5)',
							shadowBlur: 2,
							curveness: 0,
						},       
						label: {
							show: true,
							width: 60,
							height: 60,
						},
						edgeLabel: {
							show: false,
						},
						itemStyle: {
							opacity: 0.8,
							borderColor: '#fff',
							borderWidth: 1,
							shadowBlur: 2,
							shadowColor: 'rgba(0, 0, 0, 0.5)',
						},
						"categories": categorydata,
							/* [//关系网类别，可以写多组  
							{  
								"name": "事件",//关系网名称  
								
							},
								{  
								"name": "人物",//关系网名称  
								
							},
								{  
								"name": "地点",//关系网名称  
								
							},
								{  
								"name": "时间",//关系网名称  
							
							}
						],  */

	                    data:nodedata,
	                    	/* [
							{
	                            name: '人物',
	                            des: '',
	                            symbolSize: 140,
	                            
								category: 1
	                        },{
	                            name: '地点',
	                            des: '',
	                            symbolSize: 140,
	                            
								category: 2
	                        },{
	                            name: '时间',
	                            des: '',
	                            symbolSize: 140,
	                           
								category: 3
	                        },
							{
	                            name: '睢水之战',
	                            des: '',                           
								category: 0,
								symbolSize: 170,
	                        }, {
	                            name: '彭城（今江苏徐州）',
	                            des: '',
	                            symbolSize: 100,
								
								category: 2
	                        },  {
	                            name: '项羽',
	                            des: '',
	                            symbolSize: 100,
								
								category: 1
	                        }, {
	                            name: '刘邦',
	                            des: '',
	                            symbolSize: 100,
								
								category: 1
	                        }, {
	                            name: '公元前205年',
	                            des: '',
	                            symbolSize: 100,
								
								category: 3
	                        }
	                    ], */
	                    links:linkdata
	                    /* [
	                        {
	                            source: '睢水之战',
	                            target: '地点',
	                            name: '',
	                            des: '睢水之战发生地点',
							
							
	                        }, {
	                            source: '地点',
	                            target: '彭城（今江苏徐州）',
								name: '',
							
	                           
	                        },{
	                            source: '睢水之战',
	                            target: '时间',
								name: '',
							
	                           
	                        }, {
	                            source: '时间',
	                            target: '公元前205年',
								name: '',
							
	                           
	                        }, {
	                            source: '睢水之战',
	                            target: '人物',
								name: '',
							
	                           
	                        }, {
	                            source: '人物',
	                            target: '项羽',
								name: '',
							
	                           
	                        }, {
	                            source: '人物',
	                            target: '刘邦',
								name: '',
							
	                           
	                        }
							
	                       ] */
	                }
	            ]
	        };
	        myChart.setOption(option);
		}
		
	
</script>
</body>
</html>
<!-- 样例数据
{code: 100, msg: "处理成功", extend: {…}}
code: 100
extend:
category: Array(4)
0: {id: 1, name: "事件"}
1: {id: 2, name: "时间"}
2: {id: 3, name: "地点"}
3: {id: 4, name: "人物"}
length: 4
__proto__: Array(0)
link: Array(1)
0: {id: 4, source: "5", target: "1"}
length: 1
__proto__: Array(0)
node: Array(5)
0: {id: 1, name: "睢水之战", categories: "1"}
1: {id: 2, name: "公元前205年", categories: "2"}
2: {id: 3, name: "彭城", categories: "3"}
3: {id: 4, name: "项羽", categories: "4"}
4: {id: 5, name: "刘邦", categories: "4"} 
-->