package rbsa.eoss.local;

import org.hipparchus.util.FastMath;
import org.orekit.errors.OrekitException;
import org.orekit.frames.TopocentricFrame;
import rbsa.eoss.CoverageAnalysis;
import rbsa.eoss.CoverageAnalysisIO;
import seak.orekit.coverage.access.TimeIntervalArray;

import java.util.Map;
import java.io.File;
import java.util.logging.Logger;

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

            long start = System.nanoTime();
            //output the time

            Map<TopocentricFrame, TimeIntervalArray> accesses = coverageAnalysis.getAccesses(fieldOfView, inclination, altitude, numSats, numPlanes);
            double revisitTime1 = coverageAnalysis.getRevisitTime(accesses);

            long t1 = System.nanoTime();
            System.out.println(String.format("Took %.4f sec", (t1 - start) / Math.pow(10, 9)));

            CoverageAnalysisIO.writeBinaryAccessData(accesses, fieldOfView, inclination, altitude, numSats, numPlanes, 20);

            Map<TopocentricFrame, TimeIntervalArray> accesses2 = CoverageAnalysisIO.readBinaryAccessData(fieldOfView, inclination, altitude, numSats, numPlanes, 20);
            double revisitTime2 = coverageAnalysis.getRevisitTime(accesses2);

            System.out.println(revisitTime1);
            System.out.println(revisitTime2);

            long end = System.nanoTime();
            System.out.println(String.format("Took %.4f sec", (end - t1) / Math.pow(10, 9)));

        }catch (OrekitException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}
