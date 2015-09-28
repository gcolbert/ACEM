/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.domain.beans.jpa.jaune;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import eu.ueb.acem.domain.beans.bleu.TeachingMode;
import eu.ueb.acem.domain.beans.jaune.Documentation;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;
import eu.ueb.acem.domain.beans.jaune.UseMode;
import eu.ueb.acem.domain.beans.jpa.AbstractEntity;
import eu.ueb.acem.domain.beans.jpa.bleu.TeachingModeEntity;
import eu.ueb.acem.domain.beans.jpa.rouge.OrganisationEntity;
import eu.ueb.acem.domain.beans.rouge.Organisation;

/**
 * @author Grégoire Colbert
 * @since 2015-05-28
 * 
 */
@Entity(name = "Resource")
@DiscriminatorColumn(length=50) /* needed because PedagogicalAndDocumentaryResource is longer than 30 */
public abstract class ResourceEntity extends AbstractEntity implements Resource {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 6066042683646249966L;

	private Long idSource;

	private String iconFileName;

	@Lob
	private String description;

	private boolean forSession;

	private boolean forActivity;

	@ManyToMany(targetEntity = ResourceCategoryEntity.class, fetch = FetchType.LAZY, mappedBy = "resources")
	private Set<ResourceCategory> categories = new HashSet<ResourceCategory>(0);

	@ManyToMany(targetEntity = UseModeEntity.class, fetch = FetchType.LAZY)
	private Set<UseMode> useModes = new HashSet<UseMode>(0);

	@ManyToOne(targetEntity = OrganisationEntity.class, fetch = FetchType.LAZY)
	private Organisation organisationPossessingResource;

	@ManyToOne(targetEntity = OrganisationEntity.class, fetch = FetchType.LAZY)
	private Organisation organisationSupportingResource;

	@ManyToMany(targetEntity = OrganisationEntity.class, fetch = FetchType.LAZY)
	private Set<Organisation> organisationsHavingAccessToResource = new HashSet<Organisation>(0);

	@ManyToMany(targetEntity = DocumentationEntity.class, fetch = FetchType.LAZY, mappedBy = "resources")
	private Set<Documentation> documentations = new HashSet<Documentation>(0);

	@ManyToOne(targetEntity = TeachingModeEntity.class)
	private TeachingMode teachingMode;

	public ResourceEntity() {
	}

	public ResourceEntity(String name) {
		this();
		setName(name);
	}

	@Override
	public Long getIdSource() {
		return idSource;
	}

	@Override
	public void setIdSource(Long idSource) {
		this.idSource = idSource;
	}

	@Override
	public String getDescription() {
		return description;
	}

	@Override
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String getIconFileName() {
		return iconFileName;
	}

	@Override
	public void setIconFileName(String iconFileName) {
		this.iconFileName = iconFileName;
	}

	@Override
	public boolean isForPedagogicalActivity() {
		return forActivity;
	}

	@Override
	public void setForPedagogicalActivity(boolean isForActivity) {
		this.forActivity = isForActivity;
	}

	@Override
	public boolean isForPedagogicalSession() {
		return forSession;
	}

	@Override
	public void setForPedagogicalSession(boolean isForSession) {
		this.forSession = isForSession;
	}

	@Override
	public Set<UseMode> getUseModes() {
		return useModes;
	}
	
	@Override
	public void setUseModes(Set<UseMode> useModes) {
		this.useModes = useModes;
	}

	@Override
	public Set<ResourceCategory> getCategories() {
		return categories;
	}

	@Override
	public void setCategories(Set<ResourceCategory> categories) {
		this.categories = categories;
	}

	@Override
	public Organisation getOrganisationPossessingResource() {
		return organisationPossessingResource;
	}
	
	@Override
	public void setOrganisationPossessingResource(Organisation organisation) {
		this.organisationPossessingResource = organisation;
	}

	@Override
	public Organisation getOrganisationSupportingResource() {
		return organisationSupportingResource;
	}

	@Override
	public void setOrganisationSupportingResource(Organisation organisation) {
		this.organisationSupportingResource = organisation;
	}

	@Override
	public Set<Organisation> getOrganisationsHavingAccessToResource() {
		return organisationsHavingAccessToResource;
	}

	@Override
	public void setOrganisationsHavingAccessToResource(Set<Organisation> organisations) {
		this.organisationsHavingAccessToResource = organisations;
	}

	@Override
	public Set<Documentation> getDocumentations() {
		return documentations;
	}

	@Override
	public void setDocumentations(Set<Documentation> documentations) {
		this.documentations = documentations;
	}

	@Override
	public TeachingMode getTeachingMode() {
		return teachingMode;
	}

	@Override
	public void setTeachingMode(TeachingMode teachingMode) {
		this.teachingMode = teachingMode;
	}

	@Override
	public int compareTo(Resource o) {
		return getName().compareTo(o.getName());
	}

}
