/*
 * FILENAME
 *     UrlShortnerServiceTest.java
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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import com.javashop.url.UrlRequest;
import com.javashop.url.UrlResponse;
import com.javashop.url.UrlShortnerService;
import com.javashop.url.UrlResponse.Status;

//
// IMPORTS
// NOTE: Import specific classes without using wildcards.
//

/**
 * <p>
 * Junit test for {@link UrlShortnerService}.
 * </p>

 * @see UrlShortnerService
 * @since 1.0
 * @author sukenshah
 * @version $Id$
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/test-context.xml"})
@WebAppConfiguration
public class UrlShortnerServiceTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private UrlShortnerService service;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private final MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(),
        Charset.forName("utf8"));

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testUrlShortnerService() {
        UrlRequest request = new UrlRequest();
        request.setServerName("www.energyaustralia.com");
        request.setPort(8080);
        request.setContextPath("ea");
        request.setUrl("changeAddress?user=1001&old-address=Collins+st,+Melbourne,+VIC+3000&new-address=Queensbridge+st,Southbank,VIC+3006");
        UrlResponse response = service.getShortUrl(request);
        assertNotNull("Expected response to be present.", response);
        String shortUrl = response.getShortUrl();
        assertNotNull("Expected short URL to be present.", shortUrl);
        assertEquals("Expected long url to be same.",
            "www.energyaustralia.com:8080/ea/changeAddress?user=1001&old-address=Collins+st,+Melbourne,+VIC+3000&new-address=Queensbridge+st,Southbank,VIC+3006",
            response.getLongUrl());

        // test for long url
        String[] strings = shortUrl.split("/");
        request.setUrl(strings[strings.length - 1]);
        response = service.getLongUrl(request);
        assertNotNull("Expected response to be present.", response);
        assertNotNull("Expected short URL to be present.", shortUrl);
        assertEquals("Expected long url to be same.",
            "www.energyaustralia.com:8080/ea/changeAddress?user=1001&old-address=Collins+st,+Melbourne,+VIC+3000&new-address=Queensbridge+st,Southbank,VIC+3006",
            response.getLongUrl());
    }

    @Test
    public void testShortUrlPostJSONRequest() throws IOException, Exception {
        UrlRequest request = new UrlRequest();
        request.setServerName("www.energyaustralia.com");
        request.setPort(8080);
        request.setContextPath("ea");
        request.setUrl("changeAddress?user=1001&old-address=Collins+st,+Melbourne,+VIC+3000&new-address=Queensbridge+st,Southbank,VIC+3006");

        mockMvc.perform(post("/shorturl")
            .content(this.json(request))
            .contentType(contentType))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.shortUrl", notNullValue()))
            .andExpect(jsonPath("$.status", is(Status.OK.toString())))
            .andExpect(jsonPath("$.longUrl",
                is("www.energyaustralia.com:8080/ea/changeAddress?user=1001&old-address=Collins+st,+Melbourne,+VIC+3000&new-address=Queensbridge+st,Southbank,VIC+3006")));
    }

    @Test
    public void testLongUrlPostJSONRequest() throws IOException, Exception {
        UrlRequest request = new UrlRequest();
        request.setServerName("www.energyaustralia.com");
        request.setPort(8080);
        request.setContextPath("ea");
        request.setUrl("changeAddress?user=1001&old-address=Collins+st,+Melbourne,+VIC+3000&new-address=Queensbridge+st,Southbank,VIC+3006");
        UrlResponse response = service.getShortUrl(request);
        assertNotNull("Expected response to be present.", response);
        String[] strings = response.getShortUrl().split("/");
        request.setUrl(strings[strings.length - 1]);

        mockMvc.perform(post("/longurl")
            .content(this.json(request))
            .contentType(contentType))
            .andExpect(status().isOk())
            .andExpect(content().contentType(contentType))
            .andExpect(jsonPath("$.shortUrl", notNullValue()))
            .andExpect(jsonPath("$.status", is(Status.OK.toString())))
            .andExpect(jsonPath("$.longUrl",
                is("www.energyaustralia.com:8080/ea/changeAddress?user=1001&old-address=Collins+st,+Melbourne,+VIC+3000&new-address=Queensbridge+st,Southbank,VIC+3006")));
    }

    protected String json(Object obj) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.jacksonMessageConverter.write(
                obj, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
