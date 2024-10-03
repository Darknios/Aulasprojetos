from abc import ABC, abstractmethod

class pagamento(ABC):
    @abstractmethod
    def pagar(self, valor):
        #Taxa x
        pass

def PagamentoCartaoCredito(Pagamento):
    def __init__(self):
        self.__taxa = 0.02 #Atributo privado - encapsulamento 
    
    def pagar(self, valor):
        total = valor + (valor * self.__taxa)
        print(f'Pagamento de R$ {total} com taxa de {self._taxa * 100} %')

def PagamentoPix(Pagamento):
     def pagar(self, valor):
        print(f'Pagamento de R$ {valor} feito com pix sem taxa!')


def PagamentoPayPal(Pagamento):
     def __init__(self):
        self.__taxa = 0.03 #Atributo privado - encapsulamento    
     def pagar(self, valor):
        total = valor + (valor * self.__taxa)
        print(f'Pagamento de R$ {valor} feito com paypal')