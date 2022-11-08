/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.epcmp.Divisor;

import java.io.IOException;

/**
 *
 * @author natha
 */
public class Variaveis {
    public String nome;
    public String tipo;
    public int valor_int;
    public String valor_string;

    public Variaveis(String nome, String tipo){
        this.nome = nome;
        this.tipo = tipo;
    }

    public Variaveis(){
    }

    public void setNome(String n) {
        nome = n;
    }

    public String getNome() {
        return nome;
    }

    public Variaveis[] obter_variaveis_do_arquivo(String[] leitura){
        int i =1;
        String linha = null;
        i=0;
        int p=0;
        int inicio_declaracao_var = 0, fim_de_declaracao = 0;
        for(String l :leitura){
            if(l.contains(".var")){
                inicio_declaracao_var = i;
                p++;
            }else if(l.contains(".end-var")){
                fim_de_declaracao = i;
                p++;
            }
            if(p == 2){
                break;
            }
            i++;
        }
        p=0;
        Variaveis[] variavies = new Variaveis[(fim_de_declaracao-inicio_declaracao_var)-1];

        for(i=(inicio_declaracao_var+1); i<fim_de_declaracao;i++){
            variavies[p] = new Variaveis(leitura[i].replace(" ",""),"int");
            p++;
        }
        return variavies;
    }
    public int procurar_letra(String palavra,char letra){
        int i =0;
        for(i=0;i<palavra.length();i++){
            if(palavra.charAt(i) == letra){
                return i;
            }
        }
        return -1;
    }



    public void setValor(String[] leitura) throws IOException {
        if(tipo.equals("String")) {
            valor_string = null;
        }else if(tipo.equals("int")){
            int i = 0;

            i=0;
            int linha_de_atribuicao = 0;
            for(String l: leitura){
                if(l.contains("istore "+nome)){
                    linha_de_atribuicao = i;
                    break;
                }
                i++;
            }
            if(leitura[linha_de_atribuicao-1].contains("bipush")){
                if(procurar_letra(leitura[linha_de_atribuicao-1], 'h') == -1){

                }else{
                    int index_letra = procurar_letra(leitura[linha_de_atribuicao-1], 'h');
                    String valor_ini = leitura[linha_de_atribuicao-1].substring(index_letra+1);
                    valor_ini = valor_ini.replace(" ","");
                    valor_int = Integer.valueOf(valor_ini);
                }
            }
        }else{
            valor_string = "ERROR";
        }
    }

    public boolean testar_se_e_variavel_de_condicao_de_loop(String[] leitura){
        int quantiade_de_loops=0;
        int i=0;
        for(String l: leitura){
            if(l != null) {
                if (l.contains("LOOPTEST")) {
                    quantiade_de_loops++;
                }
            }
        }
        int[] linhas_do_loop = new int[quantiade_de_loops];
        int p=0;
        for(String l: leitura){
            if(l.contains("LOOPTEST")){
                linhas_do_loop[i] = p;
                i++;
            }
            if(i == quantiade_de_loops){
                break;
            }
            p++;
        }
        for(i=0; i<quantiade_de_loops;i++){
            String linha_iload = leitura[linhas_do_loop[i]+1];
            linha_iload.replace(" ","");
            String nome_variavel_loop=linha_iload.substring(4);
            if(nome == nome_variavel_loop){
                return true;
            }
        }
        return false;
    }
}
