package mediatheque;

import java.util.List;
import mediatheque.items.Document;
import mediatheque.items.Utilisateur;

public interface PersistentMediatheque {
    List<Document> tousLesDocuments();

    Document getDocument(int var1);

    Utilisateur getUser(String var1, String var2);

    void nouveauDocument(int var1, Object... var2);
}