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
package eu.ueb.acem.web.utils.include;

import java.nio.file.Path;

import eu.ueb.acem.services.DomainService;


/**
 * Interface for CommonUploadOneDialog
 * 
 * @author rlorthio
 *
 */
public interface CommonUploadOneDialogInterface {
	
	/**
	 * Return the commonDialog Bean
	 * @return the bean
	 */
	public CommonUploadOneDialog getCommonUploadOneDialog();

	/**
	 * Set the selected object from the Dialog to the caller 
	 * If null there is an error on temporary file writing
	 * @param temporaryFilePath
	 * @param originalFileName
	 */
	public void setSelectedFromCommonUploadOneDialog(Path temporaryFilePath, String originalFileName);
	
	/**
	 * Get DomainService from caller
	 * @return
	 */
	public DomainService getDomainService();

}
