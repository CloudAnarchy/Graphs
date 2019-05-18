package com.company;

import java.util.*;

public class Graph {
    private int vertices; // Akmes
    private final LinkedList<Edge> [] adjacencylist; // Adjacency List
    private final int adjacencyMatrix[][];  // Adjacency Matrix
    private List<String> allPaths;
    //    private PriorityQueue<Node> pq;
//    private List<Node> nodes;
    private Set<Integer> settled;
    private int[] dist;
    private int pathsCounter;


    public Graph(int vertices) {
        if(vertices < 0 ) throw new IllegalArgumentException("You can't enter a negative number of vertices!");
        this.vertices = vertices;
        adjacencylist = new LinkedList[vertices];
        adjacencyMatrix = new int[vertices][vertices];
//        this.nodes = new ArrayList<>();

        // We create a PriorityQueue with the specified initial capacity
        // that orders its elements according to the specified comparator
//        pq = new PriorityQueue<>(vertices, new Node());
        dist = new int[vertices];
        settled = new HashSet<Integer>();


        Random r = new Random();
        //initialize adjacency lists for all the vertices
        // and adding colors to them
        for (int i = 0;i < vertices; i++){
//            this.nodes.add(new Node(i));
            adjacencylist[i] = new LinkedList<>();
        }

    }

    public void addEgde(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencyMatrix[source][destination] = weight;
        adjacencylist[source].addFirst(edge); //for directed graph
    }

    public int isEdge(int source, int destination) {
        if (source >= 0 && source < vertices && destination >= 0 && destination < vertices)
            return adjacencyMatrix[source][destination];
        else return -1;
    }

    public boolean isGraphDirected(){
        for(int i = 0;i < vertices; i++)
            for(int j = 0; j < vertices; j++)
                if(adjacencyMatrix[i][j] != adjacencyMatrix[j][i]) return true;
        return false;
    }
    /*
    public String lowestCostPath(int source, int destination){

        findAllPaths(source, destination);

        if(allPaths.size() <= 0) return null;
        String lowestCostPath = allPaths.get(0);
        ArrayList<Integer> weights = new ArrayList<>();
        ArrayList<Integer> weights = new ArrayList<>();
        // For every path
        for(int i = 0;i < allPaths.size(); i++){

            // For every single character in the path
            String nextWord = allPaths.get(i);
            for(int j = 0;j < nextWord.length(); j++){
                if(nextWord.charAt(j) == '-' || nextWord.charAt(j) == '>') continue;
                else {
                   int k = j;
                   String tmp = "";
                   // This usually won't run more than 3 times ( only if we have
                   // a number of nodes/vertices higher than that).
                   while(nextWord.charAt(k) != '-' && nextWord.charAt(k) != '>'){
                       tmp += nextWord.charAt(k);
                       k++;
                   }
                    weights.add(Integer.parseInt(tmp));
                }
            }
            for()
        }
        // Keeping track if there is a red Node in the path.
        //if(colorOfVertice[i].equals("Red"))
         //   doesPathHaveRed.add(i,true);

        return null;
    }*/
    public String lowestCostPath(int source){

        // Implementing Dijkstra's algorithm.
        // Setting all distances to "infinity".
        for(int i = 0;i < vertices; i++)
            dist[i] = Integer.MAX_VALUE;

        // Setting staring node distance to 0
        // and then we add it to the que.
//        nodes.get(source).setCost(0);
//        pq.add(nodes.get(source));
        dist[source] = 0;
        /////////////////////////////
        // While the settled nodes set is not equal to our
        // vertices. (we could also do unsettled nodes and
        // remove one every time we settle one. e.g:
        // while (unsettled.size() != 0)
        while(settled.size() != vertices){

            // We remove the minimum distance node
//            int rem = pq.remove().source;

            // Adding the finalized distance of a node
//            settled.add(rem);

//            lookGeitones(rem);
        }
        return null;
    }

    /*
    public void lookGeitones(int removed){

        int edgeDistance = -1;
        int newDistance  = -1;

        // Gia olous tous geitones tou removed.
        for(int i = 0;i < adjacencylist[removed].size(); i++){
            int nodeID = adjacencylist[removed].get(i).destination;

            if(!settled.contains(nodes.get(nodeID).source)){
                edgeDistance =
            }
        }

     */




    public String findAllPaths(int source , int destination){

        // initializing some basic variables for to find
        // all the paths between 2 nodes.
        this.allPaths = new ArrayList<>();
//        this.paths = new AllPaths();
        this.pathsCounter = 0;
        boolean[] visited = new boolean[vertices];
        visited[source] = true;

        // Main algorithm
        getPaths(source, destination, "", visited);

        // Returning all the paths in a String format.
        String tmp = "<html><h1 style = color:#ffcc00;>All paths </h1><p>";
        for(String path : allPaths)
            tmp += "\n"+path;
        return tmp;
    }

