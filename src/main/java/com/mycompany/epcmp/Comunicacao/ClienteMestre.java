/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.epcmp.Comunicacao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

/**
 *
 * @author natha
 */
public class ClienteMestre extends Thread {
    private static boolean Start=false,Close=false, Load=false;
    private int Porta;
    private int quant_server;
    private String PROXY = "127.0.0.1";
    private Socket socket;
    private int iniPort;
    //private static boolean Close;
    private int quantidade_servidores;
    public PrintStream saida;
    public BufferedReader entrada;
    
    public ClienteMestre(Socket socket){
        this.socket = socket;
    }
    
    public ClienteMestre(int Port_inicial, int quant_Servidores){
        iniPort = Port_inicial;
        quantidade_servidores = quant_Servidores;
    }
    
    
    
    public void setFaseStart(boolean b) {
        Start = b;
    }

    public void iniciar_conexao() throws IOException {
        try {
            int b=0;
            Socket[] conexoes = new Socket[quantidade_servidores];
            for(int i=0; i<=(quantidade_servidores-1);i++){
                System.out.println(""+i);
                conexoes[i] = new Socket(PROXY,(iniPort+i));
                System.out.println("Conecatado a Server "+i);
                saida = new PrintStream(conexoes[i].getOutputStream());
                entrada = new BufferedReader(new InputStreamReader(conexoes[i].getInputStream()));
                if(b==0) {
                    saida.println("Inicializar");
                    b++;
                }
                Thread thread = new ClienteMestre(conexoes[i]);
                thread.start();
                //conexoes[i].close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void run() {
        super.run();
        try {
            int i_Start=0,i_Load=0;
            while(true) {
                //Logica de Enviar mensagem deve ser feita aqui
                socket.setSoTimeout(1000000);
                receber_mensagem();
                if(Load && i_Load==0){
                    enviar_mensagem("Load");
                    i_Load=1;
                }else if(Start && i_Start ==0){
                    enviar_mensagem("Start");
                    i_Start=1;
                }else{
                    enviar_mensagem("Default");
                }
                if(!Load){
                    i_Load=0;
                }
                if(!Start){
                    i_Start=0;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void receber_mensagem() throws IOException {
        entrada = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        String msg = entrada.readLine();
        if(msg.equals(null) || msg.equals("Default")){

        }else{
           System.out.println(msg);
           if(msg.equals("Load Iniciado")){ setLoad(false); }
           if(msg.equals("Start Iniciado")){ setFaseStart(false); }
        }
    }

    public void enviar_mensagem(String msg) throws IOException {
        saida = new PrintStream(socket.getOutputStream());
        saida.println(msg);
    }


    public void setFaseClose(boolean b) {
        Close = b;
    }

    public void setLoad(boolean b) {
        Load = b;
    }
    
}
