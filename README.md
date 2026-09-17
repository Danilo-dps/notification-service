# Notification Service (Consumidor Kafka)

Este é o serviço de mensageria e notificações do nosso ecossistema de pagamentos. Construído com **Java 25** e **Spring Boot 4**, ele atua como um **Consumidor Apache Kafka**, escutando eventos gerados pela API principal e enviando notificações por e-mail de forma assíncrona utilizando o Google SMTP.

---

## 🏗️ Contexto no Ecossistema

Este projeto é uma peça fundamental da nossa arquitetura orientada a eventos:

1. **[Payment Service](https://github.com/Danilo-dps/payments-service)**: Produz os eventos de transações e acessos.
2. **Notification Service**: *(Este repositório)* Consome os eventos e processa o envio de e-mails.
3. **[Commons Library](https://github.com/dlil-software-maker/commons)**: Fornece os DTOs padronizados para a desserialização das mensagens.
4. **[Infraestrutura](https://github.com/Danilo-dps/docker-yamls/tree/main/payments-notification)**: Hospeda o cluster Kafka (KRaft) e o banco de dados.

---

## 📡 Tópicos Consumidos

O serviço está configurado (no `consumer-group-v1`) para escutar e reagir aos seguintes tópicos:

| Tópico | Descrição |
|---|---|
| 📥 `deposit-created` | Acionado quando um depósito é concluído com sucesso. |
| 💸 `transfer-created` | Acionado quando uma transferência entre usuários é realizada. |
| 🔐 `signin-notification` | Alertas de segurança sobre novos acessos à conta. |
| 🎉 `signup-notification` | E-mail de boas-vindas para novos cadastros (Usuários Físicos ou Empresas). |

---

## 🛠️ Stack Tecnológica

<p text-align="left">
  <img src="https://img.shields.io/badge/Java%2021-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java 25"/>
  <img src="https://img.shields.io/badge/Spring%20Boot%204.x-6DB33F?style=for-the-badge&logo=springboot&logoColor=white" alt="Spring Boot"/>
  <img src="https://img.shields.io/badge/Spring%20for%20Apache%20Kafka-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring for Apache Kafka"/>
  <img src="https://img.shields.io/badge/Apache%20Kafka-231F20?style=for-the-badge&logo=apachekafka&logoColor=white" alt="Apache Kafka"/>
  <img src="https://img.shields.io/badge/Spring%20Mail-6DB33F?style=for-the-badge&logo=spring&logoColor=white" alt="Spring Mail"/>
  <img src="https://img.shields.io/badge/Gmail-D14836?style=for-the-badge&logo=gmail&logoColor=white" alt="Gmail SMTP"/>
  <img src="https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven"/>
  <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker"/>
</p>

| Categoria | Tecnologia |
|---|---|
| **Linguagem** | Java 25 |
| **Framework** | Spring Boot 4.x |
| **Mensageria** | Spring for Apache Kafka (Consumer) |
| **Notificações** | Spring Boot Starter Mail (JavaMailSender) |
| **Dependência Externa** | Biblioteca `commons` (DTOs compartilhados) |
| **Build** | Apache Maven |
| **Containerização** | Docker (Kafka KRaft via infraestrutura) |

---

## 🚦 Como Executar Localmente

Para rodar este serviço, você precisa que a [Infraestrutura do Kafka](https://github.com/Danilo-dps/docker-yamls/tree/main/payments-notification) já esteja rodando e que a biblioteca `commons` esteja instalada localmente.

### Passo 1: Instalar a Biblioteca Commons
Se você ainda não instalou a biblioteca base no seu Maven local, execute:
```bash
git clone https://github.com/dlil-software-maker/commons.git
cd commons
mvn clean install
```

### Passo 2: Configurar as Variáveis de Ambiente
O serviço depende do Kafka e do servidor SMTP do Google. Crie um arquivo `.env` na raiz do projeto (ou configure diretamente na sua IDE, como na aba *Run* do IntelliJ) com as seguintes variáveis:

```env
# Configurações do Servidor e Kafka
SERVER_PORT=8081 # Use uma porta diferente da API Produtora (ex: 8081)
KAFKA_PORT=9092  # A mesma porta configurada no docker-compose

# Credenciais de E-mail (Google SMTP)
EMAIL_NOTIFICATION=seu_email@gmail.com
EMAIL_SECRET=sua_senha_de_app_gerada
```

> ⚠️ **Atenção:** A variável `EMAIL_SECRET` exige uma **"Senha de App"** do Google, não a senha padrão do seu e-mail. [Saiba como gerar aqui](https://support.google.com/accounts/answer/185833).

### Passo 3: Executar a Aplicação

Com o Kafka rodando e as variáveis configuradas, inicie o consumidor:

```bash
export $(xargs < .env) && mvn spring-boot:run
```

O serviço iniciará, se conectará ao cluster Kafka local e começará a escutar os tópicos imediatamente. Sempre que a API de pagamentos (Producer) publicar uma mensagem, você verá o log de processamento neste terminal e o e-mail será disparado.

---

## ⚙️ Comandos Úteis

### Maven — recuperar variáveis de ambiente durante o build
```bash
export $(xargs < .env) && mvn clean install
```

### Executar os testes
```bash
mvn test
```

---
