package eu.ueb.acem.dal.jaune;

import java.io.Serializable;
import java.util.Collection;

import eu.ueb.acem.dal.DAO;
import eu.ueb.acem.domain.beans.jaune.Resource;
import eu.ueb.acem.domain.beans.jaune.ResourceCategory;

public interface ResourceDAO<ID extends Serializable, E extends Resource> extends DAO<ID, E> {

	Collection<ResourceCategory> getCategories();
	
	Collection<E> retrieveAllWithCategory(ResourceCategory category);

}
