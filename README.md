# Ecos da Cidade

> Sistema colaborativo para registro, consulta e preservação da memória e do patrimônio cultural urbano.

## Sobre o projeto

O **Raízes da Cidade** é um projeto acadêmico desenvolvido no curso de **Engenharia de Software**, com o objetivo de aproximar a comunidade da preservação da memória e do patrimônio cultural de sua cidade.

A proposta consiste em uma plataforma colaborativa na qual moradores podem cadastrar patrimônios históricos e culturais, compartilhar relatos e memórias relacionados a esses locais e registrar denúncias sobre situações que possam comprometer sua conservação.

O projeto está relacionado à **ODS 11 – Cidades e Comunidades Sustentáveis**, contribuindo para a valorização e preservação do patrimônio cultural.

## Objetivos

- Incentivar a participação da comunidade na preservação do patrimônio local;
- Centralizar informações sobre patrimônios históricos e culturais;
- Permitir o compartilhamento de relatos e memórias;
- Registrar situações de abandono, risco ou descaracterização;
- Acompanhar o estado de conservação dos patrimônios;
- Facilitar a consulta das informações cadastradas.

## Funcionalidades

### Morador
- Cadastrar patrimônios;
- Consultar patrimônios;
- Compartilhar relatos e memórias;
- Registrar denúncias relacionadas aos patrimônios.

### Administrador
- Gerenciar patrimônios;
- Gerenciar relatos;
- Gerenciar denúncias;
- Alterar o status das denúncias;
- Remover registros quando necessário.

## Modelagem

O projeto utiliza conceitos de **Programação Orientada a Objetos** e **UML**, incluindo:

- Classes e objetos;
- Encapsulamento;
- Herança;
- Polimorfismo;
- Classe abstrata;
- Enumerações;
- Relacionamentos 1:N;
- Associação e composição.

A classe `Patrimonio` é abstrata e possui diferentes especializações, como `PredioHistorico`, `PracaHistorica`, `Monumento`, `ArvoreHistorica` e `CasaMarcante`.

O polimorfismo é aplicado por meio do método `gerarDescricao()`, permitindo que cada tipo de patrimônio apresente uma descrição de acordo com suas características.

## Estrutura do repositório

```text
Raizes-da-Cidade/
│
├── docs/
│   └── diagramas/
│
├── src/
│   └── ...
│
└── README.md