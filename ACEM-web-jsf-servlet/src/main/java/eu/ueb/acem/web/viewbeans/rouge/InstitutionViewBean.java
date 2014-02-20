package eu.ueb.acem.web.viewbeans.rouge;

import java.io.Serializable;

import eu.ueb.acem.domain.beans.rouge.Etablissement;

public class InstitutionViewBean implements Serializable, Comparable<InstitutionViewBean> {

	private static final long serialVersionUID = 6170498010377898612L;

	private Etablissement institution;

	private Long id;

	private String name;

	public InstitutionViewBean() {
	}

	public InstitutionViewBean(Etablissement institution) {
		this();
		setInstitution(institution);
	}

	public Etablissement getInstitution() {
		return institution;
	}

	public void setInstitution(Etablissement institution) {
		this.institution = institution;
		setId(institution.getId());
		setName(institution.getName());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int compareTo(InstitutionViewBean o) {
		return name.compareTo(o.getName());
	}

}
