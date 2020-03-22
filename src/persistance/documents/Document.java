package persistance.documents;

import mediatheque.items.*;
import persistance.DBConnect;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

/**
 * Represents an abstraction of a Document
 * @author Bouchet Ulysse & Tadjer Badr
 * @see mediatheque.items.Document
 */
public abstract class Document implements mediatheque.items.Document {
    private final int id;
    private final String title;
    private final String author;
    private Utilisateur currentBorrower;

    //The connection to the database
    private Connection conn;

    //The query to update the current borrower
    private PreparedStatement updateUser;

    /**
     * Document's constructor
     * @param id The document's id
     * @param title The document's title
     * @param author The document's author
     * @param currentBorrower The document's current borrower
     */
    public Document(int id, String title, String author, Utilisateur currentBorrower) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.currentBorrower = currentBorrower;

        //Initializes the connection to the database
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

                //Initializes the prepared statement
                updateUser = conn.prepareStatement("UPDATE DOCUMENT SET IDBORROWER = ? WHERE ID = " + this.id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Tries to book the document
     * @param user The user who wants to book the document
     * @throws ReservationException every time it is called, as it is not featured by the app yet
     */
    @Override
    public void reserver(Utilisateur user) throws ReservationException {
        throw new ReservationException();
    }

    /**
     * Tries to borrow the document
     * @param user The user who wants to borrow the document
     * @throws EmpruntException If the document cannot be borrowed by the user
     */
    @Override
    public void emprunter(Utilisateur user) throws EmpruntException {
        //If the user is a Librarian or if the document is already borrowed, it cannot be borrowed at the moment
        if (user.isBibliothecaire() || currentBorrower != null)
            throw new EmpruntException();

        try {
            //Prepares the statement
            prepare();

            //Synchronizes this document, to prevent from multiple simultaneous accesses to it and to the DB
            synchronized (this) {
                //Sets the query's parameter
                updateUser.setInt(1, (Integer) user.data()[0]);

                //Executes the query
                updateUser.executeQuery();

                //Changes the borrower
                currentBorrower = user;
            }
        } catch (SQLException e) {
            throw new EmpruntException();
        }
    }

    /**
     * Tries to return the document
     * @param user The user who wants to return the document
     * @throws RetourException If the document cannot be returned by the user
     */
    @Override
    public void rendre(Utilisateur user) throws RetourException {
        //If the user is a Librarian, it shouldn't have borrowed the doc, and can't return it neither
        if (user.isBibliothecaire())
            throw new RetourException();

        try {
            //Prepares the statement
            prepare();

            //Synchronizes this document, to prevent from multiple simultaneous accesses to it and to the DB
            synchronized (this) {
                //Sets the query's parameter
                updateUser.setNull(1, Types.INTEGER);

                //Executes the query
                updateUser.executeQuery();

                //Sets the borrower to null
                currentBorrower = null;
            }
        } catch (SQLException e) {
            throw new RetourException();
        }
    }

    /**
     * Gets datas about the document
     * data[0] : the document's id
     * data[1] : the document's title
     * data[2] : the document's author
     * data[3] : the document's currentBorrower
     * @return An array containing datas about the user
     */
    @Override
    public Object[] data() {
        return new Object[]{id, title, author, currentBorrower};
    }

    /**
     * Get a String representation of the document
     * ex : #3 L'Odyssée, Homère
     * @return a String representation of the document
     */
    @Override
    public String toString() {
        return "#" + id + " " + title + ", " + author;
    }
}
