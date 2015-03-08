package com.devankuleindiren.digitrecognition;

import javax.swing.*;

public class PredictionBoard extends JPanel {

    private JLabel result;
    private static PredictionBoard instance = null;

    public static PredictionBoard getInstance () {
        if (instance == null) {
            instance = new PredictionBoard();
        }

        return instance;
    }

    private PredictionBoard () {
        super();

        result = new JLabel("Draw a digit (0 - 9) above!");

        add(result);
    }

    public void setResult (String string) {
        result.setText(string);
    }

}
