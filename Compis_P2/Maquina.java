import java.awt.*;
import java.util.*;
import java.lang.reflect.*;

public class Maquina {
    Stack<Object> pila;
    Vector<Object> prog;
    static int pc = 0;
    int progbase = 0, numArchi = 0, x = 0, y = 0;
    boolean returning = false;
    Method metodo, metodos[];

    Class<Maquina> c;
    Graphics g;

    double angulo = 0;

    Class<Object> parames[];

    Maquina() {
    }

    Maquina(Graphics g) {
        this.g = g;
    }

    public void setGraphics(Graphics g) {
        this.g = g;
    }

    public Vector<Object> getProg() {
        return this.prog;
    }

    void initcode() {
        this.pila = new Stack<>();
        this.prog = new Vector<>();
    }

    Object pop() {
        return pila.pop();
    }

    int code(Object f) {
        System.out.println("Gen (" + f + ") size = " + prog.size());
        prog.addElement(f);
        return prog.size() - 1;
    }

    void execute(int p) {
        String inst;
        System.out.println("Progsize = " + prog.size());
        for (pc = 0; pc < prog.size(); pc = pc + 1) {
            System.out.println("Pc = " + pc + " Inst: " + prog.elementAt(pc));
        }
        for (pc = p; !(inst = (String) prog.elementAt(pc)).equals("STOP") && !returning;) {
            try {
                inst = (String) prog.elementAt(pc);
                pc = pc + 1;
                System.out.println("Pc = " + pc + " Inst: " + inst);
                c = Maquina.class;
                metodo = c.getDeclaredMethod(inst);
                metodo.invoke(this);
            } catch (NoSuchMethodException e) {
                System.out.println("No existe el metodo " + e);
            } catch (InvocationTargetException e) {
                System.out.println(e);
            } catch (IllegalAccessException e) {
                System.out.println(e);
            }
        }
    }

    void constpush() {
        Simbolo s;
        s = (Simbolo) prog.elementAt(pc);
        pc++;
        double v = s.val;
        pila.push(v);
    }

    void color() {
        Color colors[] = { Color.red, Color.green, Color.blue };
        double d1;
        d1 = ((Double) pila.pop()).doubleValue();
        if (g != null) {
            g.setColor(colors[(int) d1]);
        }

    }

    void line() {
        double d1;
        d1 = ((Double) pila.pop()).doubleValue();
        if (g != null) {
            int pos_x1 = x + 150;
            int pos_y1 = 150 - y;
            int pos_x2 = ((int) (x + d1 * Math.cos(angulo))) + 150;
            int pos_y2 = (150 - (int) (y + d1 * Math.sin(angulo)));
            Linea ln = new Linea(pos_x1, pos_y1, pos_x2, pos_y2);
            ln.dibujar(g);
        }
        x = (int) (x + d1 * Math.cos(angulo));
        y = (int) (y + d1 * Math.sin(angulo));
        System.out.println("Valores actualizados: x = " + x + " y = " + y + " d1 = " + d1);
    }

    void circulo() {
        double d1;
        d1 = ((Double) pila.pop()).doubleValue();
        if (g != null) {
            (new Circulo(x + 150, 150 - y, (int) d1)).dibujar(g);
        }
    }

}
