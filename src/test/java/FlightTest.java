import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FlightTest {

    private class OutsideLandingRadiusException extends Exception {
    }
    
    class Flyable {
        public void setLocation(int x, int y, int z) {
        }

        public void land(LandingZone l) throws OutsideLandingRadiusException {
//            throw new NoLandingFoundException();
        }

        public long getLocation() {
            return 1;
        }
    }
    class LandingZone {
        
        public int getLandingRadius() {
            return 5;
        }

        public void setLocation(int x, int y, int z) {
        }

        public void setLandingRadius(int i) {
        }

        public long getLocation() {
            return 1;
        }
    }
    
    private Flyable plane;
    private LandingZone airstrip;
    
    @Before
    public void setUp() {
        this.plane = new Flyable();
        this.airstrip = new LandingZone();
    }

    /**
     * Ensure a plane is unable to land when it is outside {@link LandingZone#getLandingRadius()}
     */
    @Test
    public void doLand_fail_outsideLandingRadius() {
        //GIVEN
        plane.setLocation(30, 30, 50);      // Use airstrip and plane created in the FlightTest#setUp() method
        airstrip.setLocation(20, 30, 0);    // Set location 10 away from each other ignoring Z

        airstrip.setLandingRadius(5);                // Set landing radius of 5

        //WHEN
        try {
            plane.land(airstrip);                      // Attempt to land plane in airstrip
            // This should result in a OutsideLandingRadiusException because the plane is
            //      outside of the airstrip's landing radius

            // The test fails if this condition is reached
            Assert.fail("The plane landed successfully when it should not have because it" +
                    "was outside landable's landing radius");
            
        } catch (OutsideLandingRadiusException e) {
            
            //THEN EXPECT
            Assert.assertNotEquals(airstrip.getLocation(), plane.getLocation());
        
        }
    }

    }
