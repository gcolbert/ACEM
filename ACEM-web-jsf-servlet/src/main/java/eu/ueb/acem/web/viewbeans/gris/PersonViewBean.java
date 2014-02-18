package eu.ueb.acem.web.viewbeans.gris;

import java.io.Serializable;

import eu.ueb.acem.domain.beans.gris.Personne;

public class PersonViewBean implements Serializable {

	private static final long serialVersionUID = 4401967530594259861L;

	Personne person;
	
	public PersonViewBean(Personne person) {
		this.person = person;
	}

	public Personne getPerson() {
		return person;
	}

	public void setPerson(Personne person) {
		this.person = person;
	}

}
