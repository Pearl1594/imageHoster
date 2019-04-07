package ImageHoster.repository;

import ImageHoster.model.Comment;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.util.List;

@Repository
public class CommentRepository {

    @PersistenceUnit(unitName = "imageHoster")
    private EntityManagerFactory emf;

    // This method persists the comment into the database
    // It creates an instance of EntityManager and of EntityTransaction
    // It starts a transaction and is commited if the transaction is successful
    // If the transaction fails \, it rollsback to the previous state/transaction
    public void createComment(Comment comment){
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(comment);
            transaction.commit();
        }
        catch (Exception e) {
            transaction.rollback();
        }
    }

    // This method gets all the comments of a particular Image (imageId)
    // It executes a JPQL to fetch the details from the database and returns a list of comments
    public List<Comment> getComments(Integer imageId) {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Comment> query = em.createQuery("select c from Comment c where c.image.id = :imageId", Comment.class).setParameter("imageId", imageId);

        List<Comment> comments = query.getResultList();
        return comments;
    }

}
