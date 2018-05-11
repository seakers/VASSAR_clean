/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rbsa.eoss;

import seak.orekit.coverage.access.TimeIntervalArray;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import rbsa.eoss.CoverageAnalysisIO;
import org.orekit.frames.TopocentricFrame;
import seak.orekit.analysis.Analysis;
import seak.orekit.constellations.Walker;
import seak.orekit.object.CoverageDefinition;
import seak.orekit.object.Instrument;
import seak.orekit.propagation.PropagatorFactory;
import seak.orekit.propagation.PropagatorType;
import seak.orekit.scenario.Scenario;
import seak.orekit.util.OrekitConfig;
import org.hipparchus.util.FastMath;
import org.orekit.bodies.BodyShape;
import org.orekit.bodies.OneAxisEllipsoid;
import org.orekit.errors.OrekitException;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScale;
import org.orekit.time.TimeScalesFactory;
import org.orekit.utils.Constants;
import org.orekit.utils.IERSConventions;
import seak.orekit.coverage.analysis.AnalysisMetric;
import seak.orekit.coverage.analysis.GroundEventAnalyzer;
import seak.orekit.event.EventAnalysis;
import seak.orekit.event.EventAnalysisEnum;
import seak.orekit.event.EventAnalysisFactory;
import seak.orekit.event.FieldOfViewEventAnalysis;
import static seak.orekit.object.CoverageDefinition.GridStyle.EQUAL_AREA;
import seak.orekit.object.fieldofview.NadirSimpleConicalFOV;

import java.io.File;
import java.io.IOException;
import org.hipparchus.stat.descriptive.DescriptiveStatistics;
import org.orekit.data.DataProvidersManager;
import seak.orekit.event.GroundEventAnalysis;

/**
 *
 * @author Prachi
 */
public class CoverageAnalysis {

    private int numThreads;
    private int coverageGridGranularity;
    private String cwd;//current working directory
    private Properties propertiesPropagator;
    private CoverageDefinition.GridStyle gridStyle;
    private FieldOfViewEventAnalysis fovEventAnalysis;

    public CoverageAnalysis(int numThreads, int coverageGridGranularity){

        this.numThreads = numThreads;
        this.coverageGridGranularity = coverageGridGranularity;
        this.gridStyle = EQUAL_AREA;
        this.cwd = System.getProperty("user.dir");

        //setup logger
        Level level = Level.ALL;
        Logger.getGlobal().setLevel(level);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(level);
        Logger.getGlobal().addHandler(handler);

        reset();
    }

    public void reset(){

        //can set the properties/number of resources available
        //to the satellite but we don't need them right now
        //so we are just instantiating it
        this.propertiesPropagator = new Properties();

        // Reset the FieldOfViewEventAnalysis
        this.fovEventAnalysis = null;
    }


