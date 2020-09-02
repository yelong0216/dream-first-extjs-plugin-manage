package dream.first.extjs.plugin.manage.cache.controller;

import java.util.ArrayList;
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

import com.github.pagehelper.PageInfo;

import dream.first.core.queryinfo.filter.QueryFilterInfo;
import dream.first.core.queryinfo.sort.QuerySortInfo;
import dream.first.extjs.controller.BaseExtJSCrudController;
import dream.first.extjs.login.LoginValidate;
import dream.first.extjs.plugin.manage.cache.dto.CacheMap;
import dream.first.extjs.support.store.TreeStoreData;

/**
 * 缓存管理器控制器
 * 
 * @since 2.0
 */
@Controller
@LoginValidate(validate = false)
@RequestMapping("plugin/manage/cache")
public class CacheManagerController extends BaseExtJSCrudController<CacheMap> {

	@Resource
	private List<CacheManagerFactory> cacheManagerFactorys;

	@RequestMapping("index")
	public String index() {
		return "plugin/manage/cache/cacheManage.jsp";
	}

	@Override
	protected boolean isNew(CacheMap model) {
		return false;
	}

	@Override
	protected void saveModel(CacheMap model) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected void modifyModel(CacheMap model) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	protected boolean deleteModel(String deleteIds) throws Exception {
		CacheManager cacheManager = getCacheManager();
		Strings.requireNonBlank(deleteIds);
		String[] deleteIdArray = deleteIds.split(",");
		for (int i = 0; i < deleteIdArray.length; i++) {
			cacheManager.removeQuietly(deleteIdArray[i]);
		}
		return true;
	}

	@Override
	protected CacheMap retrieveModel(CacheMap model) throws Exception {
		throw new UnsupportedOperationException();
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
		List<TreeStoreData<CacheManagerFactory>> treeStoreDatas = new ArrayList<TreeStoreData<CacheManagerFactory>>(
				cacheManagerFactorys.size());
		for (CacheManagerFactory cacheManagerFactory : cacheManagerFactorys) {
			TreeStoreData<CacheManagerFactory> treeStoreData = new TreeStoreData<CacheManagerFactory>(
					cacheManagerFactory);
			treeStoreData.setId(cacheManagerFactory.toString());
			treeStoreData.setText(cacheManagerFactory.getName());
			treeStoreData.setLeaf(true);
			treeStoreDatas.add(treeStoreData);
		}
		return toJson(treeStoreDatas);
	}

	/**
	 * @return 换粗管理器树
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
		List<TreeStoreData<CacheManager>> treeStoreDatas = new ArrayList<TreeStoreData<CacheManager>>(
				cacheManagers.size());
		for (CacheManager cacheManager : cacheManagers) {
			TreeStoreData<CacheManager> treeStoreData = new TreeStoreData<CacheManager>(cacheManager);
			treeStoreData.setId(cacheManager.toString() + "【" + cacheManager.getName() + "】");
//			treeStoreData.setText("【" + cacheManager.getName() + "】");
			treeStoreData.setText(cacheManager.getName());
			treeStoreData.setLeaf(true);
			treeStoreDatas.add(treeStoreData);
		}
		return toJson(treeStoreDatas);
	}

	@Override
	protected PageInfo<?> queryModel(CacheMap model, List<QueryFilterInfo> queryFilterInfos,
			List<QuerySortInfo> querySortInfos, Integer pageNum, Integer pageSize) throws Exception {
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
