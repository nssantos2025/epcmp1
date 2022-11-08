/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.epcmp.Simulador;

import java.io.IOException;

/**
 *
 * @author natha
 */
public class ProcessadorV extends Thread{
   int id;
    String endereco;
    int PORTA;
    public ProcessadorV (int id,String endereco, int Porta){
        this.id = id;
        this.endereco = endereco;
        PORTA = Porta;
    }

    public void executar_Mic() throws IOException {
        String comando_de_chamada = "java -jar Mic1MMV.jar "+endereco+" "+PORTA;
        //System.out.println(comando_de_chamada);
        //Alterar o caminho do app
        String cmd_Mic[] = {"cmd.exe","/c","cd ferramentas\\Mic1MMV2 && "+comando_de_chamada};
        Process process_Mic = Runtime.getRuntime().exec(cmd_Mic);
    }

    @Override
    public void run() {
        super.run();
        try {
            executar_Mic();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    } 
}
