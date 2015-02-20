package eu.ueb.acem.domain.beans.bleu;

import java.io.Serializable;

public interface PedagogicalAdvice extends Serializable {

	Long getId();

	String getName();

	void setName(String name);
	
	String getDescription();
	
	void setDescription(String description);
	
}
