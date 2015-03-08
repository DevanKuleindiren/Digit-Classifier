package com.devankuleindiren.digitrecognition;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Main extends JFrame {

    private DrawingBoard drawingBoard;
    private PredictionBoard predictionBoard;

    private int inputNodesNo = 785;
    private int hiddenNeuronNo = 15;
    private int outputNeuronNo = 10;

    public Main() {
        super("Digit Recogniser");
        setSize(640, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        JComponent dB = createDrawingBoard();
        add(dB, BorderLayout.CENTER);
        JComponent prediction = createPredictionBoard();
        add(prediction, BorderLayout.SOUTH);

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader("weights.txt"));

            String metadataLine = bufferedReader.readLine();
            String[] metadata = metadataLine.split(",");

            int iNN = Integer.parseInt(metadata[0]);
            int hNN = Integer.parseInt(metadata[1]);
            int oNN = Integer.parseInt(metadata[2]);

            if (iNN == inputNodesNo && hNN == hiddenNeuronNo && oNN == outputNeuronNo) {

                DeepNet deepNet = DeepNet.getInstance(inputNodesNo, hiddenNeuronNo, outputNeuronNo);

                // READ WEIGHTS1
                String weights1String = bufferedReader.readLine();
                String[] weights1 = weights1String.split(",");
                for (int inputNode = 0; inputNode < inputNodesNo; inputNode++) {
                    for (int hiddenNeuron = 0; hiddenNeuron < hiddenNeuronNo; hiddenNeuron++) {
                        try {
                            deepNet.setWeight1(inputNode, hiddenNeuron, Double.parseDouble(weights1[(inputNode * hiddenNeuronNo) + hiddenNeuron]));
                        } catch (ArrayIndexOutOfBoundsException exception) {
                            System.out.println(inputNode + ", " + hiddenNeuron);
                        }
                    }
                }

                // READ WEIGHTS2
                String weights2String = bufferedReader.readLine();
                String[] weights2 = weights2String.split(",");
                for (int hiddenNeuron = 0; hiddenNeuron < hiddenNeuronNo + 1; hiddenNeuron++) {
                    for (int outputNeuron = 0; outputNeuron < outputNeuronNo; outputNeuron++) {
                        try {
                            deepNet.setWeight2(hiddenNeuron, outputNeuron, Double.parseDouble(weights2[(hiddenNeuron * outputNeuronNo) + outputNeuron]));
                        } catch (ArrayIndexOutOfBoundsException exception) {
                            System.out.println(hiddenNeuron + ", " + outputNeuron);
                        }
                    }
                }

                bufferedReader.close();

            } else throw new InvalidWeightFormatException("The network of weights in the file is invalid.");

        } catch (IOException exception) {
            System.out.println("Could not load weights.");
        } catch (InvalidWeightFormatException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private void addBorder(JComponent component, String title) {
        Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
        Border tb = BorderFactory.createTitledBorder(etch,title);
        component.setBorder(tb);
    }

    private JComponent createDrawingBoard() {
        JPanel holder = new JPanel();
        addBorder(holder,"Drawing board");
        DrawingBoard result = DrawingBoard.getInstance();
        holder.add(result);
        this.drawingBoard = result;
        return new JScrollPane(holder);
    }

    private JComponent createPredictionBoard() {
        JPanel holder = new JPanel();
        addBorder(holder, "Prediction");
        PredictionBoard result = PredictionBoard.getInstance();
        holder.add(result);
        this.predictionBoard = result;
        return holder;
    }


    public static void main(String[] args) {
        Main gui = new Main();
        gui.drawingBoard.display(new Image(new int[28][28]));
        gui.setVisible(true);
    }
}