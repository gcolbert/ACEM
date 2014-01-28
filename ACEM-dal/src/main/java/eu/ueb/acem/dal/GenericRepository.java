package eu.ueb.acem.dal;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.NamedIndexRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

public interface GenericRepository<E> extends GraphRepository<E>, RelationshipOperationsRepository<E>, NamedIndexRepository<E> {

}
