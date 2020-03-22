package persistance.utilisateurs;

/**
 * Represents a Subscriber, which is an User
 * @author Bouchet Ulysse & Tadjer Badr
 * @see mediatheque.items.Utilisateur
 * @see persistance.utilisateurs.User
 */
public class Subscriber extends User {

    /**
     * Constructor for a subscriber
     * @param id The subscriber's id
     * @param name The subscriber's name
     * @param age The subscriber's age
     * @param login The subscriber's login
     * @param password The subscriber's password
     */
    public Subscriber(int id, String name, int age, String login, String password) {
        super(id, name, age, login, password);
    }

    /**
     * Is the user a Librarian ?
     * @return false
     */
    @Override
    public boolean isBibliothecaire() {
        return false;
    }
}
