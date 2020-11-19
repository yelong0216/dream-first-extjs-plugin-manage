package dream.first.extjs.plugin.manage.cache.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.yelong.commons.lang.Strings;
import org.yelong.core.cache.CacheEntity;
import org.yelong.core.cache.CacheManager;
import org.yelong.core.cache.CacheManagerFactory;
import org.yelong.support.servlet.resource.response.ResourceResponseException;
import org.yelong.support.spring.mvc.HandlerResponseWay;
import org.yelong.support.spring.mvc.ResponseWay;

import com.github.pagehelper.PageInfo;

import dream.first.base.queryinfo.filter.DFQueryFilterInfo;
import dream.first.base.queryinfo.sort.DFQuerySortInfo;
import dream.first.extjs.base.controller.DFBaseExtJSCrudController;
import dream.first.extjs.base.login.DFLoginValidate;
import dream.first.extjs.base.msg.DFETreeStoreData;
import dream.first.extjs.plugin.manage.ExtJSPluginManage;
import dream.first.extjs.plugin.manage.cache.dto.CacheMap;

/**
 * 缓存管理器控制器
 * 
 * @since 2.0
 */
@Controller
@DFLoginValidate(validate = false)
@RequestMapping({ "cache", "extjs/plugin/manage/cache" })
public class CacheManagerController extends DFBaseExtJSCrudController<CacheMap> {

	@Resource
	private List<CacheManagerFactory> cacheManagerFactorys;

	@ResponseBody
	@RequestMapping("index")
	@ResponseWay(HandlerResponseWay.MODEL_AND_VIEW)
	public void index() throws ResourceResponseException, IOException {
		responseHtml(ExtJSPluginManage.RESOURCE_PRIVATES_PACKAGE,
				ExtJSPluginManage.RESOURCE_PREFIX + "/html/cache/cacheManage.html");
	}

	@Override
	public boolean deleteModel(String deleteIds) throws Exception {
		CacheManager cacheManager = getCacheManager();
		Strings.requireNonBlank(deleteIds);
		String[] deleteIdArray = deleteIds.split(",");
		for (int i = 0; i < deleteIdArray.length; i++) {
			cacheManager.removeQuietly(deleteIdArray[i]);
		}
		return true;
	}

	@ResponseBody
	@RequestMapping("getCacheValueHtml")
	protected String getCacheValueHtml() {
		String key = getRequest().getParameter("key");
		Strings.requireNonBlank(key);
		CacheManager cacheManager = getCacheManager();
		CacheEntity<?> cacheEntity = cacheManager.getCache(key);
		if (cacheEntity.isNull()) {
			throw new NullPointerException();
		}
		StringBuilder content = new StringBuilder("key:" + key);
		content.append("\n");
		content.append("value:" + toJson(cacheEntity.get()));

		return content.toString().replace("\n", "</br>").replace("\t", "&nbsp;&nbsp;&nbsp;");
	}

	protected CacheManager getCacheManager() {
		String cacheManagerFactoryStr = getRequest().getParameter("cacheManagerFactory");
		Strings.requireNonBlank(cacheManagerFactoryStr);
		CacheManagerFactory cacheManagerFactory = cacheManagerFactorys.stream()
				.filter(x -> cacheManagerFactoryStr.equals(x.toString())).findFirst().orElse(null);
		Objects.requireNonNull(cacheManagerFactory);

		String cacheManagerStr = getRequest().getParameter("cacheManager");
		Strings.requireNonBlank(cacheManagerStr);

		List<CacheManager> cacheManagers = cacheManagerFactory.getHasCreate();
		CacheManager cacheManager = cacheManagers.stream()
				.filter(x -> cacheManagerStr.equals(x.toString() + "【" + x.getName() + "】")).findFirst().orElse(null);
		Objects.requireNonNull(cacheManager);
		return cacheManager;
	}

	/**
	 * @return 缓存管理器工厂树
	 */
	@ResponseBody
	@RequestMapping("getCacheManagerFactoryTree")
	public String getCacheManagerFactoryTree() {
		List<DFETreeStoreData<CacheManagerFactory>> treeStoreDatas = new ArrayList<DFETreeStoreData<CacheManagerFactory>>(
				cacheManagerFactorys.size());
		for (CacheManagerFactory cacheManagerFactory : cacheManagerFactorys) {
			DFETreeStoreData<CacheManagerFactory> treeStoreData = new DFETreeStoreData<CacheManagerFactory>(
					cacheManagerFactory);
			treeStoreData.setId(cacheManagerFactory.toString());
			treeStoreData.setText(cacheManagerFactory.getName());
			treeStoreData.setLeaf(true);
			treeStoreDatas.add(treeStoreData);
		}
		return toJson(treeStoreDatas);
	}

	/**
	 * @return 缓存管理器树
	 */
	@ResponseBody
	@RequestMapping("getCacheManagerTree")
	public String getCacheManagerTree() {
		String cacheManagerFactoryStr = getRequest().getParameter("cacheManagerFactory");
		Strings.requireNonBlank(cacheManagerFactoryStr);
		CacheManagerFactory cacheManagerFactory = cacheManagerFactorys.stream()
				.filter(x -> cacheManagerFactoryStr.equals(x.toString())).findFirst().orElse(null);
		Objects.requireNonNull(cacheManagerFactory);
		List<CacheManager> cacheManagers = cacheManagerFactory.getHasCreate();
		List<DFETreeStoreData<CacheManager>> treeStoreDatas = new ArrayList<DFETreeStoreData<CacheManager>>(
				cacheManagers.size());
		for (CacheManager cacheManager : cacheManagers) {
			DFETreeStoreData<CacheManager> treeStoreData = new DFETreeStoreData<CacheManager>(cacheManager);
			treeStoreData.setId(cacheManager.toString() + "【" + cacheManager.getName() + "】");
//			treeStoreData.setText("【" + cacheManager.getName() + "】");
			treeStoreData.setText(cacheManager.getName());
			treeStoreData.setLeaf(true);
			treeStoreDatas.add(treeStoreData);
		}
		return toJson(treeStoreDatas);
	}

	@Override
	public PageInfo<?> queryModel(CacheMap model, Collection<DFQueryFilterInfo> queryFilterInfos,
			Collection<DFQuerySortInfo> querySortInfos, Integer pageNum, Integer pageSize) throws Exception {
		CacheManager cacheManager = null;
		try {
			cacheManager = getCacheManager();
		} catch (Exception e) {
			return new PageInfo<>();
		}
		String key = getRequest().getParameter("model.key");

		Set<String> keys = cacheManager.getKeys();
		List<CacheMap> cacheMaps = new ArrayList<CacheMap>();
		keys.forEach(x -> {
			if (StringUtils.isNotBlank(key)) {
				if (!x.contains(key)) {
					return;
				}
			}
			CacheMap cacheMap = new CacheMap(x, null);
			cacheMaps.add(cacheMap);
		});
		return new PageInfo<>(cacheMaps);
	}

}
