/*
 * FILENAME
 *     UrlShortnerService.java
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

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.DatatypeConverter;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Strings;
import com.javashop.url.UrlResponse.Status;
import com.javashop.url.dao.UrlDetail;
import com.javashop.url.dao.UrlDetailsDao;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * URL shortening service. TODO: Add more details here.
 * </p>
 * <p>
 * <h2>Responsibilities</h2></p>
 * <p>
 * <ul>
 *     <li> Process short URL requests </li>
 *     <li> Get original/long url for given short URLs</li>
 * </ul></p>
 *
 * @since 1.0
 * @author sukenshah
 * @version $Id$
 **/
@Controller
public class UrlShortnerService {

    private static final Logger LOGGER = Logger.getLogger(UrlShortnerService.class);
    private static final String PATH_SEPARATOR = "/";
    private static final String PORT_SEPARATOR = ":";

    @Autowired
    private UrlDetailsDao urlDetailsDao;

    @RequestMapping(value = "/shorturl", method = { RequestMethod.POST })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public @ResponseBody UrlResponse getShortUrl(@RequestBody final UrlRequest request) {
        LOGGER.debug("Shortening URL for -> " + request.getUrl());

        // check if short URL is present for given request
        String urlPrefix = constructUrlPrefix(request.getServerName(), request.getContextPath(), request.getPort());
        String longUrl = urlPrefix + request.getUrl();
        UrlDetail shortUrl = urlDetailsDao.getUrlDetail(longUrl);

        if (shortUrl != null)
            return new UrlResponse().withLongUrl(longUrl).withShortUrl(constructShortUrl(urlPrefix, shortUrl.getId()));

        // create new short URL
        Long shortUrlId = urlDetailsDao.save(longUrl);
        return new UrlResponse().withLongUrl(longUrl).withShortUrl(constructShortUrl(urlPrefix, shortUrlId));
    }

    @RequestMapping(value = "/longurl", method = { RequestMethod.POST })
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public @ResponseBody UrlResponse getLongUrl(@RequestBody final UrlRequest request) {
        LOGGER.debug("Get original URL for -> " + request.getUrl());
        String urlPrefix = constructUrlPrefix(request.getServerName(), request.getContextPath(), request.getPort());
        Long id = Long.valueOf(new String(DatatypeConverter.parseBase64Binary(request.getUrl())));

        UrlDetail urlDetail = urlDetailsDao.getUrlDetail(id);
        if (urlDetail != null)
            return new UrlResponse().withShortUrl(urlPrefix + request.getUrl()).withLongUrl(urlDetail.getLongUrl());

        return new UrlResponse().withShortUrl(urlPrefix + request.getUrl()).withStatus(Status.TIMEDOUT);
    }

    private String constructShortUrl(final String urlPrefix, final Long id) {
        return urlPrefix + DatatypeConverter.printBase64Binary(id.toString().getBytes());
    }

    private String constructUrlPrefix(final String serverName, final String contextPath, final int port) {
        StringBuilder sb = new StringBuilder(serverName);
        if (port != UrlRequest.NO_PORT)
            sb.append(PORT_SEPARATOR).append(port);
        if (!Strings.isNullOrEmpty(contextPath))
            sb.append(PATH_SEPARATOR).append(contextPath);
        return sb.append(PATH_SEPARATOR).toString();
    }
}
