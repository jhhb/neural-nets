/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package neural.nets;

import neural.nets.Trainer;

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
        Trainer trainer = new Trainer("/Users/jamesboyle/NetBeansProjects/neural-nets/8x8-integer-inputs/optdigits-8x8-int.tra");

        trainer.readFile();
        
    }
    
}
