package eu.ueb.acem.dal;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

//@Repository("genericDAO")
public class GenericDAO<ID extends Serializable, E, N extends E> implements DAO<Long, E> {

	// @Autowired
	protected GenericRepository<N> repository;

	@Override
	public Boolean exists(Long id) {
		return (id != null) ? repository.exists(id) : false;
	}

	@Override
	public E create(E entity) {
		return (E) repository.save((N) entity);
	}

	@Override
	public E retrieveById(Long id) {
		return (id != null) ? (E) repository.findOne(id) : null;
	}

	@Override
	public E retrieveByName(String name) {
		return (E) repository.findByPropertyValue("name", name);
	}

	@Override
	public Collection<E> retrieveAll() {
		Iterable<N> endResults = repository.findAll();
		Collection<E> collection = new HashSet<E>();
		if (endResults.iterator() != null) {
			Iterator<N> iterator = endResults.iterator();
			while (iterator.hasNext()) {
				collection.add(iterator.next());
			}
		}
		return collection;
	}

	@Override
	public E update(E entity) {
		return (E) repository.save((N) entity);
	}

	@Override
	public void delete(E entity) {
		repository.delete((N) entity);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
	}

	@Override
	public Long count() {
		return repository.count();
	}

}
