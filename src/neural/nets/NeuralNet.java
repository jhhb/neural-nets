/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural.nets;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author jamesboyle
 */
public class NeuralNet {
    
    private String filename = null;
    private String testfilename = null;
    private int numberOfInputNodes;
    private int numberOfOutputNodes;
    private List<Integer> representedNumbers;
    private List<List<Integer>> listOfListOfInts;
    private Random rand = new Random();
    private double alpha;
    
    
    private  List<List<Double>> edgeList;
        
    private List<Integer> inputList;
    private List<Double> outputList;
    
    
    
    public NeuralNet(int numberOfInputNodes, int numberOfOutputNodes, String filename, String testfilename, double alpha){
        
        this.filename = filename;
        this.testfilename = testfilename;
        this.numberOfInputNodes = numberOfInputNodes;
        this.numberOfOutputNodes = numberOfOutputNodes;
        this.representedNumbers = new ArrayList<>();
        this.listOfListOfInts = new ArrayList<List<Integer>>();
        this.initializeStructures();
        this.alpha = alpha;
    }
    
    public void trainNetwork(){
        //want to be able to run through network -- get outputs -- and then update
        
        for(int i = 0; i < this.representedNumbers.size(); i++){
          //  System.out.println(this.representedNumbers.get(i));
            
            ArrayList<Double> weightedSums = new ArrayList<Double>(); //This is what you want to look at
            
            weightedSums = this.calculateOutput(this.listOfListOfInts.get(i));
            
            //update edge weights depending on number of output nodes
            if(this.numberOfOutputNodes == 10){
                this.updateWeightsForTenOutputNodes(this.representedNumbers.get(i), weightedSums);
            }else{
                this.updateWeightForOneOutputNode(this.representedNumbers.get(i), weightedSums);
            }
            
        }

        // this should actually be it for the training function
    }
    
    public double testNetwork(){
        
        int totalCorrect = 0;
        
        for(int i = 0; i < this.representedNumbers.size(); i++){
            
            ArrayList<Double> weightedSums = new ArrayList<Double>();            
            weightedSums = this.calculateOutput(this.listOfListOfInts.get(i));
            
            int correct;
            if(this.numberOfOutputNodes == 10){
                correct = this.evaluateForTen(this.representedNumbers.get(i));
            }else{
                correct = this.evaluateForOne(this.representedNumbers.get(i));
            }
            
            totalCorrect += correct;
        }
        double tc = totalCorrect;
        double percentCorrect = tc / this.representedNumbers.size();
        return percentCorrect;
        
    }
    
    private int evaluateForTen(int rn){
        
        int correct = 0;
        
        int highestNode = 0;
        double highestNodeValue = this.outputList.get(0);
        for(int i = 1; i < this.numberOfOutputNodes; i++){
            if(this.outputList.get(i) > highestNodeValue){
                highestNode = i;
                highestNodeValue = this.outputList.get(i);
            }
        }
        
        if(highestNode == rn){
            correct = 1;
        }
        
        return correct;
    }
    
    private int evaluateForOne(int rn){
        int correct = 0;
        double doubleRN;
        doubleRN = rn;
        
//        System.out.println(rn + "  " + doubleRN);
//        if (Math.abs(doubleRN - this.outputList.get(0)) <= 0.5) {
//          //  System.out.println(doubleRN);
//            correct = 1;
//        }
        
        if (Math.abs(doubleRN - this.outputList.get(0) * 10) <= 0.5) {
            
          //  System.out.println(doubleRN);
            correct = 1;
        }
        
        return correct;
    }
    
    
    private void updateWeightsForTenOutputNodes(int rn, ArrayList<Double> in){
        for(int i = 0; i < this.numberOfOutputNodes; i++){
            double target = 0.0;
            if(i == rn){
                target = 1.0;
            }
            double err = target - this.outputList.get(i);
            
            //edge updates for output node i in the j loop
            double activationFunctionValue = this.activationFunction(in.get(i));                  
            double der = activationFunctionValue * (1 - activationFunctionValue);
            //System.out.println(activationFunctionValue);
            
            
            for(int j = 0; j < this.numberOfInputNodes; j++){
                //System.out.println(this.edgeList.get(j).get(i));
                //System.out.println("Updated weight on (" + j + ", " + i + ")");
                double update = this.inputList.get(j) * this.alpha * err * der + this.edgeList.get(j).get(i);
                this.edgeList.get(j).set(i, update);
                //ySstem.out.println(this.edgeList.get(j).get(i));
            }
            
        }
    }
    
    private void updateWeightForOneOutputNode(int rn, ArrayList<Double> in){
        double target = rn / 10.0; 
        double err = target - this.outputList.get(0);
        
        double activationFunctionValue = this.activationFunction(in.get(0)); 
        double der = activationFunctionValue * (1 - activationFunctionValue);
        
        for(int j = 0; j < this.numberOfInputNodes; j++){
                double update = this.inputList.get(j) * this.alpha * err * der + this.edgeList.get(j).get(0);
                this.edgeList.get(j).set(0, update);
            }
    }
    
