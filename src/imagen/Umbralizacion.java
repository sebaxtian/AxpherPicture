/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package imagen;

/**
 * Esta clase permite calcular el umbral
 * para un histograma de alguna imagen,
 * esta clase tiene diferentes metodos
 * para realizar el calculo del umbral.
 * 
 * @author Juan Sebastian Rios Sabogal
 * @Fecha mar abr  3 14:15:51 COT 2012
 * @version 0.1
 */


public class Umbralizacion {
    
    //Atributos de clase
    private Histograma histograma;
    private int umbralGris;
    private int umbralR;
    private int umbralG;
    private int umbralB;
    
    public Umbralizacion(Histograma histograma, int metodo) {
        this.histograma = histograma;
        calcularUmbral(metodo);
    }
    
    private void calcularUmbral(int metodo) {
        if(metodo == 0) {
            metodoDosPicos();
        }
    }
    
    private void metodoDosPicos() {
        String formato = histograma.getFormato();
        if(formato.equals("P2")) {
            int histogramaGris[] = histograma.getHistogramaGris();
            int pixelesPico1 = histograma.getMaxNumPixelesGris();
            int nivelPico1 = histograma.getNivelDominanteGris();
            int pixelesPico2 = 0;
            int nivelPico2 = 0;
            //busca el segundo pico mas alto y lejano al pico 1
            int normalPicos[] = new int[histogramaGris.length];
            for(int n = 0; n < normalPicos.length; n++) {
                normalPicos[n] = (int)Math.pow((n - nivelPico1), 2) * histogramaGris[n];
            }
            for(int j = 0; j < normalPicos.length; j++) {
                if(normalPicos[j] > pixelesPico2) {
                    pixelesPico2 = normalPicos[j];
                    nivelPico2 = j;
                }
            }
            //busca el pico mas bajo entre los dos picos (umbral)
            int picoBajo = pixelesPico1+pixelesPico2;
            if(nivelPico1 > nivelPico2) {
                for(int i = nivelPico2; i < nivelPico1; i++) {
                    if(histogramaGris[i] < picoBajo) {
                        picoBajo = histogramaGris[i];
                        umbralGris = (short)i;
                    }
                }
            } else {
                for(int i = nivelPico1; i < nivelPico2; i++) {
                    if(histogramaGris[i] < picoBajo) {
                        picoBajo = histogramaGris[i];
                        umbralGris = (short)i;
                    }
                }
            }
        }
        if(formato.equals("P3")) {
            int histogramaR[] = histograma.getHistogramaR();
            int histogramaG[] = histograma.getHistogramaG();
            int histogramaB[] = histograma.getHistogramaB();
            //canal R
            int pixelesPico1R = histograma.getMaxNumPixelesR();
            int nivelPico1R = histograma.getNivelDominanteR();
            int pixelesPico2R = 0;
            int nivelPico2R = 0;
            //canal G
            int pixelesPico1G = histograma.getMaxNumPixelesG();
            int nivelPico1G = histograma.getNivelDominanteG();
            int pixelesPico2G = 0;
            int nivelPico2G = 0;
            //canal B
            int pixelesPico1B = histograma.getMaxNumPixelesB();
            int nivelPico1B = histograma.getNivelDominanteB();
            int pixelesPico2B = 0;
            int nivelPico2B = 0;
            //busca el segundo pico mas alto y lejano al pico 1 para cada canal RGB
            int normalPicosR[] = new int[histogramaR.length];
            int normalPicosG[] = new int[histogramaG.length];
            int normalPicosB[] = new int[histogramaB.length];
            for(int n = 0; n < normalPicosR.length; n++) {
                normalPicosR[n] = (int)Math.pow((n - nivelPico1R), 2) * histogramaR[n];
                normalPicosG[n] = (int)Math.pow((n - nivelPico1G), 2) * histogramaG[n];
                normalPicosB[n] = (int)Math.pow((n - nivelPico1B), 2) * histogramaB[n];
            }
            for(int j = 0; j < normalPicosR.length; j++) {
                //canal R
                if(normalPicosR[j] > pixelesPico2R) {
                    pixelesPico2R = normalPicosR[j];
                    nivelPico2R = j;
                }
                //canal G
                if(normalPicosG[j] > pixelesPico2G) {
                    pixelesPico2G = normalPicosG[j];
                    nivelPico2G = j;
                }
                //canal B
                if(normalPicosB[j] > pixelesPico2B) {
                    pixelesPico2B = normalPicosB[j];
                    nivelPico2B = j;
                }
            }
            //busca el pico mas bajo entre los dos picos (umbral) para cada canal RGB
            int picoBajoR = pixelesPico1R+pixelesPico2R;
            int picoBajoG = pixelesPico1G+pixelesPico2G;
            int picoBajoB = pixelesPico1B+pixelesPico2B;
            //canal R
            if(nivelPico1R > nivelPico2R) {
                for(int i = nivelPico2R; i < nivelPico1R; i++) {
                    if(histogramaR[i] < picoBajoR) {
                        picoBajoR = histogramaR[i];
                        umbralR = (short)i;
                    }
                }
            } else {
                for(int i = nivelPico1R; i < nivelPico2R; i++) {
                    if(histogramaR[i] < picoBajoR) {
                        picoBajoR = histogramaR[i];
                        umbralR = (short)i;
                    }
                }
            }
            //cana G
            if(nivelPico1G > nivelPico2G) {
                for(int i = nivelPico2G; i < nivelPico1G; i++) {
                    if(histogramaG[i] < picoBajoG) {
                        picoBajoG = histogramaG[i];
                        umbralG = (short)i;
                    }
                }
            } else {
                for(int i = nivelPico1G; i < nivelPico2G; i++) {
                    if(histogramaG[i] < picoBajoG) {
                        picoBajoG = histogramaG[i];
                        umbralG = (short)i;
                    }
                }
            }
            //canal B
            if(nivelPico1B > nivelPico2B) {
                for(int i = nivelPico2B; i < nivelPico1B; i++) {
                    if(histogramaB[i] < picoBajoB) {
                        picoBajoB = histogramaB[i];
                        umbralB = (short)i;
                    }
                }
            } else {
                for(int i = nivelPico1B; i < nivelPico2B; i++) {
                    if(histogramaB[i] < picoBajoB) {
                        picoBajoB = histogramaB[i];
                        umbralB = (short)i;
                    }
                }
            }
        }
    }

