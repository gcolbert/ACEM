/**
 *     Copyright Université Européenne de Bretagne 2012-2015
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
package eu.ueb.acem.dal.tests.violet;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import eu.ueb.acem.dal.violet.ClassDAO;
import eu.ueb.acem.dal.violet.CourseDAO;
import eu.ueb.acem.dal.violet.CreditDAO;
import eu.ueb.acem.dal.violet.DegreeDAO;

/**
 * @author Grégoire Colbert
 * @since 2014-03-05
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/dal-common-test-context.xml")
public abstract class AbstractDegreeDAOTest extends TestCase {

	/**
	 * For Logging.
	 */
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(AbstractDegreeDAOTest.class);

	public AbstractDegreeDAOTest() {

	}
	
	protected abstract DegreeDAO<Long> getDegreeDAO();
	
	protected abstract CreditDAO<Long> getCreditDAO();
	
	protected abstract CourseDAO<Long> getCourseDAO();
	
	protected abstract ClassDAO<Long> getClassDAO();

	/**
	 * Create
	 */
	@Test
	@Transactional
	@Rollback(true)
	public final void t01_TestCreateDegree() {
	}
}
