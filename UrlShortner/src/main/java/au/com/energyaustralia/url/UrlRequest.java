/*
 * FILENAME
 *     UrlRequest.java
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

package au.com.energyaustralia.url;


//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * Request to get original/short URL for given short/long url.
 * </p>
 *
 * @since 1.0
 * @author sukenshah
 * @version $Id$
 **/
public class UrlRequest {

    public static final int NO_PORT = -1;

    private String url;
    private String serverName;
    private String contextPath;
    private int port = NO_PORT;

    /**
     * <p>
     * Getter for url.
     * </p>
     *
     * @return the url
     */
    public String getUrl() {
        return url;
    }

    /**
     * <p>
     * Setting value for url.
     * </p>
     *
     * @param url the url to set
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * <p>
     * Getter for serverName.
     * </p>
     *
     * @return the serverName
     */
    public String getServerName() {
        return serverName;
    }

    /**
     * <p>
     * Setting value for serverName.
     * </p>
     *
     * @param serverName the serverName to set
     */
    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    /**
     * <p>
     * Getter for contextPath.
     * </p>
     *
     * @return the contextPath
     */
    public String getContextPath() {
        return contextPath;
    }

    /**
     * <p>
     * Setting value for contextPath.
     * </p>
     *
     * @param contextPath the contextPath to set
     */
    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    /**
     * <p>
     * Getter for port.
     * </p>
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * <p>
     * Setting value for port.
     * </p>
     *
     * @param port the port to set
     */
    public void setPort(int port) {
        this.port = port;
    }

}
