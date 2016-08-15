package crud;

import entity.SubTask;
import entity.Task;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class SubTaskService {
    @PersistenceContext(unitName = "PostgreDataSource")
    private EntityManager em;

    public SubTask add(SubTask st){
        SubTask subTaskFromDB = em.merge(st);
        em.flush();
        return subTaskFromDB;
    }

    public void delete(int id){
        em.remove(get(id));
    }

    public void update(SubTask st){
        em.merge(st);
        em.flush();
    }

    public SubTask get(int id){
        return em.find(SubTask.class, id);
    }

    public List<SubTask> getSubsFrom(Task task){
        TypedQuery<SubTask> typedQuery = em.createQuery("SELECT c FROM SubTask c WHERE c.supertodo="+task.getId(), SubTask.class);
        return typedQuery.getResultList();
    }
}
