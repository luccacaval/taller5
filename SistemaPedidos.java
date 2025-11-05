public class SistemaPedidos {
    private ABB pedidosABB;
    private ListaEnlazada pedidosPorOrdenDeLlegado;
    public SistemaPedidos(){
        this.pedidosABB = new ABB<Pedido>();
        this.pedidosPorOrdenDeLlegado = new ListaEnlazada<HandleABB>();
    }

    public void agregarPedido(Pedido pedido){
       HandleABB handleNuevoPedido = pedidosABB.insertar(pedido);
       pedidosPorOrdenDeLlegado.agregarAtras(handleNuevoPedido);
    }

    public Pedido proximoPedido(){
        HandleABB pedidoHandle = (HandleABB) pedidosPorOrdenDeLlegado.obtener(0);
        Pedido pedido = (Pedido) pedidoHandle.valor();
        pedidoHandle.eliminar();
        pedidosPorOrdenDeLlegado.eliminar(0);
        return pedido;
    }

    public Pedido pedidoMenorId(){
        return (Pedido) pedidosABB.minimo();
    }

    public String obtenerPedidosEnOrdenDeLlegada(){
        ListaIterador it = pedidosPorOrdenDeLlegado.iterador();
        String res = "[";
        while (it.haySiguiente()) {
            HandleABB pedidoActual = (HandleABB) it.siguiente();
            res = res.concat(pedidoActual.valor().toString());
            res = res.concat(", ");
        }
        res = res.concat("]");
        res = res.replace(", ]", "]");
        return res;
    }

    public String obtenerPedidosOrdenadosPorId(){
        return pedidosABB.toString();
    }
}