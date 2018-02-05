<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>行为分析</title>
    <jsp:include flush="true" page="/WEB-INF/views/admin/common/head.jsp"></jsp:include>
    <link rel="stylesheet" href="/js/tool/charts/echarts.css">
    <script src="/js/tool/charts/echarts.min.js"></script>
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
                        <li class="active">行为分析</li>
                    </ol>
                    <%--<div class="alert alert-success J_tip" role="alert">刷新成功！</div>--%>
                    <input type="hidden" class="pageDataCount" value="${dataCount}">
                </div>
                <div class="col-sm-12 margin-top--10">
                    <form action="" class="form-horizontal J_searchForm">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="panel panel-info">
                                    <div class="panel-heading">
                                        <h4>信息选择</h4>
                                    </div>
                                    <div class="panel-body">
                                        <div class="row">
                                            <div class="col-sm-4">
                                                <div class="form-group">
                                                    <label class="control-label col-sm-4">性别</label>
                                                    <div class="col-sm-6">
                                                        <select name="gender" class="form-control">
                                                            <option value="">请选择</option>
                                                            <c:forEach var="clients1" items="${clients1}">
                                                                <option value="${clients1.gender}">${clients1.genderName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-sm-4">民族</label>
                                                    <div class="col-sm-6">
                                                        <select name="dicNation" class="form-control">
                                                            <option value="">请选择</option>
                                                            <c:forEach var="clients2" items="${clients2}">
                                                                <option value="${clients2.id}">${clients2.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-sm-4">医保类型</label>
                                                    <div class="col-sm-6">
                                                        <select name="dicMciType" class="form-control">
                                                            <option value="">请选择</option>
                                                            <c:forEach var="clients3" items="${clients3}">
                                                                <option value="${clients3.id}">${clients3.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-sm-4">用户类型</label>
                                                    <div class="col-sm-6">
                                                        <select name="userType" class="form-control">
                                                            <option value="">请选择</option>
                                                            <c:forEach var="clients4" items="${clients4}">
                                                                <option value="${clients4.id}">${clients4.name}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-sm-8">
                                                <div class="row">
                                                    <div class="col-sm-12 tag">
                                                        <div class="form-group">
                                                            <label class="control-label col-sm-2">标签类型</label>
                                                            <div class="col-sm-3">
                                                                <select name="labelTypeFirst" class="form-control tagType">
                                                                    <option value="">请选择</option>
                                                                    <c:forEach var="labelTypes1" items="${labelTypes}">
                                                                        <option value="${labelTypes1.id}">${labelTypes1.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <label class="control-label col-sm-2">标签项</label>
                                                            <div class="col-sm-3">
                                                                <select name="labelItemFirst" class="form-control tagItem">
                                                                    <option value="">请选择</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-12 tag">
                                                        <div class="form-group">
                                                            <label class="control-label col-sm-2">标签类型</label>
                                                            <div class="col-sm-3">
                                                                <select name="labelTypeSecond" class="form-control tagType">
                                                                    <option value="">请选择</option>
                                                                    <c:forEach var="labelTypes2" items="${labelTypes}">
                                                                        <option value="${labelTypes2.id}">${labelTypes2.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <label class="control-label col-sm-2">标签项</label>
                                                            <div class="col-sm-3">
                                                                <select name="labelItemSecond" class="form-control tagItem">
                                                                    <option value="">请选择</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <div class="col-sm-12 tag">
                                                        <div class="form-group">
                                                            <label class="control-label col-sm-2">标签类型</label>
                                                            <div class="col-sm-3">
                                                                <select name="labelTypeThird" class="form-control tagType">
                                                                    <option value="">请选择</option>
                                                                    <c:forEach var="labelTypes3" items="${labelTypes}">
                                                                        <option value="${labelTypes3.id}">${labelTypes3.name}</option>
                                                                    </c:forEach>
                                                                </select>
                                                            </div>
                                                            <label class="control-label col-sm-2">标签项</label>
                                                            <div class="col-sm-3">
                                                                <select name="labelItemThird" class="form-control tagItem">
                                                                    <option value="">请选择</option>
                                                                </select>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-12">
                                <div class="margin-bottom-10 margin-top--10 padding-left">
                                    <button class="btn btn-success btn-radius-no J_search" type="button"><i class="fa fa-search"></i>&nbsp;搜索</button>
                                    <a href="javascript:;" class="btn btn-success margin-left-10 btn-radius-no btn-green J_plan"><i class="fa fa-plus"></i>&nbsp;选择活动方案</a>
                                </div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>图表</h4>
                        </div>
                        <div class="panel-body">
                            <div class="col-sm-4">
                                <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
                                <div id="first" class="bg"></div>
                            </div>
                            <div class="col-sm-4">
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
                <div class="col-sm-12 margin-top--10">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>列表</h4>
                        </div>
                        <div class="panel-body">
                            <div class="table-responsive">
                                <table class="table table-hover table-bordered">
                                    <thead>
                                    <tr>
                                        <th><input type="checkbox" name="sleectAll" class="J_selectAll"></th>
                                        <th class="control-width width1">姓名</th>
                                        <th>身份证号码</th>
                                        <th>性别</th>
                                        <th>联系电话</th>
                                        <th>用户类型</th>
                                        <th>医保类型</th>
                                        <th>累计消费金额</th>
                                        <th>参与活动数</th>
                                        <th>操作</th>
                                    </tr>
                                    </thead>
                                    <tbody id="J_template"></tbody>
                                </table>
                            </div>
                            <div class="pull-left record">
                                共<span class="J_record"></span>条记录
                            </div>
                            <div class="pull-right">
                                <ul id="pageLimit"></ul>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- 操作失败对话框 -->
<div class="modal fade" id="modalDialog" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">选择活动方案</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label class="control-label col-sm-3">活动</label>
                        <div class="col-sm-6">
                            <select class="form-control col-sm-3 J_activity">
                                    <option value="-1">请选择</option>
                                <c:forEach var="event" items="${events}">
                                    <option value="${event.id}">${event.name}</option>>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <a href="javascript:;" class="btn btn-warning J_detail">详情</a>
                <button type="button" class="btn btn-success J_save">确定</button>
            </div>
        </div>
    </div>
</div>
<jsp:include flush="true" page="/WEB-INF/views/admin/common/footer.jsp"></jsp:include>
<script src="/js/portrayal-management/behavior-analysis/behavior-analysis-management-list.js"></script>
<!-- ECharts单文件引入 -->
<script src="http://echarts.baidu.com/build/dist/echarts.js"></script>
<script type="text/javascript">
    // 路径配置
    require.config({
        paths: {
            echarts: 'http://echarts.baidu.com/build/dist'
        }
    });

    // 使用患者类型占比
    require(
        [
            'echarts',
            'echarts/chart/pie' // 使用柱状图就加载line模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('first'));

            option = {
                title : {
                    text: '用户类型占比',
                    x:'center',
                    y:'bottom'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                calculable : true,
                series : [
                    {
                        name:'患者类型',
                        type:'pie',
                        radius : '55%',
                        center: ['50%', '45%'],
                        data:${jsonArrayPie}
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
            'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('second'));

            option = {
                title : {
                    text: '医保类型占比',
                    x:'center',
                    y: 'bottom'
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                calculable : true,
                series : [
                    // {
                    //     name:'半径模式',
                    //     type:'pie',
                    //     radius : [20, 110],
                    //     center : ['25%', 200],
                    //     roseType : 'radius',
                    //     width: '40%',       // for funnel
                    //     max: 40,            // for funnel
                    //     itemStyle : {
                    //         normal : {
                    //             label : {
                    //                 show : false
                    //             },
                    //             labelLine : {
                    //                 show : false
                    //             }
                    //         },
                    //         emphasis : {
                    //             label : {
                    //                 show : true
                    //             },
                    //             labelLine : {
                    //                 show : true
                    //             }
                    //         }
                    //     },
                    //     data:[
                    //         {value:10, name:'rose1'},
                    //         {value:5, name:'rose2'},
                    //         {value:15, name:'rose3'},
                    //         {value:25, name:'rose4'},
                    //         {value:20, name:'rose5'},
                    //         {value:35, name:'rose6'},
                    //         {value:30, name:'rose7'},
                    //         {value:40, name:'rose8'}
                    //     ]
                    // },
                    {
                        name:'面积模式',
                        type:'pie',
                        radius : [30, 110],
                        center : ['50%', '45%'],
                        roseType : 'area',
                        x: '50%',               // for funnel
                        max: 40,                // for funnel
                        sort : 'ascending',     // for funnel
                        data:${YBJsonArrayPie}
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
            'echarts/chart/pie' // 使用饼状图就加载pie模块，按需加载
        ],
        function (ec) {
            // 基于准备好的dom，初始化echarts图表
            var myChart = ec.init(document.getElementById('third'));

            option = {
                title : {
                    text: '患者行业占比',
                    x : 'center',
                    y : 'bottom',
                },
                tooltip : {
                    trigger: 'item',
                    formatter: "{a} <br/>{b} : {c} ({d}%)"
                },
                calculable : true,
                series : [
                    {
                        name:'访问来源',
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
                        data:${HYJsonArrayPie}
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