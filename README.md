# 🎰 Megasena Checker

Aplicação serverless desenvolvida com **Spring Boot** e implantada na **AWS Lambda** com o objetivo de conferir automaticamente os resultados da Mega-Sena, comparar com um jogo pré-configurado e enviar uma notificação por e-mail com o resultado.

---

## 📌 Objetivo

A cada execução (agendada via **AWS EventBridge**), a aplicação:

1. Busca o resultado do último sorteio da Mega-Sena via API pública
2. Verifica se o concurso já foi processado anteriormente (evita duplicidade)
3. Salva o resultado no **Amazon DynamoDB**
4. Compara as dezenas sorteadas com o jogo configurado
5. Envia um e-mail via **Amazon SES** informando a quantidade de acertos

---

## 🏗️ Arquitetura Hexagonal (Ports & Adapters)

O projeto segue os princípios da **Arquitetura Hexagonal**, isolando completamente a lógica de negócio de frameworks e detalhes de infraestrutura.

```
src/main/java/com/mega/megasenachecker/
│
├── domain/                               ← Núcleo (zero dependências de framework)
│   ├── model/
│   │   └── Concurso.java                 ← Entidade de domínio pura
│   └── port/
│       ├── in/
│       │   └── VerificarSorteioUseCase   ← Porta de entrada (interface)
│       └── out/
│           ├── ConcursoRepository        ← Porta de saída: persistência
│           ├── LoteriasGateway           ← Porta de saída: API externa
│           └── NotificacaoPort           ← Porta de saída: notificação
│
├── application/                          ← Caso de uso (orquestra o domínio)
│   └── usecase/
│       └── VerificarSorteioUseCaseImpl   ← Toda a lógica de negócio aqui
│
└── infrastructure/                       ← Adapters (detalhes técnicos)
    ├── adapter/
    │   ├── in/
    │   │   └── lambda/
    │   │       ├── LambdaHandler         ← Entrypoint AWS Lambda
    │   │       └── MegaFunctionConfig    ← Spring Cloud Function bean
    │   └── out/
    │       ├── dynamodb/
    │       │   ├── ConcursoEntity        ← Entidade anotada para DynamoDB
    │       │   └── ConcursoRepositoryAdapter  ← Implementa ConcursoRepository
    │       ├── api/
    │       │   ├── LoteriasApiResponse   ← DTO da resposta da API
    │       │   └── LoteriasApiAdapter    ← Implementa LoteriasGateway
    │       └── email/
    │           └── EmailNotificationAdapter   ← Implementa NotificacaoPort
    └── config/
        └── DynamoConfig                  ← Configuração Spring/AWS
```

### Por que Arquitetura Hexagonal?

| Benefício | Descrição |
|---|---|
| **Testabilidade** | O domínio e o caso de uso são testados com mocks simples, sem subir contexto Spring ou AWS |
| **Isolamento** | A lógica de negócio não conhece DynamoDB, SES, ou qualquer framework |
| **Substituibilidade** | Qualquer adapter pode ser trocado sem impactar o domínio |
| **Clareza** | Cada camada tem uma responsabilidade bem definida |

---

## ☁️ Infraestrutura AWS

| Serviço | Uso |
|---|---|
| **AWS Lambda** | Execução da aplicação serverless |
| **AWS EventBridge** | Agendamento automático da execução |
| **Amazon DynamoDB** | Persistência dos concursos processados |
| **Amazon SES** | Envio de e-mail com o resultado |

### Handler configurado na Lambda

```
com.mega.megasenachecker.LambdaHandler
```

### Fluxo de execução

```
EventBridge (cron)
      │
      ▼
AWS Lambda (LambdaHandler)
      │
      ▼
MegaFunctionConfig → VerificarSorteioUseCaseImpl
      │
      ├─→ LoteriasApiAdapter       (busca resultado na API pública)
      ├─→ ConcursoRepositoryAdapter (verifica/salva no DynamoDB)
      └─→ EmailNotificationAdapter  (envia resultado via SES)
```

---

## 🧪 Testes Unitários

O projeto conta com **35 testes unitários** cobrindo todas as camadas, executados com **JUnit 5** e **Mockito**, sem necessidade de subir contexto Spring ou conexões externas.

