package eu.ueb.acem.web.controllers;

import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.services.BesoinsReponsesService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;

@Controller("besoinsReponsesController")
@Scope("session")
public class BesoinsReponsesController extends AbstractContextAwareController {

	private static final long serialVersionUID = 3305497053688875560L;

	@Autowired
	private BesoinsReponsesService besoinsReponsesService;

	@Autowired
	private EditableTreeBean editableTreeBean;

	/**
	 * For Logging.
	 */
	private final static Logger logger = LoggerFactory.getLogger(BesoinsReponsesController.class);

	private TreeNode selectedNode;
	
	public BesoinsReponsesController() {
		Set<Besoin> besoins = besoinsReponsesService.getBesoinsLies(null);
		logger.info("Rechargement des besoins à la racine à partir du service effectué. Nombre total de besoins : {}", Integer.toString(besoins.size()));
	}

	public void updateTreeBean() {
		Set<Besoin> besoins = besoinsReponsesService.getBesoinsLies(null);
		for (Besoin besoin : besoins) {
			editableTreeBean.addChild(editableTreeBean.getVisibleRoot(), besoin.getNom());
		}
	}
	
	public TreeNode getTreeRoot() {
		return editableTreeBean.getRoot();
	}

    public TreeNode getSelectedNode() {  
        return selectedNode;  
    }  
  
    public void setSelectedNode(TreeNode selectedNode) {  
        this.selectedNode = selectedNode;  
    }  
	
	public EditableTreeBean getTreeBean() {
		return editableTreeBean;
	}

    public void deleteSelectedNode() {  
    	selectedNode.getChildren().clear();  
    	selectedNode.getParent().getChildren().remove(selectedNode);  
    	selectedNode.setParent(null);  
          
    	this.selectedNode = null;  
    }

    public void addChildToSelectedNode() {
    	editableTreeBean.addChild(selectedNode, "Nouveau");
    }

    public void displaySelectedSingle() {  
    	if(selectedNode != null) {  
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());  

    		FacesContext.getCurrentInstance().addMessage(null, message);  
    	}  
    }  

}
