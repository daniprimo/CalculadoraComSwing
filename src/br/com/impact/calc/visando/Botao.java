package br.com.impact.calc.visando;

import javax.swing.*;
import java.awt.*;

public class Botao extends JButton {
    public Botao (String texto, Color cor) {
        setText(texto);
        setFont(new Font("courier",Font.PLAIN,25));
        setOpaque(true);
        setBackground(cor);
        setForeground(Color.white);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));


    }
}
