public class ListaEnlazada<T> {
    private Nodo primerNodo;
    private Nodo ultimoNodo;
    private int len;

    private class Nodo {
        private T valor;
        private Nodo siguiente;
        private Nodo anterior;

        public void cambiarSiguiente(Nodo nodo){
            this.siguiente = nodo;
        }

        public void cambiarAnterior(Nodo nodo){
            this.anterior = nodo;
        }

        public void cambiarValor(T elem){
            this.valor = elem;
        }

        public Nodo(T valor, Nodo anterior,Nodo siguiente){
            this.valor = valor;
            this.anterior = anterior;
            this.siguiente = siguiente;
        }
        public boolean equals(Nodo nodo){
                if ((this.valor == nodo.valor && this.anterior == nodo.anterior && this.siguiente == nodo.siguiente)){
                    return true;
                } else{
                    return false;
                }
            }
    }

    public ListaEnlazada() {
        this.len = 0;
    }

    public int longitud() {
        return this.len;
    }

    public void agregarAdelante(T elem) {
        if (longitud() == 0){
            Nodo nuevoNodo = new Nodo(elem, null, null);
            this.primerNodo = nuevoNodo;
            this.ultimoNodo = nuevoNodo;
            this.len++;
        } else {
            Nodo nuevoNodo = new Nodo(elem, null, this.primerNodo);
            this.primerNodo.cambiarAnterior(nuevoNodo);
            this.primerNodo = nuevoNodo;
            this.len++;
        }
    }

    public void agregarAtras(T elem) {
        if (longitud() == 0){
            Nodo nuevoNodo = new Nodo(elem, null, null);
            this.primerNodo = nuevoNodo;
            this.ultimoNodo = nuevoNodo;
            this.len++;
        } else {
            Nodo nuevoNodo = new Nodo(elem, this.ultimoNodo, null);
            this.ultimoNodo.cambiarSiguiente(nuevoNodo);
            this.ultimoNodo = nuevoNodo;
            this.len++;
        }
    }

    public T obtener(int i) {
        int j = 0;
        ListaIterador it = iterador();
        while (j < i) {
            it.siguiente();
            j++;
        }
        return it.siguiente();
    }

    public void eliminar(int i) {
        if (len == 1){
            this.len = 0;
            this.primerNodo = null;
            this.ultimoNodo = null;
            return;
        }
        int j = 0;
        ListaIterador it = iterador();
        if (i != 0 && i != longitud() - 1){
            while (j < i) {
            it.siguiente();
            j++;
        }
        Nodo nuevoAnterior = it.getNodo();
        it.siguiente();
        it.siguiente();
        Nodo nuevoSigiente = it.getNodo();
        nuevoAnterior.cambiarSiguiente(nuevoSigiente);
        nuevoSigiente.cambiarAnterior(nuevoAnterior);    
        } else if (i == 0){
            it.siguiente();
            it.siguiente();
            this.primerNodo = it.getNodo();
            it.getNodo().cambiarAnterior(null);
        } else if (i == longitud() - 1){
            while (j < i) {
            it.siguiente();
            j++;
        }
            this.ultimoNodo = it.getNodo();
            it.getNodo().cambiarSiguiente(null);
        }
        this.len--;
    }

    public void modificarPosicion(int indice, T elem) {
        int j = 0;
        ListaIterador it = iterador();
        while (j < indice + 1) {
            it.siguiente();
            j++;
        }
        it.getNodo().cambiarValor(elem);
        
    }

    public ListaEnlazada(ListaEnlazada<T> lista) {
        int i = 0;
        while (i < lista.longitud()) {
            this.agregarAtras(lista.obtener(i));
            i++;
        }
    }
    
    @Override
    public String toString() {
        throw new UnsupportedOperationException("No implementada aun");
    }

    public class ListaIterador{
    	private Nodo nodo;

        public ListaIterador(Nodo nodo){
            this.nodo = nodo;
        }

        public Nodo getNodo(){
            return this.nodo;
        }

        public boolean haySiguiente() {
	        if (this.nodo.siguiente != null){
                return true;
            } else{
                return false;
            }
        }
        
        public boolean hayAnterior() {
	        if (this.nodo.anterior != null){
                return true;
            } else{
                return false;
            }
        }

        public T siguiente() {
	        this.nodo = this.nodo.siguiente;
            return this.nodo.valor;
        }
        

        public T anterior() {
            T valor = this.nodo.valor;
	        this.nodo = this.nodo.anterior;
            return valor;
        }
    }

    public ListaIterador iterador() {
	    return new ListaIterador(new Nodo(null, null, primerNodo));
    }

}