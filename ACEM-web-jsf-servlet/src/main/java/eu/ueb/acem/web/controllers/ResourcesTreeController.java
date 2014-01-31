package eu.ueb.acem.web.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.web.viewbeans.EditableTreeBean;

@Controller("resourcesTreeController")
@Scope("view")
public class ResourcesTreeController extends AbstractContextAwareController {

	private static final long serialVersionUID = -5663154564837226988L;

	private final static Logger logger = LoggerFactory.getLogger(ResourcesTreeController.class);

	@Autowired
	private EditableTreeBean editableTreeBean;
	
}
