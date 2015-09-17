/*
 * FILENAME
 *     UrlDetail.java
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

package com.javashop.url.dao;


//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * Holds the URL mapping between long and short URL.
 * </p>
 *
 * @since 1.0
 * @author sukenshah
 * @version $Id$
 **/
public class UrlDetail {

    public static final String TABLE_NAME = "URL_DETAILS";
    public static final String ID_COL = "id";
    public static final String LONG_URL = "longUrl";
    public static final String CREATION_TIMESTAMP ="created";

    private Long id;
    private String longUrl;

    /**
     * <p>
     * Getter for id.
     * </p>
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * <p>
     * Setting value for id.
     * </p>
     *
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>
     * Getter for longUrl.
     * </p>
     *
     * @return the longUrl
     */
    public String getLongUrl() {
        return longUrl;
    }

    /**
     * <p>
     * Setting value for longUrl.
     * </p>
     *
     * @param longUrl the longUrl to set
     */
    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

}
