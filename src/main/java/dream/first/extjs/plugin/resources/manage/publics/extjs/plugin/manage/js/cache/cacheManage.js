Co.initialize();
Coe.initialize();

var apiPrefix = "/extjs/plugin/manage";

Ext.onReady(function() {
	var API = {
			queryCache : rootPath + apiPrefix + "/cache/query",
			deleteCache : rootPath + apiPrefix + "/cache/delete",
			getCacheValueHtml : rootPath + apiPrefix + "/cache/getCacheValueHtml",
			
			getCacheManagerFactoryTree : rootPath + apiPrefix + "/cache/getCacheManagerFactoryTree",
			
			getCacheManagerTree :rootPath + apiPrefix + "/cache/getCacheManagerTree"
	}; 

	var selectedCacheManagerFactory;
	
	var selectedCacheManager;
	var selectedCacheManagerText;
	
	
	var firstLoadCacheManagerFactory = true;
	
	//定义左侧树Store
	var cacheManagerFactoryStore = Co.treeStore("cacheManagerFactoryStore", API.getCacheManagerFactoryTree, {
		listeners : {
			load : function(){
				var record = this.getRootNode().getChildAt(0);
				var ss = cacheManagerFactoryPanel.getSelectionModel();
				ss.select(record);
			}
		}
	});
	
	//定义左侧树
	var cacheManagerFactoryPanel = Co.tree("cacheManagerFactoryPanel", cacheManagerFactoryStore, {
		width : 150,
		listeners : {
			selectionchange : function(me, rs, eOpts) {
				var record = rs[0];
				if (record) {
					selectedCacheManagerFactory = record.data.id;
					gridPanel.setTitle("已选中【" + selectedCacheManagerFactory + "】");
					if(firstLoadCacheManagerFactory == true){
						cacheManagerTreeStore.load({
							params : {"cacheManagerFactory" : selectedCacheManagerFactory},
							callback : function() {
								cacheManagerTreePanel.getRootNode().expand(false);
								cacheManagerTreePanel.doLayout();
								cacheManagerTreePanel.getRootNode().expand(true);
							}
						});
						firstLoadCacheManagerFactory = false;
					} else {
						Co.reload(cacheManagerTreeStore,{"cacheManagerFactory" : selectedCacheManagerFactory});
					}
				}
			}
		}
	}); 
	
	var cacheManagerTreeStore = Co.treeStore("cacheManagerTreeStore", API.getCacheManagerTree,{
		autoLoad : false,
		listeners : {
			load : function(){
				var record = this.getRootNode().getChildAt(0);
				if(record){
					var ss = cacheManagerTreePanel.getSelectionModel();
					ss.select(record);
				} else {
					Co.load(cacheGridStore, {"cacheManagerFactory" : selectedCacheManagerFactory,"cacheManager" : null});
				}
			}
		}
	});
	
	var cacheManagerTreePanel = Co.tree("cacheManagerTreePanel", cacheManagerTreeStore, {
		width : 250,
		listeners : {
			selectionchange : function(me, rs, eOpts) {
				var cacheManager = rs[0];
				if (cacheManager) {
					selectedCacheManager = cacheManager.data.id;
					selectedCacheManagerText = cacheManager.data.text;
					gridPanel.setTitle("已选中【" + selectedCacheManagerText + "】");
					Co.load(cacheGridStore, {"cacheManagerFactory" : selectedCacheManagerFactory,"cacheManager" : selectedCacheManager});
				}
			}
		}
	}); 
	
	//============================ Model =========================
	Co.defineModel("Cache", ["key","value"]);
	//============================ Store =========================
	var cacheGridStore = Co.gridStore("cacheGridStore", API.queryCache, "Cache", {
		pageSize : Co.maxInt,
		autoLoad : false,
		output : "cacheTbar",
		sorters : [{
			property : "createTime",
			direction : "desc"
		}]
	});

	//============================ View =========================
	var cacheTbar = Co.toolbar("cacheTbar", [{
		type : "-", 
		text : "查看VALUE",
		handler : function(){
			var record = Co.getGridSelectSingleRecord(cacheGrid);
			if(record){
				window.open(API.getCacheValueHtml+"?cacheManagerFactory="+selectedCacheManagerFactory+
						"&cacheManager="+selectedCacheManager+
						"&key="+record.data.key);
			}
		},
		showAtContextMenu : true
	},{
		type : "*",
		handler : deleteCache,
		showAtContextMenu : true
	},"->",{
		type : "@",
		handler : searchCache,
		searchField : ["key"],
		searchEmptyText : ["请输入KEY..."]
	}
	]);

	var cacheColumns = [
		Co.gridRowNumberer(),
		{header : "KEY", dataIndex : "key", width : 250, hidden : false},
		{header : "VALUE", dataIndex : "value", width : 250, hidden : false}
		];

	var cacheGrid = Co.grid("cacheGrid", cacheGridStore, cacheColumns, cacheTbar, null, {
		deleteId : "key"
	});

//	Co.load(cacheGridStore);
	
	var gridPanel = Ext.create("Ext.panel.Panel",{
		title : "缓存",
		layout : "fit",
		items : cacheGrid
	});

	var leftPanel = Ext.create("Ext.panel.Panel",{
		layout : "border",
		items : [{
			region : "west",
			items : cacheManagerTreePanel,
			title : "缓存管理器",
			layout : "fit",
			collapsible : true,
			split : true
		},{
			region : "center",
			items : gridPanel,
			layout : "fit",
			collapsible : false,
			split : true
		}],
		border : false
	});
	
	Ext.create("Ext.container.Viewport", {
		layout : "border",
		items : [{
			region : "west",
			layout : "fit",
			title : "工厂",  
			items :[ cacheManagerFactoryPanel],
			collapsible : true,
			split : true
		},{
			region : "center",
			items : leftPanel,
			layout : "fit",
			collapsible : false,
			split : true
		}],
		border : false
	});
	
	//============================ Function =========================

	function deleteCache() {
		Co.gridDelete(cacheGrid, API.deleteCache, function(result){
			if (result.success === true) {
				Co.alert("删除成功！", function(){
					Co.reload(cacheGridStore);
				});
			} else {
				Co.alert(result.msg);
			}
		},null,{
			deleteParams : {
				"cacheManagerFactory" : selectedCacheManagerFactory,
				"cacheManager":selectedCacheManager
			}
		});	
	}
	
	function searchCache() {
		Co.load(cacheGridStore);
	}

});