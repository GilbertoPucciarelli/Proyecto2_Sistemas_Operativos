/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Gilberto
 */
public class Aplicacion {
    
        private int necesarios[][],
            asignados[][],
            maximos[][],
            disponibles[][],
            numeroProcesos,
            numeroRecursos;

    public Aplicacion(int[][] disponibles,int[][] maximos,int numeroRecursos,int numeroProcesos) {
       this.disponibles = disponibles;
       this.maximos = maximos;
       this.numeroRecursos = numeroRecursos;
       this.numeroProcesos = numeroProcesos;
        for (int i = 0; i < this.numeroProcesos; i++) {
            for (int j = 0; j < this.numeroRecursos; j++) {
                this.asignados[i][j] = 0;
            }
            
        }
    }
    
    private int[][] calculoNecesarios() {
        for (int i = 0; i < this.numeroProcesos; i++) {
            for (int j = 0; j < this.numeroRecursos; j++) //calculando matriz de necesarios
            {
                this.necesarios[i][j] = this.maximos[i][j] - this.asignados[i][j];
            }
        }

        return this.necesarios;
    }

    private boolean chequear(int i) {
        //chequeando si todos los recursos para el proceso pueden ser asignados
        for (int j = 0; j < this.numeroRecursos; j++) {
            if (this.disponibles[0][j] < this.necesarios[i][j]) {
                return false;
            }
        }

        return true;
    }

    public void esSeguro() {
        calculoNecesarios();
        boolean done[] = new boolean[this.numeroProcesos];
        int j = 0;

        while (j < this.numeroProcesos) {  //hasta que todos los procesos se asignen
            boolean asignado = false;
            for (int i = 0; i < this.numeroProcesos; i++) {
                if (!done[i] && chequear(i)) {  //intentando asignar
                    for (int k = 0; k < this.numeroRecursos; k++) {
                        this.disponibles[0][k] = this.disponibles[0][k] - this.necesarios[i][k] + this.maximos[i][k];
                    }
                    System.out.println("Proceso asignado : " + i);
                    asignado = done[i] = true;
                    j++;
                }
            }
            if (!asignado) {
                break;  //si no esta asignado
            }
        }
        if (j == this.numeroProcesos) //si todos los procesos estan asignados
        {
            System.out.println("\nAsignado de forma segura");
        } else {
            System.out.println("Todos los procesos se pueden asignar de forma segura");
        }
    }
    
}
