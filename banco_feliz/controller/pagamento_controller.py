
from model.pagamento import PagamentoCartaoCredito, PagamentoPix, PagamentoPayPal
from view.pagamento_view import PagamentoView

class PagamentoController:
    def __init__(self):
        self.view = PagamentoView()

    def processar_pagamento(self):
        self.view.mostrar_opcoes()
        escolha = input ('Digite a opcao desejada')


        valor = self.view.Solicitar_valor()

        if escolha == '1':
            metodo = PagamentoCartaoCredito()
            metodo_pagamento = 'Cartão de Crédito'
        elif escolha == '2':
            metodo = PagamentoPix()
            metodo_pagamento = 'Pix'
        elif escolha == '3':
            metodo = PagamentoPayPal()
            metodo_pagamento = 'PayPal'
        else:
            print('Opcao invalida!')
            return
        
        metodo.pagar(valor) # Digitar um valor
        self.view.mostrar_resultado(metodo_pagamento, valor)
        