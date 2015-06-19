/*
 * FILENAME
 *     UrlDetailsDao.java
 *
 * FILE LOCATION
 *     $Source$
 *
 * VERSION
 *     $Id$
 *         @version       $Revision$
 *         Check-Out Tag: $Name$
 *         Locked By:     $Lockers$
 *
 * FORMATTING NOTES
 *     * Lines should be limited to 200 characters.
 *     * Files should contain no tabs.
 *     * Indent code using four-character increments.
 *
 * COPYRIGHT
 *     @@@@@
 */

package au.com.energyaustralia.url.dao;



//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * DAO for {@link UrlDetail}.
 * </p>
 * <h2>Responsibilities</h2></p>
 * <p>
 * <ul>
 *     <li> Persists URL data</li>
 *     <li> Query for URL data for given long URL </li>
 *     <li> Clean obsolete URLs </li>
 * </ul></p>
 *
 * @since 1.0
 * @author sukenshah
 * @version $Id$
 **/
public interface UrlDetailsDao {

    /**
     * <p>
     * Save {@link UrlDetail} into database.
     * </p>
     *
     * @param detail to be saved
     * @return TRUE if success, FALSE otherwise.
     **/
    Long save(final String longUrl);

    /**
     * <p>
     * Get {@link UrlDetail} for given long URL.
     * </p>
     *
     * @param longUrl Long URL
     * @return {@link UrlDetail}.
     **/
    UrlDetail getUrlDetail(final String longUrl);

    /**
     * <p>
     * Get {@link UrlDetail} for given id.
     * </p>
     *
     * @param id {@link UrlDetail} id.
     * @return {@link UrlDetail}.
     **/
    UrlDetail getUrlDetail(final Long id);

    /**
     * <p>
     * Delete obsolete URLs from database for given expiry time.
     * </p>
     *
     * @param expiry time
     **/
    void cleanObsoleteUrls(final Long expiry);
}
