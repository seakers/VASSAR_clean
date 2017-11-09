package rbsa.eoss;

import rbsa.eoss.local.Params;
import sun.nio.ch.PollArrayWrapper;

import java.util.ArrayList;
import java.util.HashMap;
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
    private ResourcePool resourcePool;
    private ExecutorService executorService;
    private ArrayList<Future<Result>> futures;
    
    private ArchitectureEvaluator() {
        reset();
    }

    public void init(int numCPU) {
        params = Params.getInstance();
        resourcePool = new ResourcePool(numCPU);
        executorService = Executors.newFixedThreadPool(numCPU);
        futures.clear();
    }

    public void reset() {
        resourcePool = null;
        executorService = null;
        futures = new ArrayList<>();
    }

    public void clear() {
        executorService.shutdownNow();
        reset();
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
        
    public ResourcePool getResourcePool()
    {
        return resourcePool;
    }
}
