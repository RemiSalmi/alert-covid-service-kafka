package com.polytech.alertcovidservicekafka;

import com.polytech.alertcovidservicekafka.models.Location;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import java.sql.Timestamp;


import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class HttpRequestTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private MockMvc mockMvc;

    //TODO: add header and lot of stuff

    @Test
    public void shouldReturnDefaultMessage() throws Exception {
        this.mockMvc.perform(post("http://localhost:" + port + "/stream/location/")).andDo(print())
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content()
                        .string(containsString("Publish")));
    }

    @Test
    public void postLocation() throws Exception {
        //11/12/2020 @ 9:00pm (UTC)
        Timestamp timestamp = new Timestamp(1605214800);
        Location testLocation = new Location(1,timestamp,10.0,12.0);
        assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/stream/location/",testLocation,String.class)).contains("Publish");
    }

    @Test
    public void getForUser() throws Exception {
        Timestamp timestamp = new Timestamp(1605214800);
        Location testLocation = new Location(1,timestamp,10.0,12.0);
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/stream/location/1",
                String.class)).contains(testLocation.toJson());
    }
}
