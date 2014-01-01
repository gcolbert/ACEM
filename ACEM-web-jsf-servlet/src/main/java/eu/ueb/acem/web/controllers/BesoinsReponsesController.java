package eu.ueb.acem.web.controllers;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.NodeSelectEvent;
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
			editableTreeBean.addChild(editableTreeBean.getVisibleRoot(), besoin.getNom());
		}
    }

    /**
     * Recursive function to construct Tree
    public TreeNode createTree(Tree treeObj, TreeNode rootNode) {
        TreeNode newNode = new DefaultTreeNode(treeObj, rootNode);
        List<Tree> childNodes1 = treeService.getChildNodes(treeObj.getNodeId());
        for (Tree tt : childNodes1) {
            TreeNode newNode2 = createTree(tt, newNode);
        }
        return newNode;
    }
    */
    
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
    	logger.info("début setSelectedNode({})", selectedNode);
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
    	logger.info("fin setSelectedNode({})", selectedNode);
    }

    public void onNodeSelect(NodeSelectEvent event) {
    	logger.info("BesoinsReponsesController.onNodeSelect");
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
    	logger.info("[addChildToSelectedNode, selectedNode={}]", selectedNode.getData().toString());
    	TreeNode newNode = editableTreeBean.addChild(selectedNode, "Nouveau");
    	setSelectedNode(newNode);
    	saveSelectedNode();
    }

	public void saveSelectedNode() {
		logger.info("saveSelectedNode({}) début",((Menu)selectedNode.getData()).getId(), ((Menu)selectedNode.getData()).getMenuName());
		// Si selectedNode est un Besoin
		Besoin savedBesoin = besoinsReponsesService.createOrUpdateBesoin(((Menu)selectedNode.getData()).getId(),
																			((Menu)selectedNode.getData()).getMenuName(),
																			((Menu) (selectedNode.getParent().getData())).getId());
		((Menu)selectedNode.getData()).setId(savedBesoin.getId());
		logger.info("saveSelectedNode({}) fin",((Menu)selectedNode.getData()).getId(), ((Menu)selectedNode.getData()).getMenuName());
		// TODO sinon si selectedNode est une Reponse
	}

    public void deleteSelectedNode() {
    	if (selectedNode != null) {
        	if (selectedNode.getChildCount() == 0) {
        		besoinsReponsesService.deleteBesoin(((Menu) selectedNode.getData()).getId());
		    	selectedNode.getChildren().clear();  
		    	selectedNode.getParent().getChildren().remove(selectedNode);  
		    	selectedNode.setParent(null);
		    	this.selectedNode = null;
	    	}
	    	else {
	    		logger.info("The selected node has children, cannot delete!");
	    	}
    	}
    }

}
