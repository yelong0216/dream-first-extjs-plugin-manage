/**
 * 
 */
package dream.first.extjs.plugin.manage.model.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yelong.core.model.Modelable;
import org.yelong.core.model.manage.ModelAndTable;
import org.yelong.core.model.manage.ModelManager;
import org.yelong.support.servlet.resource.response.ResourceResponseException;
import org.yelong.support.spring.mvc.HandlerResponseWay;
import org.yelong.support.spring.mvc.ResponseWay;

import com.github.pagehelper.PageInfo;

import dream.first.base.queryinfo.filter.DFQueryFilterInfo;
import dream.first.base.queryinfo.sort.DFQuerySortInfo;
import dream.first.extjs.base.controller.DFBaseExtJSCrudController;
import dream.first.extjs.plugin.manage.ExtJSPluginManage;
import dream.first.extjs.plugin.manage.model.dto.ModelAndTableDTO;
import dream.first.extjs.plugin.manage.model.dto.ModelAndTableDTOBuilder;

/**
 * 
 * @since
 */
@Controller
@RequestMapping({ "model", "extjs/plugin/manage/model" })
public class ModelAndTableManageController extends DFBaseExtJSCrudController<ModelAndTableDTO> {

	@ResponseBody
	@RequestMapping("index")
	@ResponseWay(HandlerResponseWay.MODEL_AND_VIEW)
	public void index() throws ResourceResponseException, IOException {
		responseHtml(ExtJSPluginManage.RESOURCE_PRIVATES_PACKAGE,
				ExtJSPluginManage.RESOURCE_PREFIX + "/html/model/modelAndTableManage.html");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean deleteModel(String deleteIds) throws Exception {
		String[] modelClassNames = deleteIds.split(",");
		ModelManager modelManager = modelService.getModelConfiguration().getModelManager();
		for (String modelClassName : modelClassNames) {
			Class<?> modelClass = ClassUtils.getClass(modelClassName);
			modelManager.removeCacheModelAndTable((Class<Modelable>) modelClass);
		}
		return true;
	}

	@Override
	public PageInfo<?> queryModel(ModelAndTableDTO model, Collection<DFQueryFilterInfo> queryFilterInfos,
			Collection<DFQuerySortInfo> querySortInfos, Integer pageNum, Integer pageSize) throws Exception {
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

}
