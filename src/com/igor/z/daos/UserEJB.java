package com.igor.z.daos;

import com.igor.z.entity.Group;
import com.igor.z.entity.User;
import com.igor.z.springutils.AuthenticationUtils;
import org.springframework.transaction.annotation.Transactional;

import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
public class UserEJB {

    @Transactional
    public User createUser(User user) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NuGetPu");
        EntityManager entityManager = emf.createEntityManager();
        Group group = new Group();
        group.setEmail(user.getEmail());
        group.setGroupname(Group.USERS_GROUP);
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.persist(group);
        entityManager.getTransaction().commit();
        return user;
    }

    public User findUserById(String id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NuGetPu");
        EntityManager em = emf.createEntityManager();
        TypedQuery<User> query = em.createNamedQuery("findUserById", User.class);
        query.setParameter("email", id);
        User user = null;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            // getSingleResult throws NoResultException in case there is no user in DB
            // ignore exception and return NULL for user instead
        }
        em.clear();
        em.close();
        emf.close();
        return user;
    }

}
