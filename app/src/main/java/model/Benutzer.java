package model;

public class Benutzer {
    private int anzVersuche;
    private String email;
    private boolean lokalSpeichern;
    private boolean aktiv;
    private String passwort;
    private String notizen;

    public Benutzer(int anzVersuche, String email, boolean lokalSpeichern, boolean aktiv, String passwort) {
        this.anzVersuche = anzVersuche;
        this.email = email;
        this.lokalSpeichern = lokalSpeichern;
        this.aktiv = aktiv;
        this.passwort = passwort;
        this.notizen = "";
    }

    public int getAnzVersuche() {
        return anzVersuche;
    }

    public void setAnzVersuche(int anzVersuche) {
        this.anzVersuche = anzVersuche;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isLokalSpeichern() {
        return lokalSpeichern;
    }

    public void setLokalSpeichern(boolean lokalSpeichern) {
        this.lokalSpeichern = lokalSpeichern;
    }

    public boolean isAktiv() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv = aktiv;
    }

    public String getPasswort() {
        return passwort;
    }

    public void setPasswort(String passwort) {
        this.passwort = passwort;
    }

    public String getNotizen() {
        return notizen;
    }

    public void setNotizen(String notizen) {
        this.notizen = notizen;
    }

    @Override
    public String toString() {
        return "Benutzer{" +
                "anzVersuche=" + anzVersuche +
                ", email='" + email + '\'' +
                ", lokalSpeichern=" + lokalSpeichern +
                ", aktiv=" + aktiv +
                ", passwort='" + passwort + '\'' +
                ", notizen='" + notizen + '\'' +
                '}';
    }
}
