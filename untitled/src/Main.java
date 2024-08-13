public class Main {
    public static void main(String[] args) {
        ArvoreBinaria arvoreBinaria = new ArvoreBinaria();

        arvoreBinaria.inserir(80);
        arvoreBinaria.inserir(72);
        arvoreBinaria.inserir(87);
        arvoreBinaria.inserir(37);
        arvoreBinaria.inserir(-3);
        arvoreBinaria.inserir(12);
        arvoreBinaria.inserir(89);
        arvoreBinaria.inserir(83);
        arvoreBinaria.mostrarArvore();
        System.out.println();
        System.out.println();
        System.out.println();
        arvoreBinaria.removerNo(80);
        arvoreBinaria.mostrarArvore();
    }
}
