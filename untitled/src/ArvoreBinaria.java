import java.util.LinkedList;
import java.util.Queue;

public class ArvoreBinaria {
    private No raiz;

    public ArvoreBinaria() {
        this.raiz = null;
    }

    public No inserir(int valor) {
        No novoNo = new No(valor);
        if (this.raiz == null) {
            this.raiz = novoNo;
        } else {
            No atual = this.raiz;
            No pai = null;
            while (atual != null) {
                if (novoNo.getValor() < atual.getValor()) {
                    pai = atual;
                    atual = atual.getEsq();
                } else {
                    pai = atual;
                    atual = atual.getDir();
                }
            }
            if (novoNo.getValor() < pai.getValor()) {
                pai.setEsq(novoNo);
            } else {
                pai.setDir(novoNo);
            }
        }
        return novoNo;
    }

    public void preOrdem(No no) {
        if (no == null) {
            return;
        }
        System.out.println(no.getValor());
        preOrdem(no.getEsq());
        preOrdem(no.getDir());
    }

    public void emOrdem(No no) {
        if (no == null) {
            return;
        }
        emOrdem(no.getEsq());
        System.out.println(no.getValor());
        emOrdem(no.getDir());
    }

    public void posOrdem(No no) {
        if (no == null) {
            return;
        }
        posOrdem(no.getEsq());
        posOrdem(no.getDir());
        System.out.println(no.getValor());
    }

    public No getRaiz() {
        return this.raiz;
    }

    public No removerFolha(int valor) {
        if (this.raiz == null) {
            return null; // arvore vazia
        }

        No atual = this.raiz;
        No pai = null;

        while (atual != null && atual.getValor() != valor) {
            pai = atual;
            if (valor < atual.getValor()) {
                atual = atual.getEsq();
            } else {
                atual = atual.getDir();
            }
        }

        if (atual == null) {
            return null; // no nao existe
        }

        // verificando se o no e folha
        if (atual.getEsq() == null && atual.getDir() == null) {
            if (pai == null) {
                // se o no folha for a raiz
                this.raiz = null;
            } else if (pai.getEsq() == atual) {
                pai.setEsq(null);
            } else {
                pai.setDir(null);
            }
            return atual; // retorna o no removido
        }

        return null;
    }

    public void removerNoComUmFilho(int valor) {
        if (this.raiz == null) {
            return; // arvore vazia
        }

        No atual = this.raiz;
        No pai = null;

        // encontra o no e seu pai
        while (atual != null && atual.getValor() != valor) {
            pai = atual;
            if (valor < atual.getValor()) {
                atual = atual.getEsq();
            } else {
                atual = atual.getDir();
            }
        }

        if (atual == null) {
            return; // no nao existe
        }

        // verifica se tem so 1 unico filho
        if ((atual.getEsq() == null && atual.getDir() != null) || (atual.getEsq() != null && atual.getDir() == null)) {
            No filho = (atual.getEsq() != null) ? atual.getEsq() : atual.getDir();

            if (pai == null) {
                // o no é a raiz
                this.raiz = filho;
            } else if (pai.getEsq() == atual) {
                pai.setEsq(filho);
            } else {
                pai.setDir(filho);
            }
        }
    }

    public void removerNoComDoisFilhos(int valor) {
        if (this.raiz == null) {
            return; // arvore vazia
        }

        No atual = this.raiz;
        No pai = null;

        // encontrar o nó a ser removido e seu pai
        while (atual != null && atual.getValor() != valor) {
            pai = atual;
            if (valor < atual.getValor()) {
                atual = atual.getEsq();
            } else {
                atual = atual.getDir();
            }
        }

        if (atual == null) {
            return; // nó não encontrado
        }

        // verifica se o nó tem dois filhos
        if (atual.getEsq() != null && atual.getDir() != null) {
            // encontrar o sucessor (menor nó na subárvore direita)
            No sucessor = getSucessor(atual);

            if (sucessor != atual.getDir()) {
                No paiSucessor = atual;
                while (paiSucessor.getEsq() != sucessor) {
                    paiSucessor = paiSucessor.getEsq();
                }
                paiSucessor.setEsq(sucessor.getDir()); // ajusta a subárvore do pai do sucessor
                sucessor.setDir(atual.getDir()); // o sucessor assume a subárvore direita do nó atual
            }

            sucessor.setEsq(atual.getEsq()); // o sucessor assume a subárvore esquerda do nó atual

            if (pai == null) {
                this.raiz = sucessor; // o nó a ser removido é a raiz
            } else if (pai.getEsq() == atual) {
                pai.setEsq(sucessor);
            } else {
                pai.setDir(sucessor);
            }
        }
    }

