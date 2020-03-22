package persistance.utilisateurs;

import mediatheque.items.Utilisateur;

/**
 * Represents an abstraction of an User
 * @author Bouchet Ulysse & Tadjer Badr
 * @see mediatheque.items.Utilisateur
 */
public abstract class User implements Utilisateur {
    private final int id;
    private final String name;
    private final int age;

    private final String login;
    private final String password;

    /**
     * Constructor for an User
     * @param id The id of the user
     * @param name The name of the user
     * @param age The age of the user
     * @param login The login of the user
     * @param password The password of the user
     */
    public User(int id, String name, int age, String login, String password) {
        this.id = id;
        this.name = name;
        this.age = age;

        this.login = login;
        this.password = password;
    }

    /**
     * Gets the User's name
     * @return the user's name
     */
    @Override
    public String name() {
        return name;
    }

    /**
     * Gets datas about the user
     * data[0] : the user's id
     * data[1] : the user's login
     * data[2] : the user's password
     * data[3] : the user's age
     * @return An array containing datas about the user
     */
    @Override
    public Object[] data() {
        return new Object[]{id, login, password, age};
    }
}
