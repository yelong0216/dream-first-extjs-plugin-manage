/**
 * 
 */
package dream.first.extjs.plugin.manage.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.yelong.support.spring.mvc.method.search.DefaultRequestMappingHandlerMethodSearcher;
import org.yelong.support.spring.mvc.method.search.RequestMappingHandlerMethodSearcher;

import dream.first.extjs.plugin.manage.cache.controller.CacheManagerController;
import dream.first.extjs.plugin.manage.cache.controller.ClearAllCacheController;
import dream.first.extjs.plugin.manage.interfaces.controller.InterfacesController;
import dream.first.extjs.plugin.manage.model.controller.FieldAndColumnManageController;
import dream.first.extjs.plugin.manage.model.controller.ModelAndTableManageController;

public class ExtJSPluginManageConfiguration {

	@Bean
	public ClearAllCacheController clearAllCacheController() {
		return new ClearAllCacheController();
	}

	@Bean
	public CacheManagerController cacheManagerController() {
		return new CacheManagerController();
	}

	@Bean
	public InterfacesController springMVCInterfacesController() {
		return new InterfacesController();
	}

	@Bean
	public RequestMappingHandlerMethodSearcher RequestMappingHandlerMethodSearcher(
			RequestMappingHandlerMapping requestMappingHandlerMapping) {
		return new DefaultRequestMappingHandlerMethodSearcher(requestMappingHandlerMapping);
	}

	@Bean
	public FieldAndColumnManageController fieldAndColumnManageController() {
		return new FieldAndColumnManageController();
	}

	@Bean
	public ModelAndTableManageController modelAndTableManageController() {
		return new ModelAndTableManageController();
	}

}
