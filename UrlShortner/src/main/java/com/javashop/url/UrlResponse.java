/*
 * FILENAME
 *     UrlResponse.java
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

package com.javashop.url;


//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * Response for URL shortening service.
 * </p>
 *
 * @since 1.0
 * @author sukenshah
 * @version $Id$
 **/
public class UrlResponse {

    private String longUrl;
    private String shortUrl;
    private Status status = Status.OK;

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
     * Getter for shortUrl.
     * </p>
     *
     * @return the shortUrl
     */
    public String getShortUrl() {
        return shortUrl;
    }

    /**
     * <p>
     * Getter for status.
     * </p>
     *
     * @return the status
     */
    public Status getStatus() {
        return status;
    }

    /**
     * <p>
     * Setter for long URL.
     * </p>
     * @param longUrl Long URL
     * @return {@link UrlResponse}
     */
    public UrlResponse withLongUrl(final String longUrl) {
        this.longUrl = longUrl;
        return this;
    }

    /**
     * <p>
     * Setter for short URL.
     * </p>
     * @param shortUrl Short URL
     * @return {@link UrlResponse}
     */
    public UrlResponse withShortUrl(final String shortUrl) {
        this.shortUrl = shortUrl;
        return this;
    }

    /**
     * <p>
     * Setter for status.
     * </p>
     * @param status response status
     * @return {@link UrlResponse}
     */
    public UrlResponse withStatus(final Status status) {
        this.status = status;
        return this;
    }

    /**
     * <p>
     * Status of Url response.
     * </p>
     *
     * @since 1.0
     * @author sukenshah
     * @version $Id$
     **/
    public enum Status {
        OK,
        TIMEDOUT;
    }
}
