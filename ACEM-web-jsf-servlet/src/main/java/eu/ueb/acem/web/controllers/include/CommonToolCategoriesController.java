package eu.ueb.acem.web.controllers.include;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.services.ResourcesService;
import eu.ueb.acem.web.controllers.AbstractContextAwareController;
import eu.ueb.acem.web.utils.MessageDisplayer;
import eu.ueb.acem.web.viewbeans.jaune.ToolCategoryViewBean;

@Controller("commonToolCategoriesController")
@Scope("view")
public class CommonToolCategoriesController extends AbstractContextAwareController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 8337871956774150518L;

	/**
	 * For logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CommonToolCategoriesController.class);

	@Inject
	private ResourcesService resourcesService;

	private List<ToolCategoryViewBean> allToolCategoryViewBeans;

	public CommonToolCategoriesController() {
		allToolCategoryViewBeans = new ArrayList<ToolCategoryViewBean>();
	}

	@PostConstruct
	public void init() {
		loadAllToolCategoryViewBeans();
	}

	public List<ToolCategoryViewBean> getAllToolCategoryViewBeans() {
		return allToolCategoryViewBeans;
	}

	public void loadAllToolCategoryViewBeans() {
		for (ResourceCategory resourceCategory : resourcesService.retrieveAllCategories()) {
			allToolCategoryViewBeans.add(new ToolCategoryViewBean(resourceCategory));
		}
		Collections.sort(allToolCategoryViewBeans);
	}

	public void onCreateToolCategory(String name, String description, String iconFileName) {
		MessageDisplayer.info("onCreateToolCategory", name);
		ResourceCategory toolCategory = resourcesService.createResourceCategory(name, description, iconFileName);
		ToolCategoryViewBean newToolCategoryViewBean = new ToolCategoryViewBean(toolCategory);
		allToolCategoryViewBeans.add(newToolCategoryViewBean);
		Collections.sort(allToolCategoryViewBeans);
	}

	public void onModifyToolCategory(ToolCategoryViewBean toolCategoryViewBean) {
		if (toolCategoryViewBean != null) {
			toolCategoryViewBean.getDomainBean().setName(toolCategoryViewBean.getName());
			toolCategoryViewBean.getDomainBean().setDescription(toolCategoryViewBean.getDescription());
			toolCategoryViewBean.getDomainBean().setIconFileName(toolCategoryViewBean.getIconFileName());
			toolCategoryViewBean.setDomainBean(resourcesService.updateResourceCategory(toolCategoryViewBean.getDomainBean()));
			MessageDisplayer.info(msgs.getMessage(
					"TOOL_CATEGORIES.MODIFICATION_SUCCESSFUL.TITLE", null,
					getCurrentUserLocale()), msgs.getMessage(
					"TOOL_CATEGORIES.MODIFICATION_SUCCESSFUL.DETAILS", null,
					getCurrentUserLocale()));
		}
	}

	public void onDeleteToolCategory(ToolCategoryViewBean toolCategoryViewBean) {
		if (toolCategoryViewBean != null) {
			if (resourcesService.deleteResourceCategory(toolCategoryViewBean.getDomainBean().getId())) {
				allToolCategoryViewBeans.remove(toolCategoryViewBean);
				MessageDisplayer.info(
						msgs.getMessage("TOOL_CATEGORIES.DELETE_TOOL_CATEGORY.DELETION_SUCCESSFUL.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("TOOL_CATEGORIES.DELETE_TOOL_CATEGORY.DELETION_SUCCESSFUL.DETAILS",null,getCurrentUserLocale()));
			}
			else {
				MessageDisplayer.info(
						msgs.getMessage("TOOL_CATEGORIES.DELETE_TOOL_CATEGORY.DELETION_FAILED.TITLE",null,getCurrentUserLocale()),
						msgs.getMessage("TOOL_CATEGORIES.DELETE_TOOL_CATEGORY.DELETION_FAILED.DETAILS",null,getCurrentUserLocale()));
			}
		}
	}

}
