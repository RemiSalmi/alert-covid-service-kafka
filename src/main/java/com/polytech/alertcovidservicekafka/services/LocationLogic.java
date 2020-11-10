package com.polytech.alertcovidservicekafka.services;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import com.polytech.alertcovidservicekafka.models.Location;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LocationLogic {

    private final int positivenessInDays = 7;

    private final int contactCaseDistance = 2;

    public List<Location> getContactLocation(LinkedList<Location> locations, int declaredPositiveUserId, Timestamp declaredPositiveDate) {

        List<Location> contactCaseLocation = new LinkedList<>();

        Calendar calendarTool = Calendar.getInstance();

        calendarTool.setTime(declaredPositiveDate);
        calendarTool.add(Calendar.DAY_OF_WEEK, positivenessInDays);
        Timestamp positiveDatePlus = new Timestamp(calendarTool.getTime().getTime());

        calendarTool.setTime(declaredPositiveDate);
        calendarTool.add(Calendar.DAY_OF_WEEK, -positivenessInDays);
        Timestamp positiveDateMinus = new Timestamp(calendarTool.getTime().getTime());


        //We kept location between (positiveDatePlus) and (positiveDateMinus)
        //We split between : true -> location positive case, false -> other to test
        Map<Boolean, List<Location>> locationsMap = locations.stream()
                .filter(location -> location.getDate().after(positiveDateMinus) && location.getDate().before(positiveDatePlus))
                .collect(Collectors.groupingBy(location -> location.getId_user() == declaredPositiveUserId));


        locationsMap.get(true).forEach(positiveLocation -> {
            //for each positive case location we select all the other location at the same time
            Stream<Location> relevantLocationByDate = locationsMap.get(true).stream().filter(location -> location.getDate() == positiveLocation.getDate());
            //We calculate the point corresponding to the positive location
            Coordinate latPositive = Coordinate.fromDegrees(positiveLocation.getLatitude());
            Coordinate lngPositive = Coordinate.fromDegrees(positiveLocation.getLongitude());
            Point positivePoint = Point.at(latPositive, lngPositive);
            //if relevant location are in the contact case range there're considered like contact case location
            relevantLocationByDate.forEach(location -> {
                Coordinate lat = Coordinate.fromDegrees(location.getLatitude());
                Coordinate lng = Coordinate.fromDegrees(location.getLongitude());
                Point currentPoint = Point.at(lat, lng);

                double distance = EarthCalc.gcd.distance(positivePoint, currentPoint);

                if (distance < contactCaseDistance){
                    contactCaseLocation.add(location);
                }
            });
        });

        return  contactCaseLocation;
    }


}