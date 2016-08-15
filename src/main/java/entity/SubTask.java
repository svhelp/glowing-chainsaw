package entity;

import crud.TaskService;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "subtodos")
public class SubTask {

    public SubTask(String text, Task supertodo){
        this.text=text;
        this.done=false;
        this.supertodo=supertodo;
    }

    public SubTask(String text, int supertodo){
        this.text=text;
        this.done=false;
        TaskService ts = new TaskService();
        this.supertodo=ts.get(supertodo);
    }

    public SubTask(){}

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String text;

    @Column
    private Boolean done;

    @ManyToOne
    @JoinColumn(name = "supertodo")
    private Task supertodo;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public void setSupertodo(Task supertodo) {
        this.supertodo = supertodo;
    }

    public void switchDone(){ if (this.done) {this.done = false;} else {this.done = true;}}

    @Override
    public String toString(){ return "Subtask: " + text + ", done: " + done + ", super task: " + supertodo;}
}
