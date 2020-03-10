package persistance.documents;

import mediatheque.items.Utilisateur;
import persistance.AbsDocument;

import java.sql.Connection;
import java.sql.SQLException;

public class Livre extends AbsDocument {
    public Livre (int id, String title, String author, Utilisateur currentUser, Connection conn) throws SQLException {
        super(id, title, author, currentUser, conn);
    }

    @Override
    protected String toDisplay() {
        return "[Livre] " + super.toDisplay();
    }
}
