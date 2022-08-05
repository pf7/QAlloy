package kodkod.engine.config;

import java.util.Arrays;
import java.util.Collection;

import static kodkod.engine.config.QuantitativeOptions.QuantitativeSolver.*;

/**
 * Analogously to the qualitative {@link Options},
 * represents the solver used on a quantitative problem, it's configurations and other
 * characteristics to be considered during the solving process.
 *
 * @specfield solver             : QuantitativeSolver // Solver to be used
 * @specfield binaryLocation     : String             // Path to this.solver executable binary
 * @specfield maximumWeight      : Integer            // Integer values upper bound
 * @specfield incremental        : boolean            // true iff this.solver will perform incremental solving
 * @specfield maxPrimaryVariable : int                // Maximum number of primary variables in this.solver
 */
public class QuantitativeOptions {

    /**
     * Represents the currently supported quantitative solvers and its characteristics, like:
     * - if it's executed through a binary;
     * - if it supports incremental solving.
     *
     * @specfield onBinary : boolean
     * @specfield canBeIncremental : boolean
     */
    public enum QuantitativeSolver{
        CVC4( true, true),
        Z3(true, true),
        MathSAT(true, true),
        Yices(true, true);

        private final boolean onBinary;
        private final boolean canBeIncremental;

        QuantitativeSolver(boolean onBinary, boolean incremental){
            this.onBinary = onBinary;
            this.canBeIncremental = incremental;
        }

        /**
         * Checks if there is a solver with the given name currently supported.
         */
        public static boolean containsSolver(String solver){
            return Arrays.asList("CVC4", "Z3", "MATHSAT", "YICES").contains(solver.toUpperCase());
        }

        /**
         * @return Corresponding QuantitativeSolver
         * @throws IllegalArgumentException if !QuantitativeSolver.contains(solver)
         */
        public static QuantitativeSolver getSolver(String solver){
            QuantitativeSolver s;

            switch (solver.toUpperCase()){
                case "CVC4":
                    s = CVC4;
                    break;
                case "Z3":
                    s = Z3;
                    break;
                case "MATHSAT":
                    s = MathSAT;
                    break;
                case "YICES":
                    s = Yices;
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported solver: " + solver);
            }

            return s;
        }

        /**
         * @return this.onBinary
         */
        public boolean hasBinary(){
            return onBinary;
        }

    }

    private final QuantitativeSolver solver;
    private final String binaryLocation; //can be null (null iff !solver.hasBinary?)
    private final Integer maximumWeight; //null <=> unlimited
    private final boolean incremental;
    private int maxPrimaryVariable;

    /**
     * Stores the quantitative solving options associated with the given parameters.
     * @param solver Solver to be used during solving
     * @param binaryLocation Path to the solver binary (can be null)
     * @param maximumWeight Maximum integer value (can be null)
     * @param incrementalSolving true => prefer incremental solving, if the solver supports it, else false
     *        i.e.,
     *        incrementalSolving => this.incremental = this.solver.canBeIncremental
     *                           else this.incremental = false
     * @throws IllegalArgumentException if !QuantitativeSolver.contains(solver)
     */
    public QuantitativeOptions(String solver, String binaryLocation, Integer maximumWeight, boolean incrementalSolving){
        this.solver = getSolver(solver);
        this.binaryLocation = binaryLocation;
        this.maximumWeight = maximumWeight;
        this.incremental = incrementalSolving && this.solver.canBeIncremental;
        this.maxPrimaryVariable = 0;
    }

    /**
     * Stores the quantitative solving options associated with the given parameters.
     * @param solver Solver to be used during solving
     * @param binaryLocation Path to the solver binary (can be null)
     * @param maximumWeight Maximum integer value (can be null)
     * @param incrementalSolving true => prefer incremental solving, if the solver supports it, else false
     *        i.e.,
     *        incrementalSolving => this.incremental = this.solver.canBeIncremental
     *                           else this.incremental = false
     */
    public QuantitativeOptions(QuantitativeSolver solver, String binaryLocation, Integer maximumWeight, boolean incrementalSolving){
        this.solver = solver;
        this.binaryLocation = binaryLocation;
        this.maximumWeight = maximumWeight;
        this.incremental = incrementalSolving;
        this.maxPrimaryVariable = 0;
    }

    /**
     * Stores the quantitative solving options associated with the given parameters and remaining default values.
     * @param solver Solver to be used during solving
     * @param binaryLocation Path to the solver binary (can be null)
     * @throws IllegalArgumentException if !QuantitativeSolver.contains(solver)
     */
    public QuantitativeOptions(String solver, String binaryLocation){
        this(solver, binaryLocation, null, true);
    }

    /**
     * Stores the quantitative solving options associated with the given parameters and remaining default values.
     * @param solver Solver to be used during solving
     * @param binaryLocation Path to the solver binary (can be null)
     */
    public QuantitativeOptions(QuantitativeSolver solver, String binaryLocation){
        this(solver, binaryLocation, null, true);
    }

    /**
     * Creates default solver options for quantitative solving.
     * The solver defaults to CVC4.
     */
    public QuantitativeOptions(){
        this(CVC4, null, null, false);
    }

    /**
     * Deep copy of the given options.
     */
    public QuantitativeOptions(QuantitativeOptions options){
        this.solver = options.solver;
        this.binaryLocation = options.binaryLocation;
        this.maximumWeight = options.maximumWeight;
        this.incremental = options.incremental;
        this.maxPrimaryVariable = options.maxPrimaryVariable;
    }

    /**
     * @return this.solver
     */
    public QuantitativeSolver solver(){
        return this.solver;
    }

    /**
     * @return this.binaryLocation
     */
    public String getBinaryLocation() {
        return binaryLocation;
    }

    /**
     * Checks if integer function symbols will have an upper bound.
     * @return this.maximumWeight != null && this.maximumWeight >= 0
     */
    public boolean hasMaximumWeight(){
        return this.maximumWeight != null && this.maximumWeight >= 0;
    }

    /**
     * @return this.maximumWeight
     */
    public Integer getMaximumWeight() {
        return maximumWeight;
    }

    /**
     * @return this.incremental
     */
    public boolean incremental(){
        return this.incremental;
    }

    /**
     * Sets the maximum number of primary variables allowed.
     */
    public void setMaxPrimaryVariable(int maxPrimaryVariable){
        this.maxPrimaryVariable = maxPrimaryVariable;
    }

    /**
     * @return this.maxPrimaryVariable
     */
    public int getMaxPrimaryVariable(){
        return maxPrimaryVariable;
    }

    /**
     * Checks if the solver is supported under a quantitative setting.
     */
    public static boolean contains(String solver){
        return QuantitativeSolver.containsSolver(solver);
    }

    /**
     * @return Supported solvers.
     */
    public static Collection<QuantitativeSolver> getSolvers(){
        return Arrays.asList(CVC4, Z3, MathSAT, Yices);
    }

    /**
     * @return String of this quantitative options
     */
    public String toString(){
        return  "Solver:" + solver +
                "\nBinary Location:" + binaryLocation +
                "\nMaximum weight:" + maximumWeight +
                "\nIncremental solving:" + incremental +
                "\nmaxPrimaryVar:" + maxPrimaryVariable;
    }

    /**
     * @return deep-copy of this
     */
    @Override
    public QuantitativeOptions clone(){
        return new QuantitativeOptions(this);
    }

}
