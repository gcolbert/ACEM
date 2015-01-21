/**
 *     Copyright Grégoire COLBERT 2015
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
package eu.ueb.acem.web.controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-05-28
 * 
 */
@Component("toolCategoryViewBeanHandler")
@Scope("singleton")
public class ToolCategoryViewBeanHandler {

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(ToolCategoryViewBeanHandler.class);

	@Inject
	private ResourcesService resourcesService;

	public ToolCategoryViewBeanHandler() {
	}

	@PostConstruct
	public void init() {
	}

	public ToolCategoryViewBean getToolCategoryViewBean(Long id) {
		ToolCategoryViewBean viewBean = null;
		ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(id);
		if (toolCategory != null) {
			viewBean = new ToolCategoryViewBean(toolCategory);
		}
		else {
			logger.debug("There is no tool with id={} according to ResourcesService", id);
		}
		return viewBean;
	}

	public List<ToolCategoryViewBean> getAllToolCategoryViewBeans() {
		Collection<ResourceCategory> toolCategories = resourcesService.retrieveAllCategories();
		List<ToolCategoryViewBean> toolCategoryViewBeans = new ArrayList<ToolCategoryViewBean>();
		for (ResourceCategory toolCategory : toolCategories) {
			toolCategoryViewBeans.add(getToolCategoryViewBean(toolCategory.getId()));
		}
		return toolCategoryViewBeans;
	}

}
