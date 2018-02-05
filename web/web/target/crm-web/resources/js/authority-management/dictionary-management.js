$(function(){
    var setting = {
        view: {
            addHoverDom: addHoverDom,
            removeHoverDom: removeHoverDom,
            selectedMulti: false
        },
        edit: {
            enable: true,
            editNameSelectAll: true,
        },
        data: {//用pId来标识父子节点的关系
            simpleData: {
                enable: true,
                idKey: "id",
                pIdKey: "pId",
                rootPId: 0
            }
        },
        callback: {
            beforeDrag: beforeDrag,
            beforeEditName: beforeEditName,
            beforeRemove: beforeRemove,
            beforeRename: beforeRename,
            onRemove: onRemove,
            onRename: onRename
        }
    };
    function filter(treeId, parentNode, childNodes) {
        if (!childNodes) return null;
        for (var i=0, l=childNodes.length; i<l; i++) {
            childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
        }
        return childNodes;
    }
    //初始化加载节点
    var zNodes;
    Inint();
    function Inint() {
        $.ajax({
            type: "get",
            url:"/admin/party/dictionary/ajax/list",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: {id:1},     //JSON.stringify
            dataType: "json",
            async: false,
            success: function (data) {
                zNodes = data;
            }
        });
    };
    var log, className = "dark";
    //禁止拖拽事件
    function beforeDrag(treeId, treeNodes) {
        return false;
    };
    // 编辑前提示
    function beforeEditName(treeId, treeNode) {
        className = (className === "dark" ? "":"dark");
        showLog("[ "+getTime()+" beforeEditName ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.selectNode(treeNode);
        setTimeout(function() {
            if (confirm("进入节点 -- " + treeNode.name + " 的编辑状态吗？")) {
                setTimeout(function() {
                    zTree.editName(treeNode);
                }, 0);
            }
        }, 0);
        return false;
    };
    // 删除前提示
    function beforeRemove(treeId, treeNode) {
        className = (className === "dark" ? "":"dark");
        showLog("[ "+getTime()+" beforeRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.selectNode(treeNode);
        return confirm("确认删除 节点 - " + treeNode.name + " -吗？");
    };
    // 删除后操作
    function onRemove(e, treeId, treeNode) {
        showLog("[ "+getTime()+" onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name);
        var data = {
            id: treeNode.id,
            pId: treeNode.pId,
            name: treeNode.name
        };
        $.ajax({
            type: "post",
            url: "/admin/party/dictionary/ajax/del",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            async: false,
            success: function (rs) {
                if( rs.code == 0){
                }else{
                    Confirm("提示信息",rs.errMsg, function(){location.reload();});
                }
            },
            error: function (message) {
                Alert("提示信息", message);
            }
        });
    };
    // 重命名结束后操作
    function onRename(e, treeId, treeNode, isCancel) {
        showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" onRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
        var data = {
            id: treeNode.id,
            pId: treeNode.pId,
            name: treeNode.name
        };
        $.ajax({
            type: "get",
            url:"/admin/party/dictionary/ajax/update",
            contentType: "application/x-www-form-urlencoded; charset=utf-8",
            data: data,     //JSON.stringify
            dataType: "json",
            async: false,
            success: function (rs) {
                if( rs.code == 0){
                    Alert("提示信息！","操作成功")
                }else{
                    Alert("提示信息！",rs.errMsg)
                }
            },
            error: function (message) {
                alert(message);
            }
        });
    };
    // 重命名不能为空
    function beforeRename(treeId, treeNode, newName, isCancel) {
        className = (className === "dark" ? "":"dark");
        showLog((isCancel ? "<span style='color:red'>":"") + "[ "+getTime()+" beforeRename ]&nbsp;&nbsp;&nbsp;&nbsp; " + treeNode.name + (isCancel ? "</span>":""));
        if (newName.length == 0) {
            setTimeout(function() {
                var zTree = $.fn.zTree.getZTreeObj("treeDemo");
                zTree.cancelEditName();
                alert("节点名称不能为空.");
            }, 0);
            return false;
        }
        return true;
    };
    function showLog(str) {
        if (!log) log = $("#log");
        log.append("<li class='"+className+"'>"+str+"</li>");
        if(log.children("li").length > 8) {
            log.get(0).removeChild(log.children("li")[0]);
        }
    };
    // 获取时间
    function getTime() {
        var now= new Date(),
            h=now.getHours(),
            m=now.getMinutes(),
            s=now.getSeconds(),
            ms=now.getMilliseconds();
        return (h+":"+m+":"+s+ " " +ms);
    };
    // 添加节点
    var newCount = 1;
    function addHoverDom(treeId, treeNode) {
        var sObj = $("#" + treeNode.tId + "_span");//获取节点信息
        if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
        //定义添加按钮
        var addStr = "<span class='button add' id='addBtn_" + treeNode.tId+ "' title='add node' onfocus='this.blur();'></span>";
        sObj.after(addStr);
        var btn = $("#addBtn_"+treeNode.tId);
        //绑定添加事件，并定义添加操作
        if (btn){
            btn.bind("click", function(){
                var name = "new node" + (newCount++),
                    zTree = $.fn.zTree.getZTreeObj("treeDemo");
                $.ajax({
                    type: "get",
                    url: "/admin/party/dictionary/ajax/new",
                    contentType: "application/x-www-form-urlencoded; charset=utf-8",
                    data: {pId: treeNode.id, name:name},     //JSON.stringify
                    dataType: "json",
                    async: false,
                    success: function (rs) {
                        if(rs.code == 0){
                            var newID = rs.id;
                            childNode = zTree.addNodes(treeNode, {id:newID, pId:treeNode.id, name:name});
                            zTree.editName(childNode[0]);
                        }else{
                            $('#errorDialog').modal(rs.errMSg);
                        }
                    },
                    error: function (message) {
                        alert(message);
                    }
                });
            });
        }
    };
    function removeHoverDom(treeId, treeNode) {
        $("#addBtn_"+treeNode.tId).unbind().remove();
    };
    function selectAll() {
        var zTree = $.fn.zTree.getZTreeObj("treeDemo");
        zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
    };
    $(document).ready(function(){
        $.fn.zTree.init($("#treeDemo"), setting, zNodes);
        $("#selectAll").bind("click", selectAll);
    });
})