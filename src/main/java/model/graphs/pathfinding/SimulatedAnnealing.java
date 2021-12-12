package model.graphs.pathfinding;

import model.PlanningRequest;
import model.Request;
import model.graphs.Graph;
import observer.Observer;
import java.util.*;


public class SimulatedAnnealing extends TemplateTSP implements TSP {


    private float t0;
    private final float alpha = 0.96f;
    private final float beta = 1.001f;
    private final float beta0 = 0.0001f;
    private List<String> deliveryPoints;
    private List<String> pickupPoints;
    private String[] permutation;


    private int lastI;
    private int lastJ;
    private int rejected;

    public SimulatedAnnealing(Observer mainWindow) {
        addObserver(mainWindow);
    }
    public SimulatedAnnealing() {
    }

    @Override
    public void computeSolution(int timeLimit, Graph g, PlanningRequest planningRequest) {

        if (timeLimit <= 0) return;

        rejected = 0;
        deliveryPoints = new ArrayList<>();
        pickupPoints = new ArrayList<>();

        List<Request> requests = planningRequest.getRequests();
        for (Request request : requests) {
            deliveryPoints.add(request.getDeliveryId());
            pickupPoints.add(request.getPickupId());
        }

        startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.g = g;


        //greedyPermutation(planningRequest.getStartId());
        float curCost = randomPermutation(planningRequest.getStartId());

        bestSol = Arrays.copyOf(permutation,permutation.length);
        bestSolCost = getPermutationCost();


        //heat();
        t0 = 200000;
        float temp = t0;



        int m = (int) Math.floor(beta0*timeLimit);
        int timer = m;


        int nbIter = 0;

        while(nbIter < timeLimit  && temp > 0.1){

            int totalRejected = 0;
            while(timer >= 0){
                curCost = saStep(curCost,temp);
                totalRejected+=rejected;
                timer--;
            }
            //System.out.println(Arrays.toString(permutation) + "\tCost : " + curCost + "\tTemp : " +temp +"\tTime : "+m + "\tRejected :" +totalRejected);

            temp*=alpha;
            m = (int) Math.floor(beta*m);
            timer = m;
            nbIter++;

        }


    }

    /**
     * @return the cost of the current permutation
     */
    private float getPermutationCost(){
        float cost = 0;

        for (int i = 1; i < permutation.length; i++) {

            cost += g.getCost(permutation[i-1],permutation[i]);
        }
        cost += g.getCost(permutation[permutation.length -1],permutation[0]);
        return cost;
    }


    /**
     * Simulated Annealing step : move an vertex randomly, check if it improved the route and accept it
     * if it doesnt imroove, accept it with a probability of <code>Math.exp(-deltaCost/temp))</code>
     * @param curCost the current cost of the permutation
     * @param temp the current temperature
     * @return the cost of the accepted permutation
     */
    float saStep(float curCost, float temp){

        float newCost;
        float deltaCost;
        String[] current = Arrays.copyOf(permutation,permutation.length);

        randomMove();
        newCost = getPermutationCost();
        deltaCost = newCost-curCost;
        //System.out.println("OldCost: + " + curCost + "\tNewCost: "+ newCost+"\tDeltaCost: " + deltaCost +"\tTemp: "+temp+"\tProba: "+Math.exp(-deltaCost/temp) +"\tBest: "+bestSolCost);

        if(deltaCost < 0){
            curCost = newCost;
            //System.out.println(curCost);
            if(curCost < bestSolCost){
                bestSolCost = curCost;
                bestSol = Arrays.copyOf(permutation,permutation.length);
                rejected = 0;
            }
        }else{
            Random rd = new Random();
            if(rd.nextFloat() < Math.exp(-deltaCost/temp)){
                curCost = newCost;
                rejected = 0;
            }
            else{
                rejected = 1;
                permutation = current;
                //undo(permutation);

            }
        }
        return curCost;
    }

    /**
     * increase the temperature so we have enough rejections
     */
    private void heat(){
        float curCost = getPermutationCost();
        float temp = 1.0f;
        float prctReject = 1.0f;
        float rejectionThreshold = 0.05f;
        int timer = 100;
        int nbReject;
        int i,k;
        int maxIter = 200;

        for (k=0;k<maxIter && prctReject>rejectionThreshold;k++) {
            nbReject = 0;
            for (i=0;i<timer;i++) {
                curCost = saStep(curCost, temp);
                nbReject += rejected;
            }
            prctReject = (float)nbReject / (float)timer;

            temp *= 1.1;
        }

        t0 = temp;
    }

    /**
     * swap 2 elements in an array
     * @param a the index of the first node to be swapped
     * @param b the index of the second node to be swapped
     * @param permutation the permutation in wich we have to swap
     */
    private void swap(int a, int b, String[] permutation){
        String tmp = permutation[a];
        String curr = permutation[b];
        permutation[a] = curr;
        permutation[b] = tmp;
    }


    /**
     * @param source the source index to me moved
     * @param dest the destination where the source should be moved
     */
    private void moveElement( int source,
            int dest)
    {

        // Converting array to ArrayList
        List<String> list = new ArrayList<>(
                Arrays.asList(permutation));

        // Does the move by removing the source and adding it at the destination index
        list.add(dest, list.remove(source));


        // Converting the list back to array
        permutation = list.toArray(permutation);



    }

    /**
     * move one vertex randomly across the tour
     */
    public void randomMove(){

        int size = permutation.length;
        Random rd = new Random();


        boolean isValid = false;
        while(!isValid){

            lastI = rd.nextInt(size-1)+1;
            lastJ = rd.nextInt(size-1)+1;

            moveElement(lastI,lastJ);
            if(checkIsValid() && lastJ != lastI){
                isValid = true;
            }

        }


    }


    /**
     * @return true if the current generated permutation has all the pickup and delivery precedence respected
     */
    private boolean checkIsValid(){
        for (int j = 0; j < permutation.length; j++) {

            if (deliveryPoints.contains(permutation[j])) {
                boolean isValid = false;
                int deliveryIndex = deliveryPoints.indexOf(permutation[j]);
                String pickup = pickupPoints.get(deliveryIndex);
                for (int k = 0; k < j; k++) {

                    if (permutation[k].equals(pickup)) {
                        isValid = true;
                        break;
                    }
                }
                if(!isValid){
                    return false;
                }

            }
        }
        return true;
    }

    /**
     * create a random permutation of the tour
     * @param startNode the starting point
     * @return the cost of the generated permutation
     */
    public float randomPermutation(String startNode) {

        int size = g.getNbVertices();
        permutation = new String[size];

        permutation[0] = startNode;

        Set<String> vertices = g.getVertices();

        int i = 1;
        for (String node: vertices) {
            if(!node.equals(startNode)){
                permutation[i] = node;
                i++;
            }
        }

        Random rd = new Random();

        do{
            int b = rd.nextInt(size-1)+1;
            for (int j = 1; j < permutation.length; j++) {
                swap(j,b,permutation);
            }
        }while(!checkIsValid());


    return getPermutationCost();
    }
}
