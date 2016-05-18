package br.com.camiloporto.marmitex.android.provider.service;

import br.com.camiloporto.marmitex.android.model.Menu;
import br.com.camiloporto.marmitex.android.model.MenuCategory;
import br.com.camiloporto.marmitex.android.model.Seller;
import br.com.camiloporto.marmitex.android.model.MenuOption;

/**
 * Created by camiloporto on 17/11/15.
 */
public class MarmitariaBuilder {

    private Seller marmitaria;

    public MarmitariaBuilder newMarmitaria(String nomeMarmitaria, String telefone, String endereco) {
        marmitaria = new Seller(nomeMarmitaria, telefone, endereco);
        return this;
    }

    public CardapioBuilder criaCardapio(String nomeCardapio) {
        CardapioBuilder b = new CardapioBuilder();
        b.criaCardapio(nomeCardapio);
        return b;
    }

    public Seller getMarmitaria() {
        return marmitaria;
    }

    public class CardapioBuilder {

        Menu cardapio;

        public CardapioBuilder() {
            cardapio = new Menu("Name");
        }

        public CardapioBuilder criaCardapio(String nomeCardapio) {
            cardapio.setName(nomeCardapio);
            return this;
        }

        public GrupoItemsBuilder adicioneGrupo(String nomeGrupo) {
            GrupoItemsBuilder b = new GrupoItemsBuilder();
            b.adicioneGrupo(nomeGrupo);
            return b;
        }

        public MarmitariaBuilder okCardapio() {
            marmitaria.saveCardapio(cardapio);
            return MarmitariaBuilder.this;
        }


        public class GrupoItemsBuilder {

            private MenuCategory grupoItems;

            public GrupoItemsBuilder() {
                this.grupoItems = new MenuCategory("Name");
            }

            public GrupoItemsBuilder adicioneGrupo(String nomeGrupo) {
                grupoItems.setName(nomeGrupo);
                return this;
            }

            public GrupoItemsBuilder comOpcao(String opcaoGrupo) {
                MenuOption i = new MenuOption("Name");
                grupoItems.adicioneOpcao(opcaoGrupo);
                return this;
            }

            public CardapioBuilder ok() {
                cardapio.adicionaGrupo(grupoItems);
                return CardapioBuilder.this;
            }
        }
    }


}
