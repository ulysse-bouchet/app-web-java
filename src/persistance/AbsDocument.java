package persistance;

import mediatheque.items.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

public abstract class AbsDocument implements Document {
    private final int id;
    private final String title;
    private final String author;
    private Utilisateur currentUser;

    private final PreparedStatement updateUser;

    public AbsDocument(int id, String title, String author, Utilisateur currentUser, Connection conn) throws SQLException {
        this.id = id;
        this.title = title;
        this.author = author;
        this.currentUser = currentUser;
        updateUser = conn.prepareStatement("UPDATE DOCUMENT SET IDEMPRUNTEUR = ? WHERE ID = " + this.id);
    }

    @Override
    public void reserver(Utilisateur user) throws ReservationException {
        throw new ReservationException();
    }

    @Override
    public void emprunter(Utilisateur user) throws EmpruntException {
        if (user.isBibliothecaire() || currentUser != null)
            throw new EmpruntException();
        try {
            synchronized (this) {
                updateUser.setInt(1, (Integer) user.data()[0]);
                updateUser.executeQuery();
                currentUser = user;
            }
        } catch (SQLException e) {
            throw new EmpruntException();
        }
    }

    @Override
    public void rendre(Utilisateur user) throws RetourException {
        if (user.isBibliothecaire())
            throw new RetourException();
        try {
            synchronized (this) {
                updateUser.setNull(1, Types.INTEGER);
                updateUser.executeQuery();
                currentUser = null;
            }
        } catch (SQLException e) {
            throw new RetourException();
        }
    }

    @Override
    public Object[] data() {
        return new Object[]{toDisplay(), id, title, author, currentUser};
    }

    protected String toDisplay() {
        return "#" + id + " " + title + ", " + author;
    }
}
