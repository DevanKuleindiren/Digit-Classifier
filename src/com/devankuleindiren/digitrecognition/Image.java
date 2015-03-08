package com.devankuleindiren.digitrecognition;

import java.awt.*;

public class Image {

    private int[][] pixels;
    private int width = 0;
    private int height = 0;


    public Image (int[][] pixels) {
        this.pixels = pixels;
        this.height = pixels.length;
        this.width = pixels[0].length;
    }

    public void setPixel (int row, int col, int value) {
        if (row >= 0 && row < pixels.length
                && col >= 0 && col < pixels[0].length
                && value >= 0) {
            if (value <= 255) pixels[row][col] = value;
            if (value > 255) pixels[row][col] = 255;
        }
    }

    public int getPixel (int row, int col) {
        if (row >= 0 && row < pixels.length
                && col >= 0 && col < pixels[0].length) return pixels[row][col];
        return 0;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private int getCell (int row, int col) {
        return pixels[row][col];
    }

    public void draw (Graphics g, int width, int height) {
        int imageW = getWidth();
        int imageH = getHeight();

        double colScale = (double)width/(double)imageW;
        double rowScale = (double)height/(double)imageH;

        for(int col=0; col < imageW; ++col) {
            for(int row=0; row < imageH; ++row) {
                int colPos = (int)(col*colScale);
                int rowPos = (int)(row*rowScale);
                int nextCol = (int)((col+1)*colScale);
                int nextRow = (int)((row+1)*rowScale);

                if (g.hitClip(colPos,rowPos,nextCol-colPos,nextRow-rowPos)) {
                    int color = 255 - getCell(row, col);
                    g.setColor(new Color(color, color, color));
                    g.fillRect(colPos,rowPos,nextCol-colPos,nextRow-rowPos);
                }
            }
        }
    }

    public double[][] pixelsToVector () {

        double[][] vector = new double[1][(pixels.length * pixels[0].length) + 1];

        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                vector[0][(row * pixels[0].length) + col] = pixels[row][col];
            }
        }

        vector[0][vector.length - 1] = -1;

        return vector;
    }

}
