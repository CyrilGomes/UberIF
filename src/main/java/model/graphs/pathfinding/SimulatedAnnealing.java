package model.graphs.pathfinding;

import model.DeliveryTour;
import model.PlanningRequest;
import model.Request;
import model.Segment;
import model.graphs.Graph;
import observer.Observable;
import view.MainWindow;

import java.lang.reflect.Array;
import java.util.*;


public class SimulatedAnnealing extends Observable implements TSP {

    private String[] bestSol;
    private String[] permutation;
    protected Graph g;
    private PlanningRequest planningRequest;
    private float bestSolCost;
    private int timeLimit;
    private long startTime;
    private float t0;
    private float alpha = 0.95f;
    private float beta = 1.01f;
    private float beta0 = 0.0001f;
    private List<String> deliveryPoints;
    private List<String> pickupPoints;


    private int lastI;
    private int lastJ;
    private int rejected;
    public SimulatedAnnealing(MainWindow mainWindow) {
        addObserver(mainWindow);
    }

    @Override
    public void searchSolution(int timeLimit, Graph g, PlanningRequest planningRequest) {

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
        this.planningRequest = planningRequest;
        bestSolCost = Float.MAX_VALUE;


        randomPermutation(planningRequest.getStartId());
        float curCost = getPermutationCost();

        heat();
        //t0 = 100;
        float temp = t0;
        System.out.println("Trouvé :" + t0);



        int m = (int) Math.floor(beta0*timeLimit);
        int timer = m;

        int nbIter = 0;

        while(nbIter < timeLimit  && temp > 0.001){

            int totalRejected = 0;
            while(timer >= 0){
                curCost = saStep(curCost,temp);
                totalRejected+=rejected;
                timer--;
            }
            //curCost = saStep(curCost,temp);
            //System.out.println(Arrays.toString(permutation) + "\tCost : " + curCost + "\tTemp : " +temp +"\tTime : "+m + "\tRejected :" +totalRejected);
            temp*=alpha;
            m = (int) Math.floor(beta*m);
            timer = m;
            nbIter++;
            /*
            if(curCost < bestSolCost){
                bestSolCost = curCost;
            }*/



        }

        if(curCost < bestSolCost){
            bestSol = Arrays.copyOf(permutation,permutation.length);
            bestSolCost = getPermutationCost();
        }
        notifyObservers(getDeliveryTour());


    }

    private float getPermutationCost(){
        float cost = 0;

        for (int i = 1; i < permutation.length; i++) {

            cost += g.getCost(permutation[i-1],permutation[i]);
        }
        cost += g.getCost(permutation[permutation.length -1],permutation[0]);
        return cost;
    }




    float saStep(float curCost, float temp){

        float newCost;
        float deltaCost;
        randomMove();
        newCost = getPermutationCost();
        deltaCost = newCost-curCost;
        //System.out.println(" deltaCost : " + deltaCost +"Temp : "+temp+" Proba : "+Math.exp(-deltaCost/temp));
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
                undo(permutation);

            }
        }

        return curCost;

    }

    private void heat(){
        float curCost = getPermutationCost();
        float temp = 1.0f;
        float prctReject = 1.0f;
        float rejectionThreshold = 0.05f;
        int timer = 100;
        int nbReject;
        int i,k;
        int maxIter = 1000;

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
    private void swap(int a, int b, String[] permutation){
        String tmp = permutation[a];
        String curr = permutation[b];
        permutation[a] = curr;
        permutation[b] = tmp;
    }


    public void randomMove(){

        int size = permutation.length;
        Random rd = new Random();







        do{
            lastI = rd.nextInt(size-1)+1;
            lastJ = rd.nextInt(size-1)+1;

            swap(lastI,lastJ,permutation);
        }while (checkIsValid());


        /**
        System.out.println(Arrays.toString(permutation));
        System.out.println(Arrays.toString(permutation));
        reverseRoute(permutation);
        System.out.println(Arrays.toString(permutation));
        System.out.println("------------------------------------");
        */

    }


    public void undo(String[] permutation){

        swap(lastI,lastJ,permutation);
        //reverseRoute(permutation);
    }

    private void reverseRoute(String[] permutation) {
        int randLength = Math.abs(lastI-lastJ);


        int currI = Math.min(lastI, lastJ);
        int currJ = Math.max(lastI, lastJ);

        for (int i = 0 ; i < randLength/2; i++) {
            swap(currI,currJ,permutation);
            currI++;
            currJ--;
        }
    }


    private boolean checkIsValid(){

        for (int j = 0; j < permutation.length; j++) {
            if (deliveryPoints.contains(permutation[j])) {
                int deliveryIndex = deliveryPoints.indexOf(permutation[j]);
                String pickup = pickupPoints.get(deliveryIndex);
                for (int k = 0; k < permutation.length; k++) {

                    if (permutation[k].equals(pickup)) {
                        if (k > deliveryIndex) {
                            return false;
                        }

                    }
                }

            }
        }
        return true;
    }

    public void randomPermutation(String startNode) {

        int size = g.getNbVertices();
        permutation = new String[size];

        permutation[0] = startNode;

        Set<String> vertices = g.getVertices();
        int verticesSize = g.getNbVertices();

        int i = 1;
        for (String node: vertices) {
            if(!node.equals(startNode)){
                permutation[i] = node;
                i++;
            }
        }

        Random rd = new Random();

        int b = rd.nextInt(size-1)+1;
        for (int j = 1; j < permutation.length; j++) {
            swap(j,b,permutation);
        }
        for (int j = 0; j < permutation.length; j++) {
            if (deliveryPoints.contains(permutation[j])) {
                int deliveryIndex = deliveryPoints.indexOf(permutation[j]);
                String pickup = pickupPoints.get(deliveryIndex);
                for (int k = 0; k < permutation.length; k++) {

                    if (permutation[k].equals(pickup)) {
                        int pickupIndex = k;
                        if (pickupIndex > deliveryIndex) {
                            swap(k, j, permutation);
                        }

                    }
                }

            }
        }






    }

    @Override
    public String[] getSolution() {
        return bestSol;
    }

    @Override
    public float getSolutionCost() {
        return bestSolCost;
    }

    @Override
    public DeliveryTour getDeliveryTour() {
        List<Segment> segmentList = new ArrayList<>();

        System.out.println("---- Delivery Tour Get ----");
        DeliveryTour deliveryTour = new DeliveryTour(segmentList, bestSolCost);
        for (String poi: bestSol) {
            deliveryTour.addNextPoint(poi);
            System.out.println("Last intersection : "+deliveryTour.getLastIntersectionId());
        }
        int solutionSize = bestSol.length;
        for (int i = 1; i < solutionSize; i++) {
            Edge edge = g.getEdge(bestSol[i - 1], bestSol[i]);
            segmentList.addAll(edge.segmentList);
            deliveryTour.addListSegment(edge.segmentList);
        }
        Edge edge = g.getEdge(bestSol[solutionSize - 1], bestSol[0]);
        deliveryTour.addListSegment(edge.segmentList);

        return deliveryTour;
    }

}
