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

import eu.ueb.acem.domain.beans.bleu.neo4j.BesoinNode;

/**
 * @author gcolbert @since 2013-11-18
 * 
 */
public class TestUtils {
	public static BesoinNode createBesoin() {
		BesoinNode besoin = new BesoinNode();
	    besoin.setNom("Ceci est un besoin de test");
	    return besoin;
	}
/*
  public static RateUserDAO createRating(UserDAO ratingUser, UserDAO ratedUser) {
    RateUserDAO rating = new RateUserDAO();
    rating.setRatingUser(ratingUser);
    rating.setRatedUser(ratedUser);
    rating.setRate((int) Math.round(Math.random() * 4 + 1));
    rating.setRateDate(new Date());

    return rating;
  }

  public static UserDAO createUser() {
    UserDAO user = new UserDAO();
    user.setFirstname("fn_" + RandomStringUtils.randomAscii(8));
    user.setLastname("ln_" + RandomStringUtils.randomAscii(8));
    return user;
  }

  public static FriendshipDAO createFriendship(UserDAO invitingUser, UserDAO invitedUser,
      FriendshipDAO.Status status) {
    FriendshipDAO fs = new FriendshipDAO();
    fs.setInvitingUser(invitingUser);
    fs.setInvitedUser(invitedUser);
    fs.setStatus(status);
    fs.setInvitationDate(new Date());
    return fs;
  }

  public static int getQueryResultCount(Iterable<?> iterable) {
    int cpt = 0;
    Iterator<?> iter = iterable.iterator();
    while (iter.hasNext()) {
      iter.next();
      cpt++;

    }
    return cpt;
  }
  */
}
