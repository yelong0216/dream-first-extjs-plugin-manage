package dream.first.extjs.plugin.manage.model.controller;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yelong.commons.lang.Strings;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.FieldAndColumn;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.ModelManager;

import com.github.pagehelper.PageInfo;

import dream.first.core.queryinfo.filter.QueryFilterInfo;
import dream.first.core.queryinfo.sort.QuerySortInfo;
import dream.first.extjs.controller.BaseExtJSCrudController;
import dream.first.extjs.plugin.manage.model.dto.FieldAndColumnDTO;
import dream.first.extjs.plugin.manage.model.dto.ModelAndTableDTOBuilder;

/**
 * 模型字段控制器
 * 
 * @since 2.0
 */
@Controller
@RequestMapping("plugin/manage/model/field")
public class FieldAndColumnManageController extends BaseExtJSCrudController<FieldAndColumnDTO> {

	@Override
	protected void saveModel(FieldAndColumnDTO model) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void modifyModel(FieldAndColumnDTO model) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean isNew(FieldAndColumnDTO model) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean deleteModel(String deleteIds) throws Exception {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected PageInfo<?> queryModel(FieldAndColumnDTO model, List<QueryFilterInfo> queryFilterInfos,
			List<QuerySortInfo> querySortInfos, Integer pageNum, Integer pageSize) throws Exception {
		String modelClassName = getParameter("modelClassName");
		Strings.requireNonBlank("必填参数缺失：modelClassName");
		ModelManager modelManager = modelService.getModelConfiguration().getModelManager();
		Class<?> modelClass = ClassUtils.getClass(modelClassName);
		ModelAndTable modelAndTable = modelManager.getModelAndTable((Class<Modelable>) modelClass);

		List<FieldAndColumnDTO> fieldAndColumnDTOs = null;
		List<FieldAndColumn> fieldAndColumns = modelAndTable.getFieldAndColumns();
		Stream<FieldAndColumn> fieldAndColumnStream = fieldAndColumns.stream();
		String fieldName = model.getFieldName();
		if (StringUtils.isNotBlank(fieldName)) {
			fieldAndColumnStream = fieldAndColumnStream.filter(x -> x.getFieldName().contains(fieldName));
		}

		fieldAndColumnDTOs = fieldAndColumnStream.map(ModelAndTableDTOBuilder::buildFieldAndColumnDTO)
				.collect(Collectors.toList());
		return new PageInfo<>(fieldAndColumnDTOs);
	}

	@Override
	protected FieldAndColumnDTO retrieveModel(FieldAndColumnDTO model) throws Exception {
		throw new UnsupportedOperationException();
	}

}
