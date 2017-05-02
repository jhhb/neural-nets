/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural.nets;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author jamesboyle
 */
public class NeuralNets {
    public static void main(String[] args) {
        // TODO code application logic here
        
        //doesnt need to be its own function
                
        String TRAINING_8_STRING = "8x8-integer-inputs/optdigits-8x8-int.tra";
        String TEST_8_STRING = "8x8-integer-inputs/optdigits-8x8-int.tes";
        
        String TRAINING_32_STRING = "32x32-bitmaps/optdigits-32x32.tra";
        String TEST_32_STRING = "32x32-bitmaps/optdigits-32x32.tes";

        //0.005
        double alpha = 0.00001;  
        //.05 right to 90

        //64 - 10       use 0.01 - MUST BE 
        //64 - 1        use 0.00001
        //1024 10       use 0.01
        //1024 1        use 0.0015
        
        //.01, .05, .001
        
        List<Double> learningRates = new ArrayList<Double>(Arrays.asList(0.05, 0.01, 0.001));
        
        int NUMBER_OF_EPOCHS = 50;
        
        int NUMBER_OF_TRIALS = 5;
        
      //  NeuralNet nn = new NeuralNet(1024, 1, TRAINING_32_STRING, TEST_32_STRING, alpha);
       
      System.out.println("learningrate,trial,numbits,numoutput,result,epoch");
        for(int lr = 0; lr < learningRates.size(); lr++){
            for(int n = 0; n < NUMBER_OF_TRIALS; n++){
                NeuralNet nn = new NeuralNet(64, 1, TRAINING_8_STRING, TEST_8_STRING, learningRates.get(lr));
                for(int i = 0; i < 50; i++){
                    
                    nn.readFile("training");
                    nn.trainNetwork();
                    nn.readFile("testing");
                    double result = nn.testNetwork();
                    System.out.println(learningRates.get(lr) +","+n+",64,1"+"," +result+","+i);
                    
                }
            }
        }
        
        for(int lr = 0; lr < learningRates.size(); lr++){
            for(int n = 0; n < NUMBER_OF_TRIALS; n++){
                NeuralNet nn = new NeuralNet(64, 10, TRAINING_8_STRING, TEST_8_STRING, learningRates.get(lr));
               // nn.trainNetwork();
                for(int i = 0; i < 50; i++){
                    nn.readFile("training");
                    nn.trainNetwork();
                    nn.readFile("testing");
                    double result = nn.testNetwork();
                    System.out.println(learningRates.get(lr) +","+n+",64,10"+"," +result+","+i);
                }
                  
            }
               
        }

        for(int lr = 0; lr < learningRates.size(); lr++){
            for(int n = 0; n < NUMBER_OF_TRIALS; n++){
                NeuralNet nn = new NeuralNet(1024, 1, TRAINING_32_STRING, TEST_32_STRING, learningRates.get(lr));
                for(int i = 0; i < 50; i++){
                    nn.readFile("training");
                    nn.trainNetwork();
                    nn.readFile("testing");
                    double result = nn.testNetwork();
                    System.out.println(learningRates.get(lr) +","+n+",1024,1"+"," +result+","+i);
                }
            }
        }
        for(int lr = 0; lr < learningRates.size(); lr++){
            for(int n = 0; n < NUMBER_OF_TRIALS; n++){
                NeuralNet nn = new NeuralNet(1024, 10, TRAINING_32_STRING, TEST_32_STRING, learningRates.get(lr));       
                for(int i = 0; i < 50; i++){
                    nn.readFile("training");
                    nn.trainNetwork();
                    nn.readFile("testing");
                    double result = nn.testNetwork();
                    System.out.println(learningRates.get(lr) +","+n+",1024,10"+"," +result+","+i);
                } 
            }    
        }
    }
}