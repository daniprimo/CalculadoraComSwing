package br.com.impact.calc.visando;

import br.com.impact.calc.modelo.Memoria;
import br.com.impact.calc.modelo.MemoriaObservador;

import javax.swing.*;
import java.awt.*;

public class Display extends JPanel implements MemoriaObservador {

    private JLabel label ;
    public Display (){
        Memoria.getInstancia().adicionarObservador(this);

        setBackground(new Color(46, 49, 50));//cor do display
        label = new JLabel(Memoria.getInstancia().getTextoAtual());
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Serif",Font.PLAIN,23));
        //lcal onde vai fica direitta ou esquerda
        //ajustar local gerewnciado de layout
        setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 25));//acerta local do texto

        add(label);

    }


    @Override
    public void valorAlterado(String novoValor) {
        label.setText(novoValor);
    }
}
