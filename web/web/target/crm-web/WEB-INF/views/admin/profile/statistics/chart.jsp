<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>统计功能</title>
    <link rel="stylesheet" href="/js/tool/charts/echarts.css">
    <script src="/js/tool/charts/echarts.min.js"></script>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
</head>
<body>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/header.jsp"></jsp:include>
<div class="page clearfix">
    <div class="holder">
        <div class="container">
            <div class="row">
                <div class="col-sm-12">
                    <ol class="breadcrumb">
                        <li>
                            <a href="/admin"><i class="fa fa-home"></i>&nbsp;首页</a>
                        </li>
                        <li>
                            <a href="/admin/profile/label/list">画像管理</a>
                        </li>
                        <li class="active">统计展示</li>
                    </ol>
                </div>
                <div class="col-sm-12 margin-top--10">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>统计图表</h4>
                        </div>
                        <div class="panel-body">
                            <div class="col-sm-12">
                                <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                                <div id="first" class="bg"></div>
                                <hr>
                            </div>
                            <div class="col-sm-8">
                                <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                                <div id="second" class="bg"></div>
                            </div>
                            <div class="col-sm-4">
                                <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                                <div id="third" class="bg"></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- ECharts单文件引入 -->
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">
    // 路径配置
    require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });

    // 使用
    require(
        [
            'echarts',
            'echarts/chart/line' // 使用折线图就加载line模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('first'));

            option = {
                title : {
                    text: '近一周每组会员消费信息分析',
                    x : 'center',
                    y : 'bottom',
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:${jsonArrayMemberGroup}
                },
//                toolbox: {
//                    show : true,
//                    feature : {
//                        mark : {show: true},
//                        dataView : {show: true, readOnly: false},
//                        magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
//                        restore : {show: true},
//                        saveAsImage : {show: true}
//                    }
//                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : ['周一','周二','周三','周四','周五','周六','周日']
                    }
                ],
                yAxis : [
                    {
                        type : 'value'
                    }
                ],
                series : ${jsonArray}
            };

            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );

    // 使用
    require(
        [
            'echarts',
            'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('second'));

            option = {
                title : {
                    text: '近半年会员患者及销售情况分析',
                    x : 'center',
                    y : 'bottom'
                },
                tooltip : {
                    trigger: 'axis',
                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                legend: {
                    data:['销售金额', '会员患者数量']
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
//                toolbox: {
//                    show : true,
//                    feature : {
//                        mark : {show: true},
//                        dataView : {show: true, readOnly: false},
//                        magicType : {show: true, type: ['line', 'bar']},
//                        restore : {show: true},
//                        saveAsImage : {show: true}
//                    }
//                },
                calculable : true,
                xAxis : [
                    {
                        type : 'category',
                        data : ${jsonMonth},
                        axisTick: {
                            alignWithLabel: true
                        }
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        name : '销售金额',
                        position : 'left'
                    },
                    {
                        type : 'value',
                        name : '会员患者数量',
                        position : 'right'
                    }
                ],
                series : [
                    {
                        name:'销售金额',
                        type:'bar',
                        data:${jsonCostAll},
                        yAxis: 1
                    },
                    {
                        name:'会员患者数量',
                        type:'line',
                        data:${jsonClientCount},
                        yAxisIndex: 1
                    }
                ]
            };


            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );

    // 使用
    require(
        [
            'echarts',
            'echarts/chart/pie' // 使用饼图就加载pie模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('third'));

            option = {
                title : {
                    text: '会员患者人员占比',
                    x : 'center',
                    y : 'bottom',
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                legend: {
                    orient : 'vertical',
                    x : 'left',
                    data:${typeName}
                },
//                toolbox: {
//                    show : true,
//                    feature : {
//                        mark : {show: true},
//                        dataView : {show: true, readOnly: false},
//                        magicType : {
//                            show: true,
//                            type: ['pie', 'funnel'],
//                            option: {
//                                funnel: {
//                                    x: '25%',
//                                    width: '50%',
//                                    funnelAlign: 'center',
//                                    max: 1548
//                                }
//                            }
//                        },
//                        restore : {show: true},
//                        saveAsImage : {show: true}
//                    }
//                },
                calculable : true,
                series : [
                    {
                        name:'会员占比',
                        type:'pie',
                        radius : ['50%', '70%'],
                        itemStyle : {
                            normal : {
                                label : {
                                    show : false
                                },
                                labelLine : {
                                    show : false
                                }
                            },
                            emphasis : {
                                label : {
                                    show : true,
                                    position : 'center',
                                    textStyle : {
                                        fontSize : '30',
                                        fontWeight : 'bold'
                                    }
                                }
                            }
                        },
                        data:${jsonArrayPie}
                    }
                ]
            };

            // 为echarts对象加载数据
            myChart.setOption(option);
        }
    );
</script>
</body>
</html>
