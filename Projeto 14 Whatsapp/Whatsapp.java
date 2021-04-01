import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
class Mensagem{
    String nome;
    String mensagem;
    Mensagem(String nome, String mensagem){
        this.nome = nome;
        this.mensagem = mensagem;
    }
    public String toString(){
        String aux = "[" + this.nome + ": " + this.mensagem + "]";
        return aux;
    }
}
class ViewGrupo{
    String nome;
    ArrayList<Mensagem> naoLidas;
    ArrayList<Mensagem> allMensagens;
    ViewGrupo(String nome){
        this.nome = nome;
        this.naoLidas = new ArrayList<>();
        this.allMensagens = new ArrayList<>();
    }
    void viewnaoLidas(){
        for (int i = 0; i < this.naoLidas.size(); i++){
            System.out.println(this.naoLidas.get(i));
        }
        cleannaoLidas();
    }
    void cleannaoLidas(){
        this.naoLidas.clear();
    }
    void viewallMensagens(){
        for (int i = 0; i < this.allMensagens.size(); i++){
            System.out.println(this.allMensagens.get(i));
        }
    }
}
class Notificacao{
    String grupo;
    int notificacoes;
    Notificacao(String grupo){
        this.grupo = grupo;
        this.notificacoes = 0;
    }
    void addNotificacao(){
        notificacoes += 1;
    }
    void cleanNotificacoes(){
        notificacoes = 0;
    }
    public String toString(){
        String aux = grupo + " ";
        aux += "(" + notificacoes + ")";
        return aux;
    }
}
class Grupo {
    String nome;
    Map<String, Contato> participantes;
    Map<String, Contato> administradores;
    Map<String, ViewGrupo> mensagensGrupos;
    Grupo (String nome){
        this.nome = nome;
        this.participantes = new TreeMap<>();
        this.administradores = new TreeMap<>();
        this.mensagensGrupos = new TreeMap<>();
    }
    boolean verificarAdministradores (String nome){
        if(this.administradores.get(nome) != null){
            return true;
        }
        return false;
    }
    boolean verificarParticipantes (String nome){
        if(this.participantes.get(nome) != null){
            return true;
        }
        return false;
    }
    Contato getParticipante(String nome){
        if(verificarParticipantes(nome) == false){
            throw new RuntimeException("fail: user " + nome + " não pertence ao grupo " + this.nome);
        }
        return this.participantes.get(nome);
    }
    Contato getAdministrador(String nome){
        if(verificarAdministradores(nome) == false){
            throw new RuntimeException("fail: participante não é um administrador");
        }
        return this.administradores.get(nome);
    }
    ViewGrupo getViewGrupo(String userNome){
        if(verificarParticipantes(userNome) == false){
            throw new RuntimeException("fail: user " + userNome + " não pertence ao grupo " + this.nome);
        }
        return this.mensagensGrupos.get(userNome);
    }
    void addParticipante(String nome, Contato contato){
        Contato user = getParticipante(nome);
        Contato administrador = getAdministrador(user.nome);
        if(verificarParticipantes(contato.nome) == true){
            throw new RuntimeException("fail: o participante ja esta no grupo");
        }
        this.participantes.put(contato.nome, contato);
        this.mensagensGrupos.put(contato.nome, new ViewGrupo(contato.nome));
        contato.criarNotificacao(this.nome);
    }
    void removerParticipante(String admin, String remover){
        Contato administrador = getAdministrador(admin);
        Contato participante = getParticipante(nome);
        this.participantes.remove(remover);
        this.mensagensGrupos.remove(remover);
    }
    void addAdministrador(String admin, String nome){
        Contato administrador = getAdministrador(admin);
        Contato participante = getParticipante(nome);
        if(verificarAdministradores(nome) == true){
            throw new RuntimeException("fail: o participante já é um administrador");
        }
        administradores.put(nome, getParticipante(nome));
    }
    void removerAdministrador(String admin1, String admin2){
        Contato administrador = getAdministrador(admin1);
        Contato administrador2 = getAdministrador(admin2);
        administradores.remove(admin2);
    }
    void viewAdministradores(){
        for(Map.Entry<String, Contato> entry : this.administradores.entrySet()){
            System.out.println("[" + entry.getKey() + "] - ADMIN");
        }
    }
    void sairDoGrupo(String nome){
        Contato participante = getParticipante(nome);
        this.participantes.remove(nome);
        this.mensagensGrupos.remove(nome);
    }
    void enviarMensagem(String nome, String mensagem){
        Contato contato = getParticipante(nome);
        addNotificacao(nome);
        for(Map.Entry<String, ViewGrupo> entry : this.mensagensGrupos.entrySet()){
            if(entry.getKey().equals(nome) != true){
            Mensagem newMensagem = new Mensagem(nome, mensagem);
            entry.getValue().naoLidas.add(newMensagem);
            entry.getValue().allMensagens.add(newMensagem);
            }
        }
    }
    void addNotificacao(String nome){
        for(Map.Entry<String, Contato> entry : this.participantes.entrySet()){
            if(entry.getKey().equals(nome) != true){
                entry.getValue().addNotificacao(this.nome);
            }
        }
    }
    void viewMensagensNaoLidas(String userNome){
        Contato user = getParticipante(userNome);
        ViewGrupo viewMensagem = getViewGrupo(userNome);
        viewMensagem.viewnaoLidas();
        user.cleanNotificacoes(this.nome);
    }
    void viewAllMensagens(String nome){
        Contato user = getParticipante(nome);
        ViewGrupo viewMensagem = getViewGrupo(nome);
        viewMensagem.viewallMensagens();
        user.cleanNotificacoes(this.nome);
    }
    public String toString(){
        String aux = "";
        for(Map.Entry<String, Contato> entry : this.participantes.entrySet()){
            if(verificarAdministradores(entry.getKey()) == true){
                aux += "\n[" + entry.getKey() + "] - ADMIN";
            } else {
                aux += "\n[" + entry.getKey() + "]";
            }
        }
        return aux;
    }
}

