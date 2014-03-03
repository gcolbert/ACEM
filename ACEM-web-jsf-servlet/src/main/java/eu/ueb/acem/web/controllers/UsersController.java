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
package eu.ueb.acem.web.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.services.UsersService;
import eu.ueb.acem.web.viewbeans.gris.PersonViewBean;

@Controller("usersController")
@Scope("view")
public class UsersController extends AbstractContextAwareController {

	private static final long serialVersionUID = -977386846045010683L;

	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);

	private List<PersonViewBean> personViewBeans;

	@Autowired
	private UsersService usersService;

	public UsersController() {
		personViewBeans = new ArrayList<PersonViewBean>();
	}

	@PostConstruct
	public void initUsersController() {
		personViewBeans.clear();
		Set<Personne> persons = usersService.getPersons();
		for (Personne person : persons) {
			personViewBeans.add(new PersonViewBean(person));
		}
	}

	public List<PersonViewBean> getPersonViewBeans() {
		return personViewBeans;
	}

	public void setPersonViewBeans(List<PersonViewBean> personViewBeans) {
		this.personViewBeans = personViewBeans;
	}
	
	public void setAdministrator(PersonViewBean personViewBean) {
		logger.info("setAdministrator({})", personViewBean.getAdministrator());
		personViewBean.getPerson().setAdministrator(personViewBean.getAdministrator());
		usersService.updatePerson(personViewBean.getPerson());
	}

}
