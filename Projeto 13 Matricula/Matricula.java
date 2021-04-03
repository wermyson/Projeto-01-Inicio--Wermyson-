import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;

class Aluno{
    String nome;
    Map<String, Materia> materias;
    Aluno(String nome){
        this.nome = nome;
        this.materias = new TreeMap<>();
    }
    boolean verificarMateria(String materiaNome){
        if(this.materias.get(materiaNome) != null){
            return true;
        }
        return false;
    }
    Materia getMateria(String materiaNome){
        if(verificarMateria(materiaNome) == false){
            throw new RuntimeException("fail: o aluno não esta nessa disciplina");
        }
        return materias.get(materiaNome);
    }
    void removerMateria(String materiaNome){
        Materia materia = getMateria(materiaNome);
        this.materias.remove(materiaNome);
    }
    void addMateria(String materiaNome, Materia materia){
        if(verificarMateria(materiaNome) == true){
            throw new RuntimeException("fail: o aluno "+ this.nome +" ja participa da materia " + materia.nome);
        }
        materias.put(materiaNome, materia);
    }
    public String toString(){
        String aux = this.nome + " [ ";
        for(Map.Entry<String, Materia> entry : this.materias.entrySet()){
            aux += entry.getKey() + " ";
        }
        aux += "]";
        return aux;
    }
}
class Materia{
    String nome;
    Map<String, Aluno> alunos;
    Materia(String nome){
        this.nome = nome;
        this.alunos = new TreeMap<>();
    }
    boolean verificarAluno(String alunoNome){
        if(this.alunos.get(alunoNome) != null){
            return true;
        }
        return false;
    }
    Aluno getAluno(String alunoNome){
        if(verificarAluno(alunoNome) == false){
            throw new RuntimeException("fail: o aluno não esta nessa disciplina");
        }
        return alunos.get(alunoNome);
    }
    void removerAluno(String alunoNome){
        Aluno aluno = getAluno(alunoNome);
        this.alunos.remove(alunoNome);
    }
    void addAluno(String alunoNome, Aluno aluno){
        if(verificarAluno(alunoNome) == true){
            throw new RuntimeException("fail: o aluno ja participa da disciplina");
        }
        alunos.put(alunoNome, aluno);
    }
    public String toString(){
        String aux = this.nome + " [ ";
        for(Map.Entry<String, Aluno> entry : this.alunos.entrySet()){
            aux += entry.getKey() + " ";
        }
        aux += "]";
        return aux;
    }
}

