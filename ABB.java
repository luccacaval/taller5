package aed;

public class ABB<T extends Comparable<T>> {
    private Nodo raiz;

    private class Nodo {
        private Nodo padre;
        private Nodo nodoIzq;
        private Nodo nodoDer;
        private T valor;

        public Nodo(Nodo padre, T valor){
            this.padre = padre;
            this.valor = valor;
            this.nodoDer = null;
            this.nodoIzq = null;
        }
    }

    public class HandleABB implements Handle{

        @Override
        public Object valor() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'valor'");
        }

        @Override
        public void eliminar() {
            // TODO Auto-generated method stub
            throw new UnsupportedOperationException("Unimplemented method 'eliminar'");
        }
        /* ¡COMPLETAR! */
    }

    public ABB() {
        this.raiz = null;
    }

    public ABB(Nodo raiz){
        this.raiz = raiz;
    }

    public int cardinal() {
        if (this.raiz == null){
            return 0;
        } else{
            return 1 + new ABB<T>(this.raiz.nodoDer).cardinal() +  new ABB<T>(this.raiz.nodoIzq).cardinal();
        }
    }

    public T minimo(){
        Nodo nodoActual = this.raiz;
        Nodo nodoAnterior = null;
        while (nodoActual != null) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.nodoIzq;
        }
        return nodoAnterior.valor;
    }

    public T maximo(){
        Nodo nodoActual = this.raiz;
        Nodo nodoAnterior = null;
        while (nodoActual != null) {
            nodoAnterior = nodoActual;
            nodoActual = nodoActual.nodoDer;
        }
        return nodoAnterior.valor;
    }

    public HandleABB insertar(T elem){
        throw new UnsupportedOperationException("No implementado aún");
    }

    public boolean pertenece(T elem){
        Nodo nodoActual = this.raiz;
        while (nodoActual != null) {
            if (elem.compareTo(nodoActual.valor) == 0){
                return true;
            } else if (elem.compareTo(nodoActual.valor) < 0){
                nodoActual = nodoActual.nodoIzq;
            } else{
                nodoActual = nodoActual.nodoDer;
            }
        }
        return false;
    }

    private Nodo buscarNodo(T elem){
        Nodo nodoActual = this.raiz;
        while (nodoActual != null) {
            if (elem.compareTo(nodoActual.valor) == 0){
                return nodoActual;
            } else if (elem.compareTo(nodoActual.valor) < 0){
                nodoActual = nodoActual.nodoIzq;
            } else{
                nodoActual = nodoActual.nodoDer;
            }
        }
        return null;
    }

    public void eliminar(T elem){
        if (! pertenece(elem)){
            return;
        }
        Nodo nodoEliminar = buscarNodo(elem);
        if(nodoEliminar.padre == null){
            if (nodoEliminar.nodoDer == null && nodoEliminar.nodoIzq == null){
                this.raiz = null;
                return;
            }
            if (nodoEliminar.nodoDer == null){
               T nuevoValor = new ABB<>(nodoEliminar.nodoIzq).maximo();
               this.eliminar(nuevoValor);
               nodoEliminar.valor = nuevoValor;
            } else {
                T nuevoValor = new ABB<>(nodoEliminar.nodoDer).minimo();
               this.eliminar(nuevoValor);
               nodoEliminar.valor = nuevoValor;
            }
        }
        else if (nodoEliminar.nodoDer == null && nodoEliminar.nodoIzq == null){
            if (nodoEliminar.valor.compareTo(nodoEliminar.padre.valor) < 0){
                nodoEliminar.padre.nodoIzq = null;
            } else{
                nodoEliminar.padre.nodoDer = null;
            }
        } else if (nodoEliminar.nodoIzq != null && nodoEliminar.nodoDer == null){
            Nodo nodoEmpalmar = nodoEliminar.nodoIzq;
            if(nodoEliminar.valor.compareTo(nodoEliminar.padre.valor) < 0){
                nodoEliminar.padre.nodoIzq = nodoEmpalmar;
                nodoEmpalmar.padre = nodoEliminar.padre;
            } else{
                nodoEliminar.padre.nodoDer = nodoEmpalmar;
                nodoEmpalmar.padre = nodoEliminar.padre;
            }
        } else if (nodoEliminar.nodoIzq == null && nodoEliminar.nodoDer != null){
             Nodo nodoEmpalmar = nodoEliminar.nodoDer;
            if(nodoEliminar.valor.compareTo(nodoEliminar.padre.valor) < 0){
                nodoEliminar.padre.nodoIzq = nodoEmpalmar;
                nodoEmpalmar.padre = nodoEliminar.padre;
            } else{
                nodoEliminar.padre.nodoDer = nodoEmpalmar;
                nodoEmpalmar.padre = nodoEliminar.padre;
            }
        } else{
            T nuevoValor = new ABB<>(nodoEliminar.nodoIzq).maximo();
            this.eliminar(nuevoValor);
            nodoEliminar.valor = nuevoValor;
        }
    }

    public String toString(){
        String res = "{";
        if (this.raiz == null){
            return "{}";
        } else {
            ABB_Iterador it = this.iterador();
            while (it.haySiguiente()) {
               res = res.concat(it.siguiente().toString() + ',');
            }
            res = res.concat("}");
            res = res.replace(",}", "}");
        }
        return res; 
    }

    public class ABB_Iterador {
        private Nodo _actual;

        private ABB_Iterador(){
            _actual = buscarNodo(minimo());
        }

        public boolean haySiguiente() {            
            if (_actual != null){
                return true;
            } else {
                return false;
            }
        }
    
        public T siguiente() {
            T valorSiguiente = _actual.valor;
            if (_actual.nodoDer != null){
                ABB<T> subArbolDerecho = new ABB<T>(_actual.nodoDer);
                _actual = subArbolDerecho.buscarNodo(subArbolDerecho.minimo()); 
            } else {
                _actual = primerAncestroDerecho(_actual);
            }
            return valorSiguiente;
        }

        private Nodo primerAncestroDerecho(Nodo actual) {
            Nodo nodoActual = actual;
            boolean ancestroDerecho = false;
            Nodo ancestro = actual;
            while (nodoActual != null && !ancestroDerecho) {
                 ancestro = nodoActual.padre;
                 if (ancestro == null){
                    return null;
                 } 
                if (ancestro.valor.compareTo(nodoActual.valor) > 0){
                    ancestroDerecho = true;
                } else{
                    nodoActual = ancestro;
                }
            }
            return ancestro;
        }
    }

    public ABB_Iterador iterador() {
        return new ABB_Iterador();
    }

}
