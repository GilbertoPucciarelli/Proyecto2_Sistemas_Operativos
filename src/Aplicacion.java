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

    //ATRIBUTOS
    private int necesarios[][], asignados[][], maximos[][], disponibles[][], disponibles2[][], numeroProcesos, numeroRecursos;
    Interfaz2 app;

    //CONSTRUCTOR
    public Aplicacion(int[][] disponibles, int[][] maximos, int numeroRecursos, int numeroProcesos, Interfaz2 interfaz) {

        this.app = interfaz;
        this.necesarios = new int[numeroProcesos][numeroRecursos];
        this.maximos = new int[numeroProcesos][numeroRecursos];
        this.asignados = new int[numeroProcesos][numeroRecursos];
        this.disponibles = new int[1][numeroRecursos];
        this.disponibles = disponibles;
        this.disponibles2 = new int[1][numeroRecursos];
        this.maximos = maximos;
        this.numeroRecursos = numeroRecursos;
        this.numeroProcesos = numeroProcesos;
    }

    private void asignacion() {

        System.out.println("Matriz Asignados");

        for (int i = 0; i < this.numeroProcesos; i++) {
            for (int j = 0; j < this.numeroRecursos; j++) {

                int numero = (int) (Math.random() * this.maximos[i][j]);

                if (numero <= disponibles[0][j]) {

                    this.asignados[i][j] = numero;
                    this.disponibles[0][j] = this.disponibles[0][j] - numero;
                    this.disponibles2[0][j] = this.disponibles[0][j];
                    System.out.print(this.asignados[i][j] + " ");
                } else {

                    this.asignados[i][j] = 0;
                    System.out.print(this.asignados[i][j] + " ");
                }

            }
            System.out.println();
        }
    }

    //FUNCION QUE CALCULA LA MATRIZ DE NECESARIOS
    private int[][] calcularMatrizNecesarios() {

        for (int i = 0; i < this.numeroProcesos; i++) {
            for (int j = 0; j < this.numeroRecursos; j++) //MATRIZ DE NECESARIOS
            {
                this.necesarios[i][j] = this.maximos[i][j] - this.asignados[i][j];
            }
        }
        return this.necesarios;
    }

    //FUNCION QUE REVISA SI LOS RECURSOS PUEDEN SER ASIGNADOS A UN PROCESO
    private boolean revisar(int i) {

        for (int j = 0; j < this.numeroRecursos; j++) {
            if (this.disponibles[0][j] < this.necesarios[i][j]) {
                return false;
            }
        }

        return true;
    }

    //FUNCION QUE DEVUELVE EL ESTADO SEGURO
    public void esSeguro() {

        asignacion();
        calcularMatrizNecesarios();
        boolean done[] = new boolean[this.numeroProcesos];
        int j = 0;

        while (j < this.numeroProcesos) {
            boolean asignado = false;
            for (int i = 0; i < this.numeroProcesos; i++) {
                if (!done[i] && revisar(i)) {  //REVISAR SI SE PUEDEN ASIGNAR LOS RECURSOS
                    for (int k = 0; k < this.numeroRecursos; k++) {
                        this.disponibles[0][k] = this.disponibles[0][k] - this.necesarios[i][k] + this.maximos[i][k];
                    }
                    System.out.println("Proceso asignado : " + i);
                    this.app.cambiar(i);
                    asignado = done[i] = true;
                    j++;
                }
            }
            if (!asignado) {
                break;  //NO ESTA ASIGNADO
            }
        }

        if (j == this.numeroProcesos) //si todos los procesos estan asignados
        {
            System.out.println("\nAsignado de forma segura");
        } else {
            System.out.println("No todos los procesos se pueden asignar de forma segura");
        }

        Interfaz3 app2 = new Interfaz3(this.asignados, this.necesarios, this.numeroProcesos, this.numeroRecursos, this.disponibles2);
        app2.setVisible(true);
        this.app.cambiar2();
    }

}
