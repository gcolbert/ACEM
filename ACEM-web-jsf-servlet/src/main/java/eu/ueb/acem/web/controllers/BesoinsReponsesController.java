package eu.ueb.acem.web.controllers;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.services.BesoinsReponsesService;
import eu.ueb.acem.web.viewbeans.EditableTreeBean;
import eu.ueb.acem.web.viewbeans.EditableTreeBean.Menu;

@Controller("besoinsReponsesController")
@Scope("view")
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
	}

    @PostConstruct
    public void initTree() {
		Set<Besoin> besoins = besoinsReponsesService.getBesoinsLies(null);
		logger.info("[BesoinsReponsesController.initTree] Récupération de {} besoins à la racine de l'arbre.", besoins.size());
		for (Besoin besoin : besoins) {
	    	createTree(besoin, editableTreeBean.getVisibleRoot());
		}
    }

    /**
     * Recursive function to construct Tree
     */
    public void createTree(Besoin besoin, TreeNode rootNode) {
        TreeNode newNode = new DefaultTreeNode(new Menu(besoin.getId(), besoin.getName()), rootNode);
        Set<Besoin> childrenNodes = besoinsReponsesService.getBesoinsLies(besoin);
        for (Besoin besoinChild : childrenNodes) {
            createTree(besoinChild, newNode);
        }
    }
    
	public EditableTreeBean getTreeBean() {
		return editableTreeBean;
	}
	
	public TreeNode getTreeRoot() {
		return editableTreeBean.getRoot();
	}

    public TreeNode getSelectedNode() {  
        return selectedNode;  
    }
  
    public void setSelectedNode(TreeNode selectedNode) {
    	logger.info("entering setSelectedNode({})", selectedNode);
    	if (this.selectedNode != null) {
	        if (this.selectedNode.isSelected()) {
	        	this.selectedNode.setSelected(false);
	        }
    	}
        this.selectedNode = selectedNode;
    	if (this.selectedNode != null) {
	        if (! this.selectedNode.isSelected()) {
	        	this.selectedNode.setSelected(true);
	        }
    	}
    	logger.info("leaving setSelectedNode({})", selectedNode);
    }

    public void onNodeSelect(NodeSelectEvent event) {
    	logger.info("onNodeSelect");
    	/*
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", event.getTreeNode().toString());
        FacesContext.getCurrentInstance().addMessage(null, message);      	
        */
    }
    
    public void displaySelectedSingle() {  
    	if(selectedNode != null) {  
    		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected", selectedNode.getData().toString());
    		FacesContext.getCurrentInstance().addMessage(null, message);
    	}  
    }

    public void addChildToSelectedNode() {
    	logger.info("entering addChildToSelectedNode, selectedNode={}", selectedNode.getData().toString());
    	TreeNode newNode = editableTreeBean.addChild(selectedNode, null, "Nouveau");
    	setSelectedNode(newNode);
    	saveSelectedNode();
    	logger.info("leaving addChildToSelectedNode, selectedNode={}", selectedNode.getData().toString());
		logger.info("------");
    }

    public void addAnswerToSelectedNode() {
    	logger.info("entering addAnswerToSelectedNode, selectedNode={}", selectedNode.getData().toString());
    	
    }
    
	public void saveSelectedNode() {
		logger.info("entering saveSelectedNode, selectedNode={}", selectedNode.getData().toString());
		// Si selectedNode est un Besoin
		Besoin savedBesoin;
		if ((selectedNode.getParent() != null) && (selectedNode.getParent().getData() != null)) {
			savedBesoin = besoinsReponsesService.createOrUpdateBesoin(((Menu)selectedNode.getData()).getId(),
										((Menu) selectedNode.getData()).getMenuName(),
										((Menu) selectedNode.getParent().getData()).getId());
		}
		else {
			savedBesoin = besoinsReponsesService.createOrUpdateBesoin(((Menu)selectedNode.getData()).getId(),
										((Menu)selectedNode.getData()).getMenuName(),
										null);
		}
		((Menu)selectedNode.getData()).setId(savedBesoin.getId());
		// TODO sinon si selectedNode est une Reponse
		logger.info("leaving saveSelectedNode, selectedNode={}", selectedNode.getData().toString());
		logger.info("------");
	}

    public void deleteSelectedNode() {
		logger.info("entering deleteSelectedNode, selectedNode={}", selectedNode.getData().toString());
    	if (selectedNode != null) {
        	if (selectedNode.getChildCount() == 0) {
        		besoinsReponsesService.deleteBesoin(((Menu) selectedNode.getData()).getId());
		    	selectedNode.getChildren().clear();
		    	selectedNode.getParent().getChildren().remove(selectedNode);  
		    	selectedNode.setParent(null);
		    	this.selectedNode = null;
	    	}
	    	else {
        		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Impossible", "The selected node has children, cannot delete!");
        		RequestContext.getCurrentInstance().update("messages");
        		FacesContext.getCurrentInstance().addMessage(null, message);
	    		logger.info("The selected node has children, cannot delete!");
	    	}
    	}
		logger.info("leaving deleteSelectedNode");
		logger.info("------");
    }

}
