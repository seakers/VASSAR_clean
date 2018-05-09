package rbsa.eoss.local;

import org.orekit.errors.OrekitException;
import rbsa.eoss.CoverageAnalysis;

public class CoverageAnalysisTest {

    public static void main(String[] args){

        CoverageAnalysis coverageAnalysis = new CoverageAnalysis(1, 20);

        double fieldOfView = 10;
        double inclination = 90;
        double altitude = 600;
        int numSats = 1;
        int numPlanes = 1;

        try{
            coverageAnalysis.getAccesses(fieldOfView, inclination, altitude, numSats, numPlanes);

        }catch (OrekitException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

}
