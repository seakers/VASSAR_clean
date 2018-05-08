/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rbsa.eoss;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;
import org.moeaframework.algorithm.AbstractEvolutionaryAlgorithm;
import org.moeaframework.core.Algorithm;
import org.moeaframework.core.Population;
import org.moeaframework.core.Solution;
import org.moeaframework.util.TypedProperties;

import java.io.IOException;
import java.util.concurrent.Callable;

/**
 *
 * @author nozomihitomi
 */
public class InteractiveSearch implements Callable<Algorithm> {

    private final Algorithm alg;
    private final TypedProperties properties;
    private final String username;
    private final RedisClient redisClient;

    public InteractiveSearch(Algorithm alg, TypedProperties properties, String username, RedisClient redisClient) {
        this.alg = alg;
        this.properties = properties;
        this.username = username;
        this.redisClient = redisClient;
    }

    @Override
    public Algorithm call() throws IOException  {

        int populationSize = (int) properties.getDouble("populationSize", 600);
        int maxEvaluations = (int) properties.getDouble("maxEvaluations", 10000);

        // run the executor using the listener to collect results
        System.out.println("Starting " + alg.getClass().getSimpleName() + " on " + alg.getProblem().getName() + " with pop size: " + populationSize);
        alg.step();
        long startTime = System.currentTimeMillis();

        while (!alg.isTerminated() && (alg.getNumberOfEvaluations() < maxEvaluations)) {
            alg.step();
            Population pop = ((AbstractEvolutionaryAlgorithm) alg).getPopulation();
            StatefulRedisConnection<String, String> connection = redisClient.connect();
            RedisCommands<String, String> syncCommands = connection.sync();
            for(int i=1; i<3; i++){
                Solution s = pop.get(pop.size() - i);
                s.setAttribute("NFE", alg.getNumberOfEvaluations());
                // Send the new architectures through REDIS
                Gson gson = new GsonBuilder().create();
                syncCommands.rpush(this.username, gson.toJson(s));
            }
            syncCommands.ltrim(this.username, -1, -1000);
            connection.close();
            // Notify listeners of new architectures in username channel
            StatefulRedisPubSubConnection<String, String> pubsubConnection = redisClient.connectPubSub();
            RedisPubSubCommands<String, String> sync = pubsubConnection.sync();
            sync.publish(this.username, "new_arch");
            pubsubConnection.close();
        }

        alg.terminate();
        long finishTime = System.currentTimeMillis();
        System.out.println("Done with optimization. Execution time: " + ((finishTime - startTime) / 1000) + "s");

        return alg;
    }

}
