package br.com.impact.calc.modelo;


import java.util.ArrayList;
import java.util.List;

public class Memoria {

    private enum TipoCOmando {
      ZERA, SINAL,NUMERO, DIV, MULT, SUBM, SOMA, IGUAL, VIRGULA
    }
    private static final Memoria instancia = new Memoria();
    private final List<MemoriaObservador> observadores =
            new ArrayList<>();

    private TipoCOmando ultimaOPeracao = null;
    private boolean substituir = false;
    private String textoAtual = "";
    private String textoBuffer = "";

    private Memoria () {

    }
    public void adicionarObservador(MemoriaObservador o){
        observadores.add(o);
    }

    public static Memoria getInstancia() {
        return instancia;
    }

    public String getTextoAtual() {
        return textoAtual.isEmpty() ? "0": textoAtual;
    }

    public void processarComando(String valor){

        TipoCOmando tipocomando = detectarTipoComando(valor);
        System.out.println(tipocomando);

        if(tipocomando == null){
            return;
        }else if (tipocomando == TipoCOmando.ZERA) {
            textoAtual = "";
            textoBuffer = "";
            substituir = false;
            ultimaOPeracao = null;
        }else if (tipocomando == TipoCOmando.SINAL && !textoAtual.contains("-")) {
            textoAtual = "-" + textoAtual;
        }else if (tipocomando == TipoCOmando.SINAL && textoAtual.contains("-")) {
            textoAtual = textoAtual.substring(1);
        }else if (tipocomando == TipoCOmando.NUMERO
                || tipocomando == TipoCOmando.VIRGULA){
            textoAtual = substituir ? valor : textoAtual + valor;
            substituir = false;
        }else {
            substituir = true;
            textoAtual = obterResultadoOPeracao();
            textoBuffer = textoAtual;
            ultimaOPeracao = tipocomando;

        }


        observadores.forEach(o -> o.valorAlterado(getTextoAtual()));
    }

    private String obterResultadoOPeracao() {
        if (ultimaOPeracao == null
                || ultimaOPeracao == TipoCOmando.IGUAL){
            return textoAtual;
        }
        double numeroBUffer =
                Double.parseDouble(textoBuffer.replace(",","."));
        double numeroAtual =
                Double.parseDouble(textoAtual.replace(",","."));

                double resultado = 0;

                if (ultimaOPeracao == TipoCOmando.SOMA){
                    resultado = numeroBUffer + numeroAtual;
                }else if (ultimaOPeracao == TipoCOmando.SUBM){
                    resultado = numeroBUffer - numeroAtual;
                }else if (ultimaOPeracao == TipoCOmando.MULT){
                    resultado = numeroBUffer * numeroAtual;
                }else if (ultimaOPeracao == TipoCOmando.DIV){
                    resultado = numeroBUffer / numeroAtual;
                }

                String resultadoString = Double.toString(resultado).
                        replace(".",",");
                boolean inteiro = resultadoString.endsWith(",0");
        return inteiro ? resultadoString.replace(",0","") : resultadoString;
    }

    private TipoCOmando detectarTipoComando(String valor) {
        if (textoAtual.isEmpty() && valor == "0"){
            return null;
        }
        try {
            Integer.parseInt(valor);
            return TipoCOmando.NUMERO;
        }catch (NumberFormatException e){
            // quando não for numero...

            if("AC".equals(valor)){
                return  TipoCOmando.ZERA;
            }else if ("/".equals(valor)){
                return TipoCOmando.DIV;
            }else if ("*".equals(valor)){
                return TipoCOmando.MULT;
            }else if ("+".equals(valor)){
                return TipoCOmando.SOMA;
            }else if ("-".equals(valor)){
                return TipoCOmando.SUBM;
            }else if ("=".equals(valor)){
                return TipoCOmando.IGUAL;
            }else if (",".equals(valor) && !textoAtual.contains(",")){
                return TipoCOmando.VIRGULA;
            }else if ("±".equals(valor)){
                return TipoCOmando.SINAL;
            }

        }

        return null;
    }

}
