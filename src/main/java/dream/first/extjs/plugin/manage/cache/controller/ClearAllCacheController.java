/**
 * 
 */
package dream.first.extjs.plugin.manage.cache.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yelong.core.cache.CacheManager;
import org.yelong.core.cache.CacheManagerFactory;
import org.yelong.core.model.manage.ModelManager;

import dream.first.extjs.base.controller.DFBaseExtJSController;
import dream.first.extjs.base.login.DFLoginValidate;
import dream.first.extjs.base.msg.DFEJsonMsg;

/**
 * 清空所有的缓存
 * 
 * @since 2.0
 */
@Controller
@DFLoginValidate(validate = false)
public class ClearAllCacheController extends DFBaseExtJSController {

	@Resource
	private List<CacheManagerFactory> cacheManagerFactorys;

	@ResponseBody
	@RequestMapping("clearAllCache")
	public String clearAllCache() {
		for (CacheManagerFactory cacheManagerFactory : cacheManagerFactorys) {
			List<CacheManager> cacheManagers = cacheManagerFactory.getHasCreate();
			cacheManagers.forEach(CacheManager::clear);
		}
		ModelManager modelManager = modelService.getModelConfiguration().getModelManager();
		modelManager.clearCacheModelAndTable();
		return toJson(new DFEJsonMsg(true, "所有缓存已经清除"));
	}

}
