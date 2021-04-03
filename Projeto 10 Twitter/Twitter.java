import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

class Twitte {
    String id;
    User user;
    String twitte;
    ArrayList<User> curtidas;

    Twitte (User user, String twitte, String id){
        this.user = user;
        this.id = id;
        this.twitte = twitte;
        this.curtidas = new ArrayList<>();
    }
    void curtir (User user){
        curtidas.add(user);
    }

    public String toString(){
        String aux = id + ":" + user.nome + "(" + twitte + ")" + "[ ";
        for (User user : curtidas){
            aux += user.nome + " ";
        }
        aux += "]";
        return aux;
    }
}

class User {
    String nome;
    Map<String, Twitte> twittes;
    Map<String, Twitte> allTwittes;
    Map<String, Twitte> timeline;
    Map<String, User> seguindo;
    Map<String, User> seguidores;
    
    User(String nome){
        this.nome = nome;
        this.twittes = new TreeMap<>();
        this.allTwittes = new TreeMap<>();
        this.timeline = new TreeMap<>();
        this.seguindo = new TreeMap<>();
        this.seguidores = new TreeMap<>();
    }
    void verTimeline(){
        for (Map.Entry<String, Twitte> entry : this.timeline.entrySet()){
            System.out.println(entry.getValue());
        }
    }
    void verAllTwittes(){
        for (Map.Entry<String, Twitte> entry : this.allTwittes.entrySet()){
            System.out.println(entry.getValue());
        }
    }
    void cleanTimeline(){
        timeline.clear();
    }
    void twittar (Twitte twitte){
        for (Map.Entry<String, User> entry : this.seguidores.entrySet()){
            entry.getValue().timeline.put(twitte.id, twitte);
            entry.getValue().allTwittes.put(twitte.id, twitte);
        }
        this.timeline.put(twitte.id, twitte);
        this.allTwittes.put(twitte.id, twitte);
    }
    void follow (User usuario, User seguir){
        seguir.seguidores.put(usuario.nome, usuario);
        usuario.seguindo.put(seguir.nome, usuario);
    }
    void unfollow (User usuario, User unfollow){
        unfollow.seguidores.remove(usuario.nome);
        usuario.seguindo.remove(unfollow.nome);
    }
    void meusTwittes (){
        String aux = "";
        for (Map.Entry<String, Twitte> entry : this.twittes.entrySet()){
            aux = entry.getValue().toString() + "\n";
        }
        System.out.println(aux);
    }
    public String toString (){
        String aux = "";
        String seguidores = "seguidores [ ";
        String seguindo = "seguindos [ ";
        for (Map.Entry<String, User> entry : this.seguidores.entrySet()){
            seguidores += entry.getKey() + " ";
        }
        seguidores += "]";
        for (Map.Entry<String, User> entry : this.seguindo.entrySet()){
            seguindo += entry.getKey() + " ";
        }
        seguindo += "]";
        aux += nome + "\n   " + seguidores + "\n   " + seguindo;
        return aux;
    }
}

public class Twitter{
    Map<String, User> usuarios;
    Map<String, Twitte> ids;
    String lastId;
    Twitter (){
        this.usuarios = new TreeMap<>();
        this.ids = new TreeMap<>();
        this.lastId = "-1";
    }
    boolean verificarUser(String nome){
        User valido = usuarios.get(nome);
        if (valido != null){
            return true;
        }
        return false;
    }
    String gerarId(){
        if(lastId.equals("-1")){
            lastId = "0";
            return "0";
        }
        lastId = String.valueOf(Integer.parseInt(lastId) + 1);
        return lastId;
    }
    void criarUser(String nome){
        if (verificarUser(nome) == true){
            throw new RuntimeException("fail: o usuario ja existe");
        }
        User newUser = new User(nome);
        usuarios.put(newUser.nome, newUser);
    }

