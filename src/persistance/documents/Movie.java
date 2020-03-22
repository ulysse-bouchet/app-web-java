package persistance.documents;

import mediatheque.items.EmpruntException;
import mediatheque.items.ReservationException;
import mediatheque.items.Utilisateur;

/**
 * Represents a Movie, which is a Document
 * @author Bouchet Ulysse & Tadjer Badr
 * @see mediatheque.items.Document
 * @see persistance.documents.Document
 */
public class Movie extends Document {
    private final int ageMin;

    /**
     * Constructor for a movie
     * @param id The movie's id
     * @param title The movie's title
     * @param author The movie's author
     * @param currentUser The movie's currentUser
     * @param ageMin The movie's required age to be watched
     */
    public Movie(int id, String title, String author, Utilisateur currentUser, int ageMin) {
        super(id, title, author, currentUser);
        this.ageMin = ageMin;
    }

    /**
     * Tries to book the document
     * @param user The user who wants to book the document
     * @throws ReservationException every time it is called, as it is not featured by the app yet
     */
    @Override
    public void reserver(Utilisateur user) throws ReservationException {
        //If the user is too young, he cannot book the movie
        if ((int) user.data()[3] < ageMin)
            throw new ReservationException();
        super.reserver(user);
    }

    /**
     * Tries to borrow the document
     * @param user The user who wants to borrow the document
     * @throws EmpruntException If the document cannot be borrowed by the user
     */
    @Override
    public void emprunter(Utilisateur user) throws EmpruntException {
        //If the user is too young, he cannot borrow the movie
        if ((int) user.data()[3] < ageMin)
            throw new EmpruntException();
        super.emprunter(user);
    }

    /**
     * Get a String representation of the document
     * ex : [DVD] #3 L'Exorciste, je sais pas l'auteur | interdit aux moins de 18 ans
     * @return a String representation of the document
     */
    @Override
    public String toString() {
        return "[DVD] " + super.toString() + (ageMin != 0 ? " | interdit aux moins de " + ageMin + " ans" : "");
    }
}
