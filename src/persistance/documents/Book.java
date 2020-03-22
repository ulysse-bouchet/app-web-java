package persistance.documents;

import mediatheque.items.Utilisateur;

/**
 * Represents a Book, which is a Document
 * @author Bouchet Ulysse & Tadjer Badr
 * @see mediatheque.items.Document
 * @see persistance.documents.Document
 */
public class Book extends Document {

    /**
     * Constructor for a book
     * @param id The book's id
     * @param title The book's title
     * @param author The book's author
     * @param currentUser The book's currentUser
     */
    public Book(int id, String title, String author, Utilisateur currentUser) {
        super(id, title, author, currentUser);
    }

    /**
     * Get a String representation of the document
     * ex : [Livre] #3 L'Odyssée, Homère
     * @return a String representation of the document
     */
    @Override
    public String toString() {
        return "[Livre] " + super.toString();
    }
}
