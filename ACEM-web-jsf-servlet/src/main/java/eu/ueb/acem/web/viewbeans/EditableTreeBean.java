/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.ueb.acem.web.viewbeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Editable Tree
 * @author Vinu Iyer
 */
@Component("editableTreeBean")
@Scope("view")
public class EditableTreeBean implements Serializable {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private final static Logger logger = LoggerFactory.getLogger(EditableTreeBean.class);
	
	private static final long serialVersionUID = -7640100553743316532L;

	private TreeNode root;
	
	private TreeNode visibleRoot;

    // Contains the Edited Node data
    private List<Menu> editedData;

    public EditableTreeBean() {
        editedData = new ArrayList<Menu>();
        root = new DefaultTreeNode();
		// We add a visible root
        visibleRoot = new DefaultTreeNode(new Menu("Besoins"), root);
    }

    public TreeNode getRoot() {
    	/*
        if (root == null) {
        	root = new DefaultTreeNode();
        	
            TreeNode mainMenu1 = new DefaultTreeNode(new Menu("Folder1"), root);
            TreeNode mainMenu2 = new DefaultTreeNode(new Menu("Folder2"), root);
            TreeNode mainMenu3 = new DefaultTreeNode(new Menu("Folder3"), root);

            TreeNode subMenu1 = new DefaultTreeNode(new Menu("SubFolder1.1"), mainMenu1);
            TreeNode subMenu2 = new DefaultTreeNode(new Menu("SubFolder1.2"), mainMenu1);

            //Menus 
            TreeNode leaf1 = new DefaultTreeNode("Menu", new Menu("Leaf1"), subMenu1);
            TreeNode leaf2 = new DefaultTreeNode("Menu", new Menu("Leaf2"), subMenu1);
            TreeNode leaf3 = new DefaultTreeNode("Menu", new Menu("Leaf3"), subMenu2);

            //Pictures 
            TreeNode subMenu3 = new DefaultTreeNode("picture", new Menu("SubFolder2.1"), mainMenu2);
            TreeNode leaf4 = new DefaultTreeNode("picture", new Menu("Leaf4"), subMenu3);
            TreeNode leaf5 = new DefaultTreeNode("picture", new Menu("Leaf5"), subMenu3);

            //Movies 
            TreeNode leaf6 = new DefaultTreeNode(new Menu("Leaf6"), mainMenu3);
            TreeNode leaf7 = new DefaultTreeNode(new Menu("Leaf7"), mainMenu3);
        }
        */
        return root;
    }

    public TreeNode getVisibleRoot() {
    	return visibleRoot;
    }
    
	public void setRoot(TreeNode root) {
		this.root = root;
	}
    
    public void saveTree() {
        iterateNodes(root);
    }

    private void iterateNodes(TreeNode node) {

    	if (node != null) {
	        for (TreeNode iterateNode : node.getChildren()) {
	            editedData.add((Menu) node.getData());
	            if (!iterateNode.isLeaf()) {
	                iterateNodes(iterateNode);
	            }
	        }
    	}
    }

    public void addChild(TreeNode parent, String label) {
    	new DefaultTreeNode(new Menu(label), parent);
    }
    
    public static class Menu implements Serializable {

		private static final long serialVersionUID = -5623188924862380160L;

		//private String menuId;
        private String menuName;


        public Menu(String menuName) {
            this.menuName = menuName;
        }
        
/*
        public Menu(String menuId, String menuName) {
            this.menuId = menuId;
            this.menuName = menuName;
        }
*/

        /*
        public String getMenuId() {
            return menuId;
        }

        public void setMenuId(String menuId) {
            this.menuId = menuId;
        }
        */

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        @Override
        public String toString() {
            //return "Menu{" + "menuId=" + menuId + ", menuName=" + menuName + '}';
        	return "Menu{ menuName=" + menuName + '}';
        }
    }

}