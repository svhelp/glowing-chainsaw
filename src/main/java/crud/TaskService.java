package crud;

import entity.Task;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;

@Stateless
public class TaskService {
    @PersistenceContext(unitName = "PostgreDataSource")
    private EntityManager em;

    public Task add(Task task){
        Task taskFromDB = em.merge(task);
        em.flush();
        return taskFromDB;
    }

    public void delete(Integer id){
        em.remove(get(id));
    }

    public void update(Task task){
        em.merge(task);
        em.flush();
    }

    public Task get(Integer id){
        return em.find(Task.class, id);
    }

    public List<Task> getAll(){
        TypedQuery<Task> namedQuery = em.createNamedQuery("Task.getAll", Task.class);
        return namedQuery.getResultList();
    }

    public List<Task> getPage(int page, int size, int offset){
        TypedQuery<Task> typedQuery = em.createQuery("SELECT c FROM Task c ORDER BY c.date DESC, c.id DESC", Task.class).setFirstResult(page*size+offset).setMaxResults(size);
        return typedQuery.getResultList();
    }

    public List<Task> getSpecPage(int page, int size, int offset, boolean done){
        if (done == true) {
            TypedQuery<Task> typedQuery2 = em.createQuery("SELECT c FROM Task c WHERE c.done=true ORDER BY c.date DESC, c.id DESC", Task.class).setFirstResult(page*size+offset).setMaxResults(size);
            return typedQuery2.getResultList();
        } else {
            TypedQuery<Task> typedQuery3 = em.createQuery("SELECT c FROM Task c WHERE c.done=false ORDER BY c.date DESC, c.id DESC", Task.class).setFirstResult(page*size+offset).setMaxResults(size);
            return typedQuery3.getResultList();
        }
    }

    public Long getCount(){
        Query query = em.createQuery("SELECT COUNT(c) FROM Task c");
        return (Long)query.getSingleResult();
    }

}
