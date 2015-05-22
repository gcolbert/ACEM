/**
 *     Copyright Grégoire COLBERT 2015
 * 
 *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
 * 
 *     ACEM is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 * 
 *     ACEM is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 * 
 *     You should have received a copy of the GNU General Public License
 *     along with ACEM.  If not, see <http://www.gnu.org/licenses/>
 */
package eu.ueb.acem.dal.tests.violet.neo4j;

import javax.inject.Inject;

import eu.ueb.acem.dal.tests.violet.AbstractDegreeDAOTest;
import eu.ueb.acem.dal.violet.ClassDAO;
import eu.ueb.acem.dal.violet.CourseDAO;
import eu.ueb.acem.dal.violet.CreditDAO;
import eu.ueb.acem.dal.violet.DegreeDAO;

public class DegreeDAOTest extends AbstractDegreeDAOTest {

	@Inject
	private DegreeDAO<Long> degreeDAO;

	@Inject
	private CreditDAO<Long> creditDAO;

	@Inject
	private CourseDAO<Long> courseDAO;

	@Inject
	private ClassDAO<Long> classDAO;

	@Override
	protected DegreeDAO<Long> getDegreeDAO() {
		return degreeDAO;
	}

	@Override
	protected CreditDAO<Long> getCreditDAO() {
		return creditDAO;
	}

	@Override
	protected CourseDAO<Long> getCourseDAO() {
		return courseDAO;
	}

	@Override
	protected ClassDAO<Long> getClassDAO() {
		return classDAO;
	}

}
