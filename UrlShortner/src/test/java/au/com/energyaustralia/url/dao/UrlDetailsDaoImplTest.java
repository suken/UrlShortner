/*
 * FILENAME
 *     UrlDetailsDaoImplTest.java
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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.javashop.url.dao.UrlDetail;
import com.javashop.url.dao.UrlDetailsDaoImpl;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * Junit test for {@link UrlDetailsDaoImpl}.
 * </p>
 *
 * @see UrlDetailsDaoImpl
 * @since 1.0
 * @author sukenshah
 * @version $Id$
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
public class UrlDetailsDaoImplTest {

    @Autowired
    private UrlDetailsDaoImpl dao;

    @Test
    public void testSave() {
        Long id = dao.save("www.energyaustralia.com/ea/test?q=test+me");
        assertTrue("Expected id to be generated.", id > 0);

        Long id2 = dao.save("www.energyaustralia.com/ea/test?q=test+me+new");
        assertTrue("Expected id to be generated.", id2 > 0);
        assertNotSame("Expected id to be different.", id, id2);
    }

    @Test
    public void testGetUrlDetailLongUrl() {
        // case : test when url is present
        dao.save("www.energyaustralia.com/ea/test?q=test+me");
        UrlDetail detail = dao.getUrlDetail("www.energyaustralia.com/ea/test?q=test+me");
        assertNotNull("Expected detail to be present.", detail);
        assertEquals("Expected long URL to be same.", "www.energyaustralia.com/ea/test?q=test+me", detail.getLongUrl());
        assertNotNull("Expected id to be present.", detail.getId());

        // test when url is not present
        assertNull("Expected no details.", dao.getUrlDetail("bla bla bla"));
    }

    @Test
    public void testGetUrlDetailLong() {
        // case : test when id is present
        Long id = dao.save("www.energyaustralia.com/ea/test?q=test+me");
        UrlDetail detail = dao.getUrlDetail(id);
        assertNotNull("Expected detail to be present.", detail);
        assertEquals("Expected long URL to be same.", "www.energyaustralia.com/ea/test?q=test+me", detail.getLongUrl());
        assertEquals("Expected id to be same.", id, detail.getId());

        // test when id is not present
        assertNull("Expected no details.", dao.getUrlDetail(new Long(100)));
    }

    @SuppressWarnings("static-access")
    @Test
    public void testCleanObsoleteUrls() throws InterruptedException {
        dao.save("www.energyaustralia.com/ea/test?q=test+me+11");
        dao.save("www.energyaustralia.com/ea/test?q=test+me+12");
        dao.save("www.energyaustralia.com/ea/test?q=test+me+13");

        Thread.currentThread().sleep(5000);

        dao.save("www.energyaustralia.com/ea/test?q=test+me+14");
        dao.save("www.energyaustralia.com/ea/test?q=test+me+15");

        dao.cleanObsoleteUrls(Long.valueOf(3));

        // check if obsolete urls are deleted
        assertNull(dao.getUrlDetail("www.energyaustralia.com/ea/test?q=test+me+11"));
        assertNull(dao.getUrlDetail("www.energyaustralia.com/ea/test?q=test+me+12"));
        assertNull(dao.getUrlDetail("www.energyaustralia.com/ea/test?q=test+me+13"));

        // check if valid urls are still present
        assertNotNull(dao.getUrlDetail("www.energyaustralia.com/ea/test?q=test+me+14"));
        assertNotNull(dao.getUrlDetail("www.energyaustralia.com/ea/test?q=test+me+15"));
    }

}
