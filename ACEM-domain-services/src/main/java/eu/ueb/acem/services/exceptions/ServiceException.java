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
package eu.ueb.acem.services.exceptions;

/**
 * An exception thrown when forbidden action.
 */
public class ServiceException extends RuntimeException {

	/**
	 * The id for serialization.
	 */
	private static final long serialVersionUID = 4330355119369636120L;

	/**
	 * Bean constructor.
	 * 
	 * @param message
	 *            should be a key of a i18n message property (we don't deal with
	 *            internationalization of error strings in the service layer, so
	 *            the ServiceException only contains a key, not the
	 *            user-friendly message)
	 * @param cause
	 *            A cause
	 */
	protected ServiceException(final String message, final Exception cause) {
		super(message, cause);
	}
	
	/**
	 * Bean constructor.
	 * 
	 * @param message
	 *            should be a key of a i18n message property (we don't deal with
	 *            internationalization of error strings in the service layer, so
	 *            the ServiceException only contains a key, not the
	 *            user-friendly message)
	 */
	public ServiceException(final String message) {
		super(message);
	}
	
	/**
	 * Bean constructor.
	 * 
	 * @param cause
	 *            A cause
	 */
	public ServiceException(final Exception cause) {
		super(cause);
	}
}
