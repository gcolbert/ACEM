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

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;

/**
 * A service that manages loading images (e.g. icons) from the server.
 * 
 * @author Grégoire Colbert
 * @since 2014-03-20
 * 
 */
public interface ImagesService extends Serializable {

	/**
	 * Returns the image whose name is given: the complete path will be the
	 * concatenation of the "images.path" property and the given file name. If
	 * the "images.path" directory value doesn't end with a folder separator, it
	 * will be automatically appended before the concatenation.
	 * 
	 * @param imageFileName
	 *            The name of the image to load
	 * @return the corresponding File
	 */
	File getImage(String imageFileName);

	/**
	 * Returns the image whose absolute path is given.
	 * 
	 * @param imageFilePath
	 *            The absolute path of the image to load
	 * @return the corresponding File
	 */
	File getImage(Path imageFilePath);

	/**
	 * Returns the value of "images.path" config property where images can be
	 * stored and read.
	 * 
	 * @return the images directory
	 */
	String getImagesDirectory();

}
