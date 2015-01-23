package eu.ueb.acem.web.controllers;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

@Controller("administrationController")
@Scope("view")
public class AdministrationController extends AbstractContextAwareController implements PageController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -2234062060405324426L;

	/**
	 * For logging.
	 */
	@SuppressWarnings("unused")
	private static Logger logger = LoggerFactory.getLogger(AdministrationController.class);

	public AdministrationController() {
	}

	@PostConstruct
	public void init() {
	}

	@Override
	public String getPageTitle() {
		return "Administration";
	}

}
