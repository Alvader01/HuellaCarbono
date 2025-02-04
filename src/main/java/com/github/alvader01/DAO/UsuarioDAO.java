package com.github.alvader01.DAO;

import com.github.alvader01.Connection.Connection;
import com.github.alvader01.Entities.Usuario;
import com.github.alvader01.Utils.PasswordHasher;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.List;

public class UsuarioDAO {

    private final static String FINDUSERBYID = "FROM Usuario WHERE id = :id";
    private final static String FINDUSERBYEMAIL = "FROM Usuario WHERE email = :email";
    private final static String GETFACTOREMISION = "SELECT h.valor * c.factorEmision FROM Huella h JOIN Actividad a ON h.idActividad.id = a.id JOIN Categoria c ON a.idCategoria.id = c.id WHERE h.idUsuario.id = :idUsuario";


    public void insertUsuario(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        user.setContraseña(PasswordHasher.hashPassword(user.getContraseña()));
        session.persist(user);
        session.getTransaction().commit();
        session.close();
    }

    public List<BigDecimal> getFactorEmision(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(GETFACTOREMISION);
        query.setParameter("idUsuario", user.getId());
        List<BigDecimal> factorEmision = query.getResultList();
        session.close();
        return factorEmision;
    }

    public Usuario updateUsuario(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        if (user.getContraseña() != null) {
            user.setContraseña(PasswordHasher.hashPassword(user.getContraseña()));
        }
        session.update(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public Usuario deleteUsuario(Usuario user) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        session.beginTransaction();
        session.remove(user);
        session.getTransaction().commit();
        session.close();
        return user;
    }

    public Usuario findUserByID(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDUSERBYID);
        query.setParameter("id", usuario.getId());
        Usuario user = (Usuario) query.getSingleResult();
        session.close();
        return user;
    }


    public Usuario findUserByEmail(Usuario usuario) {
        Connection connection = Connection.getInstance();
        Session session = connection.getSession();
        Query query = session.createQuery(FINDUSERBYEMAIL);
        query.setParameter("email", usuario.getEmail());
        try {
            Usuario user = (Usuario) query.getSingleResult();
            return user;
        } catch (jakarta.persistence.NoResultException e) {
            return null;
        } finally {
            session.close();
        }
    }
}
