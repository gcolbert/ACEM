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
package eu.ueb.acem.services;

import java.io.File;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Grégoire Colbert
 * @since 2014-03-20
 * 
 */
@Service("imagesService")
public class ImagesServiceImpl implements ImagesService {

	@Value("${images.path}")
	private String localPathToImagesFolder;

	@Override
	public File getImage(String imageFileName) {
		String localPath = localPathToImagesFolder;
		if (! localPath.endsWith(File.separator)) {
			localPath += File.separator;
		}
		File file = new File(localPath + imageFileName);
		return file;
	}

	@Override
	public File getImage(Path imageFilePath) {
		File file = imageFilePath.toFile();
		return file;
	}
	
}
