import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

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
    public String toString (){
        String printLista = "";
        for(int i = 0; i < lista.size(); i++){
            Fone telefone = lista.get(i);
            printLista += " [" + i + ":" + telefone + "]";
        }
        return this.nome + printLista;
    }
}
class OrganizarEmOrdemAlfabetica implements Comparator<Contato>{
    public int compare(Contato one, Contato two){
        return one.nome.compareTo(two.nome);
    }
}
public class Agenda {
    ArrayList<Contato> listaDeContatos;
    Agenda (){
        this.listaDeContatos = new ArrayList<Contato>();
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
    void adicionarContato(String nome){
        if(verificarContato(nome) == true){
            System.out.println("fail: o contato ja existe");
            return;
        }
        listaDeContatos.add(new Contato(nome));
    }
    void removerContato(String entrada){
        listaDeContatos.remove(getContatoPosition(entrada));
    }
    void adicionarNumero(String nome, String descricao, String numero){
        if (verificarContato(nome) == true){
            getContato(nome).lista.add(new Fone(numero, descricao));
            return;
        }
        System.out.println("fail: contato não encontrado");
    }
    void removerNumero(String nome, String index){
        if (verificarContato(nome) == true){
            getContato(nome).lista.remove(Integer.parseInt(index));
            return;
        }
        System.out.println("fail: contato não encontrado");
    }
    void agenda(){
        Collections.sort(listaDeContatos, new OrganizarEmOrdemAlfabetica());
        for (Contato contato : listaDeContatos){
            System.out.println("- " + contato);
        }
    }
    Contato getContato(String nome){
        for(int i = 0; i < listaDeContatos.size(); i++){
            String comparar = listaDeContatos.get(i).nome;
            if(nome.equals(comparar)){
                return listaDeContatos.get(i);
            }
        }
        return null;   
    }
    int getContatoPosition(String nome){
        for(int i = 0; i < listaDeContatos.size(); i++){
            String comparar = listaDeContatos.get(i).nome;
            if(nome.equals(comparar)){
                return i;
            }
        }
        return -1;   
    }
    boolean verificarContato(String nome){
        for(int i = 0; i < listaDeContatos.size(); i++){
            String comparar = listaDeContatos.get(i).nome;
            if(nome.equals(comparar)){
                return true;
            }
        }
        return false;
    }
    public void buscar (String entrada){
        ArrayList<Contato> aux = new ArrayList<Contato>();
        for (Contato contato : listaDeContatos){
            if (contato.nome.contains(entrada)){
                aux.add(contato);
            }
            ArrayList<Fone> listaFones = contato.lista;
            for(Fone fone : listaFones){
                if (fone.descricao.contains(entrada)){
                    aux.add(contato);
                }
                if (fone.numero.contains(entrada)){
                    aux.add(contato);
                }
            }
        }
        for (Contato contato : aux){
            System.out.println("- " + contato);
        }
    }
public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Agenda agenda = new Agenda();
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
            agenda.agenda();
        } else if (ui[0].equals("buscar")){
            agenda.buscar(ui[1]);
        } else {
            System.out.println("fail: comando invalido");
        }
    }
    scanner.close();
    }
}