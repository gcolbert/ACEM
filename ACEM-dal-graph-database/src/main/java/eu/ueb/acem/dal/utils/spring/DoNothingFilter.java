/**
 *     Copyright Université Européenne de Bretagne 2012-2015
 * 
 *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
 * 
 *     ACEM is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     ACEM is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with ACEM.  If not, see <http://www.gnu.org/licenses/>
 */
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
 * @author Grégoire Colbert
 * @since 2015-06-12
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
