/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rbsa.eoss;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hipparchus.stat.descriptive.DescriptiveStatistics;
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


/**
 *
 * @author Prachi
 */
public class CoverageAnalysis {
    
    /**
     *inputs - num of sat per plane, num planes, fov, 
     */
    public CoverageAnalysis(double fieldOfView, double inclination, double altitude, int numSats, int numPlanes) throws OrekitException{
        
        //if running on a non-US machine, need the line below
        Locale.setDefault(new Locale("en", "US"));
        long start = System.nanoTime();

        OrekitConfig.init(4);
        
        //setup logger
        Level level = Level.ALL;
        Logger.getGlobal().setLevel(level);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setLevel(level);
        Logger.getGlobal().addHandler(handler);

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
        
        //Define the metrics array
        ArrayList<Double> averageRevisitTime = new ArrayList<>();
    
        Walker walker = new Walker("walker1", payload, a, i, t, p, f, inertialFrame, startDate, mu);

        //define coverage params
        //this is coverage with 20 granularity and equal area grid style
        CoverageDefinition coverageDefinition = new CoverageDefinition("covdef", 20, earthShape, EQUAL_AREA);
        coverageDefinition.assignConstellation(walker);
        
        //define where to save the coverage - in a map
        HashSet<CoverageDefinition> coverageDefinitionMap = new HashSet<>();
        coverageDefinitionMap.add(coverageDefinition);
        
        //can set the properties/number of resources available
        //to the satellite but we don't need them right now
        //so we are just instantiating it 
        Properties propertiesPropagator = new Properties();

        //propagator type
        PropagatorFactory propFactory = new PropagatorFactory(PropagatorType.J2,propertiesPropagator);
        
        //set the event analyses
        EventAnalysisFactory eventAnalysisFactory = new EventAnalysisFactory(startDate, endDate, inertialFrame, propFactory);
        ArrayList<EventAnalysis> eventanalyses = new ArrayList<>();
        FieldOfViewEventAnalysis fovEvent = (FieldOfViewEventAnalysis) eventAnalysisFactory.createGroundPointAnalysis(EventAnalysisEnum.FOV, coverageDefinitionMap, propertiesPropagator);
        eventanalyses.add(fovEvent);

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
        
        GroundEventAnalyzer eventAnalyzer = new GroundEventAnalyzer(fovEvent.getEvents(coverageDefinition));
        DescriptiveStatistics stat = eventAnalyzer.getStatistics(AnalysisMetric.DURATION, false, propertiesPropagator);
        System.out.println(String.format("Max access time %s", stat.getMean()));
        averageRevisitTime.add(stat.getMean());
        
        //output the time
        long end = System.nanoTime();
        Logger.getGlobal().finest(String.format("Took %.4f sec", (end - start) / Math.pow(10, 9)));
        
        OrekitConfig.end();
    }
    
}
