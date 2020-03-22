package persistance;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

import mediatheque.*;
import mediatheque.items.*;

import persistance.documents.Movie;
import persistance.documents.Book;

import persistance.utilisateurs.Subscriber;
import persistance.utilisateurs.Librarian;

import javax.annotation.Resource;

/**
 * Singleton class which injects itself to the 'Mediatheque'
 */
@Resource
public class LibraryData implements PersistentMediatheque {
    //Types of documents
    public static final int TYPE_BOOK = 0;
    public static final int TYPE_DVD = 1;

    //Users roles
    public static final int ROLE_SUB = 0;
    public static final int ROLE_LIB = 1;

    //Application caches for documents and users
    private final List<Document> documentsCache;
    private final List<Utilisateur> usersCache;

    private Connection conn;

    //Prepared statements for SELECT queries
    private PreparedStatement getAllDocuments;
    private PreparedStatement getUserByLoginAndPassword;
    private PreparedStatement getUserById;
    private PreparedStatement getDocument;

    //Prepared statements for INSERT queries
    private PreparedStatement insertLivre;
    private PreparedStatement insertDVD;

    // Jean-François Brette 01/01/2018
    static {
        Mediatheque.getInstance().setData(new LibraryData());
    }

    /**
     * Default and private constructor for the class
     */
    private LibraryData() {
        //Initializes the caches
        usersCache = new LinkedList<>();
        documentsCache = new LinkedList<>();

        //Initializes all the prepared statements
        prepare();
    }

