package edu.mit.csail.sdg.translator;

import kodkod.instance.Tuple;

/**
 * Quantitative extension of {@link A4Tuple}.
 * Associated with this tuple is it's weight.
 */
public class A4QtTuple extends A4Tuple {

    private final String weight;

    A4QtTuple(Tuple tuple, A4Solution sol, Number weight){
        super(tuple, sol);
        this.weight = weight.toString();
    }

    A4QtTuple(Tuple tuple, A4Solution sol, String weight){
        super(tuple, sol);
        this.weight = weight;
    }

    /**
     * returns the weight associated with this tuple.
     */
    public String getWeight(){
        return weight;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        //Int representation (1, w) = w TODO: Not needed anymore
        if(this.atom(0).equals("1"))
            sb.append(weight);
        else sb.append(super.toString()).append(" * ").append(weight);
        return sb.toString();
    }
}
