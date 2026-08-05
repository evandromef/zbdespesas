# Requisitos — ZB Despesas

## Visão Geral

O ZB Despesas é um sistema web para registro e acompanhamento de despesas pessoais, com um agente de IA embutido que permite cadastrar e consultar gastos usando linguagem natural, além do formulário tradicional.

## Problema

O usuário precisa de uma forma rápida e simples de registrar seus gastos do dia a dia e visualizar quanto já gastou no mês corrente, sem depender de planilhas manuais.

## Usuário Final

Pessoa física controlando as próprias finanças pessoais.

## Escopo do MVP

**Dentro do escopo:**

1. Cadastro de despesa com data, valor, categoria e forma de pagamento (descrição opcional).
2. Tela inicial exibindo todas as despesas do mês corrente, com total em destaque.
3. Edição e exclusão de despesas cadastradas.
4. Agente de IA capaz de cadastrar, listar, editar, excluir despesas e gerar resumo de gastos via linguagem natural.
5. Filtro de despesas por categoria e forma de pagamento na tela inicial.

**Fora do escopo do MVP** (evoluções futuras):

- Gráficos de gastos por categoria/período
- Metas e orçamento mensal
- Importação de extrato bancário
- Multiusuário completo com permissões (o MVP prevê isolamento básico de dados por usuário, mas não perfis/times)
- Aplicativo mobile nativo

## Histórias de Usuário

1. **Como usuário**, quero cadastrar uma despesa rapidamente (via formulário ou frase em linguagem natural), **para** não esquecer de registrar um gasto assim que ele acontece.
2. **Como usuário**, quero ver todas as despesas do mês atual na tela inicial, **para** ter uma visão rápida do meu gasto mensal sem precisar buscar.
3. **Como usuário**, quero editar ou excluir uma despesa já cadastrada, **para** corrigir erros de lançamento.
4. **Como usuário**, quero perguntar ao agente de IA quanto gastei em uma categoria ou período, **para** entender meus hábitos de consumo sem precisar filtrar manualmente.
5. **Como usuário**, quero filtrar as despesas do mês por categoria ou forma de pagamento, **para** analisar um recorte específico dos meus gastos.

## Modelo de Dados de Referência

| Campo              | Tipo             | Obrigatório | Observação                                                        |
|--------------------|------------------|-------------| ----------------------------------------------------------------- |
| data               | date             | Sim         | Default: data atual                                               |
| valor              | decimal(10,2)    | Sim         | Sempre positivo                                                   |
| categoria          | enum             | Sim         | Alimentação, Transporte, Moradia, Saúde, Lazer, Educação, Outros  |
| forma_pagamento    | enum             | Sim         | Dinheiro, Débito, Crédito, Pix, Transferência                     |
| descricao          | text             | Não         | Campo livre                                                       |

## Limites do Agente de IA

O agente do ZB Despesas:

- **Pode:** cadastrar, listar, editar e excluir despesas do próprio usuário autenticado; gerar resumos e respostas sobre os gastos do usuário.
- **Não pode:** acessar despesas de outros usuários; executar qualquer ação fora do domínio de despesas pessoais; cadastrar despesa com valor zero ou negativo; salvar dados sem confirmação quando a extração da frase for ambígua.

## Métricas de Sucesso

- Tempo médio para cadastrar uma despesa via chat: menor que 15 segundos.
- Taxa de acerto do agente ao extrair data/valor/categoria/forma de pagamento de uma frase em linguagem natural sem precisar de correção manual: meta inicial de 80%+.
- Zero despesas cadastradas com valor inválido (zero, negativo ou não numérico).
- Tela inicial carrega o total e a lista do mês corrente corretamente em 100% dos casos, inclusive quando não há despesas cadastradas (estado vazio).

## Riscos

| Risco | Impacto | Mitigação |
| --- | --- | --- |
| Agente extrai categoria/forma de pagamento errada de frases ambíguas | Dado incorreto no histórico financeiro do usuário | Sempre confirmar com o usuário antes de salvar quando a frase for ambígua |
| Dados financeiros são sensíveis | Vazamento ou acesso cruzado entre usuários | Isolamento de dados por usuário desde o MVP, mesmo sem sistema de permissões completo |
| Custo de API do agente de IA crescer com o uso | Custo operacional | Monitorar custo por interação desde o início; considerar cache/validações locais antes de chamar o modelo quando possível |
| Usuário esperar funcionalidades fora do MVP (gráficos, metas) | Escopo aumentar antes do core estar validado | Deixar claro na interface que essas funcionalidades estão nos próximos passos, não no MVP |
