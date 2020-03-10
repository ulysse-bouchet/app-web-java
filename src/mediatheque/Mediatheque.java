package mediatheque;

import java.util.List;

import mediatheque.items.Document;
import mediatheque.items.EmpruntException;
import mediatheque.items.RetourException;
import mediatheque.items.Utilisateur;

public class Mediatheque {
    private static Mediatheque instance = new Mediatheque();
    private PersistentMediatheque data;

    public static Mediatheque getInstance() {
        return instance;
    }

    private Mediatheque() {
    }

    public void setData(PersistentMediatheque data) {
        if (this.data == null) {
            this.data = data;
        }
    }

    public void emprunter(Document d, Utilisateur a) throws EmpruntException {
        d.emprunter(a);
    }

    public void reserver(Document d, Utilisateur a) throws EmpruntException {
        d.emprunter(a);
    }

    public void rendre(Document d, Utilisateur a) throws RetourException {
        d.rendre(a);
    }

    public List<Document> tousLesDocuments() {
        return this.data.tousLesDocuments();
    }

    public Utilisateur getUser(String login, String password) {
        return this.data.getUser(login, password);
    }

    public Document getDocument(int numDocument) {
        return this.data.getDocument(numDocument);
    }

    public void nouveauDocument(int type, Object... args) {
        this.data.nouveauDocument(type, args);
    }
}
