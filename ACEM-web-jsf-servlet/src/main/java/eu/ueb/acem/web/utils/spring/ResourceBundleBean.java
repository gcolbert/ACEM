package eu.ueb.acem.web.utils.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import javax.faces.context.FacesContext;
import javax.servlet.ServletRequest;

import java.util.HashMap;

/**
 * This class is a utility to use ReloadableResourceBundleMessageSource of
 * Spring in Jsf for bundles access
 * 
 * @author rlorthio
 *
 */
@SuppressWarnings("rawtypes")
@Component(value = "msg")
public class ResourceBundleBean extends HashMap {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7277088641160260475L;

	/**
	 * Get the msgs object define in i18n.xml
	 */
	@Autowired
	private MessageSource msgs;

	/**
	 * @see java.util.HashMap#get(java.lang.Object)
	 */
	@Override
	public String get(Object key) {
		ServletRequest request = (ServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		String message;
		try {
			message = msgs.getMessage((String) key, null, request.getLocale());
		} catch (NoSuchMessageException e) {
			message = "???" + key + "???";
		}
		return message;
	}
}