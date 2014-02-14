package eu.ueb.acem.services;

import eu.ueb.acem.domain.beans.rouge.Etablissement;

public interface OrganisationsService {

	Long countInstitutions();

	Etablissement createInstitution(String string);

	Etablissement retrieveInstitution(Long id);
	
	Etablissement updateInstitution(Etablissement institution);

	Boolean deleteInstitution(Long id);

	void deleteAllInstitutions();

}
