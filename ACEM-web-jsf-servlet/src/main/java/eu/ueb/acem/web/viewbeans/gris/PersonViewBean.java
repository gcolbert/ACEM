package eu.ueb.acem.web.viewbeans.gris;

import java.io.Serializable;

import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.web.viewbeans.rouge.OrganisationViewBean;

public class PersonViewBean implements Serializable, Comparable<PersonViewBean> {

	private static final long serialVersionUID = 4401967530594259861L;

	private Personne person;
	
	private OrganisationViewBean organisation;
	
	private String name;
	
	private String login;
	
	private String language;
	
	private Boolean administrator;
	
	public PersonViewBean() {
	}
	
	public PersonViewBean(Personne person) {
		this();
		setPerson(person);
	}

	public Personne getPerson() {
		return person;
	}

	public void setPerson(Personne person) {
		this.person = person;
		setName(person.getName());
		setLogin(person.getLogin());
		setLanguage(person.getLanguage());
		setAdministrator(person.isAdministrator());
	}

	public OrganisationViewBean getOrganisation() {
		return organisation;
	}

	public void setOrganisation(OrganisationViewBean organisation) {
		this.organisation = organisation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Boolean getAdministrator() {
		return administrator;
	}

	public void setAdministrator(Boolean administrator) {
		this.administrator = administrator;
	}

	@Override
	public int compareTo(PersonViewBean o) {
		return getPerson().compareTo(o.getPerson());
	}

	
}
