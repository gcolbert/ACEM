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
 * @author gcolbert @since 2013-11-20
 *
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
		// We add a visible root so that it is possible to right click and add children nodes
        visibleRoot = new DefaultTreeNode(new Menu("Besoins"), root);
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getVisibleRoot() {
    	return visibleRoot;
    }
    
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	/*
    public void saveTree() {
        iterateNodes(root);
    }

    public void saveNode(TreeNode node) {
    	iterateNodes(node);
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
    	else {
    		logger.error("iterateNodes(null)");
    	}
    	logger.info("iterateNodes, getChildCount={}, editedData.size={}",root.getChildCount(),editedData.size());
    }
    */

    public TreeNode addChild(TreeNode parent, String label) {
    	return new DefaultTreeNode(new Menu(label), parent);
    }
    
    public static class Menu implements Serializable {

		private static final long serialVersionUID = -5623188924862380160L;

        private String menuName;

        public Menu(String menuName) {
            this.menuName = menuName;
        }

        public String getMenuName() {
            return menuName;
        }

        public void setMenuName(String menuName) {
            this.menuName = menuName;
        }

        @Override
        public String toString() {
        	return "Menu{ menuName=" + menuName + '}';
        }
    }

}