package rbsa.eoss.local;

import rbsa.eoss.Result;
import rbsa.eoss.ResultCollection;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;

public class PrintData {
    public static void main(String[] args) {
        String filename = "/home/antoni/Programacio/daphne/VASSAR_clean/results/2017-12-11_22-30-26_test.rs";
        ResultCollection resultCollection = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            resultCollection = (ResultCollection)ois.readObject();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String csvFile = "./results/out.csv";
        try (BufferedWriter outputWriter = new BufferedWriter(new FileWriter(csvFile))) {
            for (Result result: resultCollection.getFront()) {
                outputWriter.write(result.getArch().toBitString());
                outputWriter.write(",");
                outputWriter.write(Double.toString(result.getScience()));
                outputWriter.write(",");
                outputWriter.write(Double.toString(result.getCost()));
                outputWriter.newLine();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
