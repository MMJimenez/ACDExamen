package dao;

import java.util.*;

import models.Ejemplar;
import models.Libro;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 *
 * @author FranciscoRomeroGuill
 */
public class BibliotecaDAO {
    
    private static SessionFactory sessionFactory;
    
    static{
        try{

            /* Completar conexión con hibernate */
            sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
        }catch(Exception ex){
            System.out.println("Error iniciando Hibernate");
            System.out.println(ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public void saveLibro( Libro e ){
        
        /* Guarda un libro con todos sus ejemplares en la base de datos */
        var session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(e);
        transaction.commit();

        session.close();
    }
  
    public HashSet<Libro> findByEstado(String estado){
        
        HashSet<Libro> salida = new HashSet<Libro>();
        /* 
         Devuelve el conjunto de libros que tenga el estado indicado      
        */
        var session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Ejemplar WHERE estado LIKE :estado")
                .setParameter("estado", estado);

        List<Ejemplar> ejemplares = query.getResultList();

        for (int i = 0; i < ejemplares.size(); i++) {
            salida.add(ejemplares.get(i).getLibro());
        }
        session.close();
        return salida;
    }
    
    public void printInfo(){
        
        /* 
          Muestra por consola todos los libros de la biblioteca y el número
          de ejemplares disponibles de cada uno.
          
          Debe imprimirlo de esta manera:
        
          Biblioteca
          ----------
          Como aprender java en 24h. (3)
          Como ser buena persona (1)
          Aprobando exámenes fácilmente (5)
          ...
        
        */
        var session = sessionFactory.openSession();
        Query query = session.createQuery("FROM Libro");
        List<Libro> libros = query.getResultList();

        HashSet<Libro> conjuntoLibros = new HashSet<>(libros);
        Iterator<Libro> iterator = conjuntoLibros.iterator();

        while (iterator.hasNext()){
            System.out.println(iterator.next().getTitulo() + " (" +
                    iterator.next().getEjemplares().size() + ")");
        }
        session.close();
    }
    
}