    public void removerRaiz() {
        if (this.raiz == null) {
            return; // arvore vazia
        }

        // Caso 1: raiz folha
        if (this.raiz.getEsq() == null && this.raiz.getDir() == null) {
            this.raiz = null; // A raiz é removida e a árvore fica vazia
        }
        // Caso 2: 1 filho so
        else if (this.raiz.getEsq() == null) {
            this.raiz = this.raiz.getDir(); // substitui a raiz pelo filho da direita
        } else if (this.raiz.getDir() == null) {
            this.raiz = this.raiz.getEsq(); // substitui a raiz pelo filho da esquerda
        }
        // Caso 3: raiz com 2 filhos
        else {
            No sucessor = getSucessor(this.raiz);
            No paiSucessor = this.raiz;

            // o sucessor nao é o filho direito imediato da raiz
            if (sucessor != this.raiz.getDir()) {
                paiSucessor = this.raiz.getDir();
                while (paiSucessor.getEsq() != null && paiSucessor.getEsq() != sucessor) {
                    paiSucessor = paiSucessor.getEsq();
                }

                // o pai do sucessor agora aponta para o filho direito do sucessor (((se existir
                paiSucessor.setEsq(sucessor.getDir());

                // o sucessor assume a subárvore direita da raiz
                sucessor.setDir(this.raiz.getDir());
            }

            // o sucessor assume a subárvore esquerda da raiz
            sucessor.setEsq(this.raiz.getEsq());

            // Substitui a raiz pelo sucessor
            this.raiz = sucessor;
        }
    }

    private No getSucessor(No no) {
        No sucessor = no.getDir();
        No atual = sucessor.getEsq();

        while (atual != null) {
            sucessor = atual;
            atual = atual.getEsq();
        }

        return sucessor;
    }

    public void removerNo(int valor) {
        if (this.raiz == null) {
            return; // arvore vazia
        }

        No atual = this.raiz;
        No pai = null;

        // encontrando o no e seu pai
        while (atual != null && atual.getValor() != valor) {
            pai = atual;
            if (valor < atual.getValor()) {
                atual = atual.getEsq();
            } else {
                atual = atual.getDir();
            }
        }

        if (atual == null) {
            return; // no errado
        }

        // no raiz
        if (atual == this.raiz) {
            removerRaiz();
            return;
        }

        // verificando se o no é folha
        if (atual.getEsq() == null && atual.getDir() == null) {
            removerFolha(valor);
        }
        // verifica no filho unico
        else if ((atual.getEsq() == null && atual.getDir() != null) || (atual.getEsq() != null && atual.getDir() == null)) {
            removerNoComUmFilho(valor);
        }
        // verifica se tem dois filhos
        else {
            removerNoComDoisFilhos(valor);
        }
    }

    public void mostrarArvore() {
        if (raiz == null) {
            System.out.println("Árvore vazia.");
            return;
        }

        Queue<No> fila = new LinkedList<>();
        fila.add(raiz);

        int altura = altura(raiz);
        int maxNiveis = altura + 2;

        int espacosEntreNos = (int) Math.pow(2, maxNiveis) - 1;
        int espacosEntreNiveis = espacosEntreNos / 2;

        while (!fila.isEmpty()) {
            int nosNesseNivel = fila.size();
            boolean todosNull = true;

            for (int i = 0; i < nosNesseNivel; i++) {
                No atual = fila.poll();


                printEspacos(espacosEntreNiveis);

                if (atual != null) {
                    System.out.print(atual.getValor());
                    fila.add(atual.getEsq());
                    fila.add(atual.getDir());
                    todosNull = false;
                } else {
                    System.out.print(" ");
                    fila.add(null);
                    fila.add(null);
                }


                printEspacos(espacosEntreNos);
            }

            if (todosNull) {
                break;
            }

            System.out.println();

            espacosEntreNiveis /= 2;
            espacosEntreNos /= 2;
        }
    }

    private void printEspacos(int count) {
        for (int i = 0; i < count; i++) {
            System.out.print(" ");
        }
    }

    private int altura(No no) {
        if (no == null) {
            return -1;
        }
        return 1 + Math.max(altura(no.getEsq()), altura(no.getDir()));
    }

}