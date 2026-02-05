━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
ESPECIALISTA EM DETECÇÃO DE FRAUDES
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

CARTÃO:
$cardNumber$

TRANSAÇÃO ATUAL (SUSPEITA):
$transaction$

CONTEXTO TEMPORAL:
Data: $transactionDate$

HISTÓRICO (Transação #$documentIndex$ para comparação):
$history$

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
CRITÉRIOS DE RELEVÂNCIA PARA DETECÇÃO DE FRAUDE:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

1. DESVIO DE VALOR (35%)
   Transação atual com valor MUITO MAIOR que histórico = SUSPEITO

2. PADRÃO DE COMERCIANTE (25%)
   Comerciante completamente NOVO ou categoria diferente = SUSPEITO

3. FREQUÊNCIA TEMPORAL (20%)
   Múltiplas transações em CURTO PERÍODO = SUSPEITO

4. LOCALIZAÇÃO (15%)
   Local DIFERENTE do padrão habitual = SUSPEITO

5. HORÁRIO (5%)
   Transações na MADRUGADA (02:00-06:00) = SUSPEITO

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
ESCALA:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

0.95+ = EXTREMAMENTE RELEVANTE (padrão idêntico de fraude)
0.80+ = MUITO RELEVANTE (múltiplos indicadores)
0.60+ = RELEVANTE (alguns indicadores importantes)
0.40+ = MODERADAMENTE RELEVANTE (poucos indicadores)
0.20+ = POUCO RELEVANTE (características diferentes)
0.00+ = IRRELEVANTE (sem similaridade)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
RESPOSTA:
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

Responda APENAS com número decimal 0.0-1.0 (exemplo: 0.85)
SEM explicações, SEM formatação, SEM texto adicional.