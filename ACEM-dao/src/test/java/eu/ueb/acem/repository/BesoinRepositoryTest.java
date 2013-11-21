/**
 *     Copyright Grégoire COLBERT 2013
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
package eu.ueb.acem.repository;

//import javax.annotation.Resource;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import eu.ueb.acem.dao.bleu.neo4j.BesoinRepository;
import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;

/**
 * @author gcolbert @since 2013-11-19
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class BesoinRepositoryTest extends TestCase {

  /** Besoin repository */
  @Autowired
  private BesoinRepository besoinRepository;
  
  /**
   * Create user in repo
   */
  @Test
  public final void t0TestCreateBesoin() {
    // Clear repo
	besoinRepository.deleteAll();
  	assertEquals(0, besoinRepository.count());
    
  	// Save besoin
  	BesoinNode b1 = TestUtils.createBesoin();
    b1 = besoinRepository.save(b1);
    
    // Check if besoin has been saved
    assertEquals(1, besoinRepository.count());
    
    // Get besoin
    BesoinNode b1bis = besoinRepository.findOne(b1.getId());
    assertNotNull(b1bis);
  }

  /**
   * Search besoins
   */
  /*
  @Test
  public final void t1TestSearchBesoin() {
  	// Check if previous user is still in repo
    assertEquals(1, besoinRepo.count());
    
    Besoin u1 = TestUtils.createUser();
    Besoin u2 = TestUtils.createUser();
    Besoin u3 = TestUtils.createUser();
    u1.setFirstname("Bob");
    u1.setLastname("Zebre");
    u2.setFirstname("Bobby");
    u2.setLastname("Atate");
    u3.setFirstname("Tom");
    u3.setLastname("Bobinet");
    u1 = userRepo.save(u1);
    u2 = userRepo.save(u2);
    u3 = userRepo.save(u3);

    // Check if users have been saved
    assertEquals(4, besoinRepo.count());
    
    final Pageable pager = new QueryPager(0, 3, new Sort("user.lastname"));

    Iterable<Besoin> result = besoinRepo.searchUser("firstname:bob* OR lastname:bob*", pager);
    assertNotNull(result);
    assertEquals(3, TestUtils.getQueryResultCount(result));

    final Besoin besoin = result.iterator().next();
    assertEquals("Atate", besoin.getLastname());
    
    // Clear repo
    besoinRepo.deleteAll();
  	assertEquals(0, besoinRepo.count());
  }
  	*/
}
