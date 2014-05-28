/**
 *     Copyright Grégoire COLBERT 2013
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
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.Ressource;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-05-28
 * 
 */
@Component("toolCategoryViewBeanHandler")
@Scope("singleton")
class ToolCategoryViewBeanHandler {

	private static final Logger logger = LoggerFactory.getLogger(ToolCategoryViewBeanHandler.class);

	private Map<Long, ToolCategoryViewBean> toolCategoryViewBeans;

	@Inject
	private ResourceViewBeanHandler resourceViewBeanHandler;
	
	@Inject
	private ResourcesService resourcesService;

	public ToolCategoryViewBeanHandler() {
		toolCategoryViewBeans = new HashMap<Long, ToolCategoryViewBean>();
	}
	
	@PostConstruct
	public void initToolCategoryViewBeanHandler() {
		Collection<ResourceCategory> toolCategories = resourcesService.retrieveAllCategories();
		logger.info("found {} tool categories", toolCategories.size());
		for (ResourceCategory toolCategory : toolCategories) {
			logger.info("toolCategory = {}", toolCategory.getName());
			getToolCategoryViewBean(toolCategory.getId());
		}
	}

	public ToolCategoryViewBean getToolCategoryViewBean(Long id) {
		ToolCategoryViewBean viewBean = null;
		if (toolCategoryViewBeans.containsKey(id)) {
			logger.info("toolCategoryViewBean found in toolCategoryViewBeans map, so we don't reload it.");
			viewBean = toolCategoryViewBeans.get(id);
		}
		else {
			logger.info("toolCategoryViewBean not found in toolCategoryViewBeans map, we load it with ResourcesService.");
			ResourceCategory toolCategory = resourcesService.retrieveResourceCategory(id);
			if (toolCategory != null) {
				viewBean = new ToolCategoryViewBean(toolCategory);
				
				for (Ressource resource : toolCategory.getResources()) {
					viewBean.addResourceViewBean(resourceViewBeanHandler.getResourceViewBean(resource.getId()));
				}

				toolCategoryViewBeans.put(id, viewBean);
			}
			else {
				logger.error("There is no tool with id={} according to ResourcesService", id);
			}
		}
		return viewBean;
	}

	public void removeToolCategoryViewBean(Long id) {
		toolCategoryViewBeans.remove(id);
	}
	
	public List<ToolCategoryViewBean> getToolCategoryViewBeansAsList() {
		List<ToolCategoryViewBean> toolCategoryViewBeansAsList = new ArrayList<ToolCategoryViewBean>(toolCategoryViewBeans.values());
		Collections.sort(toolCategoryViewBeansAsList);
		return toolCategoryViewBeansAsList;
	}
}
