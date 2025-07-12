-- =====================================================
-- Script SQL para criar a tabela transactions
-- =====================================================

-- Criar sequência para o ID (PostgreSQL)
CREATE SEQUENCE IF NOT EXISTS transactions_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

-- Criar tabela transactions
CREATE TABLE IF NOT EXISTS transactions (
    id BIGINT PRIMARY KEY DEFAULT nextval('transactions_id_seq'),
    code VARCHAR(255) NOT NULL,
    customer_id VARCHAR(255) NOT NULL,
    card_number VARCHAR(20) NOT NULL,
    merchant_name VARCHAR(255) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    transaction_date TIMESTAMP NOT NULL,
    location VARCHAR(255),
    fraud_status VARCHAR(20),
    fraud_analysis TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criar índices para performance
CREATE INDEX IF NOT EXISTS idx_transactions_customer_code ON transactions(code);
CREATE INDEX IF NOT EXISTS idx_transactions_transaction_date ON transactions(transaction_date);
CREATE INDEX IF NOT EXISTS idx_transactions_fraud_status ON transactions(fraud_status);
CREATE INDEX IF NOT EXISTS idx_transactions_customer_date ON transactions(customer_id, transaction_date DESC);

-- Associar sequência à tabela
ALTER SEQUENCE transactions_id_seq OWNED BY transactions.id;