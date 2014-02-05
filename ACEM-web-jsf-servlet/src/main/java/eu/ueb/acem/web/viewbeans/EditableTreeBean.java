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

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Grégoire Colbert @since 2013-11-20
 * 
 */
@Component("editableTreeBean")
@Scope("view")
public class EditableTreeBean implements Serializable {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(EditableTreeBean.class);

	private static final long serialVersionUID = -7640100553743316532L;

	private TreeNode root;

	private TreeNode visibleRoot;

	public EditableTreeBean() {
		// For some reason, the root of the tree is not visible
		root = new DefaultTreeNode(new TreeNodeData(null, "Root", null), null);
		// Therefore, we add a visible root so that it is possible to right
		// click and add children nodes
		visibleRoot = new DefaultTreeNode(new TreeNodeData(null, "Default visible root", null), root);
	}

	public TreeNode getRoot() {
		return root;
	}

	public TreeNode getVisibleRoot() {
		return visibleRoot;
	}

	public void setVisibleRootLabel(String label) {
		((TreeNodeData) visibleRoot.getData()).setLabel(label);
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode addChild(TreeNode parent, Long id, String label, String concept) {
		TreeNode child = new DefaultTreeNode(concept, new TreeNodeData(id, label, concept), parent);
		parent.setExpanded(true);
		return child;
	}

	public static class TreeNodeData implements Serializable {

		private static final long serialVersionUID = -5623188924862380160L;

		private Long id = null;

		private String label;

		private String concept;

		public TreeNodeData(Long id, String label, String concept) {
			this.id = id;
			this.label = label;
			this.concept = concept;
		}

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}

		public String getConcept() {
			return concept;
		}

		public void setConcept(String concept) {
			this.concept = concept;
		}

		public String getStyleClass() {
			return getConcept();
		}

		@Override
		public String toString() {
			return "Menu{ id=" + id + ", label=" + label + ", concept=" + concept + " }";
		}

	}

}