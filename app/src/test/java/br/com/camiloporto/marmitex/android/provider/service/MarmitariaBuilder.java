package br.com.camiloporto.marmitex.android.provider.service;

import br.com.camiloporto.marmitex.android.model.Cardapio;
import br.com.camiloporto.marmitex.android.model.GrupoAlimentar;
import br.com.camiloporto.marmitex.android.model.ItemCardapio;
import br.com.camiloporto.marmitex.android.model.Marmitaria;

/**
 * Created by camiloporto on 17/11/15.
 */
public class MarmitariaBuilder {

    private Marmitaria marmitaria;

    public MarmitariaBuilder newMarmitaria(String nomeMarmitaria, String telefone, String endereco) {
        marmitaria = new Marmitaria(nomeMarmitaria, telefone, endereco);
        return this;
    }

    public CardapioBuilder criaCardapio(String nomeCardapio) {
        CardapioBuilder b = new CardapioBuilder();
        b.criaCardapio(nomeCardapio);
        return b;
    }

    public Marmitaria getMarmitaria() {
        return marmitaria;
    }

    public class CardapioBuilder {

        Cardapio cardapio;

        public CardapioBuilder() {
            cardapio = new Cardapio();
        }

        public CardapioBuilder criaCardapio(String nomeCardapio) {
            cardapio.setDescricao(nomeCardapio);
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

            private GrupoAlimentar grupoItems;

            public GrupoItemsBuilder() {
                this.grupoItems = new GrupoAlimentar();
            }

            public GrupoItemsBuilder adicioneGrupo(String nomeGrupo) {
                grupoItems.setDescricao(nomeGrupo);
                return this;
            }

            public GrupoItemsBuilder comOpcao(String opcaoGrupo) {
                ItemCardapio i = new ItemCardapio();
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
