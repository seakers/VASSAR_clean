package rbsa.eoss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.util.FastMath;
import org.hipparchus.stat.descriptive.DescriptiveStatistics;
import org.orekit.time.AbsoluteDate;
import seak.orekit.coverage.analysis.AnalysisMetric;
import seak.orekit.coverage.analysis.GroundEventAnalyzer;
import seak.orekit.object.CoveragePoint;
import java.io.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.orekit.frames.TopocentricFrame;
import seak.orekit.coverage.access.TimeIntervalArray;

/**
 * Class that computes coverage metrics for each satellite in the constellation
 * @author Prachi
 */

public class CoverageAnalysisIO {

    private boolean binaryEncoding;

    public CoverageAnalysisIO(boolean binaryEncoding){
        this.binaryEncoding = binaryEncoding;
    }

    public void setBinaryEncoding(boolean binaryEncoding){
        this.binaryEncoding = binaryEncoding;
    }

    public void writeAccessData(AccessDataDefinition definition, Map<TopocentricFrame, TimeIntervalArray> fovEvents){
        if(this.binaryEncoding){
            this.writeAccessDataBinary(definition, fovEvents);

        }else{
            this.writeAccessDataCSV(definition, fovEvents);
        }
    }

    public Map<TopocentricFrame, TimeIntervalArray> readAccessData(AccessDataDefinition definition){
        if(this.binaryEncoding){
            return this.readAccessDataBinary(definition);
        }else{
            //this.readAccessData();
        }
        return this.readAccessDataBinary(definition);
    }

//    public Map<TopocentricFrame, TimeIntervalArray> readAccessDataCSV(AccessDataDefinition definition) {
//
//        String line;
//        List<Double> latitude = new ArrayList<>();
//        List<Double> longitude = new ArrayList<>();
//        List<SimpleDateFormat> startTime = new ArrayList<>();
//        List<AbsoluteDate> stopTime = new ArrayList<>();
//        List<Double> riseTime = new ArrayList<>();
//        List<Double> setTime = new ArrayList<>();
//
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX");
//
//        try (BufferedReader br = new BufferedReader(
//                new FileReader(new File(System.getProperty("test"), "CoverageResults" + ".csv")))) {
//
//            while ((line = br.readLine()) != null) {
//
//                String[] entry = line.split(","); // use comma as separator
//                int columns = entry.length; //get the number of columns in a row
//
//                latitude.add(Double.parseDouble(entry[0]));
//                longitude.add(Double.parseDouble(entry[1]));
//
//                for (int i = 0; i < columns; i = i + 2) {
//                    riseTime.add(Double.parseDouble(entry[i + 4]));
//                    setTime.add(Double.parseDouble(entry[i + 5]));
//                }
//            }
//
//            br.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    public void writeAccessDataCSV(AccessDataDefinition definition, Map<TopocentricFrame, TimeIntervalArray> fovEvents){

        File file = getAccessDataFile(definition);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            StringJoiner sj = new StringJoiner(",");

            sj.add("Latitude [deg]");
            sj.add("Longitude [deg]");
            sj.add("Start Time [UTC]");
            sj.add("Stop Time [UTC]");
            sj.add("Rise Time [s]");
            sj.add("Set Time [s]");
            bw.append(sj.toString());
            bw.newLine();
            bw.flush();

            GroundEventAnalyzer fovEventAnalyzer = new GroundEventAnalyzer(fovEvents);
            Iterator<CoveragePoint> iterator = fovEventAnalyzer.getCoveragePoints().iterator();

            String datarow = nextEntry(iterator, fovEventAnalyzer);
            while (datarow != null) {
                bw.append(datarow);
                bw.newLine();
                datarow = nextEntry(iterator, fovEventAnalyzer);
            }
            bw.flush();

        } catch (FileNotFoundException exc) {
            System.out.println("Exc in finding the file: " + exc.getMessage());
            exc.printStackTrace();

        } catch (IOException exc) {
            System.out.println("Exc in writing access data in csv: " + exc.getMessage());
            exc.printStackTrace();

        }
    }

    private String nextEntry(Iterator<CoveragePoint> coveragePointsIterator, GroundEventAnalyzer fovEventAnalyzer) {

        if (coveragePointsIterator.hasNext()) {
            CoveragePoint point = coveragePointsIterator.next();
            Properties prop = new Properties();

            DescriptiveStatistics accesses = fovEventAnalyzer.getStatistics(AnalysisMetric.DURATION, true, point, prop);
            DescriptiveStatistics gaps = fovEventAnalyzer.getStatistics(AnalysisMetric.DURATION, false, point, prop);
            DescriptiveStatistics riseSetTimes = fovEventAnalyzer.getStatistics(AnalysisMetric.LIST_RISE_SET_TIMES, true, point, prop);
            int riseSetTimesSize = (int) riseSetTimes.getN();

            String[] entry = new String[4 + riseSetTimesSize];
            entry[0] = String.valueOf(FastMath.toDegrees(point.getPoint().getLatitude()));
            entry[1] = String.valueOf(FastMath.toDegrees(point.getPoint().getLongitude()));
            entry[2] = String.valueOf(fovEventAnalyzer.getStartDate());
            entry[3] = String.valueOf(fovEventAnalyzer.getEndDate());

            if (riseSetTimesSize == 0) {
                return String.join(",", entry);

            } else {
                for (int i = 0; i < riseSetTimesSize; i++) {
                    entry[i + 4] = String.valueOf(riseSetTimes.getElement(i));
                }
            }

            return String.join(",", entry);

        } else {

            return null;
        }
    }

    public File getAccessDataFile(AccessDataDefinition definition) {

        StringBuilder filename = new StringBuilder();
        filename.append(String.valueOf(definition.hashCode()));

        if(!this.binaryEncoding){
            filename.append(".csv");
        }

        return new File(
                System.getProperty("orekit.coveragedatabase"),
                filename.toString()
                );
    }

    public Map<TopocentricFrame, TimeIntervalArray> readAccessDataBinary(AccessDataDefinition definition) {

        File file = getAccessDataFile(definition);

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

    public void writeAccessDataBinary(AccessDataDefinition definition, Map<TopocentricFrame, TimeIntervalArray> accesses) {

        File file = getAccessDataFile(definition);

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

    public static class AccessDataDefinition {

        private double fieldOfView;
        private double inclination;
        private double altitude;
        private int numSats;
        private int numPlanes;
        private int granularity;

        public AccessDataDefinition(double fieldOfView, double inclination, double altitude, int numSats, int numPlanes, int granularity){
            this.fieldOfView = fieldOfView;
            this.inclination = inclination;
            this.altitude = altitude;
            this.numSats = numSats;
            this.numPlanes = numPlanes;
            this.granularity = granularity;
        }

        @Override
        public int hashCode() {

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
}