class Contato {
    String nome;
    Map<String, Grupo> grupos;
    Map<String, Notificacao> notificacoes;
    Contato(String nome){
        this.nome = nome;
        this.grupos = new TreeMap<>();
        this.notificacoes = new TreeMap<>();
    }
    boolean verificarGrupos(String nome){
        if(grupos.get(nome) != null){
            return true;
        }
        return false;
    }
    Grupo getGrupo(String nome){
        if(verificarGrupos(nome) == false){
            throw new RuntimeException("fail: não foi possivel encontrar o grupo");
        }
        return grupos.get(nome);
        
    }
    boolean verificarNotificacao(String grupo){
        if(getNotificacao(grupo) == null){
            return false;
        }
        return true;
    }
    Notificacao getNotificacao(String nome){
        Notificacao noti = notificacoes.get(nome);
        if(noti == null){
            throw new RuntimeException("fail: grupo inexistente");
        }
        return noti;
    }
    void addGrupo(Grupo grupo){
        if(grupos.get(grupo.nome) != null){
            throw new RuntimeException("fail: o participante ja esta no grupo");
        }
        this.grupos.put(grupo.nome, grupo);
    }
    void removerGrupo(String grupoNome){
        if(grupos.get(grupoNome) == null){
            throw new RuntimeException("fail: não foi possivel achar o grupo");
        }
        this.grupos.remove(grupoNome);
    }
    void criarGrupo (Contato contato, Grupo grupo){
        grupos.put(grupo.nome, grupo);
        grupo.participantes.put(contato.nome, contato);
        grupo.administradores.put(contato.nome, contato);
        contato.criarNotificacao(grupo.nome);
        grupo.mensagensGrupos.put(contato.nome, new ViewGrupo(contato.nome));
    }
    void viewGrupos(){
        String aux = "[ ";
        for(Map.Entry<String, Grupo> entry : grupos.entrySet()){
            aux += entry.getKey() + " ";
        }
        aux += "]";
        System.out.println(aux);
    }
    void criarNotificacao(String grupoNome){
        if(verificarGrupos(nome) == true){
            throw new RuntimeException("fail: o grupo ja existente");
        }
        this.notificacoes.put(grupoNome, new Notificacao(grupoNome));
    }
    void viewNotificacoes(){
        String aux = "";
        for(Map.Entry<String, Notificacao> entry : this.notificacoes.entrySet()){
            aux +=  "\n" + entry.getValue();
        }
        System.out.println(aux);
    }
    void cleanNotificacoes(String grupoNome){
        Notificacao cleanNotificacoes = getNotificacao(grupoNome);
        cleanNotificacoes.cleanNotificacoes();
    }
    void addNotificacao(String grupo){
        getNotificacao(grupo).addNotificacao();
    }
}


