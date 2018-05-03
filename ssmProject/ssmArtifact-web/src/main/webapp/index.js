extend: 'Ext.container.Viewport',
    layout : 'border',
    defaults:{
    collapsible: true,
        split: true
},
items : [{
    xtype : 'maintop',
    region : 'north' ,// 把他放在最顶上
    collapsible: false,
    split: false,
    height:50
},{
    xtype : 'mainleft',
    region : 'west', // 左边面板
    title : '导航菜单',
    width : 220,
    hidden : false,

}, {
    region : 'center', // 中间面版
    xtype : 'maincenter',
    collapsible: false
}]