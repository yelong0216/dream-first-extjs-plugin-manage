Co.initialize();
Coe.initialize();

var apiPrefix = "/extjs/plugin/manage";

Ext.onReady(function() {
	var API = {
			queryInterface : rootPath + apiPrefix + "/interfaces/query",
			
			interfaceTest : rootPath +  + "/interfaces/interfaceTest"
	}; 
	
	//============================ Model =========================
	Co.defineModel("Interface", ["name","patternsCondition","beanClass","methodName","methodsCondition","beanClassSimpleName"]);
	//============================ Store =========================
	var interfaceGridStore = Co.gridStore("interfaceGridStore", API.queryInterface, "Interface", {
		autoLoad : false,
		pageSize : Co.maxInt,
		output : "interfaceTbar"
	});

	//============================ View =========================
	var interfaceTbar = Co.toolbar("interfaceTbar", [{
		type : "*",
		text : "接口测试",
		handler : testInterface,
		showAtContextMenu : true
	},"->",{
		type : "@",
		handler : searchInterface,
		searchField : ["name","methodsCondition","patternsCondition"],
		searchEmptyText : ["请输入name...","请输入method...","请输入patternsCondition..."]
	}
	]);

	var interfaceColumns = [
		Co.gridRowNumberer(),
		{header : "name", dataIndex : "name", width : 250, hidden : false},
		{header : "patternsCondition", dataIndex : "patternsCondition", width : 250, hidden : false},
//		{header : "beanClass", dataIndex : "beanClass", width : 250, hidden : false},
		{header : "beanClassSimpleName", dataIndex : "beanClassSimpleName", width : 200, hidden : false},
		{header : "methodName", dataIndex : "methodName", width : 250, hidden : false},
		{header : "methodsCondition", dataIndex : "methodsCondition", width : 250, hidden : false}
		];

	var interfaceGrid = Co.grid("interfaceGrid", interfaceGridStore, interfaceColumns, interfaceTbar, null, {
		listeners : {
			itemdblclick : function(view, record) {
				testInterface();
			}
		}
	});

	Co.load(interfaceGridStore);
	
	Ext.create("Ext.container.Viewport", {
		layout : "fit",
		items :interfaceGrid
	});
	
	//============================ Function =========================
	
	function searchInterface() {
		Co.load(interfaceGridStore);
	}
	
	
	var interfaceTestForm = Co.form(API.interfaceTest, [{
		xtype : "displayfield",
		id : "name",
		fieldLabel : "name"
	},{
		xtype : "textfield",
		id : "url",
		fieldLabel : "请求URL",
		allowBlank : false,
		blankText : "请求URL不能为空",
		editable : true,
		readOnly : false,
		maxLength: 64,
		maxLengthText: "请求URL最多不能超过64字符",
		enforceMaxLength: true
	},{
		xtype : "textfield",
		id : "method",
		fieldLabel : "method",
		allowBlank : true,
		blankText : "methodName不能为空",
		editable : true,
		readOnly : false,
		maxLength: 64,
		emptyText : "GET",
		maxLengthText: "methodName最多不能超过64字符",
		enforceMaxLength: true
	},{
		xtype : "textarea",
		id : "params",
		fieldLabel : "请求参数",
		height : 200,
		allowBlank : true,
		editable : true,
		readOnly : false,
		hideTrigger : true,
		maxLength: 1024,
		maxLengthText: "textarea最多不能超过1024字符",
	}]);

	var interfaceTestFormWindow = Co.formWindow("接口测试", interfaceTestForm, 600, 400, "fit", {
		okHandler : doTestInterface
	});
	
	/**
	 * 接口测试
	 */
	function testInterface(){
		var record = Co.getGridSelectSingleRecord(interfaceGrid);
		if(record){
			Co.resetForm(interfaceTestForm, true);
			Ext.getCmp("name").setValue(record.data.name);
			Ext.getCmp("url").setValue(record.data.patternsCondition);
			Ext.getCmp("method").setValue(record.data.methodsCondition);
			interfaceTestFormWindow.show();
		}
	}
	
	/**
	 * 进行接口测试
	 */
	function doTestInterface(){
		var url = Ext.getCmp("url").getValue();
		var method = Ext.getCmp("method").getValue();
		var params = Ext.getCmp("params").getValue();
		window.open(API.interfaceTest+"?model.url="+url+"&method="+method+"&params="+escape(params))
	}

});
