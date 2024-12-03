# language: pt
Funcionalidade: Pagamento

  Cenario: Criar pagamento com sucesso
    Quando criar um novo pagamento
    Entao deve retornar sucesso
    E deve retornar os dados do pagamento

  Cenario: Obter pagamento com sucesso
    Dado um pagamento que existe
    Quando obter o pagamento
    Entao deve retornar sucesso
    E deve retornar os dados do pagamento

  Cenario: Obter pagamento não existente
    Dado um pagamento que não existe
    Quando obter o pagamento
    Entao deve retornar não encontrado

  Cenario: Receber confirmação de pagamento
    Dado um pagamento que existe
    Quando receber a confirmação de pagamento
    Entao deve retornar sucesso
