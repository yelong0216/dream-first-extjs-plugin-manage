Coe.initialize();
Co.initialize();

var apiPrefix = "/extjs/plugin/manage";

Ext.onReady(function() {
	var API = {
		saveModelAndTableDTO : rootPath + apiPrefix + "/model/save",
		queryModelAndTableDTO : rootPath + apiPrefix + "/model/query",
		deleteModelAndTableDTO : rootPath + apiPrefix + "/model/delete",
		retrieveModelAndTableDTO : rootPath + apiPrefix + "/model/retrieve",
		
		saveFieldAndColumnDTO : rootPath + apiPrefix + "/model/field/save",
		queryFieldAndColumnDTO : rootPath + apiPrefix + "/model/field/query",
		deleteFieldAndColumnDTO : rootPath + apiPrefix + "/model/field/delete",
		retrieveFieldAndColumnDTO : rootPath + apiPrefix + "/model/field/retrieve"
	
	};
	
	//=========================字段列==========================

	//============================ Model =========================
	Co.defineModel("FieldAndColumnDTO", ["fieldName","fieldTypeName","fieldTypeSimpleName","column","selectColumn","select","extend","primaryKey","minLength","maxLength","allowBlank","allowNull","jdbcType","desc","columnName","transients"]);
	//============================ Store =========================
	var fieldAndColumnDTOGridStore = Co.gridStore("fieldAndColumnDTOGridStore", API.queryFieldAndColumnDTO, "FieldAndColumnDTO", {
		autoLoad : false,
		output : "fieldAndColumnDTOTbar",
		sorters : []
	});
		
	//============================ View =========================
	var fieldAndColumnDTOTbar = Co.toolbar("fieldAndColumnDTOTbar", ["->",{
			type : "@",
			handler : searchFieldAndColumnDTO,
			searchField : ["fieldName"],
			searchEmptyText : ["请输入fieldName..."]
		}
	]);
	
	var fieldAndColumnDTOColumns = [
		Co.gridRowNumberer(),
		{header : "fieldName", dataIndex : "fieldName", width : 150, hidden : false},
		{header : "column", dataIndex : "column", width : 100, hidden : false},
		{header : "fieldTypeSimpleName", dataIndex : "fieldTypeSimpleName", width : 150, hidden : false},
		{header : "fieldTypeName", dataIndex : "fieldTypeName", width : 100, hidden : true},
		{header : "columnName", dataIndex : "columnName", width : 100, hidden : false},
		{header : "desc", dataIndex : "desc", width : 100, hidden : true},
		
		{header : "maxLength", dataIndex : "maxLength", width : 90, hidden : false},
		{header : "allowNull", dataIndex : "allowNull", width : 80, hidden : false},
		{header : "primaryKey", dataIndex : "primaryKey", width : 90, hidden : false},
		{header : "extend", dataIndex : "extend", width : 80, hidden : false},
		{header : "transients", dataIndex : "transients", width : 80, hidden : false},
		
		{header : "selectColumn", dataIndex : "selectColumn", width : 62, hidden : true},
		{header : "select", dataIndex : "select", width : 62, hidden : true},
		{header : "minLength", dataIndex : "minLength", width : 62, hidden : true},
		{header : "allowBlank", dataIndex : "allowBlank", width : 62, hidden : true},
		{header : "jdbcType", dataIndex : "jdbcType", width : 62, hidden : true},
		
		
	];
	
	var fieldAndColumnDTOGrid = Co.grid("fieldAndColumnDTOGrid", fieldAndColumnDTOGridStore, fieldAndColumnDTOColumns, fieldAndColumnDTOTbar, null, {
		listeners : {
			itemdblclick : function(view, record) {
			}
		}
	});
	var fieldAndColumnGridWindow = Co.window("", fieldAndColumnDTOGrid, 1000, 500, "fit", {
	});
	//============================ Function =========================
	
	function searchFieldAndColumnDTO() {
		Co.load(fieldAndColumnDTOGridStore);
	}
	
	
	//============================ Model =========================
	Co.defineModel("ModelAndTableDTO", ["modelClassName","modelClassSimpleName","tableName","isView","tableAlias","tableDesc","selectSqlColumnMode"]);
	//============================ Store =========================
	var modelAndTableDTOGridStore = Co.gridStore("modelAndTableDTOGridStore", API.queryModelAndTableDTO, "ModelAndTableDTO", {
		autoLoad : false,
		output : "modelAndTableDTOTbar",
		sorters : [{
			property : "createTime",
			direction : "desc"
		}]
	});
		
	//============================ View =========================
	var modelAndTableDTOTbar = Co.toolbar("modelAndTableDTOTbar", [{
			type : "-",
			text : "查看字段列",
			handler : showFieldAndColumns,
			showAtContextMenu : true
		},{
			type : "*",
			handler : deleteModelAndTableDTO,
			showAtContextMenu : true
		},"->",{
			type : "@",
			handler : searchModelAndTableDTO,
			searchField : ["modelClassSimpleName"],
			searchEmptyText : ["请输入model名称..."]
		}
	]);
	
	var modelAndTableDTOColumns = [
		Co.gridRowNumberer(),
		{header : "modelClassSimpleName", dataIndex : "modelClassSimpleName", width : 200, hidden : false},
		{header : "tableName", dataIndex : "tableName", width : 200, hidden : false},
		{header : "tableAlias", dataIndex : "tableAlias", width : 150, hidden : false},
		{header : "tableDesc", dataIndex : "tableDesc", width : 200, hidden : false},
		{header : "selectSqlColumnMode", dataIndex : "selectSqlColumnMode", width : 142, hidden : false},
		{header : "isView", dataIndex : "isView", width : 80, hidden : false},
		{header : "modelClassName", dataIndex : "modelClassName", width : 300, hidden : true}
	];
	
	var modelAndTableDTOGrid = Co.grid("modelAndTableDTOGrid", modelAndTableDTOGridStore, modelAndTableDTOColumns, modelAndTableDTOTbar, null, {
		deleteId : "modelClassName",
		listeners : {
			itemdblclick : function(view, record) {
				showFieldAndColumns();
			}
		}
	});
	
	Co.load(modelAndTableDTOGridStore);
	
	Ext.create("Ext.container.Viewport", {
		layout : "fit",
		items : modelAndTableDTOGrid
	});
	//============================ Function =========================
	function deleteModelAndTableDTO() {
		Co.gridDelete(modelAndTableDTOGrid, API.deleteModelAndTableDTO, function(result){
			if (result.success === true) {
				Co.alert("删除成功！", function(){
					Co.reload(modelAndTableDTOGridStore);
				});
			} else {
				Co.alert(result.msg);
			}
		});	
	}
	
	function showFieldAndColumns(){
		var record = Co.getGridSelectSingleRecord(modelAndTableDTOGrid);
		if(record){
			Co.load(fieldAndColumnDTOGridStore,{"modelClassName":record.data.modelClassName});
			fieldAndColumnGridWindow.setTitle(record.data.modelClassSimpleName);
			fieldAndColumnGridWindow.show();
		}
	}
	
	function searchModelAndTableDTO() {
		Co.load(modelAndTableDTOGridStore);
	}
});