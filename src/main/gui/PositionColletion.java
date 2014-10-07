
package main.gui;

import exceptions.GraphicIndexOutOfBoundsException;

public class PositionColletion {
    private double[][][] collection;
        public double[][] getFrame(int index) throws GraphicIndexOutOfBoundsException {
            if (index < collection.length) {
                return collection[index];
            } else {
                throw new GraphicIndexOutOfBoundsException(index);
            }
        }
    private int num;
        public int getNumFrames() {
            return num;
        }

    public PositionColletion() {
        collection = new double[50][0][0];
        num = 0;
    }

    public void addFrame(double[][] newFrame) {
        if (isFull()) {
            increaseSize();
        }
        collection[num] = newFrame;
        num++;
    }
    public int getIndexOf(double[][] someFrame) {
        int index = -1;
        for (int i=0; i<getNumFrames(); i++) {
            try {
                if (getFrame(i) == someFrame) {
                    index = i;
                }
            } catch (GraphicIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
        return index;
    }

    private int getCapacity() {
        return collection.length;
    }
    private boolean isFull() {
        return getNumFrames() == getCapacity();
    }
    private void increaseSize() {
        double[][][] temp = new double[getCapacity()*2][0][0];
        for (int i=0; i<getNumFrames(); i++) {
            try {
                temp[i] = getFrame(i);
            } catch (GraphicIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                System.out.println("...this is hardcoded to not happen");
                System.exit(0);
            }
        }
        collection = temp;
    }

}
