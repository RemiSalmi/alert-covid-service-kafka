package com.polytech.alertcovidservicekafka.services;

import com.grum.geocalc.Coordinate;
import com.grum.geocalc.EarthCalc;
import com.grum.geocalc.Point;
import com.polytech.alertcovidservicekafka.models.Location;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class LocationLogic {

    private final int positivenessInDays = 7;

    private final int contactCaseDistance = 2;

    private final int contactTimeWindowInMinute = 5;


    public List<Location> getContactLocation(LinkedList<Location> locations, long declaredPositiveUserId, Timestamp declaredPositiveDate) {

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
                .filter(location -> dateIsBetween(location.getDate(),positiveDateMinus,positiveDatePlus))
                .collect(Collectors.groupingBy(location -> location.getId_user() == declaredPositiveUserId));

        if(locationsMap.containsKey(true) && locationsMap.containsKey(false)) {


            locationsMap.get(true).forEach(positiveLocation -> {

                //We calculate contact time window to the positive location
                calendarTool.setTime(positiveLocation.getDate());
                calendarTool.add(Calendar.MINUTE, contactTimeWindowInMinute);
                Timestamp positiveLocationDateNewest = new Timestamp(calendarTool.getTime().getTime());
                calendarTool.setTime(positiveLocation.getDate());
                calendarTool.add(Calendar.MINUTE, -contactTimeWindowInMinute);
                Timestamp positiveLocationDateOlder = new Timestamp(calendarTool.getTime().getTime());

                //for each positive case location we select all the other location iatn the window time
                Stream<Location> relevantLocationByDate = locationsMap.get(false).stream().filter(location -> {
                   return dateIsBetween(location.getDate(),positiveLocationDateOlder,positiveLocationDateNewest);
                });

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

                    if (distance < contactCaseDistance) {
                        contactCaseLocation.add(location);
                    }
                });
            });
        }
        return  contactCaseLocation;
    }

    private Boolean dateIsBetween(Timestamp dateToTest,Timestamp olderDate, Timestamp newestDate) {
        return dateToTest.after(olderDate) && dateToTest.before(newestDate);
    }

    @Override
    public String toString() {
        return "LocationLogic{" +
                "positivenessInDays=" + positivenessInDays +
                ", contactCaseDistance=" + contactCaseDistance +
                '}';
    }
}