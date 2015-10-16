package com.aiad_schedules.tests

import com.aiad_schedules.input.csvReader
import com.aiad_schedules.schedule.Schedule

class csvReaderTest extends GroovyTestCase {

    void testWrongFile(){

        shouldFail() {

            csvReader.getUser("files/Paul.txt");
        }
    }

    void testNotFoundFile(){

        shouldFail() {

            csvReader.getUser("files/Paul.csv");
        }
    }

    void testInvalidFile(){

        shouldFail(){

            csvReader.getUser("files/Paul.txt.csv");
        }
    }

    void testGetUserAnne() {

        def user = "Anne";

        assertEquals(user, csvReader.getUser("files/Anne.csv"));
    }

    void testGetUserJohn() {

        def user = "John";

        assertEquals(user, csvReader.getUser("files/John.csv"));
    }

    void testGetUserCarl() {

        def user = "Carl";

        assertEquals(user, csvReader.getUser("files/Jeremy.csv"));
    }

    void testWrongUser() {

        def user = "Anne";

        shouldFail() {

            assertEquals(user, csvReader.getUser("files/John.csv"));
        }
    }

    void testScheduleNullEvent() {

        def event = "null";
        Schedule test_schedule = csvReader.getSchedule("files/Anne.csv");

        assertEquals(8, test_schedule.getWeekdays().get(0).getSlots().get(0).getHour())
        assertEquals(event, test_schedule.getWeekdays().get(0).getSlots().get(0).getDescription());
    }

    void testScheduleFullEvent() {

        def event = "divide";
        def priority = 3;
        def intervenients = ["a","b","c","d"];

        Schedule test_schedule = csvReader.getSchedule("files/Jeremy.csv");

        assertEquals(8, test_schedule.getWeekdays().get(0).getSlots().get(0).getHour())
        assertEquals(event, test_schedule.getWeekdays().get(0).getSlots().get(0).getDescription());
        assertEquals(priority, test_schedule.getWeekdays().get(0).getSlots().get(0).getPriority());
        assertEquals(intervenients, test_schedule.getWeekdays().get(0).getSlots().get(0).getIntervenients());
    }
}
