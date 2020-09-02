/**
 * 
 */
package dream.first.extjs.plugin.manage.model.dto;

import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;

public final class ModelAndTableDTOBuilder {

	private ModelAndTableDTOBuilder() {
	}

	public static ModelAndTableDTO buildModelAndTableDTO(ModelAndTable modelAndTable) {
		ModelAndTableDTO modelAndTableDTO = new ModelAndTableDTO();
		Class<? extends Modelable> modelClass = modelAndTable.getModelClass();
		modelAndTableDTO.setModelClassName(modelAndTable.getModelName());
		modelAndTableDTO.setModelClassSimpleName(modelClass.getSimpleName());
		modelAndTableDTO.setSelectSqlColumnMode(modelAndTable.getSelectSqlColumnMode());
		modelAndTableDTO.setTableAlias(modelAndTable.getTableAlias());
		modelAndTableDTO.setTableDesc(modelAndTable.getTableDesc());
		modelAndTableDTO.setTableName(modelAndTable.getTableName());
		modelAndTableDTO.setView(modelAndTable.isView());
		return modelAndTableDTO;
	}

	public static FieldAndColumnDTO buildFieldAndColumnDTO(FieldAndColumn fieldAndColumn) {
		FieldAndColumnDTO fieldAndColumnDTO = new FieldAndColumnDTO();
		fieldAndColumnDTO.setAllowBlank(fieldAndColumn.isAllowBlank());
		fieldAndColumnDTO.setAllowNull(fieldAndColumn.isAllowNull());
		fieldAndColumnDTO.setColumn(fieldAndColumn.getColumn());
		fieldAndColumnDTO.setColumnName(fieldAndColumn.getColumnName());
		fieldAndColumnDTO.setDesc(fieldAndColumn.getDesc());
		fieldAndColumnDTO.setExtend(fieldAndColumn.isExtend());
		fieldAndColumnDTO.setFieldName(fieldAndColumn.getFieldName());
		Class<?> fieldType = fieldAndColumn.getFieldType();
		fieldAndColumnDTO.setFieldTypeName(fieldType.getName());
		fieldAndColumnDTO.setFieldTypeSimpleName(fieldType.getSimpleName());
		fieldAndColumnDTO.setJdbcType(fieldAndColumn.getJdbcType());
		fieldAndColumnDTO.setMaxLength(fieldAndColumn.getMaxLength());
		fieldAndColumnDTO.setMinLength(fieldAndColumn.getMinLength());
		fieldAndColumnDTO.setPrimaryKey(fieldAndColumn.isPrimaryKey());
		fieldAndColumnDTO.setSelect(fieldAndColumn.isSelect());
		fieldAndColumnDTO.setSelectColumn(fieldAndColumn.getSelectColumn());
		fieldAndColumnDTO.setTransients(fieldAndColumn.isTransient());
		return fieldAndColumnDTO;
	}

}
