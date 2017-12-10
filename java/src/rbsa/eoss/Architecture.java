package rbsa.eoss;

import rbsa.eoss.local.Params;

import java.util.*;

import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author dani
 */

public class Architecture implements Comparable<Architecture>, java.io.Serializable {
    private Params params;
    private boolean[] bitVector;
    private int numOrbits;
    private int numInstr;
    private boolean[][] bitMatrix;
    private String evalMode;
    private String payload;
    private String orbit;
    private Result result;
    private Random random;
    private String mutate;
    private String crossover;
    private String improve;
    private String heuristicsToApply;
    private String heuristicsApplied;
    private String id;
    private int numSatellites;

    
    //Constructors
    public Architecture(boolean[] bitVector, int numOrbits, int numInstruments, int numSatellites) {
        params = Params.getInstance();
        this.bitVector = bitVector;
        this.numOrbits = numOrbits;
        this.numInstr = numInstruments;
        this.numSatellites = numSatellites;
        evalMode = "RUN";
        bitMatrix = bitString2Mat(bitVector, numOrbits, numInstr);
        orbit = null;
        result = new Result(this,-1,-1,-1);
        random = new Random();
        updateOrbitPayload();
        mutate = "no";
        crossover = "no";
        improve = "no";
        heuristicsToApply = "";
        heuristicsApplied = "";
        id = UUID.randomUUID().toString();
    }

    public Architecture(boolean[][] bitMatrix, int numSatellites) {
        params = Params.getInstance();
        this.bitMatrix = bitMatrix;
        numOrbits = bitMatrix.length;
        numInstr = bitMatrix[0].length;
        bitVector = mat2BitString(bitMatrix);
        evalMode = "RUN";
        this.numSatellites = numSatellites;
        orbit = null;
        result = new Result(this,-1,-1,-1);
        random = new Random();
        updateOrbitPayload();
        mutate = "no";
        crossover = "no";
        improve = "no";
        heuristicsToApply = "";
        heuristicsApplied = "";
        id = UUID.randomUUID().toString();
    }

    public Architecture(String bitString, int numSatellites) {
        params = Params.getInstance();
        bitMatrix = booleanString2Matrix(bitString);
        numOrbits = params.numOrbits;
        numInstr = params.numInstr;
        this.numSatellites = numSatellites;
        bitVector = mat2BitString(bitMatrix);
        evalMode = "RUN";
        orbit = null;
        result = new Result(this,-1,-1,-1);
        random = new Random();
        updateOrbitPayload();
        mutate = "no";
        crossover = "no";
        improve = "no";
        heuristicsToApply = "";
        heuristicsApplied = "";
        id = UUID.randomUUID().toString();
    }

    public Architecture(HashMap<String, String[]> mapping, int numSatellites) {
        params = Params.getInstance();
        bitMatrix = new boolean[params.numOrbits][params.numInstr];
        for (int o = 0; o < params.numOrbits; o++) {
            for(int i = 0; i < params.numInstr; i++) {
                bitMatrix[o][i] = false;
            }
        }

        for (int o = 0; o < params.numOrbits; o++) {
            String orb = params.orbitList[o];
            String[] payl = mapping.get(orb);
            if (payl == null)
                continue;
            ArrayList<String> thepayl = new ArrayList<>(Arrays.asList(payl));
            for(int i = 0; i < params.numInstr; i++) {
                String instr = params.instrumentList[i];
                if(thepayl.contains(instr))
                    bitMatrix[o][i] = true;
            }
        }

        numOrbits = bitMatrix.length;
        numInstr = bitMatrix[0].length;
        bitVector = mat2BitString(bitMatrix);
        evalMode = "RUN";
        this.numSatellites = numSatellites;
        orbit = null;
        result = new Result(this,-1,-1,-1);
        random = new Random();
        updateOrbitPayload();
        mutate = "no";
        crossover = "no";
        improve = "no";
        heuristicsToApply = "";
        heuristicsApplied = "";
        id = UUID.randomUUID().toString();
    }

