package eu.ueb.acem.dal.bleu;

import java.util.Set;

import eu.ueb.acem.dal.DAO;

public interface PedagogicalNeedDAO<ID, E> extends DAO<ID, E> {

	Set<E> retrieveNeedsAtRoot();

}
