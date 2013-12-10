/*******************************************************************************
 * Copyright (c) 2013 gcolbert.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl.html
 * 
 * Contributors:
 *     gcolbert - initial API and implementation
 ******************************************************************************/
package eu.ueb.acem.dal.gris.neo4j;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.neo4j.repository.RelationshipOperationsRepository;

import eu.ueb.acem.domain.beans.gris.neo4j.EnseignantNode;

/**
 * @author gcolbert @since 2013-11-20
 *
 */
public interface EnseignantRepository extends GraphRepository<EnseignantNode>, RelationshipOperationsRepository<EnseignantNode> {

}
