package eu.ueb.acem.web.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.services.ImagesService;

@Controller("imagesController")
@Scope("singleton")
public class ImagesController {

	@Autowired
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
