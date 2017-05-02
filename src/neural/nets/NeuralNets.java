/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural.nets;
/**
 *
 * @author jamesboyle
 */
public class NeuralNets {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
       // Trainer trainer = new Trainer("/Users/jamesboyle/NetBeansProjects/neural-nets/32x32-bitmaps/optdigits-32x32.tra");
      //  Trainer trainer = new Trainer("/Users/jamesboyle/NetBeansProjects/neural-nets/8x8-integer-inputs/optdigits-8x8-int.tra");
        
        //needs to be initialized with Training AND Test data
        //trainer.readFile();
        //doesnt need to be its own function
        
        //Node 0 is the bias node
        
        String TRAINING_8_STRING = "8x8-integer-inputs/optdigits-8x8-int.tra";
        String TEST_8_STRING = "8x8-integer-inputs/optdigits-8x8-int.tes";
        
        String TRAINING_32_STRING = "32x32-bitmaps/optdigits-32x32.tra";
        String TEST_32_STRING = "32x32-bitmaps/optdigits-32x32.tes";

        
        double alpha = .001;             
        
        NeuralNet nn = new NeuralNet(1024, 1, TRAINING_32_STRING, TEST_32_STRING, alpha);
        //NeuralNet nn = new NeuralNet(64, 1, TRAINING_8_STRING, TEST_8_STRING, alpha);
        nn.readFile("training");
        nn.trainNetwork();
        //nn.trainNetwork();
        //nn.trainNetwork();
        nn.readFile("testing");
        nn.testNetwork();
        
        //nn.testNetwork();
        //NEED:
            //outut list
            //2d array of input x output
            
            //
        
        
    }
    
}
