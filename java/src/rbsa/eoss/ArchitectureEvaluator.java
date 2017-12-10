package rbsa.eoss;

import rbsa.eoss.local.Params;

import java.util.ArrayList;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class ArchitectureEvaluator {

    public static ArchitectureEvaluator getInstance() {
        if(instance == null) {
            instance = new ArchitectureEvaluator();
        }
        return instance;
    }
    
    private static ArchitectureEvaluator instance = null;

    private Params params;
    private ArrayList<Architecture> population;
    private ResourcePool resourcePool;
    private Resource searchRes;
    private ExecutorService executorService;
    private Stack<Result> results;
    private ArrayList<Future<Result>> futures;
    
    private ArchitectureEvaluator() {
        reset();
    }

    public void init(int numCPU) {
        params = Params.getInstance();
        resourcePool = new ResourcePool(numCPU);
        searchRes = new Resource();
        executorService = Executors.newFixedThreadPool(numCPU);
        results.clear();
        futures.clear();
    }

    public void reset() {
        population = null;
        results = new Stack<>();
        resourcePool = null;
        executorService = null;
        searchRes = null;
        futures = new ArrayList<>();
    }

    public void clear() {
        executorService.shutdownNow();
        reset();
    }

    public void evaluatePopulation() {
        for (Architecture arch: population) {
            GenericTask t = new GenericTask(arch, "Slow");
            futures.add(executorService.submit(t));
        }

        for (Future<Result> future: futures) {
            try {
                Result resu = future.get(); // Do something with the results..
                pushResult(resu);
                // TODO: Add a quality check to see if science < 1 and arch is not empty. Push only if it passes quality control
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public Result evaluateArchitecture(Architecture arch, String mode) {
        if (arch.getResult().getScience() == -1) { //not yet evaluated
            GenericTask t = new GenericTask(arch, mode);

            futures.clear();
            futures.add(executorService.submit(t));
            Result result = null;
            try {
                result = futures.get(0).get();
            }
            catch (Exception e) {
                System.out.println(e.getClass() + " : " + e.getMessage());
            }
            return result;
        }
        else {
            return arch.getResult();
        }
    }

    public void clearResults() {
        results.clear();
        futures.clear();
    }

    public void evalMinMax() {
        Architecture max_arch = ArchitectureGenerator.getInstance().getMaxArch();
        Result r2 = evaluateArchitecture(max_arch,"Slow");
        params.maxScience = r2.getScience();
        params.maxCost = r2.getCost();

        Architecture min_arch = ArchitectureGenerator.getInstance().getMinArch();
        Result r1 = evaluateArchitecture(min_arch,"Slow");
        params.minScience = r1.getScience();
        params.minCost = r1.getCost();

        clearResults();
    }

    public ResourcePool getResourcePool()
    {
        return resourcePool;
    }

    public Stack<Result> getResults() {
        return results;
    }

    public void setResults(Stack<Result> results) {
        this.results = results;
    }

    public synchronized void pushResult(Result result) {
        this.results.push(result);
    }

    public Resource getSearchResource() {
        return searchRes;
    }

    public void freeSearchResource() {
        try {
            searchRes.getRete().eval("(reset)");
        }
        catch (Exception e) {
            System.out.println("HOLA");
        }
    }

    public ArrayList<Architecture> getPopulation()
    {
        return population;
    }

    public void setPopulation(ArrayList<Architecture> population) {
        this.population = population;
    }
}
