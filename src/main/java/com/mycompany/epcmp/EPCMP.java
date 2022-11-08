/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.epcmp;

/**
 *
 * @author natha
 */
public class EPCMP {

    public static void main(String[] args) {
        //System.out.println("Hello World!");
        //Comunicar.main(args);
        if(args.length == 0){
            start_main.main(args);
        }else{
            monitoring_frame.main(args);
        }
    }   
}
