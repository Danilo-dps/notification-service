# Notification Service (Consumidor Kafka)

Este é o serviço de mensageria e notificações do nosso ecossistema de pagamentos. Construído com **Java 21** e **Spring Boot**, ele atua como um **Consumidor Apache Kafka**, escutando eventos gerados pela API principal e enviando notificações por e-mail de forma assíncrona utilizando o Google SMTP.

## 🏗️ Contexto no Ecossistema

Este projeto é uma peça fundamental da nossa arquitetura orientada a eventos:
1. **[Payment Service](https://github.com/Danilo-dps/payments-service)**: Produz os eventos de transações e acessos.
2. **Notification Service**: *(Este repositório)* Consome os eventos e processa o envio de e-mails.
3. **[Commons Library](https://github.com/dlil-software-maker/commons)**: Fornece os DTOs (`records`) padronizados para a desserialização das mensagens.
4. **[Infraestrutura](https://github.com/Danilo-dps/docker-yamls/tree/main/payments-notification)**: Hospeda o cluster Kafka (KRaft) e o banco de dados.

## 📡 Tópicos Consumidos

O serviço está configurado (no `consumer-group-v1`) para escutar e reagir aos seguintes tópicos:

* 📥 `deposit-created`: Acionado quando um depósito é concluído com sucesso.
* 💸 `transfer-created`: Acionado quando uma transferência entre usuários é realizada.
* 🔐 `signin-notification`: Alertas de segurança sobre novos acessos à conta.
* 🎉 `signup-notification`: E-mail de boas-vindas para novos cadastros (Usuários Físicos ou Empresas).

## 🛠️ Stack Tecnológica

* **Linguagem & Framework:** Java 21, Spring Boot 3.x
* **Mensageria:** Spring for Apache Kafka (Consumer)
* **Notificações:** Spring Boot Starter Mail (JavaMailSender)
* **Dependência Externa:** Biblioteca `commons` (DTOs compartilhados)

---

## 🚦 Como Executar Localmente

Para rodar este serviço, você precisa que a [Infraestrutura do Kafka](https://github.com/Danilo-dps/docker-yamls/tree/main/payments-notification) já esteja rodando e que a biblioteca `commons` esteja instalada localmente.

### Passo 1: Instalar a Biblioteca Commons
Se você ainda não instalou a biblioteca base no seu Maven local, execute:
```bash
git clone [https://github.com/dlil-software-maker/commons.git](https://github.com/dlil-software-maker/commons.git)
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
*Atenção: A variável `EMAIL_SECRET` exige uma "Senha de App" do Google, não a senha padrão do seu e-mail. [Saiba como gerar aqui](https://support.google.com/accounts/answer/185833).*

### Passo 3: Executar a Aplicação

Com o Kafka rodando e as variáveis configuradas, inicie o consumidor:

O serviço iniciará, se conectará ao cluster Kafka local e começará a escutar os tópicos imediatamente. Sempre que a API de pagamentos (Producer) publicar uma mensagem, você verá o log de processamento neste terminal e o e-mail será disparado.
