package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import rbsa.eoss.*;
import rbsa.eoss.local.Params;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class EvalPerformanceTest {

    private Params params;
    private ArchitectureEvaluator AE = null;

    public void initJess() {
        // Set a path to the project folder
        String path = System.getProperty("user.dir");

        // Initialization
        String search_clps = "";
        params = Params.initInstance(path, "FUZZY-ATTRIBUTES", "test","normal", search_clps);//FUZZY or CRISP
        AE = ArchitectureEvaluator.getInstance();
        AE.init(1);
    }


    @Test
    void evalPerformance() {
        initJess();

        ArrayList<String> bitStrings = new ArrayList<>();
        ArrayList<Double> expectedCosts = new ArrayList<>();
        ArrayList<Double> expectedSciences = new ArrayList<>();

        String csvFile = "java/src/test/EOSS_data.csv";
        String line = "";
        String csvSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] arch = line.split(csvSplitBy);
                bitStrings.add(arch[0]);
                expectedSciences.add(Double.parseDouble(arch[1]));
                expectedCosts.add(Double.parseDouble(arch[2]));
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        // Generate a new architecture
        for (int i = 0; i < bitStrings.size(); i += 10) {
            Architecture architecture = new Architecture(bitStrings.get(i), 1);

            // Evaluate the architecture
            Result result = AE.evaluateArchitecture(architecture,"Slow");

            // Save the score and the cost
            double cost = result.getCost();
            double science = result.getScience();

            assertEquals(expectedCosts.get(i).doubleValue(), cost);
            assertEquals(expectedSciences.get(i).doubleValue(), science);

            System.out.println("Architecture " + i + " is correct.");
        }
    }
}