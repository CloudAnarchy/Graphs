package com.company;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.DefaultGraph;


import org.graphstream.ui.view.Viewer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

public class Gui extends JFrame {

    private MyGraph myGraph;
    private Graph visualGraph;
    private final int EDGES_RANDOM_NUM = 150;
    private final int VERTICES_RANDOM_NUM = 20;
    private final int WEIGHT_RANDOM_NUM = 200;

    public Gui(String[] welcomeTexts){

        this.myGraph = loadTemplateGraph();
        this.visualGraph = loadTamplateVisualGraph(myGraph);
        changeStyle(0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(490, 550);
       // setResizable(false);
        setLayout(new GridLayout(2,3));


        JPanel panel = new JPanel();


        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(0,1));

        panel2.add(loadGraphBut("Load a new Graph"));
        panel2.add(printGraphBut( "Print current Graph"));


        panel.add(makeGraphBut("Make an EMPTY Graph"));
        panel.add(addNodeBut("Add a Node"));
        panel.add(addEdgeBut( "Add an Edge"));
        panel.add(findAllPathsBut("Find all paths between 2 nodes"));
        panel.add(shortestPathBut("Find shortest path between 2 nodes"));
        panel.add(areNodesConnectedBut("Are all nodes connected to the Graph?"));
        panel.add(isGraphIsomorphicBut("Is current Graph isomorphic?"));
        panel.add(lowestCostPathBut("Lowest Cost Path to source!"));
        panel.add(isGraphDirected("Is current Graph directed?"));
        panel.add(changeNodeColorBut("Change Color of a Node"));

        visualizingTheGraph();


        add(panel2);
        add(panel);
        //pack();
        setVisible(true);
        JOptionPane.showMessageDialog(null, String.format(welcomeTexts[1], 130, 130));
        JOptionPane.showMessageDialog(null, String.format(welcomeTexts[0], 130, 130));
    }