    public void getPaths(int source, int destination, String path, boolean[] visited){
        String newPath = path +  "->" + source;
        visited[source] = true;
        LinkedList<Edge> list = adjacencylist[source];

        for (Edge edge : list){
            if(edge.destination != destination && visited[edge.destination] == false){
                getPaths(edge.destination, destination, newPath, visited);
            }else if(edge.destination == destination){
                if(allPaths.size() != 0){
                    // If I add to my List while i am in the for
                    // sometimes it will produce a ConcurrentModificationException
                    // So I keep a "remainder" and i add the path/String later.
                    boolean concurrentSolution = false;

                    // If tmp != from the rest paths so far add it
                    // to the list
                    for(String s : allPaths){
                        String tmp = newPath + "->" + edge.destination;
                        if(tmp != s) concurrentSolution = true;
                    }
                    if(concurrentSolution)
                        allPaths.add(pathsCounter++,newPath + "->" + edge.destination);
                }else{
                    allPaths.add(pathsCounter++,newPath + "->" + edge.destination);
                }
                System.out.println(newPath + "->" + edge.destination);
            }
        }
        //remove from path
        visited[source] = false;
    }

    public String shortestPath(int source, int destination){

        // First we find all the paths.
        findAllPaths(source , destination);

        // Then we just check for their lengths.
        String shortestPath;
        if(allPaths.size() > 0){
            shortestPath = allPaths.get(0);
            for(int i = 0; i < allPaths.size(); i++)
                if(allPaths.get(i).length() < shortestPath.length())
                    shortestPath = allPaths.get(i);
        }else shortestPath = null;
        return shortestPath;
    }

    public int getVertices(){
        return vertices;
    }

    public String isGraphIsomorphic(){
        String text = "<html><h1 style = color:#057aa0;>Is Current Graph isomorphic?</h1><p>";

        boolean isIsomorphic = true;

        for(int i = 0;i < vertices; i++){
            for(int j = 0;j < vertices; j++){
                if(adjacencyMatrix[i][j] != adjacencyMatrix[j][i]){
                    isIsomorphic = false;
                    break;
                }
            }
        }
        text += ""+isIsomorphic;
        return text;
    }
    public String areNodesConnected(){
        String notConnected = "<html><h1 style = color:red;> Not Connected Nodes!</h1><p>";

        int count = 0;
        System.out.println("Not Connected Nodes!");
        for(int i = 0;i < vertices; i++){
            LinkedList<Edge> list = adjacencylist[i];

            if(list.size() == 0) {
                notConnected += "V"+i+"\n";
                System.out.println("V"+i);
            }else count++;
        }

        if(count == vertices)
            notConnected = "<html><h1 style = color:#06c666;>All Connected!</h1><p>All the nodes are connected to the Graph";
        return notConnected;
    }
    public String printGraph(){

        // ADJACENCY LIST PRINT
        String myText = "<html><h1 style = color:#0c6d62;>\tList and Matrix</h1><p>";
        myText += "\n\tADJACENCY LIST\n\n";
        System.out.println("\n\t ADJACENCY LIST");
        for (int i = 0;i < vertices; i++) {
            LinkedList<Edge> list = adjacencylist[i];
            myText += "V"+i;
            System.out.print("V"+i);
            for (int j = 0; j < list.size() ; j++) {
                myText += "---> " +
                        list.get(j).destination + "(" +  list.get(j).weight+"W) ";

                System.out.print("---> " +
                        list.get(j).destination + "(" +  list.get(j).weight+"W) ");
            }
            myText += "\n";
            System.out.println();
        }



        // ADJACENCY MATRIX PRINT
        myText +="\nADJACENCY MATRIX\n\n     ";
        System.out.println("\n");
        System.out.println("\tADJACENCY MATRIX");
        System.out.print("    ");
        for(int k = 0;k<vertices;k++){
            if(k < 10){
                myText += "0"+k+"  ";
                System.out.print("0"+k+"  ");
            } else {
                myText += ""+k+"  ";
                System.out.print(""+k+"  ");
            }
        }

        myText+= "\n";
        System.out.println("\n");

        for(int i = 0;i < vertices; i++){
            for(int j = 0;j < vertices; j++){
                if(j == 0){
                    if(i < 10){
                        myText += "0"+i+"|  ";
                        System.out.print("0"+i+"|  ");
                    } else {
                        myText += ""+i+"|  ";
                        System.out.print(i+"|  ");
                    }
                }
                myText += adjacencyMatrix[i][j] + "    ";
                System.out.print(adjacencyMatrix[i][j] + "   ");
            }
            myText += "\n";
            System.out.println();
        }

        return myText;
    }


    /*
    // Inner class Nodes
    static class Node implements Comparator<Node> {
        private int source;
        private int cost;
        private int weight;
        private Random r = new Random();
        private boolean isRed;

        public Node(){}
        public Node(int source){
            this.source = node;
            this.isRed = r.nextBoolean();
        }

        public Ne(int node, int cost){
            this.source = node;
            this.cost  = cost;
            this.isRed = r.nextBoolean();
        }

        public Edge(int node, int cost, int weight){
            this.source = node;
            this.cost  = cost;
            this.weight = weight;
            this.isRed = r.nextBoolean();
        }

        @Override
        public int compare(Node node1, Node node2){
            if(node1.cost < node2.cost) return -1;
            if(node1.cost > node2.cost) return 1;
            return 0;
        }

        public int getNode() {
            return source;
        }
        public void setNode(int node) {
            this.source = node;
        }
        public int getCost() {
            return cost;
        }
        public void setCost(int cost) {
            this.cost = cost;
        }
        public boolean isRed() {
            return isRed;
        }
    }

     */



    // Inner class Edge
    static class Edge {
        int source;
        int destination;
        int weight;

        public Edge(int source, int destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }
}


