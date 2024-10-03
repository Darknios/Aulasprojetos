class PagamentoView:    
    def mostrar_resultado(self, metodo, valor):
        print(f'Pagamento de R$ {valor}, realizado com {metodo}')

    def mostrar_opcoes(self):
        print("Escolha uma forma de pagamento:")
        print("1 - Cartão de Crédito")
        print("2 - Pix")
        print("3 - PayPal")

    def Solicitar_valor(self):
        try:
            return float (input("Digite o valor a ser pago: "))
        except ValueError as e:
            print (f"Entrada inválida {e}" - 'Digite um valor numérico.')
            return self.Solicitar_valor()