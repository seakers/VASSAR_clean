package rbsa.eoss;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.orekit.frames.TopocentricFrame;
import seak.orekit.coverage.access.TimeIntervalArray;
import seak.orekit.event.FieldOfViewEventAnalysis;

public class CoverageAnalysisIO {

    public static Map<TopocentricFrame, TimeIntervalArray> readBinaryAccessData(double fieldOfView, double inclination, double altitude, int numSats, int numPlanes, int granularity){

        File file = getBinaryAccessDataFile(fieldOfView, inclination, altitude, numSats, numPlanes, granularity);

        Map<TopocentricFrame, TimeIntervalArray> out = new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            out = (HashMap<TopocentricFrame, TimeIntervalArray>) ois.readObject();

        } catch (FileNotFoundException exc) {
            System.out.println("Exc in finding the file: " + exc.getMessage());
            exc.printStackTrace();

        } catch (IOException exc) {
            System.out.println("Exc in writing binary access data: " + exc.getMessage());
            exc.printStackTrace();

        } catch (ClassNotFoundException exc) {
            exc.printStackTrace();
        }

        return out;
    }

    public static void writeBinaryAccessData(Map<TopocentricFrame, TimeIntervalArray> accesses, double fieldOfView, double inclination, double altitude, int numSats, int numPlanes, int granularity){

        File file = getBinaryAccessDataFile(fieldOfView, inclination, altitude, numSats, numPlanes, granularity);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {

            oos.writeObject(accesses);

        } catch (FileNotFoundException exc) {
            System.out.println("Exc in finding the file: " + exc.getMessage());
            exc.printStackTrace();

        } catch (IOException exc) {
            System.out.println("Exc in writing binary access data: " + exc.getMessage());
            exc.printStackTrace();

        }
    }

    public static File getBinaryAccessDataFile(double fieldOfView, double inclination, double altitude, int numSats, int numPlanes, int granularity){
        return new File(
                System.getProperty("orekit.coveragedatabase"),
                String.valueOf(getHashCode(fieldOfView, inclination, altitude, numSats, numPlanes, granularity)));

    }

    public static int getHashCode(double fieldOfView, double inclination, double altitude, int numSats, int numPlanes, int granularity){

        // Round inclination and altitude values to the first decimal
        double inclinationRoundOff = Math.round(inclination * 10.0) / 10.0;
        double altitudeRoundOff = Math.round(altitude * 10.0) / 10.0;

        return new HashCodeBuilder(17, 37).
                append(fieldOfView).
                append(inclinationRoundOff).
                append(altitudeRoundOff).
                append(numSats).
                append(numPlanes).
                append(granularity).
                toHashCode();
    }

}
