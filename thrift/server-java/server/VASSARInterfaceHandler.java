package server;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import java.lang.reflect.Field;
import java.util.*;

import org.apache.thrift.TException;
import rbsa.eoss.local.Params;
import javaInterface.BinaryInputArchitecture;
import javaInterface.VASSARInterface;
import javaInterface.ObjectiveSatisfaction;
import rbsa.eoss.Architecture;
import rbsa.eoss.ArchitectureEvaluator;
import rbsa.eoss.Result;
import rbsa.eoss.CritiqueGenerator;

public class VASSARInterfaceHandler implements VASSARInterface.Iface {

    private Params params;
    private Params specialParams;
    private ArchitectureEvaluator AE = null;
    private ArchitectureEvaluator specialAE = null;

    public VASSARInterfaceHandler() {
        initJess();
    }

    public void ping() {
      System.out.println("ping()");
    }
  
    private void initJess() {
        // Set a path to the project folder
        String path = System.getProperty("user.dir");
        
        // Initialization
        String search_clps = "";
        params = Params.initInstance(path, "FUZZY-ATTRIBUTES", "test","normal", search_clps);//FUZZY or CRISP
        AE = ArchitectureEvaluator.getInstance();
        AE.init(1);
    }

    @Override
    public BinaryInputArchitecture eval(List<Boolean> boolList) {
        // Input a new architecture design
        // There must be 5 orbits. Instrument name is represented by a capital letter, taken from {A,B,C,D,E,F,G,H,I,J,K,L}
        
        String bitString = "";
        for (Boolean b: boolList) {
            bitString += b ? "1" : "0";
        }

        // Generate a new architecture
        Architecture architecture = new Architecture(bitString, 1);

        // Evaluate the architecture
        Result result = AE.evaluateArchitecture(architecture,"Slow");
        
        // Save the score and the cost
        double cost = result.getCost();
        double science = result.getScience();
        List<Double> outputs = new ArrayList<>();
        outputs.add(science);
        outputs.add(cost);
        
        System.out.println("Performance Score: " + science + ", Cost: " + cost);
        return new BinaryInputArchitecture(0, boolList, outputs);
    }

    @Override
    public BinaryInputArchitecture evalSpecial(List<Boolean> boolList) throws TException {
        // Input a new architecture design
        // There must be 5 orbits. Instrument name is represented by a capital letter, taken from {A,B,C,D,E,F,G,H,I,J,K,L}
        String bitString = "";
        for (Boolean b: boolList) {
            bitString += b ? "1" : "0";
        }

        // Generate a new architecture
        Architecture architecture = new Architecture(bitString, 1);

        // Evaluate the architecture
        Result result = specialAE.evaluateArchitecture(architecture,"Slow");

        // Save the score and the cost
        double cost = result.getCost();
        double science = result.getScience();
        List<Double> outputs = new ArrayList<>();
        outputs.add(science);
        outputs.add(cost);

        System.out.println("Special Performance Score: " + science + ", Cost: " + cost);
        return new BinaryInputArchitecture(0, boolList, outputs);
    }

    @Override
    public List<BinaryInputArchitecture> runLocalSearch(List<Boolean> boolList, int experiment_stage) {
        String bitString = "";
        for (Boolean b: boolList) {
            bitString += b ? "1" : "0";
        }

        ArrayList<String> samples = randomLocalChange(bitString, 4, experiment_stage);

        List<BinaryInputArchitecture> out = new ArrayList<>();

        for (String sample: samples) {
            // Generate a new architecture
            Architecture architecture = new Architecture(sample, 1);

            // Evaluate the architecture
            Result result = AE.evaluateArchitecture(architecture,"Slow");

            // Save the score and the cost
            double cost = result.getCost();
            double science = result.getScience();
            List<Double> outputs = new ArrayList<>();
            outputs.add(science);
            outputs.add(cost);

            System.out.println("bitString: " + sample + ", Science: " + science + ", Cost: " + cost);

            BinaryInputArchitecture arch = new BinaryInputArchitecture(0, bitString2BoolArray(sample), outputs);
            out.add(arch);
        }

        return out;
    }

