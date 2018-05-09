package rbsa.eoss.local;

import org.hipparchus.util.FastMath;
import org.orekit.errors.OrekitException;
import rbsa.eoss.CoverageAnalysis;

public class CoverageAnalysisTest {

    public static void main(String[] args){

        CoverageAnalysis coverageAnalysis = new CoverageAnalysis(1, 20);

        double fieldOfView = 50; // [deg]
        double inclination = 70; // [deg]
        double altitude = 800 * 1000 ; // [m]
        int numSats = 1;
        int numPlanes = 1;

        double[] latBounds = new double[]{FastMath.toRadians(-70), FastMath.toRadians(70)};
        double[] lonBounds = new double[]{FastMath.toRadians(-180), FastMath.toRadians(180)};

        try{
            double meanRevisitTime = coverageAnalysis.getRevisitTime(fieldOfView, inclination, altitude, numSats, numPlanes, latBounds, lonBounds);

        }catch (OrekitException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}