    void twittar(String nome, String mensagem){
        User user = getUser(nome);
        String id = gerarId();
        ids.put(id, new Twitte(user, mensagem, id));
        user.twittar(ids.get(id));
        
    }
    User getUser(String nome){
        if (verificarUser(nome) == false){
            throw new RuntimeException("fail: o usuario não foi encontrado");
        }
        return usuarios.get(nome);
    }
    void verTimeline(String nome){
        User user = getUser(nome);
        System.out.println("timeline - " + nome);
        user.verTimeline();
        user.cleanTimeline();
    }
    void verAllTwittes(String nome){
        User user = getUser(nome);
        System.out.println("Todos os Twittes recebidos - " + nome);
        user.verAllTwittes();
        user.cleanTimeline();
    }
    void meusTwittes(String nome){
        User user = getUser(nome);
        user.meusTwittes();
    }
    void seguir(String usuario, String seguir){
        User user = getUser(usuario);
        User follow = getUser(seguir);
        user.follow(user, follow);
    }
    void unFollow(String usuario, String deixarDeSeguir){
        User user = getUser(usuario);
        User unfollow = getUser(deixarDeSeguir);
        user.unfollow(user, unfollow);
    }
    boolean follow (String usuario, String verificar){
        User user = getUser(usuario);
        if(user.seguindo.get(verificar) != null){
            return true;
        }
        return false;
    }
    void like (String nome, String id){
        User user = getUser(nome);
        if (Integer.parseInt(id) > Integer.parseInt(lastId)){
            throw new RuntimeException("fail: id inexistente");
        }
        String userId = ids.get(id).user.nome;
        if(follow(nome, userId) == false){
            throw new RuntimeException("fail: siga o usuario para completar a ação!\nUser:" + userId);
        }
        ids.get(id).curtir(user);
    }

    public String toString (){
        String aux = "";
        for (Map.Entry<String, User> entry : this.usuarios.entrySet()){
            aux += entry.getValue() + "\n";
        }
        return aux;
    }
    public static void main(String[] args) {
            Scanner scanner = new Scanner(System.in);
            Twitter sistema = new Twitter();
            
            while(true){
                String line = scanner.nextLine();
                System.out.println("$" + line);
                String ui[] = line.split(" ");
                try {
                    if (ui[0].equals("end"))
                        break;
                    else if (ui[0].equals("addUser")) {
                        sistema.criarUser(ui[1]);
                    } else if (ui[0].equals("show")) {
                        System.out.print(sistema);
                    } else if (ui[0].equals("myTwittes")) {
                        sistema.meusTwittes(ui[1]);
                    } else if (ui[0].equals("follow")) {
                        sistema.seguir(ui[1], ui[2]);
                    }
                    else if (ui[0].equals("twittar")) {
                        String msg = "";
                        for(int i = 2; i < ui.length; i++)
                            msg += ui[i] + " ";
                        sistema.twittar(ui[1], msg);
                    }else if (ui[0].equals("timeline")) {
                        sistema.verTimeline(ui[1]);
                    }else if (ui[0].equals("allTwittes")) {
                        sistema.verAllTwittes(ui[1]);
                    }else if (ui[0].equals("like")) {
                        sistema.like(ui[1], ui[2]);
                    }else if (ui[0].equals("unfollow")) {
                        sistema.unFollow(ui[1], ui[2]);
                    }else{
                        System.out.println("fail: comando invalido");
                    }
                } catch (RuntimeException rt){
                    System.out.println(rt.getMessage());
                }
            }
            scanner.close();
        // Twitter twitter = new Twitter();
        // System.out.println("----------------------------\nRepositório de Usuários\n----------------------------");
        // twitter.criarUser("goku");
        // twitter.criarUser("sara");
        // twitter.criarUser("tina");
        // System.out.println(twitter);
        // System.out.println("----------------------------\nSeguir e ser seguido\n----------------------------");
        // twitter.seguir("goku", "tina");
        // twitter.seguir("goku", "sara");
        // twitter.seguir("sara", "tina");
        // System.out.println(twitter);
        // System.out.println("----------------------------\nVoa passarinho\n----------------------------");
        // twitter.twittar("sara", "hoje estou triste");
        // twitter.twittar("tina", "ganhei chocolate");
        // twitter.twittar("sara", "partiu ru");
        // twitter.twittar("tina", "chocolate ruim");
        // twitter.twittar("goku", "internet maldita");
        // // twitter.verTimeline("goku");
        // // twitter.verTimeline("tina");
        // // twitter.verTimeline("sara");
        // // OBSERVAÇÃO: ao visualizar a timeline, os twittes nela iram suvmir, então comente esta parte acima para poder ver os likes
        // System.out.println("----------------------------\nGostei dei like\n----------------------------");
        // twitter.like("sara", "1");
        // twitter.like("goku", "1");
        // twitter.like("sara", "3");
        // twitter.verTimeline("goku");
        // twitter.verTimeline("sara");
        // System.out.println("----------------------------\nFoi cancelado, bye, bye\n----------------------------");
        // twitter.unFollow("goku", "tina");
        // System.out.println(twitter);
        // System.out.println("----------------------------\nErros de passar raiva\n----------------------------");
        // //twitter.verTimeline("bruno");
        // //twitter.seguir("goku", "kuririm");
        // //twitter.like("sara", "8");
        // //----------------------------------acabou------------------------------------
    }
}