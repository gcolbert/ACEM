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
package eu.ueb.acem.services;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import eu.ueb.acem.dal.common.bleu.TeachingModeDAO;
import eu.ueb.acem.domain.beans.bleu.TeachingMode;

/**
 * Implementation of TeachingModesService.
 * 
 * @author Grégoire Colbert
 * @since 2015-07-16
 */
@Service("teachingModesService")
public class TeachingModesServiceImpl implements TeachingModesService {

	/**
	 * For serialization.
	 */
	private static final long serialVersionUID = 7611992932665646689L;

	@Inject
	private TeachingModeDAO<Long> teachingModeDAO;

	@Override
	public Long countTeachingModes() {
		return teachingModeDAO.count();
	}

	@Override
	public TeachingMode createTeachingMode(String name, String description) {
		return teachingModeDAO.create(name, description);
	}

	@Override
	public TeachingMode retrieveTeachingMode(Long id, boolean initialize) {
		return teachingModeDAO.retrieveById(id, initialize);
	}

	@Override
	public TeachingMode updateTeachingMode(TeachingMode teachingMode) {
		return teachingModeDAO.update(teachingMode);
	}

	@Override
	public Boolean deleteTeachingMode(Long id) {
		if (teachingModeDAO.exists(id)) {
			teachingModeDAO.delete(teachingModeDAO.retrieveById(id));
		}
		return !teachingModeDAO.exists(id);
	}

}
