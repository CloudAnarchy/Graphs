package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class Gui extends JFrame {

    private Graph graph;

    public Gui(){

        this.graph = loadGraph();
        changeStyle(0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        //setResizable(false);
        setLayout(new GridLayout(0,1));


        //graph.lowestCostPath(1);
        JPanel panel = new JPanel();
        JButton b = new JButton();
        panel.setLayout(null);
        panel.setLayout(new GridLayout(0,2));

        panel.add(loadGraphBut("Load a new Graph"));
        panel.add(printGraphBut( "Print current Graph"));
        panel.add(makeGraphBut("Make an EMPTY Graph"));
        panel.add(addEdgeBut( "Add an Edge"));
        panel.add(findAllPathsBut("Find all paths between 2 nodes"));
        panel.add(shortestPathBut("Find shortest path between 2 nodes"));
        panel.add(areNodesConnectedBut("Are all nodes connected to the graph?"));
        panel.add(isGraphIsomorphicBut("Is current graph isomorphic?"));
        panel.add(lowestCostPath("Αυτό με τα χρώματα."));
        panel.add(isGraphDirected("Is current graph directed?"));



        add(panel);
        //pack();
        setVisible(true);
    }

    public JButton lowestCostPath(String title){
        JButton b = new JButton(title);

        b.addActionListener(e ->{


        });


        return b;
    }

    public JButton isGraphIsomorphicBut(String title){
        JButton b = new JButton(title);

        b.addActionListener(e -> {
            String text = graph.isGraphIsomorphic();
            JOptionPane.showMessageDialog(null, String.format(text));
        });
        return b;
    }

    public JButton isGraphDirected(String title){
        JButton b = new JButton(title);

        b.addActionListener(e -> {
            boolean isDirected = graph.isGraphDirected();
            String text = "<html><h1 style = color:#9966ff;>Is current Graph Directed?</h1><p>"+isDirected;

            JOptionPane.showMessageDialog(null, String.format(text));
        });

        return b;
    }

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
                                String tmp,text = "<html><h1 style = color:#ff9933;>The Shortest Path is</h1><p>";

                                if((tmp = graph.shortestPath(nums[0], nums[1])) == null){
                                    text += "No paths for source "+nums[0]+" and destination "+nums[1];
                                }else text += tmp;

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

            String text = graph.areNodesConnected();
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
                                String text = graph.findAllPaths(nums[0], nums[1]);

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

        b.addActionListener(e -> graph = loadGraph());
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
                                graph.addEgde(nums[0], nums[1], nums[2]);
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
        //b.setBounds(x, y, width, height);

        b.setBackground(Color.GREEN);
        b.addActionListener(e -> {

            String text = graph.printGraph();
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
                        graph = new Graph(V);
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

    // Makes and loads a random graph
    private Graph loadGraph(){

        // We create a random number for Vertices. (V > 0)
        Random random = new Random();

        ArrayList<String> paths = new ArrayList<>();
        boolean found;

        int V = random.nextInt(15)+1;

        // We make a graph with these Vertices
        Graph graph = new Graph(V);
        // I add edges x10 Vertices.
        for(int i = 0; i < graph.getVertices() * (random.nextInt(5)+1); i++){
            found = false;
            // Our random number for our source and destination
            // can't be a number bigger or smaller than our existing vertices
            int source = random.nextInt(V);
            int destination = random.nextInt(V);
            while(source == destination) source = random.nextInt(V); // So we can do DFS easier.Later on.
            String tmp = source+""+destination;

            // To ensure that we won't add the same path more
            // than once. e.g: 0->1 , 0->1
            for(String path : paths) if(tmp.equals(path)) found = true;


            if (found) continue;
            else paths.add(tmp);


            // I put weight lower than 10 because it looks better at printing.
            int weight = random.nextInt(8)+1;
            graph.addEgde(source, destination, weight);
        }
        // Debugging
//        graph.addEgde(0, 1, 4);
//        graph.addEgde(0, 2, 3);
//        graph.addEgde(1, 3, 2);
//        graph.addEgde(1, 2, 5);
//        graph.addEgde(2, 3, 7);
//        graph.addEgde(2, 0, 10);
//        graph.addEgde(3, 4, 2);
//        graph.addEgde(4, 0, 4);
//        graph.addEgde(4, 1, 4);
//        graph.addEgde(4, 5, 6);
        return graph;
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
