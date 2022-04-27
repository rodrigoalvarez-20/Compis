class Simbolo {
    String nombre;
    double val;
    int defn;
    Simbolo sig;
    public String metodo;
    public short tipo;

    Simbolo(String s, short t, double d) {
        nombre = s;
        tipo = t;
        val = d;
    }

    public Simbolo obtenSig() {
        return sig;
    }

    public void ponSig(Simbolo s) {
        sig = s;
    }

    public String obtenNombre() {
        return nombre;
    }
}