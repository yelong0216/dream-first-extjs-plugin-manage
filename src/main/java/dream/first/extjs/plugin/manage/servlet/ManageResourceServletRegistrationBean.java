package dream.first.extjs.plugin.manage.servlet;

import org.yelong.support.servlet.resource.ResourceServlet;
import org.yelong.support.servlet.resource.response.ResourceResponseHandler;
import org.yelong.support.spring.boot.servlet.resource.ResourceServletRegistrationBean;

import dream.first.extjs.plugin.manage.servlet.ManageResourceServletRegistrationBean.ManageResourceServlet;

public class ManageResourceServletRegistrationBean extends ResourceServletRegistrationBean<ManageResourceServlet> {

	public static final String urlPrefix = "/resources/extjs/plugin/manage";

	public static final String resourceRootPath = "/dream/first/extjs/plugin/resources/manage/publics/extjs/plugin/manage";

	public ManageResourceServletRegistrationBean() {
		this(urlPrefix);
	}

	public ManageResourceServletRegistrationBean(String urlPrefix) {
		this(urlPrefix, resourceRootPath);
	}

	public ManageResourceServletRegistrationBean(String urlPrefix, String resourceRootPath) {
		super(urlPrefix, resourceRootPath, new ManageResourceServlet());
	}

	public static final class ManageResourceServlet extends ResourceServlet {

		private static final long serialVersionUID = -454745587938652439L;

		public ManageResourceServlet() {
		}

		public ManageResourceServlet(ResourceResponseHandler resourceResponseHandler) {
			super(resourceResponseHandler);
		}

	}

}
