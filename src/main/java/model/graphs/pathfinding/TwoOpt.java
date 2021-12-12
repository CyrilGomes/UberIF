package model.graphs.pathfinding;

import model.PlanningRequest;
import model.graphs.Graph;
import observer.Observer;
import view.MainWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class TwoOpt extends TemplateTSP{
    
    


    public TwoOpt(Observer mainWindow) {
        addObserver(mainWindow);
    }
    public TwoOpt() {
    }

    private float getPermutationCost(String[] route){
        float cost = 0;

        for (int i = 1; i < route.length; i++) {

            cost += g.getCost(route[i-1],route[i]);
        }
        cost += g.getCost(route[route.length -1],route[0]);
        return cost;
    }


    // Method to add element at position
    private static void addElement(
            String[] arr, int source,
            int dest)
    {
        // Converting array to ArrayList
        List<String> list = new ArrayList<>(
                Arrays.asList(arr));

        // Adding the element at position
        list.add(dest, list.remove(source));


        // Converting the list back to array
        arr = list.toArray(arr);

    }


    private String[] optSwap(String[] route, int i, int j){


        String[] newRoute = new String[route.length];


        newRoute = Arrays.copyOf(route,route.length);

        addElement(newRoute,i,j);



        /*
        int counter = 0;
        for (int k = 0; k < i; k++) {
            newRoute[counter] = route[k];
            counter++;
        }
        for (int k = j; k >= i; k--) {
            newRoute[counter] = route[k];
            counter++;
        }
        for (int k = j+1; k < route.length; k++) {
            newRoute[counter] = route[k];
            counter++;
        }*/

        return newRoute;

    }

    @Override
    protected void computeSolution(int timeLimit, Graph g, PlanningRequest planningRequest) {
        boolean amelioration = true;
        Set<String> vertices = g.getVertices();
        int size = g.getNbVertices();

        bestSol = new String[size];
        bestSol[0] = planningRequest.getStartId();
        int k = 1;
        for (String vertex:vertices) {
            if(vertex != bestSol[0]){
                bestSol[k] = vertex;
                k++;
            }
        }

        while (amelioration){
            bestSolCost = getPermutationCost(bestSol);
            amelioration = false;
            System.out.println(Arrays.toString(bestSol));
            for (int i = 1; i < size -1 ; i++) {
                for (int j = i+1; j < size; j++) {
                    String[] newRoute = optSwap(bestSol,i,j);
                    float newDist = getPermutationCost(newRoute);
                    if(newDist < bestSolCost){
                        bestSol = Arrays.copyOf(newRoute,newRoute.length);
                        bestSolCost = newDist;
                        amelioration = true;
                        notifyObservers(getDeliveryTour());
                        System.out.println(bestSolCost);
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        System.out.println("Fini");
    }
}
