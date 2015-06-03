///**
// *     Copyright Université Européenne de Bretagne 2012-2015
// * 
// *     This file is part of Atelier de Création d'Enseignement Multimodal (ACEM).
// * 
// *     ACEM is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// * 
// *     ACEM is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// * 
// *     You should have received a copy of the GNU General Public License
// *     along with ACEM.  If not, see <http://www.gnu.org/licenses/>
// */
//package eu.ueb.acem.dal.tests.rouge.jpa;
//
//import javax.inject.Inject;
//
//import eu.ueb.acem.dal.gris.PersonDAO;
//import eu.ueb.acem.dal.jaune.ResourceDAO;
//import eu.ueb.acem.dal.rouge.OrganisationDAO;
//import eu.ueb.acem.dal.tests.rouge.AbstractOrganisationDAOTest;
//import eu.ueb.acem.domain.beans.gris.Teacher;
//import eu.ueb.acem.domain.beans.jaune.Equipment;
//import eu.ueb.acem.domain.beans.rouge.AdministrativeDepartment;
//import eu.ueb.acem.domain.beans.rouge.Community;
//import eu.ueb.acem.domain.beans.rouge.Institution;
//import eu.ueb.acem.domain.beans.rouge.TeachingDepartment;
//
//public class OrganisationDAOTest extends AbstractOrganisationDAOTest {
//
//	@Inject
//	private OrganisationDAO<Long, Community> communityDAO;
//
//	@Inject
//	private OrganisationDAO<Long, Institution> institutionDAO;
//
//	@Inject
//	private OrganisationDAO<Long, AdministrativeDepartment> administrativeDepartmentDAO;
//
//	@Inject
//	private OrganisationDAO<Long, TeachingDepartment> teachingDepartmentDAO;
//
//	@Inject
//	private PersonDAO<Long, Teacher> teacherDAO;
//
//	@Inject
//	private ResourceDAO<Long, Equipment> equipmentDAO;
//
//	@Override
//	protected OrganisationDAO<Long, Community> getCommunityDAO() {
//		return communityDAO;
//	}
//
//	@Override
//	protected OrganisationDAO<Long, Institution> getInstitutionDAO() {
//		return institutionDAO;
//	}
//
//	@Override
//	protected OrganisationDAO<Long, AdministrativeDepartment> getAdministrativeDepartmentDAO() {
//		return administrativeDepartmentDAO;
//	}
//
//	@Override
//	protected OrganisationDAO<Long, TeachingDepartment> getTeachingDepartmentDAO() {
//		return teachingDepartmentDAO;
//	}
//
//	@Override
//	protected PersonDAO<Long, Teacher> getTeacherDAO() {
//		return teacherDAO;
//	}
//
//	@Override
//	protected ResourceDAO<Long, Equipment> getEquipmentDAO() {
//		return equipmentDAO;
//	}
//
//	
//}
