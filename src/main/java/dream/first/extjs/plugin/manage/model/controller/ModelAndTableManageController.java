/**
 * 
 */
package dream.first.extjs.plugin.manage.model.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.ModelManager;

import com.github.pagehelper.PageInfo;

import dream.first.core.queryinfo.filter.QueryFilterInfo;
import dream.first.core.queryinfo.sort.QuerySortInfo;
import dream.first.extjs.controller.BaseExtJSCrudController;
import dream.first.extjs.plugin.manage.model.dto.ModelAndTableDTO;
import dream.first.extjs.plugin.manage.model.dto.ModelAndTableDTOBuilder;

/**
 * 
 * @since
 */
@Controller
@RequestMapping("plugin/manage/model")
public class ModelAndTableManageController extends BaseExtJSCrudController<ModelAndTableDTO> {

	@RequestMapping("index")
	public String index() {
		return "plugin/manage/model/modelAndTableDTOManage.jsp";
	}

	@Override
	protected void saveModel(ModelAndTableDTO model) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void modifyModel(ModelAndTableDTO model) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean isNew(ModelAndTableDTO model) {
		throw new UnsupportedOperationException();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected boolean deleteModel(String deleteIds) throws Exception {
		String[] modelClassNames = deleteIds.split(",");
		ModelManager modelManager = modelService.getModelConfiguration().getModelManager();
		for (String modelClassName : modelClassNames) {
			Class<?> modelClass = ClassUtils.getClass(modelClassName);
			modelManager.removeCacheModelAndTable((Class<Modelable>) modelClass);
		}
		return true;
	}

	@Override
	protected PageInfo<?> queryModel(ModelAndTableDTO model, List<QueryFilterInfo> queryFilterInfos,
			List<QuerySortInfo> querySortInfos, Integer pageNum, Integer pageSize) throws Exception {
		String modelClassSimpleName = getParameter("model.modelClassSimpleName");

		ModelManager modelManager = modelService.getModelConfiguration().getModelManager();
		Map<Class<? extends Modelable>, ModelAndTable> modelAndTableMap = modelManager.getModelAndTableMap();
		List<ModelAndTableDTO> modelAndTableDTOs = null;
		if (StringUtils.isNotBlank(modelClassSimpleName)) {
			modelAndTableDTOs = modelAndTableMap.values().stream()
					.filter(x -> x.getModelClass().getSimpleName().contains(modelClassSimpleName))
					.map(ModelAndTableDTOBuilder::buildModelAndTableDTO).collect(Collectors.toList());

		} else {
			modelAndTableDTOs = modelAndTableMap.values().stream().map(ModelAndTableDTOBuilder::buildModelAndTableDTO)
					.collect(Collectors.toList());
		}
		return new PageInfo<>(modelAndTableDTOs);
	}

	@Override
	protected ModelAndTableDTO retrieveModel(ModelAndTableDTO model) throws Exception {
		throw new UnsupportedOperationException();
	}

}
