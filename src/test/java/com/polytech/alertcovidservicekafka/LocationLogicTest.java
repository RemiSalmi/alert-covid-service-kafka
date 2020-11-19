package com.polytech.alertcovidservicekafka;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import com.polytech.alertcovidservicekafka.models.Location;
import com.polytech.alertcovidservicekafka.services.LocationLogic;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LocationLogicTest {

    @Autowired
    private LocationLogic locationLogic;



    @Test
    public void testPositiveWithWithEmptyList() throws Exception {
        LinkedList<Location> testList = new LinkedList<>();
        Long id_user = 1l;
        Timestamp declaredPositiveDate = new Timestamp(1605214800);
        assertThat(locationLogic.getContactLocation(testList,id_user,declaredPositiveDate)).isEmpty();
    }

    //Declare user have no position
    @Test
    public void testPositiveWithNoPositionForOurUser() throws Exception {
        LinkedList<Location> testList = new LinkedList<>();
        Timestamp twelveNovemberTime = new Timestamp(1605214800);
        Location location = new Location(2,twelveNovemberTime,11.0,12.0);
        testList.addFirst(location);
        Long id_user = 1l;
        assertThat(locationLogic.getContactLocation(testList,id_user,twelveNovemberTime)).isEmpty();
    }

    //Declare user have position with himself
    @Test
    public void testPositiveWithOnlyAPositionOfHimself() throws Exception {
        LinkedList<Location> testList = new LinkedList<>();
        Timestamp twelveNovemberTime = new Timestamp(1605214800);
        Location location = new Location(1,twelveNovemberTime,11.0,12.0);
        testList.addFirst(location);
        Long id_user = 1l;
        assertThat(locationLogic.getContactLocation(testList,id_user,twelveNovemberTime)).isEmpty();
    }


    //User at same position at same time than our user
    @Test
    public void testPositiveThatMatchOnOnePosition() throws Exception {
        LinkedList<Location> testList = new LinkedList<>();
        Timestamp twelveNovemberTime = new Timestamp(1605214800);
        Location ourUserLocation = new Location(1,twelveNovemberTime,11.0,12.0);
        Location otherUserLocation = new Location(2,twelveNovemberTime,11.0,12.0);

        testList.addFirst(ourUserLocation);
        testList.addFirst(otherUserLocation);
        Long id_user = 1l;

        assertThat(locationLogic.getContactLocation(testList,id_user,twelveNovemberTime)).containsExactly(otherUserLocation);
    }

    //TODO:multiple contact location for a user and our user

    //Different users at different position verify that all user are
    @Test
    public void testPositiveThatMatchOnMultiplePosition() throws Exception {

        Timestamp twelveNovemberTime = new Timestamp(1605214800);
        Timestamp twelveNovemberTimeTwoMinMore = new Timestamp(1605214980);
        Timestamp thirteenNovemberTime = new Timestamp(1605225600);
        Timestamp twelveNovemberTimeOtherYear = new Timestamp(1573592400);
        Timestamp twoNovemberTime = new Timestamp(1604319132);

        Location ourUserLocation = new Location(1,twelveNovemberTime,3.8719315,43.6248723);
        Location otherUser2Location = new Location(2,twelveNovemberTime,11.0,12.0);
        Location otherUser4Location = new Location(4,twoNovemberTime,3.8719315,43.6248723);
        Location otherUser5Location = new Location(5,twelveNovemberTimeOtherYear,3.8719315,43.6248723);
        Location otherUser6Location = new Location(6,twelveNovemberTime,43.6248723,3.8719315);
        Location otherUser7Location = new Location(7,thirteenNovemberTime,-23.5589681,-109.0758897);
        Location otherUser8Location = new Location(8,twoNovemberTime,3.8719315,43.6248723);

        //positives locations
        Location otherUser9Location = new Location(9,twelveNovemberTimeTwoMinMore,3.8719316,43.6248725);
        Location otherUser3Location = new Location(3,twelveNovemberTime,3.8719315,43.6248723);

        LinkedList<Location> testList = new LinkedList<>();
        Collections.addAll(testList,ourUserLocation,otherUser2Location,otherUser3Location,otherUser4Location,otherUser5Location,otherUser6Location,otherUser7Location,otherUser8Location,otherUser9Location);

        Long id_user = 1l;

        List<Location> contactCaseLocation = locationLogic.getContactLocation(testList,id_user,twelveNovemberTime);

        assertThat(contactCaseLocation).containsOnly(otherUser9Location,otherUser3Location);
    }


    @Test
    public void contextLoads() throws Exception {
        assertThat(locationLogic).isNotNull();
    }

}
