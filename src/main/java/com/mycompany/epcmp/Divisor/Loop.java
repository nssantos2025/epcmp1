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
    public int erro=0;
    private static int temp_inicio_loop=0;
    private static int temp_fim_loop=0;

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
                //correção
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
        return valor;
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

    private int calcular_add(int add, int n_loop, boolean b){
        if(!b){
            int adicionamento_f=0;
            for(int i=0; i<n_loop;i++){
                adicionamento_f = adicionamento_f+add;
            }
            return adicionamento_f;
        }else{
            int adicionamento_f=0;
            for(int i=0; i<n_loop;i++){
                if((i+1) == n_loop){
                    adicionamento_f = adicionamento_f+add;
                }else {
                    adicionamento_f = adicionamento_f +(add - 1);
                }
            }
            return adicionamento_f;
        }
    }

    private String[] criar_novo_conteudo(int acrescimos, int numero_loop, boolean caso_esp, int quantiade_loops) {
        int i=0;

        if(numero_loop ==0){
            String newconteudo[];
            newconteudo = new String[conteudo_loop.length];
            int novo_termino = inicio_loop+acrescimos;
            for(String l:conteudo_loop){
                //System.out.println(l);
                if(l.equals("bipush "+(fim_loop))){
                    l = "bipush "+novo_termino;
                }
                newconteudo[i] = l;
                i++;
            }
            temp_inicio_loop = inicio_loop;
            temp_fim_loop = novo_termino;
            //System.out.println(numero_loop+" : "+temp_inicio_loop+" a "+temp_fim_loop);
            return newconteudo;
        }else{

            String newconteudo[];
            newconteudo = new String[conteudo_loop.length+2];
            int novo_termino, novo_inicio;
            //if(caso_esp == false) {
                novo_inicio = temp_fim_loop+1;
                if(novo_inicio != fim_loop){
                    if(novo_inicio > fim_loop ){
                        int regulador = novo_inicio-fim_loop;
                        novo_inicio = novo_inicio - regulador;
                    }
                }
                novo_termino = novo_inicio+acrescimos;
                if(novo_termino != fim_loop){
                    if(novo_termino > fim_loop){
                        int regulador = novo_termino-fim_loop;
                        novo_termino = novo_termino - regulador;
                    }
                    if(novo_termino < fim_loop && numero_loop == (quantiade_loops-1)){
                        int regulador = fim_loop-novo_termino;
                        novo_termino = novo_termino+regulador;
                    }
                }
                //novo_termino = inicio_loop + (acrescimos * (numero_loop + 1));
                //novo_inicio = inicio_loop + (acrescimos * numero_loop);
                temp_fim_loop = novo_termino;
                temp_inicio_loop = novo_inicio;
            //}else{
                //novo_termino = inicio_loop + (acrescimos * (numero_loop + 1));
                //novo_inicio = temp_inicio_loop+acrescimos;
                //novo_termino = temp_fim_loop+acrescimos+calcular_balanco(intervalo,numero_loop);
                //novo_termino = inicio_loop+calcular_add(acrescimos,(numero_loop+1),caso_esp);
                //novo_inicio = inicio_loop + ((acrescimos-1) * numero_loop);
            //}
            //System.out.println(numero_loop+" : "+temp_inicio_loop+" a "+temp_fim_loop);
            for(String l:conteudo_loop){
                if(i == 0){
                    newconteudo[i] = "bipush "+novo_inicio;
                    i++;
                    newconteudo[i] = "istore "+variavel_de_condicao.nome;
                    i++;
                }
                if(l.equals("bipush "+(fim_loop))){
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
        temp_fim_loop = 0;
        temp_inicio_loop=0;
        int fator_de_divisao;
        int quantidade_loops=0;
        int intervalo_loop = fim_loop-inicio_loop;
        if(intervalo_loop < 0){
            intervalo_loop = intervalo_loop*(-1);
        }
        //int quantidade_reps = intervalo_loop+1;
        fator_de_divisao = determinar_fator_de_divisao(max_nucleos, intervalo_loop);
        //quantidade_loops = quantidade_reps/fator_de_divisao;
        quantidade_loops = max_nucleos;
        int acresimo_no_limitador = (intervalo_loop-1)/quantidade_loops;
        /*while(acresimo_no_limitador == 1 && acresimo_no_limitador > 0){
            quantidade_loops =quantidade_loops-1;
            acresimo_no_limitador = (intervalo_loop-1)/quantidade_loops;
        }*/

        //System.out.println("Quantos Loops: "+quantidade_loops);
        Loop[] novosloops = new Loop[quantidade_loops];
        for(int i =0; i< quantidade_loops;i++){
            boolean caso_esp = false;
            if((i+1) == quantidade_loops && (intervalo_loop)%quantidade_loops != 0){
                caso_esp = true;
            }
            String[] novo_conteudo = criar_novo_conteudo(acresimo_no_limitador,i, caso_esp,quantidade_loops);
           // System.out.println("Loop "+i);
            /*for(String m: novo_conteudo){
                System.out.println("    "+m);
            }*/
            int novo_inicio =0, novo_final =0;
            /*if(i==0){
                novo_inicio = inicio_loop;
                novo_final = inicio_loop+acresimo_no_limitador;
            }else{
                if(!caso_esp){
                    novo_inicio = inicio_loop+(acresimo_no_limitador*i);
                    novo_final = inicio_loop+(acresimo_no_limitador*(i+1))-1;
                }else{
                    novo_final = inicio_loop+calcular_add(acresimo_no_limitador,(quantidade_loops),caso_esp);
                    novo_inicio = inicio_loop + ((acresimo_no_limitador-calcular_balanco(intervalo_loop,quantidade_loops))*i);
                }
            }*/
            novo_inicio = temp_inicio_loop;
            novo_final = temp_fim_loop;
            //System.out.println("loop "+i+" vai de "+novo_inicio+" ate "+novo_final);
            novosloops[i] = new Loop("Default",variavel_de_condicao,novo_conteudo,novo_inicio,novo_final);
            if(novo_final == fim_loop){
                erro = i;
                break;
            }
            caso_esp = false;
        }
        return novosloops;
    }

    private int calcular_balanco(int intervalo_loop, int quantidade_loops) {
        int resto = intervalo_loop%quantidade_loops;
        //System.out.println("calcular_balanco: "+resto);
        return resto;
    }
}
