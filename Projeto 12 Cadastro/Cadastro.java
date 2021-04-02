import java.util.ArrayList;
import java.util.TreeMap;
import java.util.Map;

class Operacao {
    int id;//ordem q a operação foi feita
    String nome;
    float valor;//movimentação
    float saldo;//saldo atual da conta ao executar uma operação
    
    Operacao(int id, String nome, float valor, float saldo){
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.saldo = saldo;
    }
    public String toString (){
        return id + ": " + nome + ": " + valor + ": " + saldo;
    }
}
class Conta {
    int id;
    int idConta;
    String nome;
    float saldo;
    ArrayList<Operacao> extrato;
    ArrayList<String> estornados;

    Conta(String nome, int idConta){
        this.id = 0;
        this.idConta = idConta;
        this.nome = nome;
        this.saldo = 0;
        this.extrato = new ArrayList<>();
        this.estornados = new ArrayList<>();
    }
    void saque(int valor){
        if (valor <= 0){
            throw new RuntimeException("fail: valor negativo ou nulo");
        }
        if (valor > this.saldo){
            throw new RuntimeException("fail: saldo insuficiente");
        }
        this.saldo -= valor;
        this.extrato.add(new Operacao(this.id, "Saque", (-valor), this.saldo));
        this.id += 1;
    }
    void deposito(int valor){
        if (valor <= 0){
            throw new RuntimeException("fail: valor negativo ou nulo");
        }
        this.saldo += valor;
        this.extrato.add(new Operacao(this.id, "Deposito", valor, this.saldo));
        this.id += 1;

    }
    void transferenciaSaida(int valor){
        if (valor <= 0){
            throw new RuntimeException("fail: valor negativo ou nulo");
        }
        if (valor > this.saldo){
            throw new RuntimeException("fail: saldo insuficiente");
        }
        this.saldo -= valor;
        this.extrato.add(new Operacao(this.id, "TransferenciaEnviada", (-valor), this.saldo));
        this.id += 1;
    }
    void transferenciaEntrada(int valor){
        if (valor <= 0){
            throw new RuntimeException("fail: valor negativo ou nulo");
        }
        this.saldo += valor;
        this.extrato.add(new Operacao(this.id, "TransferenciaRecebida", valor, this.saldo));
        this.id += 1;
    }
    void extratoCompleto(){
        for (int i = 0; i < this.extrato.size(); i++){
            System.out.println(this.extrato.get(i));
        }
    }
    void extratoParcial(int numero){
        ArrayList<Operacao> retorno = new ArrayList<Operacao>(); 
        int tamanhoExtrato = this.extrato.size();
        if (numero >= tamanhoExtrato){
            extratoCompleto();
            return;
        }
        if (numero <= 0){
            throw new RuntimeException("fail: comando invalido");
        }
        for(int i = tamanhoExtrato-1; i >= (tamanhoExtrato-numero); i--){
            retorno.add(this.extrato.get(i));
        }
        System.out.println(retorno);
    }
    void tarifa(int valor){
        if (valor <= 0) {
            throw new RuntimeException("fail: valor negativo ou nulo");
        }
        if (valor > this.saldo){
            throw new RuntimeException("fail: saldo insuficiente");
        }
        this.saldo -=valor;
        this.extrato.add(new Operacao(this.id, "Tarifa", (-valor), this.saldo));
        this.id += 1;

    }
    int verificarEstorno(int id){
        if(estornados == null){
            return -1;
        }
        for (int i = 0; i < estornados.size(); i++){
            int valor = Integer.parseInt(this.estornados.get(i));
            if(id == valor){
                return id;
            }
        }
        return -1;
    }
    void upTime(){
        this.saldo += 0;
    }
    void estornar (ArrayList<String> array){
        ArrayList<String> index = array;
        for (int i = 0; i < index.size(); i++){
            int idEstorno = Integer.parseInt(index.get(i));//converte o id q foi dado no array de estorno para int
            if (idEstorno >= extrato.size() || idEstorno < 0){//verificar se o id passado esta contido no extrato
                throw new RuntimeException("id não existe");//id nao existe
            }
            if(verificarEstorno(idEstorno) != -1){
                throw new RuntimeException("O indice " + idEstorno + " ja foi estornado");
            }
            if (this.extrato.get(idEstorno).nome.equals("Tarifa") == false){
                throw new RuntimeException("O id: " + idEstorno + " é um " + this.extrato.get(idEstorno).nome + " e não uma Tarifa");
            }
            this.saldo -= this.extrato.get(idEstorno).valor;
            this.extrato.add(new Operacao(this.id, "Estorno", (-this.extrato.get(idEstorno).valor), this.saldo));
            this.estornados.add(index.get(i));
            this.id += 1;
        }
    }
}
class ContaCorrente extends Conta{
    public ContaCorrente(String nome, int idConta){
        super(nome, idConta);
    }
    void upTime(){
        this.saldo -= 20;
        extratoUpTime();
    }
    void extratoUpTime(){
        this.extrato.add(new Operacao(this.id, "Taxa", (-20), this.saldo));
        this.id += 1;
    }
    public String toString (){
        return this.idConta + ":" + this.nome + ":" + this.saldo + ":CC";
    }
}
class ContaPoupanca extends Conta{
    public ContaPoupanca(String nome, int idConta){
        super(nome, idConta);
    }
    void upTime(){
        this.saldo += saldo/100;
        extratoUpTime();
    }
    void extratoUpTime(){
        this.extrato.add(new Operacao(this.id, "Rendimento", (this.saldo/100), this.saldo));
        this.id += 1;
    }
    public String toString (){
        return this.idConta + ":" + this.nome + ":" + this.saldo + ":CP";
    }
}
class Cliente{
    String nome;
    Map<String, Conta> contas;
    Cliente(String nome){
        this.nome = nome;
        this.contas = new TreeMap<>();
    }
    boolean verificarConta(String id){
        if(contas.get(id) != null){
            return true;
        }
        return false;
    }
    Conta getConta(String id){
        if(verificarConta(id) == false){
            throw new RuntimeException("fail: conta inexistente");
        }
        return contas.get(id);
    }
}
public class Cadastro{
    Map<String, Cliente> clientes;
    Map<String, Conta> contas;
    String lastId;
    Cadastro(){
        this.clientes = new TreeMap<>();
        this.contas = new TreeMap<>();
        this.lastId = "-1";
    }
    boolean verificarContas(String id){
        if(contas.get(id) != null){
            return true;
        }
        return false;
    }
    boolean verificarCliente(String nome){
        if(clientes.get(nome) != null){
            return true;
        }
        return false;
    }
    String gerarId(){
        this.lastId = String.valueOf(Integer.parseInt(lastId)+1);
        return this.lastId;
    }
    void addCliente(String nome){
        if(verificarCliente(nome) == true){
            throw new RuntimeException("fail: o cliente ja existe");
        }
        clientes.put(nome, new Cliente(nome));
        String idCC = gerarId();
        String idCP = gerarId();
        Conta cc = new ContaCorrente(nome, Integer.parseInt(idCC));
        Conta cp = new ContaPoupanca(nome, Integer.parseInt(idCP));
        this.contas.put(idCC, cc);
        this.contas.put(idCP, cp);
        Cliente cliente = getCliente(nome);
        cliente.contas.put(idCC, cc);
        cliente.contas.put(idCP, cp);
    }
    Conta getConta(String id){
        if(verificarContas(id) == false){
            throw new RuntimeException("fail: conta nao encontrada");
        }
        return contas.get(id);
    }
    Cliente getCliente(String nome){
        if(verificarCliente(nome) == false){
            throw new RuntimeException("fail: cliete nao encontrado");
        }
        return clientes.get(nome);
    }
    void saque(String id, String value){
        Conta conta = getConta(id);
        int valor = Integer.parseInt(value);
        conta.saque(valor);
    }
    void deposito(String id, String value){
        Conta conta = getConta(id);
        int valor = Integer.parseInt(value);
        conta.deposito(valor);
    }
    void estornar(String idConta, String ids){
        Conta conta = getConta(idConta);
        String index[] = ids.split(" ");
        ArrayList<String> array = new ArrayList<String>();
        for (int i = 0; i < index.length; i++){
            array.add(index[i]);
        }
        conta.estornar(array);
    }
    void extrato(String idConta){
        Conta conta = getConta(idConta);
        conta.extratoCompleto();
    }
    void extratoParcial(String idConta, String indice){
        Conta conta = getConta(idConta);
        int index = Integer.parseInt(indice);
        conta.extratoParcial(index);
    }
    void tarifa(String idConta, String value){
        Conta conta = getConta(idConta);
        int valor = Integer.parseInt(value);
        conta.tarifa(valor);
    }
    void transferencia(String id1, String id2, String value){
        int valor = Integer.parseInt(value);
        Conta conta01 = getConta(id1);
        Conta conta02 = getConta(id2);
        conta01.transferenciaSaida(valor);
        conta02.transferenciaEntrada(valor);
    }
    void viewCliente(String nome){
        Cliente cliente = getCliente(nome);
        for(Map.Entry<String, Conta> entry : cliente.contas.entrySet()){
            System.out.println(entry.getValue());
        }
    }
    void viewContas(){
        for(Map.Entry<String, Conta> entry : this.contas.entrySet()){
            System.out.println(entry.getValue());
        }
    }
    void upTime(){
        for(Map.Entry<String, Conta> entry : this.contas.entrySet()){
            entry.getValue().upTime();
        }
    }
public static void main(String[] args) {
    Cadastro sistema = new Cadastro();
    System.out.println("-- adicionar um cliente ao sistema --");
    sistema.addCliente("Almir");
    sistema.addCliente("Julia");
    sistema.addCliente("Maria");
    sistema.viewContas();
    System.out.println("-- depositos e saques --");
    sistema.deposito("0", "100");
    sistema.deposito("1", "200");
    sistema.deposito("2", "50");
    sistema.deposito("3", "300");
    sistema.saque("3", "50");
    sistema.saque("0", "70");
    // sistema.saque("1", "300"); //case erro saldo insuficiente
    sistema.viewContas();
    System.out.println("-- transferencias --");
    sistema.transferencia("3", "5", "200");
    sistema.transferencia("0", "4", "25");
    // sistema.transferencia("9", "1", "3");//case erro: conta nao encontrada
    // sistema.transferencia("2", "8", "10");//case erro: conta nao encontrada
    sistema.viewContas();
    System.out.println("-- upTime --");
    sistema.upTime();
    sistema.viewContas();
    System.out.println("-- funções antigas atualizadas --");
    sistema.extrato("3");
    sistema.extratoParcial("3", "2");
    sistema.tarifa("3", "20");
    sistema.tarifa("3", "20");
    sistema.estornar("3", "4 5");
    sistema.extrato("3");
    System.out.println("end");
}
}