    /**
     * Initializes all the prepared statements
     * @see persistance.DBConnect
     */
    private void prepare() {
        try {
            //If the connection is closed or has not been initialized
            if (conn == null || conn.isClosed()) {
                //Gets the instance of the connection to the database
                conn = DBConnect.connect();

                //Initializes all the prepared statements
                getAllDocuments = conn.prepareStatement("SELECT * FROM DOCUMENT ORDER BY ID");
                getUserByLoginAndPassword = conn.prepareStatement("SELECT * FROM TABLE_USER WHERE LOGIN = ? AND PASSWORD = ?");
                getUserById = conn.prepareStatement("SELECT * FROM TABLE_USER WHERE ID = ?");
                getDocument = conn.prepareStatement("SELECT * FROM DOCUMENT WHERE ID = ?");
                insertLivre = conn.prepareStatement("INSERT INTO DOCUMENT(ID, TYPE, TITLE, AUTHOR) VALUES (SEQ_DOC.nextval, ?, ?, ?)");
                insertDVD = conn.prepareStatement("INSERT INTO DOCUMENT(ID, TYPE, TITLE, AUTHOR, AGEMIN) VALUES (SEQ_DOC.nextval, ?, ?, ?, ?)");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all the documents of the database
     * @return all the documents
     * @see mediatheque.items.Document
     * @see persistance.documents.Document
     * @see persistance.documents.Book
     * @see persistance.documents.Movie
     */
    @Override
    public List<Document> tousLesDocuments() {
        //Initializes a List for the documents
        List<Document> documents = new LinkedList<>();
        try {
            //Prepares the connection to the DB
            prepare();

            //Executes the query and saves the response in a ResultSet
            ResultSet results = getAllDocuments.executeQuery();

            //While there are more results
            while (results.next()) {
                //Gets the values that are common to all the documents
                int id = results.getInt("id");
                String title = results.getString("title");
                String author = results.getString("author");
                Utilisateur user = getUser(results.getInt("idBorrower"));

                //Act in function of the document's type
                switch (results.getInt("type")) {
                    //If it is a book
                    case TYPE_BOOK:
                        //Adds it to the list
                        documents.add(new Book(id, title, author, user));
                        break;
                    //If it is a movie
                    case TYPE_DVD:
                        //Gets its minimum age
                        int ageMin = results.getInt("ageMin");
                        //Adds it to the list
                        documents.add(new Movie(id, title, author, user, ageMin));
                        break;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Return the list of all the documents
        return documents;
    }

    /**
     * Gets an user from the database (or from the cache) that corresponds to the given logins and passwords
     * @param login The login of the user
     * @param password The password of the user
     * @return The user corresponding to the params, else null
     * @see mediatheque.items.Utilisateur
     * @see persistance.utilisateurs.User
     * @see persistance.utilisateurs.Librarian
     * @see persistance.utilisateurs.Subscriber
     */
    @Override
    public Utilisateur getUser (String login, String password) {
        //Tries to find the user in the cache
        Utilisateur u = usersCache.stream().filter (
                (user) -> user.data()[1].equals(login) && user.data()[2].equals(password)
        ).findFirst().orElse(null);

        //If it hasn't been found in the cache
        if (u == null) {
            try {
                //Prepares the connection to the DB
                prepare();

                //Sets the query's parameters
                getUserByLoginAndPassword.setString(1, login);
                getUserByLoginAndPassword.setString(2, password);

                //Executes the query and saves the response in a ResultSet
                ResultSet result = getUserByLoginAndPassword.executeQuery();

                //If the response is not empty
                if (result.next()) {
                    //Gets the values that are common to all the users
                    int id = result.getInt("id");
                    String name = result.getString("name");
                    int age = result.getInt("age");

                    //Act in function of the user's role
                    switch (result.getInt("role")) {
                        //If the user is a subscriber
                        case ROLE_SUB:
                            //Creates a Subscriber corresponding to the values
                            u = new Subscriber(id, name, age, login, password);
                            break;
                        //If the user is a librarian
                        case ROLE_LIB:
                            //Creates a Librarian corresponding to the values
                            u = new Librarian(id, name, age, login, password);
                            break;
                    }

                    //Adds the user to the cache
                    usersCache.add(u);

                    //If the cache is too big, remove its oldest item
                    if (usersCache.size() > 10) usersCache.remove(0);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //Returns the user (or null)
        return u;
    }

    /**
     * Gets the user corresponding to a given id
     * @param id The id of the user
     * @return The user corresponding to the id if it exists, else null
     * @see mediatheque.items.Utilisateur
     * @see persistance.utilisateurs.User
     * @see persistance.utilisateurs.Librarian
     * @see persistance.utilisateurs.Subscriber
     */
    private Utilisateur getUser (int id) {
        //Tries to find the user in the cache
        Utilisateur u = usersCache.stream().filter (
            (user) -> user.data()[0].equals(id)
        ).findFirst().orElse(null);

        //If it hasn't been found in the cache
        if (u == null) {
            try {
                //Prepares the connection to the DB
                prepare();

                //Sets the query's parameters
                getUserById.setInt(1, id);

                //Executes the query and saves the response in a ResultSet
                ResultSet result = getUserById.executeQuery();

                //If the response is not empty
                if (result.next()) {
                    //Gets the values that are common to all the users
                    String name = result.getString("name");
                    int age = result.getInt("age");
                    String login = result.getString("login");
                    String password = result.getString("password");

                    //Act in function of the user's role
                    switch (result.getInt("role")) {
                        //If the user is a subscriber
                        case ROLE_SUB:
                            //Creates a Subscriber corresponding to the values
                            u = new Subscriber(id, name, age, login, password);
                            break;
                        //If the user is a librarian
                        case ROLE_LIB:
                            //Creates a Librarian corresponding to the values
                            u = new Librarian(id, name, age, login, password);
                            break;
                    }

                    //Adds the user to the cache
                    usersCache.add(u);

                    //If the cache is too big, remove its oldest item
                    if (usersCache.size() > 10) usersCache.remove(0);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //Returns the user (or null)
        return u;
    }

    /**
     * Gets the document corresponding to a given id
     * @param numDocument The id of the document
     * @return The document corresponding to the id if it exists, else null
     * @see mediatheque.items.Document
     * @see persistance.documents.Document
     * @see persistance.documents.Book
     * @see persistance.documents.Movie
     */
    @Override
    public Document getDocument(int numDocument) {
        //Tries to find the document in the cache
        Document d = documentsCache.stream().filter(
                (document) -> document.data()[0].equals(numDocument)
        ).findFirst().orElse(null);

        //If it hasn't been found in the cache
        if (d == null) {
            try {
                //Prepares the connection to the DB
                prepare();

                //Sets the query's parameters
                getDocument.setInt(1, numDocument);

                //Executes the query and saves the response in a ResultSet
                ResultSet result = getDocument.executeQuery();

                //If the response is not empty
                if (result.next()) {
                    //Gets the values that are common to all the documents
                    String title = result.getString("title");
                    String author = result.getString("author");
                    Utilisateur user = getUser(result.getInt("idBorrower"));

                    //Act in function of the document's role
                    switch (result.getInt("type")) {
                        //If it is a book
                        case TYPE_BOOK:
                            //Adds it to the list
                            d = new Book(numDocument, title, author, user);
                            break;
                        //If it is a movie
                        case TYPE_DVD:
                            //Gets its minimum age
                            int ageMin = result.getInt("ageMin");
                            //Adds it to the list
                            d = new Movie(numDocument, title, author, user, ageMin);
                            break;
                    }

                    //Removes from the cache all the documents that are not borrowed :
                    // we assume that borrowed documents may be returned quickly, so we keep them
                    List<Document> toUnCache = new LinkedList<>();
                    for (Document doc : documentsCache)
                        if(doc.data()[3] == null) //data[3] is an instance of the user that possesses the document
                            toUnCache.add(doc);
                    documentsCache.removeAll(toUnCache);

                    //Adds the document to the cache
                    documentsCache.add(d);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //Returns the document if it exists, else null
        return d;
    }

    /**
     * Adds a new document to the database
     * @param type The type of the document (Book, Movie...)
     * @param args The datas of the document (Title, Author...)
     * @see mediatheque.items.Document
     * @see persistance.documents.Document
     * @see persistance.documents.Book
     * @see persistance.documents.Movie
     */
    @Override
    public void nouveauDocument(int type, Object... args) {
        try {
            //Prepares the connection to the DB
            prepare();

            //Act in function of the document's role
            switch (type) {
                //If it is a book
                case TYPE_BOOK:
                    //Sets the query's parameters
                    insertLivre.setInt(1, TYPE_BOOK);
                    insertLivre.setString(2, (String) args[0]);
                    insertLivre.setString(3, (String) args[1]);

                    //Executes the query
                    insertLivre.executeQuery();
                    break;
                //If it is a movie
                case TYPE_DVD:
                    //Sets the query's parameters
                    insertDVD.setInt(1, TYPE_DVD);
                    insertDVD.setString(2, (String) args[0]);
                    insertDVD.setString(3, (String) args[1]);
                    insertDVD.setInt(4, Integer.parseInt((String) args[2]));

                    //Executes the query
                    insertDVD.executeQuery();
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
