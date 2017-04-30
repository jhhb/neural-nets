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
    private int numberOfInputNodes;
    private int numberOfOutputNodes;
    private List<Integer> representedNumbers;
    private List<List<Integer>> listOfListOfInts;
    private Random rand = new Random();
    
    
    private  List<List<Double>> edgeList;
        
    private List<Integer> inputList;
    private List<Double> outputList;
    
    
    
    public NeuralNet(int numberOfInputNodes, int numberOfOutputNodes, String filename){
        
        this.filename = filename;
        this.numberOfInputNodes = numberOfInputNodes;
        this.numberOfOutputNodes = numberOfOutputNodes;
        this.representedNumbers = new ArrayList<>();
        this.listOfListOfInts = new ArrayList<List<Integer>>();
    }
    
    public void trainNetwork(){
        //want to be able to run through network -- get outputs -- and then update
        

        
        for(int i = 0; i < this.representedNumbers.size(); i++){
            this.calculateOutput(this.listOfListOfInts.get(i));        
         //   this.updateNetwork();
            
        }

        
    }
    
    //entire bitmap as list
    private void calculateOutput(List<Integer> bitmap){
        
        //set value in inputList to bitmap value 
        for(int i = 0; i < this.inputList.size(); i++){
            inputList.set(i, bitmap.get(i));
        }
        
        
                
        
        
    }
//    
//    public void testNetwork(){
//        
//    }

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
                randomVal = randomVal * 2 -1;
                tempEdgeList.add(randomVal);
            }
            edgeList.add(tempEdgeList);
            tempEdgeList.clear();
        }         
    }
    
    public void readFile(){
        FileInputStream in = null;
        ArrayList<Integer> ints = new ArrayList<>();

        try(BufferedReader br = new BufferedReader(new FileReader(this.filename))){
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
        System.out.println("Read " + this.representedNumbers.size() + " numbers from " + this.filename);
        
        System.out.println(this.listOfListOfInts.size());
        System.out.println(this.listOfListOfInts.get(0).size());
    }
}
