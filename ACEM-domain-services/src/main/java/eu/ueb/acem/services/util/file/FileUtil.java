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
package eu.ueb.acem.services.util.file;

import java.awt.Dimension;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.imageio.stream.ImageInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import eu.ueb.acem.services.exceptions.ServiceException;

/**
 * Technical class for File operations.
 * 
 * @author rlorthio
 *
 */
public final class FileUtil {

	/**
	 * For logging.
	 */
	private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);
	
	/**
	 * Constructor.
	 */
	private FileUtil() {
		super();
	}

	public static void createDirectory(String parentDirectory, String directory) {
		File f = new File(parentDirectory);
		if (!f.exists()) {
			throw new ServiceException("SERVICE_PARENTDIRECTORY_NOT_EXISTS");
		}
		File fc = new File(FileUtil.getNormalizedFilePath(parentDirectory + "/"
				+ directory));
		if (!fc.exists() && !fc.mkdir()) {
				throw new ServiceException("SERVICE_DIRECTORY_NOT_CREATED");
		}
	}

	public static void renameDirectoryOrFile(String sourcePath,
			String targetPath) {
		File f = new File(sourcePath);
		if (!f.exists()) {
			throw new ServiceException("SERVICE_PARENTDIRECTORY_NOT_EXISTS");
		}
		if (!f.renameTo(new File(targetPath))) {
			throw new ServiceException(
					"SERVICE_DIRECTORY_OR_FILE_RENAME_FAILED");
		}
	}

	/**
	 * Method to copy an InputStream in an OutputStream.
	 * 
	 * @param in
	 *            the inputStream to copy
	 * @param out
	 *            the target outputStream
	 * @throws IOException
	 *             If the first byte cannot be read for any reason other than
	 *             the end of the file, if the input stream has been closed, or
	 *             if some other I/O error occurs.
	 */
	public static void copyInputStream(final InputStream in,
			final OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int len;

		while ((len = in.read(buffer)) >= 0) {
			out.write(buffer, 0, len);
		}

		in.close();
		out.close();
	}

	/**
	 * Method to unzip a file in target directory.
	 * 
	 * @param directoryTarget
	 *            target directory for unzip
	 * @param inputFile
	 *            file to unzip
	 * @param deleteFileAfterUnZip
	 *            if true delete the original zip file
	 * @throws IOException
	 *             If an I/O error has occurred
	 */
	public static void unzipFile(final String directoryTarget,
			final String inputFile, final boolean deleteFileAfterUnZip)
			throws IOException {
		Enumeration<? extends ZipEntry> entries;
		ZipFile zipFile;
		File file;

		zipFile = new ZipFile(FileUtil.getNormalizedFilePath(inputFile));
		entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			if (entry.isDirectory()) {
				(new File(FileUtil.getNormalizedFilePath(directoryTarget
						+ File.separator + entry.getName()))).mkdirs();
				continue;
			} else {
				File f = new File(
						FileUtil.getNormalizedFilePath(directoryTarget
								+ File.separator + entry.getName()));
				if (!new File(f.getParent()).exists()) {
					new File(f.getParent()).mkdirs();
				}
				copyInputStream(
						zipFile.getInputStream(entry),
						new BufferedOutputStream(new FileOutputStream(FileUtil
								.getNormalizedFilePath(directoryTarget
										+ File.separator + entry.getName()))));
			}
		}
		zipFile.close();

		if (deleteFileAfterUnZip) {
			file = new File(inputFile);
			file.delete();
		}
	}

	public static boolean verifFilesNames(final String inputFile)
			throws IOException {
		Enumeration<? extends ZipEntry> entries;
		ZipFile zipFile;
		Boolean verifFilesNames = true;
		zipFile = new ZipFile(inputFile);
		entries = zipFile.entries();
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			verifFilesNames = verifFileName(entry.getName());
			if (!verifFilesNames) {
				zipFile.close();
				return false;
			}
		}
		zipFile.close();
		return verifFilesNames;
	}

	private static Boolean verifFileName(final String s) {
		return s.matches("(\\w|-|_|&|\\\\|/|\\.)+");
	}

	/**
	 * Method to copy a file from Upload temp file.
	 * 
	 * @param outputFile
	 *            the target File
	 * @param in
	 *            the inputStream to copy
	 * @throws IOException
	 *             If an I/O error has occurred
	 */
	public static void copyInputStream(final String outputFile,
			final InputStream in) throws IOException {
		FileOutputStream fout = null;
		byte[] buf = new byte[1024];
		int read = 0;
		fout = new FileOutputStream(outputFile);
		while ((read = in.read(buf)) > 0) {
			fout.write(buf, 0, read);
		}
		fout.flush();
		fout.close();
	}

	/**
	 * Delete an non empty directory.
	 * 
	 * @param directoryToDelete The directory to delete
	 * @return true if deleted
	 */
	public static boolean deleteDir(final File directoryToDelete) {
		if (directoryToDelete.isDirectory()) {
			String[] children = directoryToDelete.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(directoryToDelete,
						children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return directoryToDelete.delete();
	}

	/**
	 * Delete an non empty directory.
	 * 
	 * @param directoryNameToDelete The name of the directory to delete
	 * @return true if deleted
	 */
	public static boolean deleteDir(final String directoryNameToDelete) {
		File directoryToDelete = new File(
				FileUtil.getNormalizedFilePath(directoryNameToDelete));

		if (directoryToDelete.isDirectory()) {
			String[] children = directoryToDelete.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(directoryToDelete,
						children[i]));
				if (!success) {
					return false;
				}
			}
		}

		// The directory is now empty so delete it
		return directoryToDelete.delete();
	}

	/**
	 * Method to get all the file that respond to a filter. If recursiveSearch
	 * is set to true the same search will be done in all the subdirectories
	 * 
	 * @param pathToSearch A path where the files will be searched
	 * @param filter A filename filter
	 * @param recursiveSearch Set to true for a recursive search
	 * @return collection of filenames
	 */
	public static Collection<String> getFiles(final String pathToSearch,
			final FilenameFilter filter, final boolean recursiveSearch) {
		Collection<String> list = null;
		DirectoryFilenameFilter directoryFilenameFilter = new DirectoryFilenameFilter();
		File directory = new File(pathToSearch);
		if (directory.exists() && directory.isDirectory()) {
			File[] subfiles = directory.listFiles(filter);
			list = new ArrayList<String>();
			if (subfiles != null) {
				for (int i = 0; i < subfiles.length; i++) {
					File subfile = subfiles[i];
					list.add(subfile.getPath().substring(
							directory.getPath().length()));
				}
			}
			if (recursiveSearch) {
				File[] subdirectories = directory
						.listFiles(directoryFilenameFilter);
				if (subdirectories != null) {
					for (int i = 0; i < subdirectories.length; i++) {
						File subdirectorie = subdirectories[i];
						getFiles(list, directory, subdirectorie, filter,
								directoryFilenameFilter);
					}
				}
			}

		}
		return list;
	}

	/**
	 * Use by public getFiles.
	 * 
	 * @param list A list
	 * @param directory A directory
	 * @param filter A filter
	 * @param directoryFilenameFilter
	 */
	private static void getFiles(final Collection<String> list,
			final File pathToSearch, final File directory,
			final FilenameFilter filter,
			final FilenameFilter directoryFilenameFilter) {
		File[] subfiles = directory.listFiles(filter);
		if (subfiles != null) {
			for (int i = 0; i < subfiles.length; i++) {
				File subfile = subfiles[i];
				list.add(subfile.getPath().substring(
						pathToSearch.getPath().length()));
			}
		}
		File[] subdirectories = directory.listFiles(directoryFilenameFilter);
		if (subdirectories != null) {
			for (int i = 0; i < subdirectories.length; i++) {
				File subdirectorie = subdirectories[i];
				getFiles(list, pathToSearch, subdirectorie, filter,
						directoryFilenameFilter);
			}
		}
	}

	/**
	 * Copy a directory from strPath to dstPath (Content only).
	 * 
	 * @param srcPath Source path
	 * @param dstPath Destination path
	 * @return true if copy Ok
	 * @throws IOException 
	 *             If an I/O error has occurred
	 */
	public static boolean copyDirectory(final File srcPath, final File dstPath)
			throws IOException {
		if (srcPath.isDirectory()) {
			if (!dstPath.exists()) {
				dstPath.mkdirs();
			}

			String[] files = srcPath.list();
			for (int i = 0; i < files.length; i++) {
				copyDirectory(new File(srcPath, files[i]), new File(dstPath,
						files[i]));
			}
		} else {
			if (!srcPath.exists()) {
				return false;
			} else {
				InputStream in = new FileInputStream(srcPath);
				OutputStream out = new FileOutputStream(dstPath);

				// Transfer bytes from in to out
				byte[] buf = new byte[1024];
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				in.close();
				out.close();
			}
		}
		return true;
	}

	/**
	 * Copy a file from strPath to dstPath.
	 * 
	 * @param srcPath
	 *            Source path
	 * @param dstPath
	 *            Destination path
	 * @return true if copy Ok
	 * @throws IOException
	 *             If an I/O error has occurred
	 */
	public static boolean copyFile(final File srcPath, final File dstPath)
			throws IOException {
		if (!srcPath.exists()) {
			return false;
		} else {
			InputStream in = new FileInputStream(srcPath);
			OutputStream out = new FileOutputStream(dstPath);

			// Transfer bytes from in to out
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
		return true;
	}

	/**
	 * Get normalized file path (replace with good File.separator).
	 * 
	 * @param filePath The file path to normalize
	 * @return Normalized file path
	 */
	public static String getNormalizedFilePath(final String filePath) {
		String retour = filePath;
		if (filePath != null) {
			retour = retour.replace("\\", File.separator);
			retour = retour.replace("//", File.separator);
			retour = retour.replace("/\\", File.separator);
			retour = retour.replace("/", File.separator);
			retour = retour.replace("\\", File.separator);
			retour = retour.replace("\\\\", File.separator);
		}
		return retour;
	}

	/**
	 * Method to create a random file name.
	 * 
	 * @param pathTempDirectory
	 *            : folder that will contain the created file
	 * @param extension
	 *            : extension for the created file
	 * @return a File
	 */
	public static File getTempFile(final String pathTempDirectory,
			final String extension) {
		int num = (int) (Math.random() * 999999 + 1);
		String imgName = Integer.valueOf(num).toString();
		String tmpImgPath = pathTempDirectory + File.separator + imgName + "."
				+ extension;
		File tempFile = new File(getNormalizedFilePath(tmpImgPath));
		while (tempFile.exists()) {
			num = (int) (Math.random() * 999999 + 1);
			imgName = Integer.valueOf(num).toString();
			tmpImgPath = pathTempDirectory + File.separator + imgName + "."
					+ extension;
			tempFile = new File(getNormalizedFilePath(tmpImgPath));
		}
		return tempFile;
	}

	/**
	 * Method that returns the files with the given extension (eg:"xml", do NOT
	 * specify the dot) in the directory.
	 * 
	 * @param directory The directory to scan
	 * @param extension The extension to filter
	 * @return array of the files with the given extension in the directory
	 */
	public static File[] getFilesWithExtension(File directory,
			final String extension) {
		return directory.listFiles(new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				return pathname.getName().toLowerCase()
						.endsWith("." + extension.toLowerCase());
			}
		});
	}

	/**
	 * Gets image dimensions for given file
	 * 
	 * @param imgFile
	 *            image file
	 * @return dimensions of image
	 */
	public static Dimension getImageDimension(final File imgFile) {
		int pos = imgFile.getName().lastIndexOf(".");
		if (pos == -1) {
			return new Dimension(0, 0);
		}
		String suffix = imgFile.getName().substring(pos + 1);
		Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(suffix);
		if (iter.hasNext()) {
			ImageReader reader = iter.next();
			try {
				ImageInputStream stream = new FileImageInputStream(imgFile);
				reader.setInput(stream);
				int width = reader.getWidth(reader.getMinIndex());
				int height = reader.getHeight(reader.getMinIndex());
				return new Dimension(width, height);
			} catch (IOException e) {
				logger.error("getImageDimension({}) raised I/O Exception", imgFile, e);
			} finally {
				reader.dispose();
			}
		}
		return new Dimension(0, 0);
	}

	/**
	 * Calculates the dimension of a thumbnail from a original dimension of an
	 * image and a maximum size (height or width depending of the original
	 * image).
	 * 
	 * @param originalDimension
	 *            The original dimension of the image
	 * @param size
	 *            The length of the thumbnail's height of width
	 * @return The computed dimension of the thumbnail
	 */
	public static Dimension getThumbnailDimension(Dimension originalDimension, int size) {
		Dimension d = new Dimension();
		d.setSize(originalDimension.width, originalDimension.height);
		if (d.height > size || d.width > size) {
			float result = (float) originalDimension.height / originalDimension.width * size;
			d.setSize(size, (new Float(result)).intValue());
		}
		return d;
	}

	/**
	 * Test if a file name is an image (doesn't verify the content) png, jpg,
	 * gif, bmp, jpeg, tiff, tif
	 * 
	 * @param fileName
	 *            The file name
	 * @return true if the file name ends with an image extension, false
	 *         otherwise
	 */
	public static boolean isImage(String fileName) {
		if (fileName.toLowerCase().endsWith(".png")
				|| fileName.toLowerCase().endsWith(".jpg")
				|| fileName.toLowerCase().endsWith(".gif")
				|| fileName.toLowerCase().endsWith(".bmp")
				|| fileName.toLowerCase().endsWith(".jpeg")
				|| fileName.toLowerCase().endsWith(".tiff")
				|| fileName.toLowerCase().endsWith(".tif")) {
			return true;
		}

		return false;

	}

}
