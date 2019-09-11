package com.example.autenticacionfirebaseyandroid.modelo;

public class Usuarios {
    /*4: Se crean los atributos para el registro*/
    private String numeroDocumento;
    private String nombreCompleto;
    private String edad;
    private String email;
    private String clave;


    /*5: Se crea los metodos getter y setter*/

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }


    /*6: nos vamos a la clase de registro*/
}
