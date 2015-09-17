/*
 * FILENAME
 *     FluentJdbcQueryBuilderTest.java
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

import org.junit.Test;

import com.javashop.url.dao.FluentJdbcQueryBuilder;
import com.javashop.url.dao.FluentJdbcQueryBuilder.QueryStatement;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

public class FluentJdbcQueryBuilderTest {

    @Test
    public void testSelectQuery() {
        // case 1 : select all from table
        String query = FluentJdbcQueryBuilder.select().from("testBla").getQuery();
        assertEquals("Expected query to be same.", "select * from testBla", query.trim());

        // case 2 : select specific fields from table
        query = FluentJdbcQueryBuilder.select().addFields("no", "desc", "age").from("testBla").getQuery();
        assertEquals("Expected query to be same.", "select no, desc, age from testBla", query.trim());

        // case 3: select specific fields from table with single condition
        QueryStatement builder = FluentJdbcQueryBuilder.select().addFields("no", "desc", "age").from("testBla");
        builder.where().field("age").ge(10);
        query = builder.getQuery();
        assertEquals("Expected query to be same.", "select no, desc, age from testBla where age >= ?", query.trim());

        // case 4: select specific fields from table with multiple condition combinations
        builder = FluentJdbcQueryBuilder.select().addFields("no", "desc", "age").from("testBla");
        builder.where().field("age").ge(10).and().field("no").eq(20);
        query = builder.getQuery();
        assertEquals("Expected query to be same.", "select no, desc, age from testBla where ( age >= ? and no = ? )", query.trim());

//        // case 4.1: select specific fields from table with multiple condition combinations
//        builder = FluentJdbcQueryBuilder.select().addFields("no", "desc", "age").from("testBla");
//        builder.where().field("age").ge(10).and().field("no").eq(20).or().field("desc").ne("bla bla");
//        query = builder.getQuery();
//        assertEquals("Expected query to be same.", "select no, desc, age from testBla where ( age >= ? and no = ? )", query.trim());
    }

}
