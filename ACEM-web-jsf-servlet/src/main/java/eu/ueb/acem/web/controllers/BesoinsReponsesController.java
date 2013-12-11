package eu.ueb.acem.web.controllers;

import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.springframework.beans.factory.annotation.Autowired;

import eu.ueb.acem.domain.beans.bleu.Besoin;
import eu.ueb.acem.services.BesoinsReponsesService;
import eu.ueb.acem.web.viewbeans.TreeBean;

@ManagedBean(name="besoinsReponsesController")
@SessionScoped
public class BesoinsReponsesController extends AbstractContextAwareController {

	private static final long serialVersionUID = 3305497053688875560L;

	@Autowired
	private BesoinsReponsesService besoinsReponsesService;
	
	@Autowired
	private TreeBean treeBean;
	
	public BesoinsReponsesController() {
		
	}
	
	public TreeNode getData() {
		Set<Besoin> besoinsRacines = besoinsReponsesService.getBesoinsRacines();
		TreeNode root = new DefaultTreeNode("Root", null);
		
		for (Besoin besoin : besoinsRacines) {
			TreeNode node = new DefaultTreeNode(besoin.getNom(), root);
		}

		treeBean.setRoot(root);
		return treeBean.getRoot();
	}
}
