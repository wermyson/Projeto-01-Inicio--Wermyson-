//import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

class Pessoa {
    String nome;
    int idade;

    Pessoa (String nome, int idade){
        this.nome = nome;
        this.idade = idade;
    }
    public String toString(){
        return nome + ": " + idade + " ";
    }
}

public class Topic {
    int lotacaoMax;
    int prioridade;
    ArrayList<Pessoa> assentoNormal;
    ArrayList<Pessoa> assentoPrioritario;

    Topic (int lotacaoMax, int prioridade){
        this.lotacaoMax = lotacaoMax;
        this.prioridade = prioridade;
        assentoNormal = new ArrayList<>();
        for (int i = 0; i < (this.lotacaoMax - this.prioridade); i++){
            assentoNormal.add(null);
        }
        assentoPrioritario = new ArrayList<>();
        for (int i = 0; i < this.prioridade; i++){
            assentoPrioritario.add(null);
        }
    }
    int procurarVagas(ArrayList<Pessoa> array){
        for(int i = 0; i < array.size(); i++){
            if(array.get(i) == null){
                return i;// tem vaga, retorna o assento q tá livre
            }
        }
        return -1;//n tem vaga
    }
    int procurarPessoa(ArrayList<Pessoa> array, String pessoa){//ve se uma pessoa está em determinado tipo de assento
        for(int i = 0; i < array.size(); i++){
            Pessoa comparar = array.get(i);
            if(comparar != null && pessoa.equals(comparar.nome)){
                return i;//posição da pessoa
            }
        }
        return -1;//a pessoa n esta
    }
    void adicionar (String nome, int idade){
        Pessoa pessoa = new Pessoa(nome, idade);
        int repitido = procurarPessoa(assentoNormal, pessoa.nome);//ver se a pessoa já esta na topikers
        if(repitido != -1){//a pessoa esta na topic
            System.out.println("você ja comprou uma passagem");
            return;
        }
        repitido = procurarPessoa(assentoPrioritario, pessoa.nome);
        if(repitido != -1){
            System.out.println("você ja comprou uma passagem");
            return;
        }
        if (procurarVagas(assentoPrioritario) == -1 && procurarVagas(assentoNormal) == -1){
            System.out.println("Não vai estar dando querido"); //nao tem vaga
            return;
        }
        if (pessoa.idade < 60){//se a peesoa eh menor de 60
            int vaga = procurarVagas(assentoNormal);//procurar nos assntos normais se tem vaga
            if(vaga != -1){//se a vaga n for false
                assentoNormal.set(vaga, pessoa);//a pessoa senta
                return;
            }
            vaga = procurarVagas(assentoPrioritario);
            assentoPrioritario.set(vaga, pessoa);
            return;
        }
        if (pessoa.idade >= 60){
            int vaga = procurarVagas(assentoPrioritario);
            if (vaga != -1){
                assentoPrioritario.set(vaga, pessoa);
                return;
            }
            vaga = procurarVagas(assentoNormal);
            assentoNormal.set(vaga, pessoa);
            return;
        }
        System.out.println("Você achou uma cadeira");
    }
    void remover(String pessoa){
        int posicao = procurarPessoa(assentoNormal, pessoa);
        if (posicao != -1){
            assentoNormal.set(posicao, null);
            return;
        }
        posicao = procurarPessoa(assentoPrioritario, pessoa);
        if (posicao != -1){
            assentoPrioritario.set(posicao, null);
            return;
        }
        System.out.println("Pessoa não encontrada");
    }
    public String toString(){
        String prioritarias = "[ ";
        String normais = "[ ";
        for (Pessoa pessoa: assentoNormal){
            if (pessoa == null){
                normais += "= ";
            }else {
                normais += pessoa;
            }
        }
        normais += "]";
        for (Pessoa pessoa: assentoPrioritario){
            if(pessoa == null){
                prioritarias += "@ ";
            } else {
                prioritarias += pessoa;
            }
        }
        prioritarias += "]";
        return "Assentos prioritarios:\n" + prioritarias + "\nAssentos normais:\n" + normais;
    }
    public static void main(String[] args) {
        System.out.println("Seja bem vindo a um simulador de Topic\nAqui você podera simular... uma Topic\nAqui abaixo vão os comandos que você pode inserir:");
        System.out.println("iniciar (numero máximo de pessoas, assentos prioritarios)\nadicionar (nome, idade)\nremover (nome)\nparar --> para o código");
        Scanner scanner = new Scanner(System.in);
        Topic topic = new Topic(2, 1);
        while (true){
        String line = scanner.nextLine();
        String ui[] = line.split(" ");
        if (ui[0].equals("iniciar")){
            topic = new Topic(Integer.parseInt(ui[1]), Integer.parseInt(ui[2]));
            System.out.println(topic);
        } else if (ui[0].equals("parar")){
            break;
        } else if (ui[0].equals("adicionar")){
            topic.adicionar(ui[1], Integer.parseInt(ui[2]));
            System.out.println(topic);
        } else if (ui[0].equals("remover")){
            topic.remover(ui[1]);
            System.out.println(topic);
        } else {
            System.out.println("Erro: comando invalido");
        }
        }
        scanner.close();
    }
}