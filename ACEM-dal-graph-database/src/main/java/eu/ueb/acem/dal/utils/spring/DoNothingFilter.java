package eu.ueb.acem.dal.utils.spring;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * This class is a transparent servlet {@link javax.servlet.Filter}. It exists
 * in the graph database DAL because the relational database DAL needs a
 * specific filter
 * (org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter) and we
 * cannot use Spring profiles in "web.xml" to instanciate the filter only when
 * property "relational-database" is set. Therefore, we always instanciate a
 * filter in "web.xml": if "graph-database" property is set, then the
 * DoNothingFilter is loaded. If "relational-database" property is set, then
 * org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter is loaded.
 * 
 * @author gcolbert
 */
public class DoNothingFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		filterChain.doFilter(servletRequest, servletResponse);
	}

	@Override
	public void destroy() {
	}

}