| Classe testada | Arquivo de teste | Cenários |
|---|---|---|
| `Concurso` | `ConcursoTest` | Construtor padrão, construtor com parâmetros, setters |
| `VerificarSorteioUseCaseImpl` | `VerificarSorteioUseCaseImplTest` | Gateway nulo, número nulo, concurso já salvo, concurso novo, 0/4/5/6 acertos, dezenas nulas, erros de gateway e repositório |
| `EmailNotificationAdapter` | `EmailNotificationAdapterTest` | Remetente, destinatário, assunto, corpo, chamada ao mailSender |
| `ConcursoRepositoryAdapter` | `ConcursoRepositoryAdapterTest` | Registro não encontrado, registro encontrado, mapeamento ao salvar, nome da tabela |
| `LoteriasApiAdapter` | `LoteriasApiAdapterTest` | Resposta mapeada, dezenas ausentes, erro da API, headers HTTP |
| `MegaFunctionConfig` | `MegaFunctionConfigTest` | Delegação ao use case, retorno correto, inputs variados |
| Contexto Spring | `MegasenaCheckerApplicationTests` | Carregamento do contexto |


## ⚙️ Variáveis de Ambiente

Todas as configurações sensíveis são carregadas a partir do arquivo `.env` na raiz do projeto (nunca versionar este arquivo).

Crie um arquivo `.env` com o seguinte conteúdo:

```env
# AWS SES - credenciais SMTP
MAIL_USERNAME=sua_access_key_smtp
MAIL_PASSWORD=sua_secret_key_smtp

# E-mail
EMAIL_SENDER=seu-email@gmail.com
EMAIL_RECIPIENT=seu-email@gmail.com

# DynamoDB
DYNAMODB_TABLE_NAME=megasena

# Dezenas do seu jogo (separadas por vírgula)
MEU_JOGO=04,11,15,29,38,41
```

> ⚠️ O arquivo `.env` **não deve ser versionado**. Adicione-o ao `.gitignore`.

---

## 🛠️ Tecnologias

| Tecnologia | Versão | Uso |
|---|---|---|
| Java | 21 | Linguagem |
| Spring Boot | 3.3.5 | Framework base |
| Spring Cloud Function | 2023.0.3 | Integração com AWS Lambda |
| AWS SDK v2 (DynamoDB Enhanced) | 3.1.1 | Persistência no DynamoDB |
| Spring Mail | - | Envio de e-mail via SES |
| JUnit 5 | - | Testes unitários |
| Mockito | - | Mocks nos testes |

---

## 📦 Build e Deploy

### Gerar o artefato para AWS Lambda

O arquivo gerado para deploy na Lambda é:

```
target/megasena-checker-0.0.1-SNAPSHOT-aws.jar
```

### Configuração na AWS Lambda

| Campo | Valor |
|---|---|
| **Runtime** | Java 21 |
| **Handler** | `com.mega.megasenachecker.LambdaHandler` |
| **Memória recomendada** | 512 MB |
| **Timeout recomendado** | 30 segundos |
| **Arquivo de deploy** | `*-aws.jar` (gerado pelo maven-shade-plugin) |

---

## 📬 Exemplo de e-mail recebido

```
Assunto: Resultado do Concurso 2800 - Acertos: 4

Resultado obtido e salvo para o concurso: 2800
Dezenas sorteadas: [04, 11, 15, 29, 06, 07]
--------------------------------------------------
Meu jogo: [04, 11, 15, 29, 38, 41]
Quantidade de acertos: 4
Números acertados: [04, 11, 15, 29]
🔥 4 acertos → "Agora ficou interessante..."
--------------------------------------------------
```

---

## 📁 Estrutura do Projeto

```
megasena-checker/
├── src/
│   ├── main/
│   │   ├── java/com/mega/megasenachecker/
│   │   │   ├── domain/           ← Entidades e portas
│   │   │   ├── application/      ← Casos de uso
│   │   │   ├── infrastructure/   ← Adapters, config e entrypoints
│   │   │   ├── LambdaHandler.java
│   │   │   └── MegasenaCheckerApplication.java
│   │   └── resources/
│   │       └── application.properties
│   └── test/                     ← 35 testes unitários
├── .env                          ← Variáveis de ambiente (não versionar)
├── pom.xml
└── README.md
```