    /**
     * Computes the accesses for satellites sharing the same field of view
     * @param fieldOfView
     * @param inclination
     * @param altitude
     * @param numSats
     * @param numPlanes
     * @throws OrekitException
     */
    public GroundEventAnalyzer getAccesses(double fieldOfView, double inclination, double altitude, int numSats, int numPlanes) throws OrekitException{

        long start = System.nanoTime();

        OrekitConfig.init(this.numThreads);

        // Reset the properties setting
        this.propertiesPropagator = new Properties();

        //if running on a non-US machine, need the line below
        Locale.setDefault(new Locale("en", "US"));

        // Load default dataset saved in the project root directory
        StringBuffer pathBuffer = new StringBuffer();
        final File currrentDir = new File(this.cwd);
        if (currrentDir.exists() && (currrentDir.isDirectory() || currrentDir.getName().endsWith(".zip"))) {
            pathBuffer.append(currrentDir.getAbsolutePath());
            pathBuffer.append(File.separator);
            pathBuffer.append("orekit-data");
        }
        System.setProperty(DataProvidersManager.OREKIT_DATA_PATH, pathBuffer.toString());

        TimeScale utc = TimeScalesFactory.getUTC();
        AbsoluteDate startDate = new AbsoluteDate(2020, 1, 1, 00, 00, 00.000, utc);
        AbsoluteDate endDate = new AbsoluteDate(2020, 1, 8, 00, 00, 00.000, utc); //7 days run time
        double mu = Constants.WGS84_EARTH_MU; // gravitational coefficient

        //must use IERS_2003 and EME2000 frames to be consistent with STK
        Frame earthFrame = FramesFactory.getITRF(IERSConventions.IERS_2003, true);
        Frame inertialFrame = FramesFactory.getEME2000();

        BodyShape earthShape = new OneAxisEllipsoid(Constants.WGS84_EARTH_EQUATORIAL_RADIUS,
                Constants.WGS84_EARTH_FLATTENING, earthFrame);

        //Enter satellite orbital parameters
        double h = altitude; //altitude
        double a = Constants.WGS84_EARTH_EQUATORIAL_RADIUS+h; //semi-major axis
        double i = FastMath.toRadians(inclination);

        //define instruments and payload
        NadirSimpleConicalFOV fov = new NadirSimpleConicalFOV(FastMath.toRadians(fieldOfView), earthShape);
        ArrayList<Instrument> payload = new ArrayList<>();
        Instrument view1 = new Instrument("view1", fov, 100, 100);
        payload.add(view1);

        //number of total satellites
        int t = numSats;

        //number of planes
        int p = numPlanes;

        //number of phases
        int f = 0;

        double raan = 0;
        double anomaly = 0;
        Walker walker = new Walker("walker1", payload, a, i, t, p, f, inertialFrame, startDate, mu, raan, anomaly);

        //define coverage params
        //this is coverage with 20 granularity and equal area grid style
        CoverageDefinition coverageDefinition = new CoverageDefinition("covdef", this.coverageGridGranularity, earthShape, this.gridStyle);
        coverageDefinition.assignConstellation(walker);

        //define where to save the coverage - in a map
        HashSet<CoverageDefinition> coverageDefinitionMap = new HashSet<>();
        coverageDefinitionMap.add(coverageDefinition);

        //propagator type
        PropagatorFactory propFactory = new PropagatorFactory(PropagatorType.J2,propertiesPropagator);

        //set the event analyses
        EventAnalysisFactory eventAnalysisFactory = new EventAnalysisFactory(startDate, endDate, inertialFrame, propFactory);
        ArrayList<EventAnalysis> eventanalyses = new ArrayList<>();
        FieldOfViewEventAnalysis fovEventAnalysis = (FieldOfViewEventAnalysis) eventAnalysisFactory.createGroundPointAnalysis(EventAnalysisEnum.FOV, coverageDefinitionMap, propertiesPropagator);
        eventanalyses.add(fovEventAnalysis);

        //set the analyses
        ArrayList<Analysis> analyses = new ArrayList<>();

        Scenario scene = new Scenario.Builder(startDate, endDate, utc).
                eventAnalysis(eventanalyses).analysis(analyses).
                covDefs(coverageDefinitionMap).name("SMAP").properties(propertiesPropagator).
                propagatorFactory(propFactory).build();

        try {
            Logger.getGlobal().finer(String.format("Running Scenario %s", scene));
            Logger.getGlobal().finer(String.format("Number of points:     %d", coverageDefinition.getNumberOfPoints()));
            Logger.getGlobal().finer(String.format("Number of satellites: %d", walker.getSatellites().size()));
            scene.call();
        } catch (Exception ex) {
            Logger.getLogger(CoverageAnalysis.class.getName()).log(Level.SEVERE, null, ex);
            throw new IllegalStateException("scenario failed to complete.");
        }

        Logger.getGlobal().finer(String.format("Done Running Scenario %s", scene));

        GroundEventAnalyzer fovEvents = new GroundEventAnalyzer(((GroundEventAnalysis) fovEventAnalysis).getEvents(coverageDefinition));
     
        //output the time
        long end = System.nanoTime();
        Logger.getGlobal().finest(String.format("Took %.4f sec", (end - start) / Math.pow(10, 9)));
        OrekitConfig.end();

        return fovEvents;
    }


    /**
     * takes information about the fov events and the orbits
     * to calculate the rise and set times of the accesses
     */

    public double getRevisitTime(double fieldOfView, double inclination, double altitude, int numSats, int numPlanes, double[] latBounds, double[] lonBounds) throws OrekitException{
        GroundEventAnalyzer fovEvents = this.getAccesses(fieldOfView, inclination, altitude, numSats, numPlanes);
        return this.getRevisitTime(fovEvents, latBounds, lonBounds);
    }

    /**
     * computes the revisit time using the accesses
     * @param fovEvents
     * @param latBounds
     * @param lonBounds
     * @return 
     */
    public double getRevisitTime(GroundEventAnalyzer fovEvents, double[] latBounds, double[] lonBounds){
        // Method to compute average revisit time from accesses
        
        System.out.println(Arrays.asList(fovEvents));
        
        //output the hashmap containing all the rise and the set times
        CoverageAnalysisIO cov = new CoverageAnalysisIO(fovEvents);;
        
        try{
            cov.writeAccessData(fovEvents);
        }catch(IOException exc){
            throw new IllegalStateException("File output failed.", exc);
        }
        
        DescriptiveStatistics stat;

        if(latBounds.length == 0 && lonBounds.length == 0){
            stat = fovEvents.getStatistics(AnalysisMetric.DURATION, false, this.propertiesPropagator);

        }else{
            stat = fovEvents.getStatistics(AnalysisMetric.DURATION, false, latBounds, lonBounds, this.propertiesPropagator);
        }

        double mean = stat.getMean();

        //System.out.println(String.format("Max access time %s", mean)); // Mean revisit time?

        return mean;
    }
}
