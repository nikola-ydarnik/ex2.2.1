package hiber.dao;

import hiber.model.User;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserById(String model, int series) {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery(
              "SELECT u from User u JOIN FETCH u.car c " +
                 "WHERE c.model = :model AND c.series = :series", User.class);
      query.setParameter("model", model);
      query.setParameter("series", series);
      return query.getResultStream().findFirst().orElse(null);
   }
}
