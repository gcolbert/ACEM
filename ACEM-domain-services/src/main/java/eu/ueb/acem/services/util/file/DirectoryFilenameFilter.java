package eu.ueb.acem.services.util.file;

import java.io.File;
import java.io.FilenameFilter;

/**
 * File Filter for Directory.
 * @author rlorthio
 *
 */
public class DirectoryFilenameFilter implements FilenameFilter {

    /**
	 * Constructor.
	 */
	public DirectoryFilenameFilter() {
		super();
	}
	
	@Override
	public boolean accept(final File dir, final String name) {
        boolean retour = false;
        if (dir.isDirectory()) {
        	retour = true;
        }
		return retour;
	}
}
