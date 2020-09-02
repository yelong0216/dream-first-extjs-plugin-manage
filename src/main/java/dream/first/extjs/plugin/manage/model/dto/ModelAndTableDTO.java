/**
 * 
 */
package dream.first.extjs.plugin.manage.model.dto;

import org.yelong.core.model.sql.SelectSqlColumnMode;

public class ModelAndTableDTO {

	private String modelClassName;

	private String modelClassSimpleName;

	private String tableName;

	private boolean isView;

	private String tableAlias;

	private String tableDesc;

	private SelectSqlColumnMode selectSqlColumnMode;

	public String getModelClassName() {
		return modelClassName;
	}

	public void setModelClassName(String modelClassName) {
		this.modelClassName = modelClassName;
	}

	public String getModelClassSimpleName() {
		return modelClassSimpleName;
	}

	public void setModelClassSimpleName(String modelClassSimpleName) {
		this.modelClassSimpleName = modelClassSimpleName;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean isView() {
		return isView;
	}

	public void setView(boolean isView) {
		this.isView = isView;
	}

	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		this.tableAlias = tableAlias;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public SelectSqlColumnMode getSelectSqlColumnMode() {
		return selectSqlColumnMode;
	}

	public void setSelectSqlColumnMode(SelectSqlColumnMode selectSqlColumnMode) {
		this.selectSqlColumnMode = selectSqlColumnMode;
	}

}