    public void metodoIsodata() {
        this.umbralGris = 0;
        int t = 0, PixelesFi = 0;
        int histogramaGris[] = this.histograma.getHistogramaGris();
        int Size = histogramaGris.length, TotalPixels = 0;
        int PixelsBackGround, PixelsForeground, TotalPixelsBg, TotalPixelsFg;

        for (int i = 0; i < Size; i++) {
            PixelesFi += i * histogramaGris[i];
            TotalPixels += histogramaGris[i];
        }
        this.umbralGris = PixelesFi / TotalPixels;

        while (true) {
            PixelsBackGround = 0;
            TotalPixelsBg = 0;
            PixelsForeground = 0;
            TotalPixelsFg = 0;

            for (int i = 0; i < this.umbralGris; i++) {
                PixelsBackGround += i * histogramaGris[i];
                TotalPixelsBg += histogramaGris[i];
            }
            PixelsForeground = PixelesFi - PixelsBackGround;
            TotalPixelsFg = TotalPixels - TotalPixelsBg;

            t = (PixelsBackGround / TotalPixelsBg) + (PixelsForeground / TotalPixelsFg);
            t /= 2;

            if (this.umbralGris == t) {
                break;
            } else {
                this.umbralGris = t;
            }
        }
    }
    
    
    public void metodoOtsu(){
        int histogramaGris[] = this.histograma.getHistogramaGris();
        int Size = histogramaGris.length;
        int total = 0;

        for (int i = 0; i < Size; i++) {
            total += histogramaGris[i];
        }

        float wb = 0, wf = 0;//frecuencias de background y foreground
        this.umbralGris = 0;//valor umbral
        double Maximo = 0, temp = 0, Sum = 0, sum = 0;
        double mb = 0, mf = 0;

        for (int i = 0; i < Size; i++) {
            Sum += i * histogramaGris[i];
        }
        for (int t = 0; t < Size; t++) {
            wb += histogramaGris[t];
            if (wb == 0) {
                continue;
            }


            wf = total - wb;
            if (wf == 0) {
                break;
            }


            sum += t * histogramaGris[t];

            mb = sum / wb;
            mf = (Sum - sum) / wf;

            temp = wb * wf * Math.pow(mb - mf, 2);

            if (temp > Maximo) {

                Maximo = temp;
                this.umbralGris = t;
            }
        }
    }
    
    
    public int getUmbralGris() {
        return umbralGris;
    }
    
    public int getUmbralR() {
        return umbralR;
    }
    
    public int getUmbralG() {
        return umbralG;
    }
    
    public int getUmbralB() {
        return umbralB;
    }
}
