/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.epcmp.Divisor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author natha
 */
public class Fabricador {
     private String nome_dos_arquivos;
    private String[] conteudo_completo_arquivo;
    private Loop[] loops;
    private String destino_do_arquivo;

    private int linha_de_inicio_loop;
    private int linha_de_fim_loop;

    public Fabricador(String nome_do_arquivo, String[] conteudo, Loop[] loops, String destino_do_arquivo){
        nome_dos_arquivos = nome_do_arquivo;
        conteudo_completo_arquivo = conteudo;
        this.loops = loops;
        this.destino_do_arquivo = destino_do_arquivo;
    }

    private void setInicio_e_Fim_do_Loop() {
        int i=0;
        for(String l:conteudo_completo_arquivo){
            if(l.equals("LOOPTEST0:")){
                linha_de_inicio_loop = i;
            }else if(l.contains("LOOPEXIT")){
                linha_de_fim_loop = i;
            }
            i++;
        }
    }

    public void criar_arquivos() throws IOException {
        setInicio_e_Fim_do_Loop();
        int quantidade_de_linhas_antes_loop = linha_de_inicio_loop+1;
        int quantidade_de_linhas_depois_loop = conteudo_completo_arquivo.length - linha_de_fim_loop;
        if((conteudo_completo_arquivo.length-1-linha_de_fim_loop)<=2 ){
            quantidade_de_linhas_depois_loop = 1;
        }
        int i;
        int total_linhas;
        for(i=0;i<(loops.length);i++){
            total_linhas = (quantidade_de_linhas_antes_loop+quantidade_de_linhas_depois_loop+loops[i].conteudo_loop.length)-1;
            //System.out.println("Loop "+i+" t: "+total_linhas);
            String[] novo_conteudo = new String[total_linhas];
            int p=0;
            for(String l:conteudo_completo_arquivo){
                if(p < (quantidade_de_linhas_antes_loop-1)){
                    novo_conteudo[p] = l;
                }else{
                    break;
                }
                p++;
            }
            int primeiro = 0;
            for(String l:loops[i].conteudo_loop){
                novo_conteudo[p] = l;
                p++;

            }
            if(quantidade_de_linhas_depois_loop == 1){
                novo_conteudo[p] = conteudo_completo_arquivo[conteudo_completo_arquivo.length-1];
            }else{
                for(int y=(conteudo_completo_arquivo.length-quantidade_de_linhas_depois_loop);y<conteudo_completo_arquivo.length;y++){
                    novo_conteudo[p] = conteudo_completo_arquivo[y];
                    p++;
                }
            }
            /*for (String l: novo_conteudo){
                System.out.println(l);
            }*/
            String caminho_absoluto = destino_do_arquivo+nome_dos_arquivos+i+".jas";
            FileWriter file = new FileWriter(caminho_absoluto);
            BufferedWriter bw = new BufferedWriter(file);
            for(int y=0;y<novo_conteudo.length;y++){
                bw.write(novo_conteudo[y]+"\n");
            }
            System.out.println("Arquivo "+i+" Pronto");
            bw.flush();
            file.close();
        }
    }
}
