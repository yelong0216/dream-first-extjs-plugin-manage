package dream.first.extjs.plugin.manage.interfaces.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.yelong.http.client.HttpClient;
import org.yelong.http.exception.HttpException;
import org.yelong.http.request.HttpRequest;
import org.yelong.http.request.HttpRequestFactory;
import org.yelong.http.response.HttpResponse;
import org.yelong.support.servlet.resource.response.ResourceResponseException;
import org.yelong.support.spring.mvc.HandlerResponseWay;
import org.yelong.support.spring.mvc.ResponseWay;
import org.yelong.support.spring.mvc.method.search.RequestMappingHandlerMethodSearcher;
import org.yelong.support.spring.mvc.method.search.SearchCondition;
import org.yelong.support.spring.mvc.method.search.SearchMode;
import org.yelong.support.spring.mvc.method.search.SearchName;

import com.github.pagehelper.PageInfo;

import dream.first.base.queryinfo.filter.DFQueryFilterInfo;
import dream.first.base.queryinfo.sort.DFQuerySortInfo;
import dream.first.extjs.base.controller.DFBaseExtJSCrudController;
import dream.first.extjs.base.login.DFLoginValidate;
import dream.first.extjs.plugin.manage.ExtJSPluginManage;
import dream.first.extjs.plugin.manage.interfaces.dto.InterfaceTestInfo;

/**
 * 接口控制器
 * 
 * @since 2.0
 */
@Controller
@DFLoginValidate(validate = false)
@RequestMapping({ "interfaces", "extjs/plugin/manage/interfaces" })
public class InterfacesController extends DFBaseExtJSCrudController<HashMap<String, Object>> {

	@Resource
	private HttpClient httpClient;

	@Resource
	private RequestMappingHandlerMapping requestMappingHandlerMapping;

	@Resource
	private RequestMappingHandlerMethodSearcher requestMappingHandlerMethodSearcher;

	@ResponseBody
	@RequestMapping("index")
	@ResponseWay(HandlerResponseWay.MODEL_AND_VIEW)
	public void index() throws ResourceResponseException, IOException {
		responseHtml(ExtJSPluginManage.RESOURCE_PRIVATES_PACKAGE,
				ExtJSPluginManage.RESOURCE_PREFIX + "/html/interfaces/springMVCInterfaces.html");
	}

	@Override
	public PageInfo<?> queryModel(HashMap<String, Object> model, Collection<DFQueryFilterInfo> queryFilterInfos,
			Collection<DFQuerySortInfo> querySortInfos, Integer pageNum, Integer pageSize) throws Exception {
		String patternsConditionStr = getRequest().getParameter("model.patternsCondition");
		String methodsConditionStr = getRequest().getParameter("model.methodsCondition");
		String name = getRequest().getParameter("model.name");

		List<SearchCondition> searchConditions = new ArrayList<SearchCondition>();

		Map<RequestMappingInfo, HandlerMethod> handlerMethods;
		if (StringUtils.isNotBlank(patternsConditionStr)) {
			searchConditions.add(new SearchCondition(SearchName.PATTERN, SearchMode.CONTAINS, patternsConditionStr));
		}

		if (StringUtils.isNotBlank(methodsConditionStr)) {
			for (String method : methodsConditionStr.split(",")) {
				searchConditions.add(new SearchCondition(SearchName.METHOD, SearchMode.CONTAINS, method.toUpperCase()));
			}
		}

		if (StringUtils.isNotBlank(name)) {
			searchConditions.add(new SearchCondition(SearchName.NAME, SearchMode.CONTAINS, name));
		}

		handlerMethods = requestMappingHandlerMethodSearcher.search(searchConditions);

		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

		for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
			RequestMappingInfo info = entry.getKey();
			HandlerMethod method = entry.getValue();

			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", info.getName());

			PatternsRequestCondition patternsCondition = info.getPatternsCondition();
			map.put("patternsCondition", patternsCondition.getPatterns().stream().collect(Collectors.joining(",")));
			RequestMethodsRequestCondition requestMethodsRequestCondition = info.getMethodsCondition();
			map.put("methodsCondition", requestMethodsRequestCondition.getMethods().stream()
					.map(RequestMethod::toString).collect(Collectors.joining(",")));

			map.put("beanClass", method.getBeanType().toString());
			map.put("methodName", method.getMethod().getName());
			map.put("beanClassSimpleName", method.getBeanType().getSimpleName());
			mapList.add(map);
		}
		return new PageInfo<>(mapList);
	}

	@ResponseBody
	@RequestMapping("interfaceTest")
	public String interfaceTest(@ModelAttribute InterfaceTestInfo interfaceTestInfo) throws HttpException, IOException {
		HttpServletRequest request = getRequest();
		String scheme = request.getScheme();
		String serverName = request.getServerName();
		int serverPort = request.getServerPort();

		String url = scheme + "://" + serverName + ":" + serverPort + interfaceTestInfo.getUrl();

		String method = interfaceTestInfo.getMethod();
		// 默认GET
		if (StringUtils.isBlank(method)) {
			method = "GET";
		}

		HttpRequest httpRequest = HttpRequestFactory.create(url, method);

		httpRequest.addHeader("Cookie", "JSESSIONID=" + request.getSession().getId());

		String params = interfaceTestInfo.getParams();
		if (StringUtils.isNotBlank(params)) {
			@SuppressWarnings("unchecked")
			Map<String, String> paramMap = getGson().fromJson(params, HashMap.class);
			if (MapUtils.isNotEmpty(paramMap)) {
				paramMap.forEach(httpRequest::addParam);
			}
		}

		HttpResponse httpResponse = httpClient.execute(httpRequest);

		return httpResponse.getContentStr();
	}

}
