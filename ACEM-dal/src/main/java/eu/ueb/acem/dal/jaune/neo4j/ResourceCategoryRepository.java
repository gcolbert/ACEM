package eu.ueb.acem.dal.jaune.neo4j;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;

import eu.ueb.acem.dal.GenericRepository;
import eu.ueb.acem.domain.beans.jaune.neo4j.ResourceCategoryNode;

public interface ResourceCategoryRepository extends GenericRepository<ResourceCategoryNode> {

	@Query(value = "MATCH (n:ResourceCategory) WHERE id(n)=({id}) RETURN count(n)")
	Long count(@Param("id") Long id);

}
