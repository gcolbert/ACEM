package eu.ueb.acem.web.controllers;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import eu.ueb.acem.domain.beans.gris.Personne;
import eu.ueb.acem.services.UsersService;

@Controller("usersController")
@Scope("session")
public class UsersController extends AbstractContextAwareController {

	private static final long serialVersionUID = -977386846045010683L;

	Collection<Personne> users;

	@Autowired
	private UsersService usersService;

	public UsersController() {
		
	}

	@PostConstruct
	public void initUsers() {
		users = usersService.getUsers();
	}

	public Collection<Personne> getUsers() {
		return users;
	}

	public void setUsers(Collection<Personne> users) {
		this.users = users;
	}

}