    //Getters
    public int getNumSatellites() {
        return numSatellites;
    }
    public String getId() {
        return id;
    }
    public double getUtility() {
        return result.getUtility();
    }
    public String getOrbit() {
        return orbit;
    }
    public String getMutate() {
        return mutate;
    }
    public String getCrossover() {
        return crossover;
    }
    public String getImprove() {
        return improve;
    }
    public String getEvalMode() {
        return evalMode;
    }
    public boolean[] getBitVector() {
        return bitVector;
    }
    public boolean[][] getBitMatrix() {
        return bitMatrix;
    }
    public Result getResult() {
        return result;
    }
    public int getTotalInstruments() {
        return sumAllInstruments(bitMatrix);
    }
    public String getHeuristicsToApply() {
        return heuristicsToApply;
    }
    
    //Setters
    public void setUtility(double utility) {
        result.setUtility(utility);
    }
    public void setMutate(String mutate) {
        this.mutate = mutate;
    }
    public void setCrossover(String crossover) {
        this.crossover = crossover;
    }
    public void setImprove(String improve) {
        this.improve = improve;
    }
    public void setResult(Result result) {
        this.result = result;
    }
    public void setBitVector(boolean[] bitVector) {
        this.bitVector = bitVector;
    }
    public void setEvalMode(String evalMode) {
        this.evalMode = evalMode;
    }
    public void setHeuristicsToApply(String heuristicsToApply) {
        this.heuristicsToApply = heuristicsToApply;
    }

    //toString
    @Override
    public String toString() {
        String ret = "Arch = " + numSatellites + " x ";
        for (int o = 0; o < numOrbits; o++) {
            String orb = params.orbitList[o];
            String[] payls = this.getPayloadInOrbit(orb);
            if (payls != null) {
                ret += "\n" + orb + ": " + StringUtils.join(payls, " ") ;
            }
        }
        return ret;
    }

    public String toFactString() {
        String ret = "(MANIFEST::ARCHITECTURE" + " (id " + id + ") (num-sats-per-plane " + numSatellites + ") (bitString " + toBitString() + ") (payload " + payload + ") (orbit " + orbit + ")"
                + " (mutate " + mutate + " ) (crossover " + crossover + ") (improve " + improve + ") (heuristics-to-apply " + heuristicsToApply + " ) (heuristics-applied " + heuristicsApplied + ") "
                + "(factHistory F" + params.nof + ")";
        params.nof++;
        if (result != null) {
            ret += " (benefit " + result.getScience() + " ) (lifecycle-cost " + result.getCost() + ")" + " (pareto-ranking " + result.getParetoRanking() + " ) (utility " + result.getUtility() + ")";
        }
        ret += ")";
        return ret;
    }

    public String toBitString() {
        String str = "\"";
        for (boolean b: bitVector) {
            String c = "0";
            if (b) {
                c = "1";
            }
            str += c;
        }
        str += "\"";
        return str;
    }

    // Heuristics
    public Architecture mutate1bit() {
        if (random.nextBoolean()) { //mutate matrix but not nsats
            Integer index = random.nextInt(numOrbits*numInstr - 1);
            boolean[] newBitString = new boolean[numOrbits*numInstr];
            System.arraycopy(bitVector,0, newBitString,0,numOrbits*numInstr);
            newBitString[index] = !bitVector[index];
            Architecture newOne = new Architecture(newBitString, this.numOrbits, this.numInstr, this.numSatellites);
            newOne.setCrossover(crossover);
            newOne.setImprove(improve);
            return newOne;
        }
        else { // mutate nsats but not matrix
            Architecture newOne = new Architecture(bitVector, this.numOrbits, this.numInstr,
                    params.numSatellites[random.nextInt(params.numSatellites.length)]);
            newOne.setCrossover(crossover);
            newOne.setImprove(improve);
            return newOne;
        }
    }
    
