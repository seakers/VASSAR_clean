package rbsa.eoss.local;

import org.hipparchus.util.FastMath;
import org.orekit.errors.OrekitException;
import org.orekit.frames.TopocentricFrame;
import rbsa.eoss.CoverageAnalysis;
import rbsa.eoss.CoverageAnalysisIO;
import rbsa.eoss.Orbit;
import seak.orekit.coverage.access.TimeIntervalArray;

import java.util.Map;
import java.util.StringJoiner;

public class CoverageAnalysisTest {

    public static void main(String[] args){

        try {
            int coverageGranularity = 20;
            CoverageAnalysis coverageAnalysis = new CoverageAnalysis(1, coverageGranularity, true, false);

            String path = ".";
            Params.initInstance(path, "CRISP-ATTRIBUTES", "test","normal","");

            int numSats = 1;
            int numPlanes = 1;

            double[] latBounds = new double[]{FastMath.toRadians(-70), FastMath.toRadians(70)};
            double[] lonBounds = new double[]{FastMath.toRadians(-180), FastMath.toRadians(180)};

            CoverageAnalysisMode mode = CoverageAnalysisMode.SINGLE;

            if(mode == CoverageAnalysisMode.SINGLE){

                    double fieldOfView = 100; // [deg]
                    double inclination = 90; // [deg]
                    double altitude = 800 * 1000 ; // [m]

                    long start = System.nanoTime();
                    //output the time

                    Map<TopocentricFrame, TimeIntervalArray> accesses = coverageAnalysis.getAccesses(fieldOfView, inclination, altitude, numSats, numPlanes, null);
                    double revisitTime1 = coverageAnalysis.getRevisitTime(accesses);

                    long t1 = System.nanoTime();
                    System.out.println(String.format("Took %.4f sec", (t1 - start) / Math.pow(10, 9)));

                    long end = System.nanoTime();
                    System.out.println(String.format("Took %.4f sec", (end - t1) / Math.pow(10, 9)));

            }else if(mode == CoverageAnalysisMode.NESTED){

                long start = System.nanoTime();

                double[] fieldOfViewValues = new double[]{35, 55};

                int total = 10;
                int cnt = 0;

                for(double fov:fieldOfViewValues){

                    for(String orbitName: Params.getInstance().orbitList){
                        //"LEO-600-polar-NA","SSO-600-SSO-AM","SSO-600-SSO-DD","SSO-800-SSO-AM","SSO-800-SSO-DD"

                        long t1 = System.nanoTime();

                        Orbit orb = new Orbit(orbitName, numPlanes, numSats);
                        double fieldOfView = fov; // [deg]
                        double inclination = orb.getInclinationNum(); // [deg]
                        double altitude = orb.getAltitudeNum(); // [m]

                        Map<TopocentricFrame, TimeIntervalArray> accesses = coverageAnalysis.getAccesses(fieldOfView, inclination, altitude, numSats, numPlanes, null);

                        double revisitTime = coverageAnalysis.getRevisitTime(accesses);

                        StringJoiner sb = new StringJoiner("\n");
                        sb.add("orbit: " + orb.toString() + " (inclination: " + orb.getInclinationNum() + ", altitude: " + orb.getAltitudeNum() + ")");
                        sb.add("fov: " + fov);
                        sb.add("revisitTime: " + revisitTime);
                        sb.add(cnt + "/" + total);
                        System.out.println(sb.toString());

                        long t2 = System.nanoTime();
                        System.out.println(String.format("Took %.4f sec", (t2 - t1) / Math.pow(10, 9)));

                        cnt++;
                    }
                }
                long end = System.nanoTime();
                System.out.println(String.format("Took %.4f sec in total", (end - start) / Math.pow(10, 9)));

            }

        }catch (OrekitException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public enum CoverageAnalysisMode{
        SINGLE,
        NESTED
    }
}