public class Whatsapp {
    Map<String, Contato> usuarios;
    Map<String, Grupo> grupos;
    Whatsapp(){
        this.usuarios = new TreeMap<>();
        this.grupos = new TreeMap<>();
    }
    boolean verificarUser(String nome){
        if(usuarios.get(nome) != null){
            return true;
        }
        return false;
    }
    Contato getUser(String nome){
        if(verificarUser(nome) == false){
            throw new RuntimeException("fail: usuario inexistente no sistema");
        }
        return usuarios.get(nome);
    }
    void criarUsuario(String nome){
        if(verificarUser(nome) == true){
            throw new RuntimeException("fail: o usuario já existe");
        }
        usuarios.put(nome, new Contato(nome));
    }
    boolean verificarGrupo(String nome){
        if(this.grupos.get(nome) != null){
            return true;
        }
        return false;
    }
    Grupo getGrupo(String nome){
        if(verificarGrupo(nome) == false){
            throw new RuntimeException("fail: grupo inexistente no sistema");
        }
        return this.grupos.get(nome);
    }
    void criarGrupo(String nome, String grupo){
        if(verificarGrupo(grupo) == true){
            throw new RuntimeException("fail: o grupo já existe");
        }
        grupos.put(grupo, new Grupo(grupo));
        Contato user = getUser(nome);
        Grupo chat = getGrupo(grupo);
        user.criarGrupo(user, chat);
    }
    void enviarMensagem(String nomeUser, String grupo, String mensagem){
        Contato user = getUser(nomeUser);
        user.getGrupo(grupo).enviarMensagem(nomeUser, mensagem);
    }
    void addParticipante(String userNome, String userAddNome, String grupoNome){
        Contato user = getUser(userNome);
        Contato userAdd = getUser(userAddNome);
        Grupo grupo = getGrupo(grupoNome);
        grupo.addParticipante(userNome, userAdd);
        userAdd.addGrupo(grupo);
    }
    void removerParticipante(String adminNome, String userNome, String grupoNome){
        Contato admin = getUser(adminNome);
        Contato user = getUser(userNome);
        Grupo grupo = admin.getGrupo(grupoNome);
        grupo.removerParticipante(adminNome, userNome);
    }
    void sairdoGrupo(String userNome, String grupoNome){
        Contato user = getUser(userNome);
        Grupo grupo = user.getGrupo(grupoNome);
        grupo.sairDoGrupo(userNome);
        user.removerGrupo(grupoNome);
    }
    void addAdministrador(String adminNome, String userNome, String grupoNome){
        Contato administrador = getUser(adminNome);
        Contato user = getUser(userNome);
        Grupo grupo = administrador.getGrupo(grupoNome);
        grupo.addAdministrador(adminNome, userNome);
    }
    void removerAdministrador(String admin1, String admin2, String grupoNome){
        Contato admin = getUser(admin1);
        Contato remover = getUser(admin2);
        Grupo grupo = admin.getGrupo(grupoNome);
        grupo.removerAdministrador(admin1, admin2);
    }
    void viewNotificacoes(String userNome){
        Contato user = getUser(userNome);
        user.viewNotificacoes();
    }
    boolean verificarUserGrupo(String userNome, String grupoNome){
        if(getGrupo(grupoNome).verificarParticipantes(userNome) == false){
            throw new RuntimeException("fail: user " + userNome + " não pertence ao grupo " + grupoNome);
        }
        return true;
    }
    void viewMensagensNaoLidas(String userNome, String grupoNome){
        verificarUserGrupo(userNome, grupoNome);
        Grupo grupo = getGrupo(grupoNome);
        grupo.viewMensagensNaoLidas(userNome);
    }
    void viewAllMensagens(String userNome, String grupoNome){
        Contato user = getUser(userNome);
        Grupo grupo = user.getGrupo(grupoNome);
        grupo.viewAllMensagens(userNome);
    }
    void viewGrupos(String userNome){
        Contato user = getUser(userNome);
        user.viewGrupos();
    }
    void viewParticipantes(String grupoNome){
        Grupo grupo = getGrupo(grupoNome);
        if (grupo == null){
            throw new RuntimeException("fail: grupo não encontrado");
        }
        System.out.println(grupo);
    }
    void viewAdministradores(String grupoNome){
        Grupo grupo = getGrupo(grupoNome);
        if (grupo == null){
            throw new RuntimeException("fail: grupo não encontrado");
        }
        grupo.viewAdministradores();
    }
    void viewUsers(){
        String aux = "Usuarios:\n[ ";
        for (Map.Entry<String, Contato> entry : usuarios.entrySet()){
            aux += entry.getKey() + " ";
        }
        aux += "]";
        System.out.println(aux);
    }
    void viewChats(){
        String aux = "Grupos:\n[ ";
        for (Map.Entry<String, Grupo> entry : grupos.entrySet()){
            aux += entry.getKey() + " ";
        }
        aux += "]";
        System.out.println(aux);
    }
    public String toString(){
        String aux = "Usuarios:\n[ ";
        for (Map.Entry<String, Contato> entry : usuarios.entrySet()){
            aux += entry.getKey() + " ";
        }
        aux += "]\nGrupos:\n[ ";
        for (Map.Entry<String, Grupo> entry : grupos.entrySet()){
            aux += entry.getKey() + " ";
        }
        aux += "]";
        return aux;
    }
public static void main(String[] args) {
    Whatsapp whatsapp = new Whatsapp();
    whatsapp.criarUsuario("goku");
    whatsapp.criarUsuario("sara");
    whatsapp.criarUsuario("tina");
    whatsapp.viewUsers();
    whatsapp.criarGrupo("goku", "guerreiros");
    whatsapp.criarGrupo("goku", "homens");
    whatsapp.criarGrupo("sara", "familia");
    // whatsapp.criarGrupo("sara", "guerreiros");
    // System.out.println(whatsapp);
    System.out.println("-- ver chats do usuarios --");
    whatsapp.viewGrupos("goku");
    whatsapp.viewGrupos("sara");
    whatsapp.viewGrupos("tina");
    System.out.println("-- adicionar pessoa ao grupo // ver membros --");
    whatsapp.addParticipante("goku", "sara", "guerreiros");
    // whatsapp.addParticipante("sara", "tina", "guerreiros"); //o participante tem que ser um administrador para adicionar alguem
    whatsapp.addAdministrador("goku", "sara", "guerreiros");
    System.out.println("-- ver admins --");
    whatsapp.viewAdministradores("guerreiros");
    whatsapp.addParticipante("sara", "tina", "guerreiros");
    System.out.println("-- ver participantes --");
    whatsapp.viewParticipantes("guerreiros");
    // whatsapp.addParticipante("tina", "goku", "familia"); //erro participante não esta no grupo
    System.out.println("-- ver grupos --");
    whatsapp.viewGrupos("sara");
    whatsapp.viewGrupos("tina");
    whatsapp.viewGrupos("goku");
    System.out.println("-- ver participantes --");
    whatsapp.viewParticipantes("guerreiros");
    whatsapp.viewParticipantes("familia");
    System.out.println("-- sair de um grupo --");
    whatsapp.sairdoGrupo("sara", "guerreiros");
    whatsapp.viewParticipantes("guerreiros");
    whatsapp.viewGrupos("sara");
    System.out.println("-- enviar mensagem para um grupo --");
    whatsapp.enviarMensagem("goku", "guerreiros", "oi, eu sou o goku");
    whatsapp.enviarMensagem("tina", "guerreiros", "oi goku");
    System.out.println("-- ver notificações --");
    whatsapp.viewNotificacoes("goku");
    whatsapp.viewNotificacoes("tina");
    System.out.println("-- ler novas mensagens --");
    whatsapp.viewMensagensNaoLidas("goku", "guerreiros");
    whatsapp.viewMensagensNaoLidas("tina", "guerreiros");
    // whatsapp.viewMensagensNaoLidas("sara", "guerreiros"); //case erro: participante não faz parte do grupo
    whatsapp.enviarMensagem("goku", "guerreiros", "vamos sair tina?");
    whatsapp.enviarMensagem("tina", "guerreiros", "voce ta com fome goku?");
    whatsapp.enviarMensagem("goku", "guerreiros", "to com saudade de voce.");
    whatsapp.viewNotificacoes("tina");
    whatsapp.viewNotificacoes("goku");
    whatsapp.viewMensagensNaoLidas("goku", "guerreiros");
    whatsapp.viewMensagensNaoLidas("tina", "guerreiros");
    System.out.println("end");
}
}