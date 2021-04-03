import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class Fone {
    String descricao; // label
    String numero; // numero de telefone

    Fone(String numero, String descricao) {
        this.numero = numero; // define o numero de telefone
        this.descricao = descricao; // define a decricao do numero
    }

    public String toString() {
        return this.descricao + ":" + this.numero;
    }
}

class Contato {
    Boolean favorito; // se o contato é favorito ou não
    ArrayList<Fone> lista; // array que guarda fones

    Contato() {
        this.favorito = false; // se é ou não um contato favorito
        this.lista = new ArrayList<Fone>(); // define a lista de numeros
    }

    public String toString() {
        String printLista = "";
        for (int i = 0; i < lista.size(); i++) {//percorre o array de fones
            Fone telefone = lista.get(i);
            printLista += " [" + i + ":" + telefone + "]";
        }
        return printLista;
    }
}

public class Agenda3 {
    Map<String, Contato> listaDeContatos;

    Agenda3() {
        this.listaDeContatos = new TreeMap<>();
    }

    boolean verificarNumero(String number) {// verificar se o num é valido, se só tem 123456789().
        boolean result = true;
        String numerosCertos = "1234567890().";
        for (int i = 0; i < number.length(); i++) {//percorrer o tamanho do number
            if (result == false) {
                break;
            }
            int contagem = 0;
            for (int x = 0; x < numerosCertos.length(); x++) {
                if (number.charAt(i) != numerosCertos.charAt(x)) {
                    contagem += 1;//tem q ser diferente (numerosCertos.length - 1)
                }
                if (contagem == numerosCertos.length()) {
                    result = false;//se for igual ao tamanho da lista, significa q ele não é igual a nenhum x da lista
                }
            }
        }
        return result;
    }

    void adicionarContato(String nome) {
        if (verificarContato(nome) == true) {
            System.out.println("fail: o contato ja existe");
            return;
        }
        listaDeContatos.put(nome, new Contato());
    }

    void removerContato(String entrada) {
        listaDeContatos.remove(entrada);
    }

    void adicionarFavorito(String nome) {
        if (verificarContato(nome) == true) {
            if (getContato(nome).favorito == true){
                System.out.println("fail: contato já esta na lista de favoritos");
                return;
            }
            getContato(nome).favorito = true;
            return;
        }
        System.out.println("fail: contato inexistente");
    }

    void removerFavorito(String nome) {
        if (verificarContato(nome) == true) {
            if (getContato(nome).favorito == false){
                System.out.println("fail: contato não pertence a lista de favoritos");
                return;
            }
            getContato(nome).favorito = false;
            return;
        }
        System.out.println("fail: contato inexistente");
    }

    void adicionarNumero(String nome, String descricao, String numero) {
        if (verificarContato(nome) == true && verificarNumero(numero) == true) {
            getContato(nome).lista.add(new Fone(numero, descricao));
            return;
        }
        if (verificarNumero(numero) == false){
            System.out.println("fail: numero invalido");
            return;
        }
        System.out.println("fail: contato não encontrado");
    }

    void removerNumero(String nome, String index) {
        if (verificarContato(nome) == true) {
            if (Integer.parseInt(index) > getContato(nome).lista.size() || Integer.parseInt(index) < 0){
                System.out.println("fail: indice invalido");
                return;
            }
            getContato(nome).lista.remove(Integer.parseInt(index));
            return;
        }
        System.out.println("fail: contato não encontrado");
    }

    void favoritos (){
        Map<String, Contato> aux = new TreeMap<>();
        for (Map.Entry<String, Contato> entry : listaDeContatos.entrySet()){//rodar a procura de favs
            if (entry.getValue().favorito == true){
                aux.put(entry.getKey(), entry.getValue());//coloca os favs num map aux 
            }
        }
        for (Map.Entry<String, Contato> entry : aux.entrySet()) {
            System.out.println("@ " + entry.getKey() + " " + entry.getValue());//mostrar os favs
        }
    }

    Contato getContato(String nome) {
        if (listaDeContatos.containsKey(nome)) {
            return listaDeContatos.get(nome);
        }
        return null;
    }

    boolean verificarContato(String nome) {
            if (listaDeContatos.containsKey(nome)) {
                return true;
            }
        return false;
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
            System.out.println(favoritar(entry.getValue().favorito) + entry.getKey() + " " + entry.getValue());
        }
    }

    String favoritar (Boolean bool){
        if (bool == true){
            return "@ ";
        }
        return "- ";
    }

    public String toString(){
        String aux = "";
        for (Map.Entry<String, Contato> entry : listaDeContatos.entrySet()){
            aux += (favoritar(entry.getValue().favorito) + entry.getKey() + " " + entry.getValue()) + "\n";
        }
        return aux;
    }
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Agenda3 agenda = new Agenda3();
    while(true){
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
        } else if (ui[0].equals("adicionarFavorito")){
            agenda.adicionarFavorito(ui[1]);
        } else if (ui[0].equals("removerFavorito")){
            agenda.removerFavorito(ui[1]);
        } else if (ui[0].equals("favoritos")){
            agenda.favoritos();
        } else if (ui[0].equals("buscar")){
            agenda.buscar(ui[1]);
        } else {
            System.out.println("fail: comando invalido");
        }
    }
    scanner.close();
    }
}