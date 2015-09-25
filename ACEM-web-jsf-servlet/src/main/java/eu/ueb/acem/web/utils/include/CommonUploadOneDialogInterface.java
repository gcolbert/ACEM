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
	 * Returns the commonDialog bean
	 * 
	 * @return the bean
	 */
	public CommonUploadOneDialog getCommonUploadOneDialog();

	/**
	 * Sets the selected object from the dialog.
	 * 
	 * @param temporaryFilePath
	 *            The temporary path where the uploaded file is written at the
	 *            end of the upload process
	 */
	public void setSelectedFromCommonUploadOneDialog(Path temporaryFilePath);

	/**
	 * Get DomainService from caller
	 * 
	 * @return The caller's instance of DomainService
	 */
	public DomainService getDomainService();

}
