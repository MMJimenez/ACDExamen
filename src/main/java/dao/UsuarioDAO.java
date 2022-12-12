package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.Usuario;

public class UsuarioDAO {
    
    private Connection connection;
    
    /* Completar consultas */
    static final String INSERT_QUERY ="INSERT INTO examen.usuario (nombre, apellidos, dni) VALUES (?, ?, ?);";
    static final String LIST_QUERY="SELECT * FROM examen.usuario;";
    static final String GET_BY_DNI="SELECT * FROM examen.usuario WHERE dni = ?;";


    private static final String USER = "newuser";
    private static final String PASSWORD = "newuser";
    private static final String URL = "jdbc:mysql://localhost:3306/examen";
    
    public void connect(){
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Base de datos conectada");
        }catch(Exception ex){
            System.out.println("Error al conectar a la base de datos");
            System.out.println(ex);
        }     
    }
    
    public void close(){
        try {
            connection.close();
        } catch (Exception ex) {
            System.out.println("Error al cerrar la base de datos");     
        }
    }
    
    public void save(Usuario user){
        /**
         * Completar código para guardar un usuario 
         * Este método no debe generar el id del usuario, ya que la base
         * de datos es la que lo genera.
         * Una vez obtenido el id del usuario tras la consulta sql,
         * hay que modificarlo en el objeto que se pasa como parámetro 
         * de forma que pase a tener el id correcto.
         */

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(INSERT_QUERY, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getNombre());
            preparedStatement.setString(2, user.getApellidos());
            preparedStatement.setString(3, user.getDni());
            preparedStatement.executeUpdate();

            ResultSet generatedId = preparedStatement.getGeneratedKeys();
            if (generatedId.next()) {
                user.setId(generatedId.getLong(1));
            }

            System.out.println("Introducido usuario correctamente con nuevo id: " + user.getId());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Usuario> list(){

        var salida = new ArrayList<Usuario>(0);
        
        /* Completar código para devolver un arraylist con todos los usuarios */

        try (var pst = connection.prepareStatement(LIST_QUERY)) {

            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                Usuario usuario = new Usuario();
                usuario.setId((long) resultSet.getInt("id"));
                usuario.setNombre(resultSet.getString("nombre"));
                usuario.setApellidos(resultSet.getString("apellidos"));
                usuario.setDni(resultSet.getString("dni"));

                salida.add(usuario);
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return salida;
    }    
    
    public Usuario getByDNI(String dni){
        
        Usuario salida = new Usuario();
        
        /**
         * Completar código para devolver el usuario que tenga ese dni.
         * Si no existe, se debe devolver null
         */

        try (var pst = connection.prepareStatement(GET_BY_DNI)) {

            pst.setString(1, dni);

            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                salida.setId((long) resultSet.getInt("id"));
                salida.setNombre(resultSet.getString("nombre"));
                salida.setApellidos(resultSet.getString("apellidos"));
                salida.setDni(resultSet.getString("dni"));

                return salida;
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return null;
    }    
}
