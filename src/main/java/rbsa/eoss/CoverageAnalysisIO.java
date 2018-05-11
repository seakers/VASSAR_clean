package rbsa.eoss;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.math3.util.FastMath;
import org.hipparchus.stat.descriptive.DescriptiveStatistics;
import org.orekit.time.AbsoluteDate;
import seak.orekit.coverage.analysis.AnalysisMetric;
import seak.orekit.coverage.analysis.GroundEventAnalyzer;
import seak.orekit.object.CoveragePoint;
import java.io.*;
import java.util.Map;
import java.util.HashMap;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.orekit.frames.TopocentricFrame;
import seak.orekit.coverage.access.TimeIntervalArray;

/**
 * Class that computes coverage metrics for each satellite in the constellation
 * @author Prachi
 */


public class CoverageAnalysisIO {

    private boolean binaryEncoding;

    private GroundEventAnalyzer fovEventAnalyzer;
    private Iterator<CoveragePoint> iterator;
    private int count;

    public CoverageAnalysisIO(){
        this.binaryEncoding = true;
    }

    public CoverageAnalysisIO(GroundEventAnalyzer fovEventAnalyzer) {
        this.binaryEncoding = false;
        this.fovEventAnalyzer = fovEventAnalyzer;
        this.iterator = fovEventAnalyzer.getCoveragePoints().iterator();
        this.count = 0;
    }

    protected String nextEntry() {
        if (iterator.hasNext()) {
            CoveragePoint point = iterator.next();
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

            count++;

            return String.join(",", entry);

        } else {

            return null;
        }
    }

    public boolean readAccessData() {

        String line;
        List<Double> latitude = new ArrayList<>();
        List<Double> longitude = new ArrayList<>();
        List<SimpleDateFormat> startTime = new ArrayList<>();
        List<AbsoluteDate> stopTime = new ArrayList<>();
        List<Double> riseTime = new ArrayList<>();
        List<Double> setTime = new ArrayList<>();


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSX");

        try (BufferedReader br = new BufferedReader(
                new FileReader(new File(System.getProperty("test"), "CoverageResults" + ".csv")))) {

            while ((line = br.readLine()) != null) {

                String[] entry = line.split(","); // use comma as separator
                int columns = entry.length; //get the number of columns in a row

                latitude.add(Double.parseDouble(entry[0]));
                longitude.add(Double.parseDouble(entry[1]));

                for (int i = 0; i < columns; i = i + 2) {
                    riseTime.add(Double.parseDouble(entry[i + 4]));
                    setTime.add(Double.parseDouble(entry[i + 5]));
                }
            }

            br.close();
        } catch (FileNotFoundException e) {

            e.printStackTrace();
            return false;

        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean writeAccessData(GroundEventAnalyzer fovEvents) throws IOException {

        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter(new File(System.getProperty("test"), "CoverageResults" + ".csv")))) {

            bw.append("Latitude [deg]").append(",").append("Longitude [deg]").append(",").append("Start Time [UTC]").append(",").append("Stop Time [UTC]").append(",").append("Rise Time [s]").append(",").append("Set Time [s]");

            bw.newLine();
            String datarow = nextEntry();
            while (datarow != null) {
                bw.append(datarow);
                bw.newLine();
                datarow = nextEntry();
            }
            bw.flush();

        } catch (IOException exc) {
            Logger.getLogger(CoverageAnalysisIO.class.getName()).log(Level.SEVERE, null, exc);
            return false;
        }
        return true;
    }


    public File getAccessDataFile(double fieldOfView, double inclination, double altitude, int numSats, int numPlanes, int granularity) {

        StringBuilder filename = new StringBuilder();
        filename.append(String.valueOf(getHashCode(fieldOfView, inclination, altitude, numSats, numPlanes, granularity)));

        if(!this.binaryEncoding){
            filename.append(".csv");
        }

        return new File(
                System.getProperty("orekit.coveragedatabase"),
                filename.toString()
                );
    }

    public Map<TopocentricFrame, TimeIntervalArray> readBinaryAccessData(double fieldOfView, double inclination, double altitude, int numSats, int numPlanes, int granularity) {

        File file = getAccessDataFile(fieldOfView, inclination, altitude, numSats, numPlanes, granularity);

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

    public void writeBinaryAccessData(Map<TopocentricFrame, TimeIntervalArray> accesses, double fieldOfView, double inclination, double altitude, int numSats, int numPlanes, int granularity) {

        File file = getAccessDataFile(fieldOfView, inclination, altitude, numSats, numPlanes, granularity);

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

    public static int getHashCode(double fieldOfView, double inclination, double altitude, int numSats, int numPlanes, int granularity) {

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