package eu.ueb.acem.dal;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

public interface GenericRepository<N> extends GraphRepository<N>, RelationshipOperationsRepository<N> {

	Iterable<N> findByName(String name);

}