    // Utils
    public boolean[][] booleanString2Matrix(String bitString) {
        boolean[][] mat = new boolean[params.numOrbits][params.numInstr];
        for (int i = 0; i < params.numOrbits; i++) {
            for (int j = 0; j < params.numInstr; j++) {
                String b = bitString.substring(params.numInstr *i + j,params.numInstr*i + j + 1);
                if (b.equalsIgnoreCase("1")) {
                    mat[i][j] = true;
                }
                else if (b.equalsIgnoreCase("0")) {
                    mat[i][j] = false;
                }
                else {
                    System.out.println("Architecture: booleanString2Matrix string b is nor equal to 1 or 0!");
                }
            }         
        }
        return mat;
    }

    public static boolean[][] bitString2Mat(boolean[] bitString, int norb, int ninstr) {
        boolean[][] mat = new boolean[norb][ninstr];
        int b = 0;
        for (int i = 0; i < norb; i++) {
            for (int j = 0; j < ninstr; j++) {
                mat[i][j] = bitString[b++];
            }         
        }
        return mat;
    }

    public static boolean[] mat2BitString(boolean[][] mat) {
        int norb = mat.length;
        int ninstr = mat[0].length;
        boolean[] bitString = new boolean[norb*ninstr];
        int b = 0;
        for (int i = 0; i < norb; i++) {
            for (int j = 0; j < ninstr; j++) {
               bitString[b++] = mat[i][j];
            }
        }
        return bitString;
    }

    private int sumRowBool(boolean[][] mat, int row) {
        int x = 0;
        int ncols = mat[0].length;
        for (int i = 0;i<ncols;i++) {
            if (mat[row][i]) {
                x += 1;
            }
        }
        return x;
    }

    private int sumAllInstruments(boolean[][] mat) {
        int x = 0;
        for (boolean[] row: mat) {
            for (boolean val: row) {
                if (val) {
                    x += 1;
                }
            }
        }
        return x;
    }

    public String[] getPayloadInOrbit(String orb) {
        String[] thepayloads = null;
         for (int i = 0; i < params.numOrbits; i++) {
            if (orb.equalsIgnoreCase(params.orbitList[i])) {
                int n = sumRowBool(bitMatrix, i);
                thepayloads = new String[n];
                int k = 0;
                for (int j = 0; j < params.numInstr; j++) {
                    if (bitMatrix[i][j]) {
                        thepayloads[k++] = params.instrumentList[j];
                    }
                }
            }
        }
        return thepayloads;
    }

    private void updateOrbitPayload() {
        for (int i = 0; i < params.numOrbits; i++) {
            int n = sumRowBool(bitMatrix, i);
            if (n > 0) {
                orbit = params.orbitList[i];
                payload = "";
                int k = 0;
                for (int j = 0; j < params.numInstr; j++) {
                    if (bitMatrix[i][j]) {
                        payload += " " + params.instrumentList[j];
                        k++;
                    }
                }
            }
        }
    }
    
    //CompareTo
    @Override
    public int compareTo(Architecture other) {
        if(this.toBitString().compareTo(other.toBitString()) == 0 && this.getNumSatellites() == other.getNumSatellites()) {
            return 0;
        }
        else {
            return 1;
        }
    }

    private static int compare2zero(double x) {
        if(x < 0) {
            return 1;
        }
        else if (x > 0) {
            return -1;
        }
        else {
            return 0;
        }
    }
    public static Comparator<Architecture> ArchCrowdDistComparator = (Architecture a1, Architecture a2) -> {
        double x = (a1.getResult().getCrowdingDistance() - a2.getResult().getCrowdingDistance());
        return compare2zero(x);
    };

    public static Comparator<Architecture> ArchScienceComparator = (Architecture a1, Architecture a2) -> {
        double x = (a1.getResult().getScience() - a2.getResult().getScience());
        return compare2zero(x);
    };

    public static Comparator<Architecture> ArchCostComparator = (Architecture a1, Architecture a2) -> {
        double x = (a1.getResult().getCost() - a2.getResult().getCost());
        return compare2zero(x);
    };
    
    public boolean isFeasibleAssignment() {
        return (sumAllInstruments(bitMatrix) <= params.MAX_TOTAL_INSTR);
    }
    
    public Architecture copy(){
        Architecture arch = new Architecture(this.bitVector, this.numOrbits, this.numInstr, this.numSatellites);
        arch.setResult(this.result);
        return arch;
    }
}
