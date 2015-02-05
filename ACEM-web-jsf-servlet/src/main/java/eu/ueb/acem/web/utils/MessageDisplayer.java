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
package eu.ueb.acem.web.utils;

import java.util.Locale;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.transaction.TransactionSystemException;

import eu.ueb.acem.services.exceptions.ServiceException;

/**
 * Tool to display a message to the user after an action has been performed on
 * the server side. The JSF page must have a
 * <p:growl id="someId">
 * component in the same form, and the component that calls the server must
 * reference this id using update=":someId" attribute.
 * 
 * @author gcolbert
 * @since 2014-03-17
 */
public class MessageDisplayer {

	public static void info(String summary, String details) {
		FacesMessage facesMessage = new FacesMessage(
				FacesMessage.SEVERITY_INFO, summary, details);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public static void warn(String summary, String details) {
		FacesMessage facesMessage = new FacesMessage(
				FacesMessage.SEVERITY_WARN, summary, details);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public static void error(String summary, String details, Logger logger) {
		logger.error("ERROR: Summary=[" + summary + "], Details=[" + details
				+ "]");
		FacesMessage facesMessage = new FacesMessage(
				FacesMessage.SEVERITY_ERROR, summary, details);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		logger.error(summary + " " + details);
	}

	public static void fatal(String summary, String details, Logger logger) {
		logger.error("FATAL: Summary=[" + summary + "], Details=[" + details
				+ "]");
		FacesMessage facesMessage = new FacesMessage(
				FacesMessage.SEVERITY_FATAL, summary, details);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		logger.error(summary + " " + details);
	}

	/**
	 * Displays an error message for exception e.
	 * 
	 * @param e
	 * @param msgs
	 * @param locale
	 * @param logger
	 */
	public static void error(Exception e,
			ReloadableResourceBundleMessageSource msgs,
			Locale locale, Logger logger) {
		if (e instanceof ServiceException) {
			MessageDisplayer.error(msgs.getMessage(
					e.getMessage(), null,
					locale), "", logger);
		}
		else if (e instanceof ConstraintViolationException) {
			ConstraintViolationException cve = (ConstraintViolationException) e;
			for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
				MessageDisplayer.error(msgs.getMessage(cv.getMessageTemplate(),
						null, locale), "", logger);
			}
		}
		else if (e instanceof TransactionSystemException) {
			TransactionSystemException tse = (TransactionSystemException) e;
			if (tse.getRootCause() instanceof ConstraintViolationException) {
				ConstraintViolationException cve = (ConstraintViolationException) tse.getRootCause();
				for (ConstraintViolation<?> cv : cve.getConstraintViolations()) {
					MessageDisplayer.error(msgs.getMessage(cv.getMessageTemplate(),
							null, locale), "", logger);
				}
			}
		}else{
			MessageDisplayer.error(msgs.getMessage("APPLICATION.MESSAGE.ERROR",
					null, locale) + e.getMessage(), "", logger);
		}
			
	}
	
}
