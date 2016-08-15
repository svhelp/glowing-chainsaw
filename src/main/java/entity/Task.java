package entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="todos")
@NamedQuery(name="Task.getAll", query="SELECT c from Task c")
public class Task {

    public Task(){}

    public Task(String text, Boolean done, Date date){
        this.text=text;
        this.done=done;
        this.date=date;
    }

    public Task(String text, Boolean done){
        this.text=text;
        this.done=done;
        this.date = new Date();
    }

    public Task(String text){
        this.text=text;
        this.done=false;
        this.date = new Date();
    }

    @Id
    @Column(name = "todos_id")
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private Boolean done;

    @Column(nullable = false)
    private Date date;

    public Integer getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public Boolean getDone() {
        return done;
    }

    public Date getDate() {
        return date;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public void switchDone(){ if (this.done) {this.done = false;} else {this.done = true;}}

    @Override
    public String toString(){ return "Task: " + text + " done: " + done;}

    @Override
    public boolean equals(Object other){
        if (other == null) return false;
        if (other == this) return true;
        if (!(other instanceof Task))return false;
        Task otherMyClass = (Task)other;
        if (this.id == otherMyClass.getId() && this.done == otherMyClass.getDone()) //турбо костыль
            return true;
        else return false;
    }
}
