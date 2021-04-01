import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class Fone {
    String descricao; //label
    String numero; //numero de telefone
    Fone (String numero, String descricao){
        this.numero = numero; //define o numero de telefone
        this.descricao = descricao; //define a decricao do numero
    }
    public String toString (){
        return this.descricao + ":" + this.numero;
    }
}
class Contato {
    String nome; //nome do contato
    ArrayList<Fone> lista = new ArrayList<Fone>(); //array que guarda fones
    
    Contato (String nome){
        this.nome = nome; //define o nome do contato
        this.lista = lista; //define a lista de numeros
    }
    boolean verificarNumero (String number){//verificar se o num é valido, se só tem 123456789().
        boolean result = true;
        String numerosCertos = "1234567890().";
        for(int i = 0; i < number.length(); i++){
            if(result == false){
                break;
            }
            int contagem = 0;
            for(int x = 0; x < numerosCertos.length(); x++){
                if(number.charAt(i) != numerosCertos.charAt(x)){
                    contagem = contagem + 1;
                }
                if(contagem == numerosCertos.length()){
                    result = false;
                }
            }
        }
        return result;
    }
    void adicionarNumero(String descricao, String numero){
        if (verificarNumero(numero) == true){
            lista.add(new Fone(numero, descricao));
            return;
        }
        throw new RuntimeException ("fail: numero invalido");
    }

    void removerNumero(String indice){
        int index = Integer.parseInt(indice);
        if (index > lista.size()){
            lista.remove(index);
            return;
        }
        throw new RuntimeException ("fail: index invalido");
    }
    public String toString (){
        String printLista = "";
        for(int i = 0; i < lista.size(); i++){
            Fone telefone = lista.get(i);
            printLista += " [" + i + ":" + telefone + "]";
        }
        return this.nome + printLista;
    }
}

public class Agenda {
    Map<String, Contato> listaDeContatos;

    Agenda() {
        this.listaDeContatos = new TreeMap<>();
    }

    void adicionarNumero(String nome, String descricao, String numero){
        Contato contato = getContato(nome);
        if(contato != null){
            contato.adicionarNumero(descricao, numero);
            return;
        }
        throw new RuntimeException ("fail: o contato não foi encontrado");
    }

    void removerNumero(String nome, String indice){
        Contato contato = getContato(nome);
        if(contato != null){
            contato.removerNumero(indice);
            return;
        }
        throw new RuntimeException ("fail: o contato não foi encontrado");
    }

    void adicionarContato(String nome) {
        if (getContato(nome) != null) {
            throw new RuntimeException ("fail: o contato ja existe");
        }
        listaDeContatos.put(nome, new Contato(nome));
    }

    void removerContato(String entrada) {
        listaDeContatos.remove(entrada);
    }

    Contato getContato(String nome){
        if (listaDeContatos.containsKey(nome)) {
            return listaDeContatos.get(nome);
        }
        return null;
    }

    public void buscar(String entrada) {
        Map<String, Contato> aux = new TreeMap<>();
        for (Map.Entry<String, Contato> entry : listaDeContatos.entrySet()) {
            if (entry.getKey().contains(entrada)){ // verifica se contém a entrada no nome
                aux.put(entry.getKey(), entry.getValue()); // guarda a chave e o contato na variavel aux
            }
            ArrayList<Fone> listaFones = entry.getValue().lista;
            for(Fone fone : listaFones){
                if (fone.descricao.contains(entrada)){ // verifica se contém a entrada na descrição da lista do contato acessado pela chave X
                    aux.put(entry.getKey(), entry.getValue());
                }
                if (fone.numero.contains(entrada)){ // verifica se contém a entrada no número da lista do contato acessado pela chave X
                    aux.put(entry.getKey(), entry.getValue());
                }
            }
        }
        for (Map.Entry<String, Contato> entry : aux.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }
    }

    public String toString (){
        String aux = "";
        for (Map.Entry<String, Contato> entry : listaDeContatos.entrySet()){
            aux += (entry.getKey() + " " + entry.getValue()) + "\n";
        }
        return aux;
    }
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Agenda agenda = new Agenda();
    while(true){
        try {
        String line = scanner.nextLine();
        String ui[] = line.split(" ");
        if (ui[0].equals("adicionarContato")){
            agenda.adicionarContato(ui[1]);
        } else if(ui[0].equals("parar")){
            break;
        } else if(ui[0].equals("removerContato")){
            agenda.removerContato(ui[1]);
        }else if(ui[0].equals("adicionarNum")){
            agenda.adicionarNumero(ui[1], ui[2], ui[3]);;
        } else if (ui[0].equals("removerNum")){
            agenda.removerNumero(ui[1], ui[2]);
        } else if (ui[0].equals("agenda")){
            System.out.println(agenda);
        } else if (ui[0].equals("buscar")){
            agenda.buscar(ui[1]);
        } else {
            System.out.println("fail: comando invalido");
        }
    } catch (RuntimeException e){
        System.out.println(e.getLocalizedMessage());
    }
    }
    scanner.close();
    }
}