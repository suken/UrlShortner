/*
 * FILENAME
 *     UrlCleanerJob.java
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

package com.javashop.url.scheduler;

import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.javashop.url.dao.UrlDetailsDao;


//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * URL cleaner is a cron job to delete obsolete (timed out) URLs from database.
 * Refer to spring configuration for schedule related details.
 * </p>
 *
 * @see Job
 * @since 1.0
 * @author sukenshah
 * @version $Id$
 **/
@Component
public class UrlCleanerJob {

    private static final Logger LOGGER = Logger.getLogger(UrlCleanerJob.class);

    @Autowired
    private UrlDetailsDao urlDetailsDao;

    private Long expiryTime;

    /**
     * <p>
     * Deletes the short URL records from database which are expired. The expiry time is injected via spring configuration.
     * </p>
     **/
    public void cleanObsoleteUrls() {
        LOGGER.info("====> Starting URL cleaning job. Expiry time = " + expiryTime);
        urlDetailsDao.cleanObsoleteUrls(expiryTime);
        LOGGER.info("====> Finished URL cleaning job.");
    }

    /**
     * <p>
     * Setting value for expiryTime.
     * </p>
     *
     * @param expiryTime the expiryTime to set
     */
    public void setExpiryTime(Long expiryTime) {
        this.expiryTime = expiryTime * 60; // convert to seconds
    }

}
