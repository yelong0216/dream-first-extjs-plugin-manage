/**
 * 
 */
package dream.first.extjs.plugin.manage.model.dto;

public class FieldAndColumnDTO {

	private String fieldName;

	private String fieldTypeName;

	private String fieldTypeSimpleName;

	private String column;

	private String selectColumn;

	private boolean select;

	private boolean extend;

	private boolean primaryKey;

	private Long minLength;

	private Long maxLength;

	private boolean allowBlank;

	private boolean allowNull;

	private String jdbcType;

	private String desc;

	private String columnName;

	private boolean transients;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldTypeName() {
		return fieldTypeName;
	}

	public void setFieldTypeName(String fieldTypeName) {
		this.fieldTypeName = fieldTypeName;
	}

	public String getFieldTypeSimpleName() {
		return fieldTypeSimpleName;
	}

	public void setFieldTypeSimpleName(String fieldTypeSimpleName) {
		this.fieldTypeSimpleName = fieldTypeSimpleName;
	}

	public String getColumn() {
		return column;
	}

	public void setColumn(String column) {
		this.column = column;
	}

	public String getSelectColumn() {
		return selectColumn;
	}

	public void setSelectColumn(String selectColumn) {
		this.selectColumn = selectColumn;
	}

	public boolean isSelect() {
		return select;
	}

	public void setSelect(boolean select) {
		this.select = select;
	}

	public boolean isExtend() {
		return extend;
	}

	public void setExtend(boolean extend) {
		this.extend = extend;
	}

	public boolean isPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Long getMinLength() {
		return minLength;
	}

	public void setMinLength(Long minLength) {
		this.minLength = minLength;
	}

	public Long getMaxLength() {
		return maxLength;
	}

	public void setMaxLength(Long maxLength) {
		this.maxLength = maxLength;
	}

	public boolean isAllowBlank() {
		return allowBlank;
	}

	public void setAllowBlank(boolean allowBlank) {
		this.allowBlank = allowBlank;
	}

	public boolean isAllowNull() {
		return allowNull;
	}

	public void setAllowNull(boolean allowNull) {
		this.allowNull = allowNull;
	}

	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public boolean isTransients() {
		return transients;
	}

	public void setTransients(boolean transients) {
		this.transients = transients;
	}

}
