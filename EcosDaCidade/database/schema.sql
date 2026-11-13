-- ============ USUÁRIO (morador e administrador na mesma tabela) ============
CREATE TABLE usuario (
    id             NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tipo           VARCHAR2(15)  NOT NULL,
    nome           VARCHAR2(100) NOT NULL,
    email          VARCHAR2(150) NOT NULL UNIQUE,
    senha          VARCHAR2(100) NOT NULL,
    data_cadastro  DATE DEFAULT SYSDATE NOT NULL,
    pontuacao      NUMBER DEFAULT 0 NOT NULL,
    CONSTRAINT ck_usuario_tipo CHECK (tipo IN ('MORADOR', 'ADMINISTRADOR'))
);

-- ============ PATRIMÔNIO (as 6 subclasses numa tabela só) ============
CREATE TABLE patrimonio (
    id                   NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tipo                 VARCHAR2(30)  NOT NULL,
    morador_id           NUMBER        NOT NULL,
    nome                 VARCHAR2(150) NOT NULL,
    descricao            VARCHAR2(1000),
    data_cadastro        DATE DEFAULT SYSDATE NOT NULL,
    estado_conservacao   VARCHAR2(20)  NOT NULL,
    status_aprovacao     VARCHAR2(20)  DEFAULT 'PENDENTE' NOT NULL,

    logradouro           VARCHAR2(150) NOT NULL,
    numero               NUMBER        NOT NULL,
    bairro               VARCHAR2(100) NOT NULL,
    cidade               VARCHAR2(100) NOT NULL,
    estado               VARCHAR2(50)  NOT NULL,

    especie              VARCHAR2(100),
    idade_estimada       NUMBER,
    ano_construcao       NUMBER,
    antigo_proprietario  VARCHAR2(150),
    motivo_importancia   VARCHAR2(500),
    ramo_atividade       VARCHAR2(100),
    ano_fundacao         NUMBER,
    autor                VARCHAR2(150),
    material             VARCHAR2(100),
    area                 NUMBER(12,2),
    estilo_arquitetonico VARCHAR2(100),

    CONSTRAINT fk_patrimonio_morador FOREIGN KEY (morador_id) REFERENCES usuario(id),
    CONSTRAINT ck_patrimonio_tipo CHECK (tipo IN
        ('ArvoreHistorica','CasaMarcante','ComercioTradicional',
         'Monumento','PracaHistorica','PredioHistorico')),
    CONSTRAINT ck_patrimonio_status CHECK (status_aprovacao IN
        ('PENDENTE','APROVADO','REJEITADO','REMOVIDO')),
    CONSTRAINT ck_patrimonio_estado CHECK (estado_conservacao IN
        ('EXCELENTE','BOM','REGULAR','RUIM','CRITICO'))
);

-- ============ RELATO ============
CREATE TABLE relato (
    id              NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    titulo          VARCHAR2(150)  NOT NULL,
    conteudo        VARCHAR2(2000) NOT NULL,
    data_publicacao TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    status          VARCHAR2(20)   DEFAULT 'PUBLICADO' NOT NULL,
    autor_id        NUMBER NOT NULL,
    patrimonio_id   NUMBER NOT NULL,
    CONSTRAINT fk_relato_autor FOREIGN KEY (autor_id) REFERENCES usuario(id),
    CONSTRAINT fk_relato_patrimonio FOREIGN KEY (patrimonio_id) REFERENCES patrimonio(id),
    CONSTRAINT ck_relato_status CHECK (status IN ('PUBLICADO','REMOVIDO'))
);

-- ============ DENÚNCIA ============
CREATE TABLE denuncia (
    id             NUMBER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    tipo           VARCHAR2(30)   NOT NULL,
    descricao      VARCHAR2(2000) NOT NULL,
    data_registro  TIMESTAMP DEFAULT SYSTIMESTAMP NOT NULL,
    status         VARCHAR2(20)   DEFAULT 'PENDENTE' NOT NULL,
    morador_id     NUMBER NOT NULL,
    patrimonio_id  NUMBER NOT NULL,
    CONSTRAINT fk_denuncia_morador FOREIGN KEY (morador_id) REFERENCES usuario(id),
    CONSTRAINT fk_denuncia_patrimonio FOREIGN KEY (patrimonio_id) REFERENCES patrimonio(id),
    CONSTRAINT ck_denuncia_tipo CHECK (tipo IN
        ('ABANDONO','VANDALISMO','RISCO_DE_DEMOLICAO',
         'DESCARACTERIZACAO','RISCO_AMBIENTAL','OUTRO')),
    CONSTRAINT ck_denuncia_status CHECK (status IN
        ('PENDENTE','PUBLICADA','REJEITADA','REMOVIDA'))
);

-- ============ ADMINISTRADOR PRÉ-CADASTRADO ============
INSERT INTO usuario (tipo, nome, email, senha)
VALUES ('ADMINISTRADOR', 'Administrador', 'admin@ecosdacidade.com', 'admin123');

COMMIT;