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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.inject.Inject;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.services.ImagesService;

/**
 * @author Grégoire Colbert
 * @since 2014-01-22
 * 
 */
@Controller("imagesController")
@Scope("session")
public class ImagesController extends AbstractContextAwareController {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 6541923413899165659L;

	/**
	 * For logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(ImagesController.class);

	@Inject
	private ImagesService imagesService;
	
	public StreamedContent getImage() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();

		if (context.getCurrentPhaseId() == PhaseId.RENDER_RESPONSE) {
			// So, we're rendering the HTML. Return a stub StreamedContent so
			// that it will generate right URL.
			return new DefaultStreamedContent();
		}
		else {
			// So, browser is requesting the image. Return a real
			// StreamedContent with the image bytes.
			String imageFileName = context.getExternalContext().getRequestParameterMap().get("imageFileName");
			
			File image = imagesService.getImage(imageFileName);
			return new DefaultStreamedContent(new FileInputStream(image));
		}
	}

}
