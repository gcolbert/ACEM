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
package eu.ueb.acem.web.utils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

public class MessageDisplayer {

	public static void showMessageToUserWithSeverityInfo(String summary, String details) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, details);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public static void showMessageToUserWithSeverityWarn(String summary, String details) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, details);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
	public static void showMessageToUserWithSeverityError(String summary, String details) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, details);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}

	public static void showMessageToUserWithSeverityFatal(String summary, String details) {
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_FATAL, summary, details);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
	}
	
}
