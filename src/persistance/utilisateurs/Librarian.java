package persistance.utilisateurs;

/**
 * Represents a Librarian, which is an User
 * @author Bouchet Ulysse & Tadjer Badr
 * @see mediatheque.items.Utilisateur
 * @see persistance.utilisateurs.User
 */
public class Librarian extends User {

    /**
     * Constructor for a librarian
     * @param id The librarian's id
     * @param name The librarian's name
     * @param age The librarian's age
     * @param login The librarian's login
     * @param password The librarian's password
     */
    public Librarian(int id, String name, int age, String login, String password) {
        super(id, name, age, login, password);
    }

    /**
     * Is the user a Librarian ?
     * @return true
     */
    @Override
    public boolean isBibliothecaire() {
        return true;
    }
}