    //entire bitmap as list
    private ArrayList<Double> calculateOutput(List<Integer> bitmap){
        
        ArrayList<Double> weightedSums = new ArrayList<Double>();
        //set value in inputList to bitmap value 
        for(int i = 0; i < this.inputList.size(); i++){
            
            this.inputList.set(i, bitmap.get(i));
        }
        
        // for each output node, calculate input from all input nodes and set output node value
        for(int i = 0; i < this.outputList.size(); i++) {
            // sum of weights to output node i
            double sum = 0;
            // for each edge connected to the output node
            for(int j = 0; j < this.inputList.size(); j++) {
                double edgeWeight = this.edgeList.get(j).get(i);
                int input = this.inputList.get(j);
                sum +=  edgeWeight * input;
            }
            double newVal = 0;
            newVal = this.activationFunction(sum);
            this.outputList.set(i, newVal);
            weightedSums.add(sum);
        } 
        return weightedSums;
    }
    
    // activation function. I think it is in the handout but I'm not sure
    // that i would code it correctly. Justin I think you would probably have 
    // an easy time with it.
    private double activationFunction(double in) {
        double denominator = 1.0 + Math.pow(Math.E, -1.0 * in); // add bias here
        denominator = 1 / denominator;
        return denominator;
    }
    

    
    

    public void initializeStructures(){
        
        edgeList = new ArrayList<List<Double>>();
        inputList = new ArrayList<Integer>();
        outputList = new ArrayList<Double>();
        
        for(int i = 0; i < this.numberOfInputNodes; i++) {
            inputList.add(-1);
            List<Double> tempEdgeList = new ArrayList<Double>();
            for(int j = 0; j < this.numberOfOutputNodes; j++) {
                if(i == 0) {
                    outputList.add(-1.0);
                }
                Double randomVal = rand.nextDouble();
                randomVal = randomVal * .001 - .0005;
                tempEdgeList.add(0.0);
            }
            edgeList.add(tempEdgeList);
        }         
    }
    
    public void readFile(String fileType){
        
        this.representedNumbers.clear();
        this.listOfListOfInts.clear();
        
        String fileName = "NULL";
        if(fileType == "training"){
            fileName = this.filename;
        }else if (fileType == "testing"){
            fileName = this.testfilename;
        }
        FileInputStream in = null;
        ArrayList<Integer> ints = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(fileName))){
            String line;
            int firstLine = 0;
            int lineNumber = 0;
            boolean reading32By32 = true;            
            
            while((line = br.readLine()) != null){
               // System.out.println(line);
                if(firstLine == 0){
                    if(!Character.isDigit(line.charAt(0))){ //if its a 32x32, skip 2 lines
                        br.readLine();
                        br.readLine();  
                    }
                    else{
                        reading32By32 = false;
                        //TODO - FIX THIS SHIT
                        
                        String[] splitString = line.split(",");
                        for(int i = 0; i < splitString.length-1; i++){
                            ints.add(Integer.parseInt(splitString[i]));
                        }
                        int numberRepresented = Integer.parseInt(splitString[splitString.length-1]);
                            //push number that is represented into arrayList of numbers 
                        this.representedNumbers.add(numberRepresented);

                            //push ints into listOfListOfInts
                        this.listOfListOfInts.add(ints);
                        ints = new ArrayList<>();
                    }
                    firstLine=1;
                }
                else{
                    if(!reading32By32){ //if its an 8x8 
                       // ArrayList<Integer> ints = new ArrayList<>();
                        String[] splitString = line.split(",");
                        for(int i = 0; i < splitString.length-1; i++){
                            ints.add(Integer.parseInt(splitString[i]));
                        }
                        int numberRepresented = Integer.parseInt(splitString[splitString.length-1]);
                            //push number that is represented into arrayList of numbers 
                        this.representedNumbers.add(numberRepresented);

                            //push ints into listOfListOfInts
                        this.listOfListOfInts.add(ints);
                        ints = new ArrayList<>();
                    //    System.out.println(numberRepresented);
                    }   
                    else{ //if it's a 32x32
                        //CAN I DO THIS???
                        if(lineNumber == 32){
                          //  System.out.println(line);
                            lineNumber = -1;
                            String [] result = line.split(" ");
                            String singleResult = result[1];
                            int numberRepresented = Integer.parseInt(singleResult);
                            this.representedNumbers.add(numberRepresented);
                            this.listOfListOfInts.add(ints);
                            ints = new ArrayList<>();
                        }
                        else{
//                            ArrayList<Integer> ints = new ArrayList<>();
                            String[] splitString = line.split("");
                            for(int i = 0; i < line.length(); i++){
                                ints.add(Integer.parseInt(splitString[i]));
                            }
                        }                     
                        lineNumber+=1;                                         
                    }  
                }
            }
            br.close();
        }
        catch( IOException exception){
            System.err.println(exception);
        }
  //      System.out.println("Read " + this.representedNumbers.size() + " numbers from " + this.filename);
        
  //      System.out.println(this.listOfListOfInts.size());
  //      System.out.println(this.listOfListOfInts.get(0).size());
    }
}
