/*
 * FILENAME
 *     UrlDetailsDaoImpl.java
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

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * Implementation for {@link UrlDetailsDao}.
 * </p>
 *
 * @see UrlDetailsDao
 * @since 1.0
 * @author sukenshah
 * @version $Id$
 **/
public class UrlDetailsDaoImpl extends JdbcDaoSupport implements UrlDetailsDao {

    private static final Logger LOGGER = Logger.getLogger(UrlDetailsDaoImpl.class);

    private static final UrlDetailRowMapper ROW_MAPPER = new UrlDetailRowMapper();

    private static final String INSERT_URL_DETAILS = "insert into " + UrlDetail.TABLE_NAME
        + " (" + UrlDetail.LONG_URL + ", " + UrlDetail.CREATION_TIMESTAMP + ") values (?, systimestamp)";

    /**
     * {@inheritDoc}
     *
     * @see UrlDetailsDao#save(com.javashop.url.dao.UrlDetail)
     */
    public Long save(final String longUrl) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Inserting URL details for -> " + longUrl);

        getJdbcTemplate().update(INSERT_URL_DETAILS, longUrl);
        return getUrlDetail(longUrl).getId();
    }

    private static final String GET_DETAILS_QUERY = "select * from " + UrlDetail.TABLE_NAME
        + " where " + UrlDetail.LONG_URL + " = ?";

    /**
     * {@inheritDoc}
     *
     * @see UrlDetailsDao#getUrlDetail(java.lang.String)
     */
    public UrlDetail getUrlDetail(final String longUrl) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Querying URL details for -> " + longUrl);

        List<UrlDetail> results = getJdbcTemplate().query(GET_DETAILS_QUERY, new Object[]{longUrl}, ROW_MAPPER);
        return (!results.isEmpty()) ? results.get(0) : null;
    }

    private static final String GET_DETAILS_ID_QUERY = "select * from " + UrlDetail.TABLE_NAME
        + " where " + UrlDetail.ID_COL + " = ?";

    /**
     * {@inheritDoc}
     *
     * @see UrlDetailsDao#getUrlDetail(java.lang.Long)
     */
    public UrlDetail getUrlDetail(final Long id) {
        if (LOGGER.isDebugEnabled())
            LOGGER.debug("Querying URL details for ID -> " + id);

        List<UrlDetail> results = getJdbcTemplate().query(GET_DETAILS_ID_QUERY, new Object[]{id}, ROW_MAPPER);
        return (!results.isEmpty()) ? results.get(0) : null;
    }

    private static final String CLEAN_DELETE_STATEMENT = "delete from " + UrlDetail.TABLE_NAME
        + " where TIMESTAMPDIFF(SECOND, " + UrlDetail.CREATION_TIMESTAMP + ", systimestamp) >= ?";

    /**
     * {@inheritDoc}
     *
     * @see UrlDetailsDao#cleanObsoleteUrls(java.lang.Long)
     */
    public void cleanObsoleteUrls(final Long expiry) {
        int deletedRecords = getJdbcTemplate().update(CLEAN_DELETE_STATEMENT, expiry);
        LOGGER.info("Deleted " + deletedRecords + " obsolete URL detail records.");
    }

    /**
     * <p>
     * Bean property row mapper for {@link UrlDetail}.
     * </p>
     *
     * @see BeanPropertyRowMapper
     * @since 1.0
     * @author sukenshah
     * @version $Id$
     **/
    private static class UrlDetailRowMapper extends BeanPropertyRowMapper<UrlDetail> {

        private UrlDetailRowMapper() {
            super(UrlDetail.class);
        }
    }
}
