/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rbsa.eoss;

import jess.*;
import rbsa.eoss.local.Params;

public class Resource {
    
    private Rete r;
    private QueryBuilder qb;
    private MatlabFunctions m;
    
    public Resource(Params params) {
        r = new Rete();
        qb = new QueryBuilder(r);
        m = new MatlabFunctions(this);
        r.addUserfunction(m);
        
        JessInitializer.getInstance().initializeJess(r, qb, m, params);
    }
    
    public Rete getRete() {
        return r;
    }

    public QueryBuilder getQueryBuilder() {
        return qb;
    }

    public MatlabFunctions getM() {
        return m;
    }
    
}
