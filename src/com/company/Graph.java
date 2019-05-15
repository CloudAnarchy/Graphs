package com.company;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private int vertices; // Akmes
    private LinkedList<Edge> [] adjacencylist; // Adjacency List
    private final int adjacencyMatrix[][];  // Adjacency Matrix
    private List<String> allPaths = new ArrayList<String>();
    private int pathsCounter = 0;

    public Graph(int vertices) {
        if(vertices < 0 ) throw new IllegalArgumentException("You can't enter a negative number of vertices!");
        this.vertices = vertices;
        adjacencylist = new LinkedList[vertices];
        adjacencyMatrix = new int[vertices][vertices];

        //initialize adjacency lists for all the vertices
        for (int i = 0; i < vertices ; i++) adjacencylist[i] = new LinkedList<>();

    }

    public void addEgde(int source, int destination, int weight) {
        Edge edge = new Edge(source, destination, weight);
        adjacencyMatrix[source][destination] = weight;
        adjacencylist[source].addFirst(edge); //for directed graph
    }

    public int isEdge(int i, int j) {
        if (i >= 0 && i < vertices && j > 0 && j < vertices)
            return adjacencyMatrix[i][j];
        else
            return -1;
    }

    public boolean isGraphDirected(){
        for(int i = 0;i < vertices; i++)
            for(int j = 0; j < vertices; j++)
                if(adjacencyMatrix[i][j] != adjacencyMatrix[j][i]) return false;
        return true;
    }

    public String findAllPaths(int source , int destination){
        boolean[] visited = new boolean[vertices];
        visited[source] = true;
        getPaths(source, destination, "", visited);
        String tmp = "<html><h1>All paths </h1><p>";
        for(String path : allPaths)
            tmp += "\n"+path;
        return tmp;
    }

    public void getPaths(int source, int destination, String path, boolean[] visited){
        String newPath = path +  "->" + source;
        visited[source] = true;
        LinkedList<Edge> list = adjacencylist[source];
        for (int i = 0; i <list.size() ; i++) {
            Edge edge = list.get(i);
            if(edge.destination != destination && visited[edge.destination] == false){
//              visited[edge.destination] = true;
                getPaths(edge.destination, destination, newPath, visited);
            }else if(edge.destination == destination){
                if(allPaths.size() != 0){
                    for(String s : allPaths){
                        String tmp = newPath + "->" + edge.destination;
                        if(tmp != s) allPaths.add(pathsCounter++,newPath + "->" + edge.destination);
                    }
                }else{
                    allPaths.add(pathsCounter++,newPath + "->" + edge.destination);
                }

                //allPaths = newPath + "->" + edge.destination;
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
        // TODO not done yet!! checkarei kai tis akmes oles apola ta paths!
        //      or not...not sure.
        String shortestPath = allPaths.get(0);
        for(int i = 0; i < allPaths.size(); i++)
            if(allPaths.get(i).length() < shortestPath.length())
                shortestPath = allPaths.get(i);

        return shortestPath;
    }

    public int getVertices(){
        return vertices;
    }

    public String areNodesConnected(){
        String notConnected = "<html><h1>Not Connected Nodes!</h1><p>\n";

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
            notConnected = "<html><h1>All Connected!</h1><p>All the nodes are connected to the Graph";
        return notConnected;
    }

    public String printGraph(){

        // ADJACENCY LIST PRINT
        String myText = "<html><h1>\tList and Matrix</h1><p>";
        myText += "\n\tADJACENCY LIST\n\n";
        System.out.println("\n\t ADJACENCY LIST");
        for (int i = 0; i <vertices ; i++) {
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
        myText +="\n\tADJACENCY MATRIX\n\n   ";
        System.out.println("\n");
        System.out.println("  ADJACENCY MATRIX");
        System.out.print("   ");
        for(int k = 0;k<vertices;k++){
            myText += " "+k;
            System.out.print(" "+k);
        }

        myText+= "\n";
        System.out.println("\n");

        for(int i = 0;i < vertices; i++){
            for(int j = 0;j < vertices; j++){
                if(j == 0){
                    myText += i+"|  ";
                    System.out.print(i+"|  ");
                }
                myText += adjacencyMatrix[i][j] + " ";

                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            myText += "\n";
            System.out.println();
        }

        return myText;
    }
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


