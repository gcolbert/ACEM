package eu.ueb.acem.services;

import eu.ueb.acem.domain.beans.bleu.Besoin;

public interface BesoinReponseService {

	Besoin addBesoin(Besoin parent, Besoin nouveauBesoin);
	
	
}