    public JButton changeNodeColorBut(String title){
        JButton b = new JButton(title);

        b.addActionListener(new MakingButton(){
            private String userInput;
            private int newButPressedCount = 0;
            private int[] nums = new int[2];

            @Override
            public JButton newBut(){
                JButton button = new JButton("Enter");

                button.addActionListener(e -> {
                    userInput = getTextField().getText();
                    getTextField().setText("");

                    try{
                        nums[newButPressedCount] = Integer.parseInt(userInput);
                        nums[newButPressedCount] = Math.abs(nums[newButPressedCount]);

                        switch(newButPressedCount){
                            case 0:
                                getLabel().setText("Is the color red? (1 or 0): ");
                                newButPressedCount++;
                                break;
                            case 1:
                                newButPressedCount = 0;


                                String nodeColor = myGraph.changeNodeColor(nums[0], nums[1]);
                                visualGraph.getNode(nums[0]).addAttribute("ui.style","fill-color: "+nodeColor);
                                remove(getP());
                                break;
                        }
                        validate();
                        repaint();
                    }catch(NumberFormatException ee){
                        String infoMessage = "Please Enter a valid number";
                        JOptionPane.showMessageDialog(null, infoMessage, "Not a valid input!" , JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                return button;
            }

        });

        return b;
    }


    public JButton addNodeBut(String title){
        JButton b = new JButton(title);

        // Either v is the same but i just want to illustrate
        // that i am only using the library only for the visuals nothing else.
        b.addActionListener(e ->{
            int vertices = myGraph.getVertices();
            myGraph.setVertices(vertices+1);

            int v = visualGraph.getNodeCount();
            visualGraph.addNode(""+v).addAttribute("ui.label", "Node: "+v);


            String nodeColor = myGraph.gettingNode(vertices).isRed() ? "red;" : "blue;";
            visualGraph.getNode(v).addAttribute("ui.style", "fill-color: "+nodeColor);
        });


        return b;
    }

    public void visualizingTheGraph(){
        Viewer viewer = this.visualGraph.display();
        viewer.enableAutoLayout();
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    }

    public JButton lowestCostPathBut(String title){
        JButton b = new JButton(title);

        b.addActionListener(new MakingButton(){
            private String userInput;
            private int num = 0;

            @Override
            public JButton newBut(){
                JButton button = new JButton("Enter");

                button.addActionListener(e -> {
                    userInput = getTextField().getText();
                    System.out.println(userInput); // Debugging
                    getTextField().setText("");

                    try {
                        num = Integer.parseInt(userInput);
                        num = Math.abs(num);
                        String text = myGraph.lowestCostPath(num);

                        JOptionPane.showMessageDialog(null, String.format(text));
                        revalidate();
                        repaint();
                        remove(getP());
                    }catch ( NumberFormatException ee){
                        String infoMessage = "Please Enter a valid number";
                        JOptionPane.showMessageDialog(null, infoMessage, "Not a valid input!" , JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                return button;
            }
        });
        return b;
    }
    public JButton isGraphIsomorphicBut(String title){
        JButton b = new JButton(title);

        b.addActionListener(e -> {
            String text = myGraph.isGraphIsomorphic();
            JOptionPane.showMessageDialog(null, String.format(text));
        });
        return b;
    }

    public JButton isGraphDirected(String title){
        JButton b = new JButton(title);

        b.addActionListener(e -> {
            boolean isDirected = myGraph.isGraphDirected();
            String text = "<html><h1 style = color:#9966ff;>Is current Graph Directed?</h1><p>"+isDirected;

            JOptionPane.showMessageDialog(null, String.format(text));
        });

        return b;
    }

    // A helping class so i can write a bit less.
    private class MakingButton implements ActionListener{

        private JTextField textField = new JTextField(5);
        private JPanel p;
        private JLabel label;

        @Override
        public void actionPerformed(ActionEvent e){
            label = new JLabel("Enter source:");
            label.setLabelFor(textField);
            JButton newButton = newBut();

            //JPanel
            p = new JPanel();
            p.add(label);
            p.add(textField);
            p.add(newButton);

            add(p, BorderLayout.CENTER);
            setVisible(true);
        }

        // Getters
        public JTextField getTextField() {
            return textField;
        }
        public JPanel getP() {
            return p;
        }
        public JLabel getLabel() {
            return label;
        }
        public JButton newBut(){return null;}

    }

    public JButton shortestPathBut(String title){
        JButton b = new JButton(title);

        b.addActionListener(new MakingButton(){
            private String userInput;
            private int newButPressedCount = 0;
            private int[] nums = new int[2];

            @Override
            public JButton newBut(){
                JButton button = new JButton("Enter");

                button.addActionListener(e -> {
                    userInput = getTextField().getText();
                    System.out.println(userInput); // Debugging
                    getTextField().setText("");

                    try{
                        nums[newButPressedCount] = Integer.parseInt(userInput);
                        nums[newButPressedCount] = Math.abs(nums[newButPressedCount]);

                        switch(newButPressedCount){
                            case 0:
                                getLabel().setText("Enter destination:");
                                newButPressedCount++;
                                break;
                            case 1:
                                newButPressedCount = 0;
                                String shortestPath,text = "<html><h1 style = color:#ff9933;>The Shortest Path is</h1><p>";

                                if((shortestPath = myGraph.shortestPath(nums[0], nums[1])) == null){
                                    text += "No paths for source "+nums[0]+" and destination "+nums[1];
                                }else{


                                    text += shortestPath;
                                }

                                JOptionPane.showMessageDialog(null, String.format(text));
                                revalidate();
                                repaint();
                                remove(getP());
                                break;
                        }
                        validate();
                        repaint();
                    }catch(NumberFormatException ee){
                        String infoMessage = "Please Enter a valid number";
                        JOptionPane.showMessageDialog(null, infoMessage, "Not a valid input!" , JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                return button;
            }

        });

        return b;
    }

    public JButton areNodesConnectedBut(String title){
        JButton b = new JButton(title);

        b.addActionListener(e -> {

            String text = myGraph.areNodesConnected();
            JOptionPane.showMessageDialog(null, String.format(text, 180, 180));
        });

        return b;
    }


    public JButton findAllPathsBut(String title){
        JButton b = new JButton(title);

        b.addActionListener(new MakingButton(){
            private String userInput;
            private int newButPressedCount = 0;
            private int[] nums = new int[2];

            @Override
            public JButton newBut(){
                JButton button = new JButton("Enter");

                button.addActionListener(e -> {
                    userInput = getTextField().getText();
                    System.out.println(userInput); // Debugging
                    getTextField().setText("");

                    try{
                        nums[newButPressedCount] = Integer.parseInt(userInput);
                        nums[newButPressedCount] = Math.abs(nums[newButPressedCount]);

                        switch(newButPressedCount){
                            case 0:
                                getLabel().setText("Enter destination:");
                                newButPressedCount++;
                                break;
                            case 1:
                                newButPressedCount = 0;
                                String text = myGraph.findAllPaths(nums[0], nums[1]);

                                JOptionPane.showMessageDialog(null, String.format(text));
                                revalidate();
                                repaint();
                                remove(getP());
                                break;
                        }
                        validate();
                        repaint();
                    }catch(NumberFormatException ee){
                        String infoMessage = "Please Enter a valid number";
                        JOptionPane.showMessageDialog(null, infoMessage, "Not a valid input!" , JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                return button;
            }

        });

        b.setVisible(true);
        return b;
    }
    public JButton loadGraphBut(String title){
        JButton b = new JButton(title);

        b.setBackground(Color.GREEN);

        b.addActionListener(e->{
           myGraph = loadGraph();
           visualizingTheGraph();
        });
        return b;
    }
    public JButton addEdgeBut(String title){
        JButton b = new JButton(title);

        b.addActionListener(new MakingButton() {

            private String userInput;
            private int newButPressedCount = 0;
            private int[] nums = new int[3];

            @Override
            public JButton newBut(){
                JButton button = new JButton("Enter");

                button.addActionListener(e -> {

                    userInput = getTextField().getText();
                    System.out.println(userInput); // Debugging
                    getTextField().setText("");

                    try{
                        nums[newButPressedCount] = Integer.parseInt(userInput);
                        nums[newButPressedCount] = Math.abs(nums[newButPressedCount]);

                        switch(newButPressedCount){
                            case 0:
                                getLabel().setText("Enter destination:");
                                newButPressedCount++;
                                break;
                            case 1:
                                getLabel().setText("Enter weight:");
                                newButPressedCount++;
                                break;
                            case 2:
                                newButPressedCount = 0;
                                myGraph.addEgde(nums[0], nums[1], nums[2]);

                                // Checking in the visual Graph if the edge exists.
                                String edgeID = "" + nums[0] + "" + nums[1];
                                boolean addEdge = true;
                                for(Edge edge : visualGraph.getEachEdge()) {
                                    if (edge.getId().equals(edgeID)) {
                                        JOptionPane.showMessageDialog(null, "This edge already exists");
                                        addEdge = false;
                                    }
                                }

                                // Adding a new visual Edge
                                if(addEdge) {
                                    visualGraph.addEdge(edgeID, nums[0], nums[1], true)
                                            .addAttribute("weight", nums[2]);
                                    visualGraph.getEdge(edgeID).addAttribute("ui.label", "" + nums[2]);
                                }
                                remove(getP());
                                break;
                        }
                        validate();
                        repaint();
                    }catch(NumberFormatException ee){
                        String infoMessage = "Please Enter a valid number";
                        JOptionPane.showMessageDialog(null, infoMessage, "Not a valid input!" , JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                return button;
            }


        });

        return b;
    }
    public JButton printGraphBut(String title){
        JButton b = new JButton(title);

        b.setBackground(Color.GREEN);
        b.addActionListener(e -> {

            String text = myGraph.printGraph();
            JOptionPane.showMessageDialog(null, String.format(text, 200, 200));
            revalidate();
            repaint();

        });

        return b;
    }
    public JButton makeGraphBut(String title){

        JButton b = new JButton(title);

        b.addActionListener(new MakingButton() {

            private String userInput;

            @Override
            public JButton newBut(){
                getLabel().setText("Enter Number of vertices:");
                JButton button = new JButton("Enter");

                button.addActionListener(e -> {

                    userInput = getTextField().getText();
                    System.out.println(userInput);  // Debugging
                    getTextField().setText("");

                    try{
                        int V = Integer.parseInt(userInput);
                        V = Math.abs(V);
                        myGraph = new MyGraph(V);

                        // Making a new Empty Graph

                        visualGraph = new DefaultGraph("Empty Graph");
                        for(int i = 0;i < V; i++)
                            visualGraph.addNode(""+i);
                        for(int i = 0;i < V; i++){
                            String nodeColor = myGraph.gettingNode(i).isRed() ? "red;" : "blue;";
                            visualGraph.getNode(i).setAttribute("ui.style", "fill-color: "+nodeColor);
                        }
                        visualGraph = addCosmetics(visualGraph);


                        visualizingTheGraph();
                        remove(getP());
                        validate();
                        repaint();
                    } catch (NumberFormatException ee){
                        String infoMessage = "Please Enter a valid number";
                        JOptionPane.showMessageDialog(null, infoMessage, "Not a valid input!" , JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                return button;
            }
        }
        );

        b.setVisible(true);
        return b;
    }

    private Graph loadTamplateVisualGraph(MyGraph myGraph){
        Graph graph = new DefaultGraph("Visual Graph");

        //Sprite[] sprites = new Sprite[14];

        for(int i = 0;i < 6; i++)
            graph.addNode(""+i);

        graph.addEdge("01", "0", "1", true).addAttribute("weight", 10);
        graph.addEdge("02", "0", "2", true).addAttribute("weight", 15);
        graph.addEdge("15", "1", "5", true).addAttribute("weight", 15);
        graph.addEdge("13", "1", "3", true).addAttribute("weight", 12);
        graph.addEdge("24", "2", "4", true).addAttribute("weight", 10);
        graph.addEdge("34", "3", "4", true).addAttribute("weight", 2);
        graph.addEdge("35", "3", "5", true).addAttribute("weight", 1);
        graph.addEdge("54", "5", "4", true).addAttribute("weight", 5);
        for (Edge e : graph.getEachEdge())
            e.addAttribute("label", "" + (int) e.getNumber("weight"));

        //SpriteManager spriteManager = new SpriteManager(graph);
        for(int i = 0;i < 6; i++) {
            Node node = graph.getNode(i);

            // Setting up a label
            node.setAttribute("ui.label","Node: "+i);

            // Giving it color bases on the color of my nodes.
            if(myGraph.gettingNode(i).isRed())
                node.setAttribute("ui.style", "fill-color: red;");
            else
                node.setAttribute("ui.style", "fill-color: blue;");
        }

       //String tmp  ="node { stroke-mode: plain; fill-color: white;shape: rounded-box;size-mode: fit;text-size:13; padding: 4px, 4px;}";
        //graph.addAttribute("ui.stylesheet", tmp);

        return addCosmetics(graph);
    }

    private Graph addCosmetics(Graph graph){
        URL url = this.getClass().getResource("attributes.css");

        graph.addAttribute("ui.stylesheet","url("+url+")");
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
        return graph;
    }

    // Loads a simple directed graph.
    private MyGraph loadTemplateGraph(){
        MyGraph myGraph = new MyGraph(6);

        myGraph.addEgde(0, 1, 10);
        myGraph.addEgde(0, 2, 15);
        myGraph.addEgde(1, 5, 15);
        myGraph.addEgde(1, 3, 12);
        myGraph.addEgde(2, 4, 10);
        myGraph.addEgde(3, 4, 2);
        myGraph.addEgde(3, 5, 1);
        myGraph.addEgde(5, 4, 5);

        return myGraph;
    }
    // Makes and loads a random myGraph
    private MyGraph loadGraph(){

        // New visual Graph
        Graph graph = new DefaultGraph("New Graph");

        // We create a random number for Vertices. (V > 0)
        Random random = new Random();
        ArrayList<String> paths = new ArrayList<>();
        boolean found;

        int V = random.nextInt(VERTICES_RANDOM_NUM)+1;

        // Adding the nodes to the visual graph.
        for(int i = 0;i < V; i++)
            graph.addNode(""+i);

        // We make a myGraph with these Vertices
        MyGraph myGraph = new MyGraph(V);
        // I add edges x10 Vertices.
        for(int i = 0; i < random.nextInt(EDGES_RANDOM_NUM)+1; i++){
            found = false;
            // Our random number for our source and destination
            // can't be a number bigger or smaller than our existing vertices
            int source = random.nextInt(V);
            int destination = random.nextInt(V);
            while(source == destination) source = random.nextInt(V); // So we can do DFS easier.Later on.
            String tmp = source+""+destination;

            // To ensure that we won't add the same path more
            // than once. e.g: 0->1 , 0->1
            for(String path : paths)
                if(tmp.equals(path)) found = true;

            if (found) continue;
            else paths.add(tmp);

            int weight = random.nextInt(WEIGHT_RANDOM_NUM);
            myGraph.addEgde(source, destination, weight);
            graph.addEdge(""+source+""+destination, source, destination, true).addAttribute("weight", ""+weight);
        }

        // Adding the weight labels.
        for (Edge e : graph.getEachEdge())
            e.addAttribute("label", "" + (int) e.getNumber("weight"));

        int k = 0;
        for(Node node : graph.getEachNode()) {
            // Setting up a label
            node.setAttribute("ui.label","Node: "+k);

            // Giving it color bases on the color of my nodes.
            String nodeColor = myGraph.gettingNode(k).isRed() ? "red;" : "blue;";

            node.setAttribute("ui.style", "fill-color: "+nodeColor);
            k++;
        }
        this.visualGraph = addCosmetics(graph);
        return myGraph;
    }

    private void changeStyle(int styleChoice){
        try{
            switch(styleChoice){
                case 0:
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                    break;
                case 1:
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
                    break;
                case 2:
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
                    break;
            }
        }catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }
    }
}
