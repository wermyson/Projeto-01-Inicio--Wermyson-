import java.util.Scanner;

class Grafite {
    float calibre;
    String dureza;
    int tamanho;
    
    Grafite (float calibre, String dureza, int tamanho){
        this.calibre = calibre;
        this.dureza = dureza;
        this.tamanho = tamanho;
    }
    public String toString() {
        return "Atributos da ponta" + "\nCalibre: " + this.calibre + "\nDureza: " + this.dureza + "\nTamanho: " + this.tamanho;
    }
}

public class Lapiseira {
    int tamanhoMax;
    float calibre;
    Grafite ponta;
    Lapiseira (int tamanhoMax, float calibre){
        this.tamanhoMax = tamanhoMax;
        this.calibre = calibre;
        this.ponta = null;
    }
    void inserir (float calibre, String dureza, int tamanho){
        if (ponta != null){
            System.out.println("A lapiseira ainda tem uma ponta, remova ela caso queira adicionar uma nova");
            return;
        }
        if (calibre != this.calibre || tamanho > this.tamanhoMax || tamanho < 1){
            System.out.println("Fail: ponta incorreta, siga os parametos pedidos.");
            return;
        }
        if (dureza.equals("6B") || dureza.equals("4B") || dureza.equals("2B") || dureza.equals("HB")){
            this.ponta = new Grafite(calibre, dureza, tamanho);
            System.out.println("Ponta inserida");
            System.out.println(this.ponta.toString()); 
            return;
        }
        System.out.println("Fail: ponta incorreta, siga os parametos pedidos.");
    }
    void remover() {
        if (this.ponta == null){
            System.out.println("Não existe ponta para remover");
            return;
        }
        this.ponta = null;
        System.out.println("Ponta removida");
    }
    String checarPonta (){
        if (this.ponta == null){
            return "Não";
        }
        return "Sim";
    }
    void escrever (int paginas){
        if (this.ponta == null){
            System.out.println("Não é possivel escrever sem uma ponta");
            return;
        }
        int paginasEscritas = 0;
        int pontaGasta = 0;
        int calibre = milimetros();
        if (this.ponta.tamanho < calibre){
            System.out.println("Ponta insuficiente, substitua a ponta");
            return;
        }
        if (this.ponta.tamanho < calibre*paginas){
            for(int i = this.ponta.tamanho; i >= calibre; i -= calibre){
                paginasEscritas += 1;
                pontaGasta += calibre;
            }
            this.ponta.tamanho -= pontaGasta;
            System.out.println("Não foi possivel escrever a quantidade pedida de paginas.\nForam escritas: " + paginasEscritas + "/" + paginas + " paginas");
            if (this.ponta.tamanho == 0){
                System.out.println("A ponta acabou!");
                this.ponta = null;
            }
            if (this.ponta != null){
                System.out.println(this.ponta.toString());
            }
            return;
        }
        this.ponta.tamanho -= calibre*paginas;
        System.out.println("Você escreveu as " + paginas + " paginas que queria\nLetra muito bonita inclusive hehe");
        if (this.ponta.tamanho == 0){
            System.out.println("A ponta acabou!");
            this.ponta = null;
        }
        if (this.ponta != null){
        System.out.println(this.ponta.toString());
        }
    }
    int milimetros (){
        String valor = ponta.dureza;
        if (valor.equals("6B")){
            return 6;
        }
        if (valor.equals("4B")){
            return 4;
        }
        if (valor.equals("2B")){
            return 2;
        }
        return 1;
    }
    public String toString() {
        return "Atributos da Lapiseira" + "\nCalibre: " + this.calibre + "\nTamanho Maximo da ponta: " + this.tamanhoMax + "\nTubo cheio: " + checarPonta();
    }
    public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Lapiseira lapiseira = new Lapiseira(0, 0.5f);
    System.out.println("Seja bem vindo ao simulador de Lapiseira\nAqui você podera simular uma lapiseira obviamente\nAtenção para os comandos abaixo");
    System.out.println("iniciar(Tamanho Maximo da ponta, calibre) --> Esse comando ira criar uma Lapiseira\ninserir(Calibre, Dureza, Tamaho) --> Esse comando ira adicionar uma ponta a sua lapiseira\nremover(nenhuma atributo) --> Esse aqui remove a ponta\nescrever(numero de paginas) --> Esse ira escrever algumas paginas para você\nparar(nenhum atributo) --> esse para o código\nBoa sorte!!!");
    while(true){
        String line = scanner.nextLine();
        String ui[] = line.split(" ");
        if (ui[0].equals("iniciar")){
            lapiseira = new Lapiseira(Integer.parseInt(ui[1]), Float.parseFloat(ui[2]));
            System.out.println(lapiseira);
        } else if (ui[0].equals("parar")){
            break;
        } else if (ui[0].equals("inserir")){
            lapiseira.inserir(Float.parseFloat(ui[1]), ui[2], Integer.parseInt(ui[3]));
            System.out.println(lapiseira);
        } else if (ui[0].equals("remover")){
            lapiseira.remover();
            System.out.println(lapiseira);
        } else if (ui[0].equals("escrever")){
            lapiseira.escrever(Integer.parseInt(ui[1]));
            System.out.println(lapiseira);
        } else {
        System.out.println("Erro: comando invalido");
        }
    }
    scanner.close();
    }
}

