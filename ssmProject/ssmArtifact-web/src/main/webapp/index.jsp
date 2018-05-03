<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="extjs/ext-all-debug.js"></script>
    <script type="text/javascript" src="extjs/ext-all.js"></script>
    <script type="text/javascript" src="extjs/ext-bootstrap.js"></script>
    <script type="text/javascript" src="extjs/locale-zh_CN.js"></script>

    <link rel="stylesheet" type="text/css" href="extjs/theme-crisp/resources/theme-crisp-all.css" />
    <link rel="stylesheet" type="text/css" href="extjs/theme-crisp/resources/theme-crisp-all-debug.css" />

    <script type="text/javascript" src="index.js"></script>


    <script type="text/javascript" >
        Ext.onReady(function(){
            extend: 'Ext.toolbar.Toolbar',
                alias:'widget.maintop',
                style:'background-color:rgba(68,70,77,0.8);',

                items:[{
                xtype: 'image',
                bind: { // 数据绑定到MainModel中data的system.logo
                    hidden: '{!systerm.logo}', // 如果system.logo未设置，则此image不显示
                    src: '{systerm.logo}' // 根据system.iconUrl的设置来加载图片
                }
            }, {
                xtype: 'label',
                bind: {
                    text: '{system.name}' // text值绑定到system.name
                },
                style: 'font-size:20px;color:#FFF;'
            }, {
                xtype: 'label',
                style: 'color:#FFF;',
                bind: {
                    text: '({system.version})'
                }
            }, '->', {
                xtype:'button',
                bind:{
                    iconCls:'x-fa fa-user', //图标
                    text:'<span style="font-size: 14px;">{userInfo.userName}</span>'
                },
                menu:[{  //按钮下拉框
                    text:'注销',
                    iconCls:'x-fa fa-power-off',
                    handler: 'onLoginOutClick'
                }],
                style:'border:none;background-color:rgba(255,255,255,0.6);background-image:none;'
            }],
        })
    </script>
</head>
<body>



</body>
</html>