    private ArrayList<String> randomLocalChange(String bitString, int n, int experiment_stage) {
        Random rand = new Random();
        int numVars = params.orbitList.length * params.instrumentList.length;

        ArrayList<String> out = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int k = rand.nextInt(numVars);
            if (experiment_stage > 0) {
                k = params.instrumentList.length*(k/params.instrumentList.length) + 6*(experiment_stage - 1) + (k%12)/2;
            }

            StringBuilder tempBitString = new StringBuilder(bitString);
            if (bitString.charAt(k) == '1') {
                tempBitString.setCharAt(k, '0');
            }
            else {
                tempBitString.setCharAt(k, '1');
            }
            out.add(tempBitString.toString());
        }
        return out;
    }

    private List<Boolean> bitString2BoolArray(String bitString){
        List<Boolean> out = new ArrayList<>();
        for (int i = 0; i < bitString.length(); i++) {
            out.add(bitString.charAt(i) == '1');
        }
        return out;
    }

    @Override
    public List<String> getCritique(List<Boolean> boolList) {
        String bitString = "";
        for(Boolean b: boolList){
            bitString += b ? "1" : "0";
        }
        
        System.out.println(bitString);

        // Generate a new architecture
        Architecture architecture = new Architecture(bitString, 1);

        // Initialize Critique Generator
        CritiqueGenerator critiquer = new CritiqueGenerator(architecture);

        return critiquer.getCritique();
    }

    @Override
    public ArrayList<String> getOrbitList() {
        ArrayList<String> orbitList = new ArrayList<>();
        for(String o: params.orbitList){
            orbitList.add(o);
        }
        return orbitList;
    }

    @Override
    public ArrayList<String> getInstrumentList() {
        ArrayList<String> instrumentList = new ArrayList<>();
        for (String i: params.instrumentList) {
            instrumentList.add(i);
        }
        return instrumentList;
    }

    @Override
    public ArrayList<String> getObjectiveList() {
        ArrayList<String> objectiveList = new ArrayList<>();
        params.objectiveDescriptions.forEach((k, v) -> {
            objectiveList.add(k);
        });
        return objectiveList;
    }

    @Override
    public List<ObjectiveSatisfaction> getScoreExplanation(List<Boolean> arch, boolean useSpecial) {
        String bitString = "";
        for (Boolean b: arch) {
            bitString += b ? "1" : "0";
        }

        // Generate a new architecture
        Architecture architecture = new Architecture(bitString, 1);
        architecture.setEvalMode("DEBUG");

        // Evaluate the architecture
        Result result = null;
        // Save the explanations for each stakeholder score
        List<ObjectiveSatisfaction> explanations = new ArrayList<>();
        if (useSpecial) {
            result = specialAE.evaluateArchitecture(architecture, "Slow");
            for (int i = 0; i < specialParams.panelNames.size(); ++i) {
                explanations.add(new ObjectiveSatisfaction(specialParams.panelNames.get(i),
                        result.getPanelScores().get(i), specialParams.panelWeights.get(i)));
            }
        }
        else {
            result = AE.evaluateArchitecture(architecture, "Slow");
            for (int i = 0; i < params.panelNames.size(); ++i) {
                explanations.add(new ObjectiveSatisfaction(params.panelNames.get(i),
                        result.getPanelScores().get(i), params.panelWeights.get(i)));
            }
        }

        return explanations;
    }

    @Override
    public int changeLoadedFiles(Map<String, String> params_map) throws TException {
        // Set a path to the project folder
        String path = System.getProperty("user.dir");
        // Initialization
        String search_clps = "";
        specialParams = Params.newInstance(path, "FUZZY-ATTRIBUTES", "test","normal", search_clps);
        try {
            for (Map.Entry<String, String> entry : params_map.entrySet()) {
                Field field = Params.class.getField(entry.getKey());
                field.set(specialParams, specialParams.path + entry.getValue());
            }
            specialAE = ArchitectureEvaluator.getNewInstance();
            specialAE.init(1, specialParams);
            return 0;
        }
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}