public class Matricula{
    Map<String, Materia> materias;
    Map<String, Aluno> alunos;
    Matricula(){
        this.materias = new TreeMap<>();
        this.alunos = new TreeMap<>();
    }
    boolean verificarAluno(String alunoNome){
        if(this.alunos.get(alunoNome) != null){
            return true;
        }
        return false;
    }
    Aluno getAluno(String alunoNome){
        if(verificarAluno(alunoNome) == false){
            throw new RuntimeException("fail: o aluno " + alunoNome + " não consta no sistema");
        }
        return this.alunos.get(alunoNome);
    }
    boolean verificarMateria(String materiaNome){
        if(this.materias.get(materiaNome) != null){
            return true;
        }
        return false;
    }
    Materia getMateria(String materiaNome){
        if(verificarMateria(materiaNome) == false){
            throw new RuntimeException("fail: a materia " + materiaNome + " não consta no sistema");
        }
        return this.materias.get(materiaNome);
    }
    void removerMateria(String materiaNome){
        Materia materia = getMateria(materiaNome);
        this.materias.remove(materiaNome);
    }
    void addMateria(String materiaNome){
        if(verificarMateria(materiaNome) == true){
            throw new RuntimeException("fail: a materia ja foi adicionada");
        }
        materias.put(materiaNome, new Materia(materiaNome));
    }
    void removerAluno(String alunoNome){
        Aluno aluno = getAluno(alunoNome);
        this.alunos.remove(alunoNome);
    }
    void addAluno(String alunoNome){
        if(verificarAluno(alunoNome) == true){
            throw new RuntimeException("fail: o aluno ja foi adicionado");
        }
        alunos.put(alunoNome, new Aluno(alunoNome));
    }
    void newMateria(String entrada){
        String ui[] = entrada.split(" ");
        for(int i = 0; ui.length > i; i++){
            if(verificarMateria(ui[i]) == true){
                System.out.println("fail: a materia " + ui[i] + " não foi adicionada pois ja existe");
                return;
            }
            addMateria(ui[i]);
        }
    }
    void newAluno(String entrada){
        String ui[] = entrada.split(" ");
        for(int i = 0; ui.length > i; i++){
            if(verificarAluno(ui[i]) == true){
                System.out.println("fail: o aluno " + ui[i] + " não foi adicionada pois ja existe");
                return;
            }
            addAluno(ui[i]);
        }
    }
    void addAlunoMateria(String alunoNome, String materiaNome){
        Aluno aluno = getAluno(alunoNome);
        String ui[] = materiaNome.split(" ");
        for(int i = 0; ui.length > i; i++){
            Materia materia = getMateria(ui[i]);
            if(materia.verificarAluno(aluno.nome) == true){
                System.out.println("fail: o aluno " + ui[i] + " ja esta nesta disciplina");
                return;
            }
            aluno.addMateria(ui[i], materia);
            materia.addAluno(alunoNome, aluno);
        }
    }
    void removerAlunoMateria(String alunoNome, String materiaNome){
        Aluno aluno = getAluno(alunoNome);
        String ui[] = materiaNome.split(" ");
        for(int i = 0; ui.length > i; i++){
            Materia materia = getMateria(ui[i]);
            if(materia.verificarAluno(aluno.nome) == false){
                System.out.println("fail: o aluno " + ui[i] + " não pertence a materia " + materia.nome);
                return;
            }
            aluno.removerMateria(materia.nome);
            materia.removerAluno(alunoNome);
        }
    }
    void removerAlunoSistema(String alunoNome){
        Aluno aluno = getAluno(alunoNome);
        for(Map.Entry<String, Materia> entry : aluno.materias.entrySet()){
            entry.getValue().removerAluno(alunoNome);
        }
        removerAluno(alunoNome);
    }
    void removerMatriculaSistema(){
        Materia  materia = getMateria(materiaNome);
        Aluno aluno = getAluno(alunoNome);
        for(Map.Entry<String, Aluno> entry : materia.alunos.entrySet()){
            entry.getValue().removerMateria(materiaNome);
        }
        removerMateria(materiaNome);
    }
    public String toString(){
        String aux = "Alunos:\n";
        for(Map.Entry<String, Aluno> entry : this.alunos.entrySet()){
            aux += entry.getValue() + "\n";
        }
        aux += "\nMaterias:\n";
        for(Map.Entry<String, Materia> entry : this.materias.entrySet()){
            aux += entry.getValue() + "\n";
        }
        return aux;
    }
    public static void main(String[] args) {
        // Matricula sistema = new Matricula();
        // System.out.println("-- case adicionar alunos no sistema --");
        // sistema.newAluno("alice edson bruno");
        // sistema.newMateria("poo aps");
        // sistema.newMateria("fup");
        // System.out.println(sistema);
        // System.out.println("-- case matricular alunos --");
        // sistema.addAlunoMateria("bruno", "fup aps poo");
        // sistema.addAlunoMateria("alice", "fup poo");
        // sistema.addAlunoMateria("edson", "fup");
        // System.out.println(sistema);
        // System.out.println("-- case remover aluno --");
        // sistema.removerAlunoMateria("bruno", "poo aps");
        // System.out.println(sistema);
        // System.out.println("-- case remover aluno do sistema --");
        // sistema.removerAlunoSistema("alice");
        // System.out.println(sistema);
        // System.out.println("-- end --");
        Scanner scanner = new Scanner(System.in);
        Matricula sistema = new Matricula();
            
            while(true){
                String line = scanner.nextLine();
                System.out.println("$" + line);
                String ui[] = line.split(" ");
                try {
                    if (ui[0].equals("end"))
                        break;
                    else if (ui[0].equals("nwalu")) {
                        for(int i = 1; ui.length > i; i++){
                        sistema.newAluno(ui[i]);
                        }
                    } else if (ui[0].equals("show")) {
                        System.out.print(sistema);
                    } else if (ui[0].equals("nwdis")){
                        for(int i = 1; ui.length > i; i++){
                        sistema.newMateria(ui[i]);
                        }
                    } else if (ui[0].equals("tie")){
                        for(int i = 2; ui.length > i; i++){
                        sistema.addAlunoMateria(ui[1], ui[i]);
                        }
                    } else if (ui[0].equals("untie")){
                        for(int i = 2; ui.length > i; i++){
                        sistema.removerAlunoMateria(ui[1], ui[i]);
                        }
                    } else if (ui[0].equals("rmalu")){
                        sistema.removerAlunoSistema(ui[1]);
                    } else {
                        System.out.println("fail: comando invalido");
                    }
                } catch (RuntimeException rt){
                    System.out.println(rt.getMessage());
                }
            }
            scanner.close();
    }
}