/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.epcmp.Divisor;

/**
 *
 * @author natha
 */
public class Loop {
    private String tipo_de_condicao;
    private Variaveis[] variaveis_de_condicao;
    private Variaveis variavel_de_condicao;
    public String[] conteudo_loop;
    private int inicio_loop;
    private int fim_loop;

    public Loop(String tipo_de_condicao, Variaveis[] variaveis, String[] conteudo_loop){
        this.tipo_de_condicao = tipo_de_condicao;
        variaveis_de_condicao = variaveis;
        this.conteudo_loop = conteudo_loop;
    }

    public Loop(String tipo_de_condicao, Variaveis variavel, String[] conteudo_loop){
        this.tipo_de_condicao = tipo_de_condicao;
        variavel_de_condicao = variavel;
        this.conteudo_loop = conteudo_loop;
    }


    public Loop(String tipo_de_condicao, Variaveis variavel, String[] conteudo_loop, int inicio, int fim){
        this.tipo_de_condicao = tipo_de_condicao;
        variavel_de_condicao = variavel;
        this.conteudo_loop = conteudo_loop;
        inicio_loop = inicio;
        fim_loop = fim;
    }

    public void Set_limites(){
        if(tipo_de_condicao.equals("Default")){
            if(variavel_de_condicao.tipo.equals("int")){
                inicio_loop = variavel_de_condicao.valor_int;
                fim_loop = obter_valor_de_condicao();
            }
        }
    }

    public int obter_valor_de_condicao(){
        int i =0;
        int linhaif = 0;
        for(String linha:conteudo_loop){
            if(linha.contains("if_icmpeq")){
                linhaif = i;
                break;
            }
            i++;
        }
        String linha_valor = conteudo_loop[linhaif-1];
        int valor = Integer.valueOf(linha_valor.substring(6).replace(" ",""));
        return (valor+1);
    }

    public int getFim_loop() {
        return fim_loop;
    }

    public int getInicio_loop() {
        return inicio_loop;
    }

    boolean testar_se_o_numero_e_par(int numero){
        if((numero%2) == 0){
            return true;
        }else{
            return false;
        }
    }
    private String[] criar_novo_conteudo(int acrescimos, int numero_loop) {
        int i=0;

        if(numero_loop ==0){
            String newconteudo[];
            newconteudo = new String[conteudo_loop.length];
            int novo_termino = inicio_loop+acrescimos;
            for(String l:conteudo_loop){
                //System.out.println(l);
                if(l.equals("bipush "+fim_loop)){
                    l = "bipush "+novo_termino;
                }
                newconteudo[i] = l;
                i++;
            }
            return newconteudo;
        }else{
            String newconteudo[];
            newconteudo = new String[conteudo_loop.length+2];
            int novo_termino = inicio_loop+(acrescimos*(numero_loop+1));
            int novo_inicio = inicio_loop+(acrescimos*numero_loop);
            for(String l:conteudo_loop){
                if(i == 0){
                    newconteudo[i] = "bipush "+novo_inicio;
                    i++;
                    newconteudo[i] = "istore "+variavel_de_condicao.nome;
                    i++;
                }
                if(l.equals("bipush "+fim_loop)){
                    l = "bipush "+novo_termino;
                }
                newconteudo[i] = l;
                i++;
            }
            return newconteudo;
        }
    }
    int determinar_fator_de_divisao(int max_n, int intervalo_l){
        if( (intervalo_l % max_n) == 0 ){
            return intervalo_l/max_n;
        }else{
            return (intervalo_l/max_n)+1;
        }
    }

    public Loop[] dividir_loops(int max_nucleos){
        int quantidade_loops=0;
        int intervalo_loop = fim_loop-inicio_loop;
        int fator_de_divisao=determinar_fator_de_divisao(max_nucleos, intervalo_loop);
        if(intervalo_loop < 0){
            intervalo_loop = intervalo_loop*-1;
        }
        if(testar_se_o_numero_e_par(fator_de_divisao) && testar_se_o_numero_e_par(intervalo_loop)){
            quantidade_loops = intervalo_loop/fator_de_divisao;
        }else if(!testar_se_o_numero_e_par(fator_de_divisao) && !testar_se_o_numero_e_par(intervalo_loop)){
            quantidade_loops = intervalo_loop/fator_de_divisao;
        }else{
            quantidade_loops = intervalo_loop/fator_de_divisao;
            /*
            int max_de_nucleos = max_nucleos;
            if(fator_de_divisao > max_de_nucleos){
                fator_de_divisao--;
                quantidade_loops = intervalo_loop/fator_de_divisao;
            }else{
                fator_de_divisao++;
                quantidade_loops = intervalo_loop/fator_de_divisao;
            }*/
        }
        //System.out.println(""+quantidade_loops);
        int acresimo_no_limitador = intervalo_loop/quantidade_loops;
        Loop[] novosloops = new Loop[quantidade_loops];
        for(int i =0; i< quantidade_loops;i++){
            String[] novo_conteudo = criar_novo_conteudo(acresimo_no_limitador,i);
            System.out.println("Loop "+i);
            /*for(String m: novo_conteudo){
                System.out.println("    "+m);
            }*/
            int novo_inicio =0, novo_final =0;
            if(i==0){
                novo_inicio = inicio_loop;
                novo_final = inicio_loop+acresimo_no_limitador;
            }else{
                novo_inicio = inicio_loop+(acresimo_no_limitador*i);
                novo_final = inicio_loop+(acresimo_no_limitador*(i+1));
            }
            novosloops[i] = new Loop("Default",variavel_de_condicao,novo_conteudo,novo_inicio,novo_final);
        }
        return novosloops;
    }
}
