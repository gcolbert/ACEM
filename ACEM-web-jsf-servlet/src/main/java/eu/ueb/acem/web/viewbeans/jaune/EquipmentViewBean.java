/**
 *     Copyright Grégoire COLBERT 2015
 * 
 *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
 * 
 *     ACEM is free equipment: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free equipment Foundation, either version 3 of the License, or
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
package eu.ueb.acem.web.viewbeans.jaune;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.domain.beans.jaune.Equipment;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.web.viewbeans.AbstractViewBean;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

/**
 * @author Grégoire Colbert
 * @since 2014-02-25
 * 
 */
public class EquipmentViewBean extends AbstractViewBean implements ResourceViewBean, Serializable,
		Comparable<EquipmentViewBean> {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = -555050946530463265L;

	private static final Logger logger = LoggerFactory.getLogger(EquipmentViewBean.class);

	private Equipment equipment;

	private String name;

	private String iconFileName;

	private String description;

	private List<UseModeViewBean> useModeViewBeans;

	private OrganisationViewBean organisationPossessingResourceViewBean;

	private List<OrganisationViewBean> organisationsViewingResourceViewBeans;

	public EquipmentViewBean() {
		useModeViewBeans = new ArrayList<UseModeViewBean>();
	}

	public EquipmentViewBean(Equipment equipment) {
		this();
		setEquipment(equipment);
	}

	@Override
	public Equipment getDomainBean() {
		return equipment;
	}

	@Override
	public void setDomainBean(Resource resource) {
		setEquipment((Equipment) resource);
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
		setId(equipment.getId());
		setName(equipment.getName());
		setIconFileName(equipment.getIconFileName());
		setDescription(equipment.getDescription());

		for (UseMode useMode : equipment.getUseModes()) {
			logger.info("UseModeViewBean.setDomainBean : we add the useMode '{}' as a UseMode", useMode.getName());
			useModeViewBeans.add(new UseModeViewBean(useMode));
		}
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		getDomainBean().setName(name);
	}

	@Override
	public String getIconFileName() {
		return iconFileName;
	}

	@Override
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
		getDomainBean().setIconFileName(iconFileName);
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
		getDomainBean().setDescription(description);
	}

	@Override
	public OrganisationViewBean getOrganisationPossessingResourceViewBean() {
		return organisationPossessingResourceViewBean;
	}

	@Override
	public void setOrganisationPossessingResourceViewBean(OrganisationViewBean organisationViewBean) {
		this.organisationPossessingResourceViewBean = organisationViewBean;
		getDomainBean().setOrganisationPossessingResource(organisationViewBean.getDomainBean());
	}

	@Override
	public List<OrganisationViewBean> getOrganisationViewingResourceViewBeans() {
		return organisationsViewingResourceViewBeans;
	}

	@Override
	public void addOrganisationViewingResourceViewBean(OrganisationViewBean organisationViewBean) {
		organisationsViewingResourceViewBeans.add(organisationViewBean);
	}

	@Override
	public void removeOrganisationViewingResourceViewBean(OrganisationViewBean organisationViewBean) {
		organisationsViewingResourceViewBeans.remove(organisationViewBean);
	}

	@Override
	public UseModeViewBean getUseModeViewBean() {
		if (useModeViewBeans.size() > 0) {
			return useModeViewBeans.get(0);
		}
		else {
			return null;
		}
	}

	@Override
	public void setUseModeViewBean(UseModeViewBean useModeViewBean) {
		this.useModeViewBeans.set(0, useModeViewBean);
	}

	@Override
	public List<UseModeViewBean> getUseModeViewBeans() {
		return useModeViewBeans;
	}

	@Override
	public void setUseModeViewBeans(List<UseModeViewBean> useModeViewBeans) {
		this.useModeViewBeans = useModeViewBeans;
	}

	@Override
	public int compareTo(EquipmentViewBean o) {
		return name.compareTo(o.getName());
	}

}
