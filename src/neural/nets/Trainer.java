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

/**
 *
 * @author jamesboyle
 */
public class Trainer {
    
    private List<Integer> representedNumbers;
    
    private List<List<Integer>> listOfListOfInts;
    
    private String filename = null;
    
    public Trainer(String filename){
        this.filename = filename;
        this.representedNumbers = new ArrayList<>();
        this.listOfListOfInts = new ArrayList<List<Integer>>();
    }
    
    public void readFile(){
        FileInputStream in = null;
        
        try(BufferedReader br = new BufferedReader(new FileReader(this.filename))){
            String line;
            int firstLine = 0;
            int lineNumber = 0;
            boolean reading32By32 = true;
            while((line = br.readLine()) != null){
                if(firstLine == 0){
                    if(!Character.isDigit(line.charAt(0))){ //if its a 32x32, skip 2 lines
                        br.readLine();
                        br.readLine();  
                    }
                    else{
                        reading32By32 = false;
                    }
                    firstLine=1;
                }
                else{
                    if(!reading32By32){ //if its an 8x8 
                        ArrayList<Integer> ints = new ArrayList<>();
                        String[] splitString = line.split(",");
                        for(int i = 0; i < splitString.length-1; i++){
                            ints.add(Integer.parseInt(splitString[i]));
                        }
                        int numberRepresented = Integer.parseInt(splitString[splitString.length-1]);
                            //push number that is represented into arrayList of numbers 
                        this.representedNumbers.add(numberRepresented);

                            //push ints into listOfListOfInts
                        this.listOfListOfInts.add(ints);
                        System.out.println(numberRepresented);
                    }   
                    else{ //if it's a 32x32
                        //CAN I DO THIS???
                        if(lineNumber == 32){
                            System.out.println(line);
                            lineNumber = -1;
                            String [] result = line.split(" ");
                            String singleResult = result[1];
                            int numberRepresented = Integer.parseInt(singleResult);
                            this.representedNumbers.add(numberRepresented);
                            
                        }
                        else{
                            ArrayList<Integer> ints = new ArrayList<>();
                            String[] splitString = line.split("");
                            for(int i = 0; i < line.length(); i++){
                                ints.add(Integer.parseInt(splitString[i]));
                            }
                            this.listOfListOfInts.add(ints);
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
    }
}