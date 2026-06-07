# NutriSpace — DevOps

API Java NutriSpace containerizada com Docker e Oracle XE, executada em VM Linux no Microsoft Azure.

**Repositório:** https://github.com/GuuiSOares/nutrispace-devops

---

## Equipe

| Nome | RM |
|------|-----|
| Lucas Silva Gastão Pinheiro | 563960 |
| Geovanne Coneglian Passos | 562673 |
| Guilherme Soares de Almeida | 563143 |

Representante: **RM 562673**

---

## Descrição da solução

Este repositório conteineriza a API REST Java (Spring Boot) do NutriSpace com Docker e Oracle XE, executando ambos em uma VM Linux no Microsoft Azure.

O acesso externo é feito pelo IP público da VM na porta 8080. A API persiste os dados no Oracle via rede interna Docker (`nutrispace_net`).

---

## Arquitetura macro

![Arquitetura NutriSpace no Azure](docs/arquitetura-azure.png)

---

## Containers

| Container | Imagem | Porta |
|-----------|--------|-------|
| `api-nutrispace-rm562673` | Build via `Dockerfile` | 8080 |
| `db-nutrispace-rm562673` | `gvenzl/oracle-xe:21-slim-faststart` | 1521 |

---

## Pré-requisitos

- Conta Azure
- [Azure CLI](https://learn.microsoft.com/cli/azure/install-azure-cli) autenticado (`az login`)
- Git

---

## Credenciais padrão

| Acesso | Usuário | Senha |
|--------|---------|-------|
| SSH na VM | `admlnx` | `Fiap@2tdsvms` |
| API (Swagger) | `lucas@nutrispace.com` | `senha123` |

Valores definidos em `azure/provision-vm.sh` (VM) e `docker/data-docker.sql` (API).

---

## How to — execução completa

Existem dois cenários. Na **primeira vez**, a VM ainda não existe — não há IP para SSH. É preciso criar a VM no Azure Cloud Shell, obter o IP e só então conectar.

---

### A — Primeira vez (criar a VM e publicar a aplicação)

**Onde executar:** Azure Cloud Shell (antes da VM existir, não há SSH).

**A.1 — Clonar o repositório no Cloud Shell**

```bash
git clone https://github.com/GuuiSOares/nutrispace-devops.git
cd nutrispace-devops
```

**A.2 — Criar a VM na Azure**

```bash
bash azure/provision-vm.sh
```

| Parâmetro | Valor |
|-----------|-------|
| Resource Group | `rg-nutrispace-dev` |
| VM | `vm-nutrispace-dev` |
| Região | `canadacentral` |
| SO | AlmaLinux 10 |
| Tamanho | `Standard_B2als_v2` |

O script cria a VM, libera as portas 22, 8080 e 1521, instala o Docker e grava o IP em `azure/vm-info.env`.

**A.3 — Obter o IP público**

```bash
source azure/vm-info.env && echo $VM_PUBLIC_IP
```

Anote o IP exibido (ex: `20.220.59.168`). Também é possível consultar no portal Azure, na visão geral da VM.

**A.4 — Conectar na VM**

```bash
ssh admlnx@<IP_PUBLICO>
```

Senha: `Fiap@2tdsvms` (não aparece enquanto digita — é normal).

**A.5 — Clonar o repositório na VM e entrar na pasta**

Dentro da VM (`[admlnx@vm-nutrispace-dev ~]$`):

```bash
git clone https://github.com/GuuiSOares/nutrispace-devops.git
cd nutrispace-devops
ls -l
```

Estrutura do repositório:

```
nutrispace-devops/
├── Dockerfile
├── docker-compose.yml
├── docker/
│   ├── application-docker.properties
│   ├── schema-docker.sql
│   └── data-docker.sql
├── azure/
│   ├── provision-vm.sh
│   ├── deploy-app.sh
│   └── cleanup-vm.sh
├── docs/
│   └── arquitetura-azure.png
└── NutriSpace-GS-main/
    └── API Java (Spring Boot)
```

**A.6 — Subir os containers**

```bash
docker compose up -d --build
```

Aguarde 5 a 8 minutos na primeira execução (build da API e inicialização do Oracle).

A partir daqui, continue no **Passo 1** abaixo (verificar containers, testes, banco e API).

---

Se o repositório ainda não existir na VM, use `git clone` no lugar de `git pull` (passo A.5).

A partir daqui, continue no **Passo 1** abaixo.

---

### Passo 1 — Verificar os containers

```bash
docker compose ps
docker compose logs db-nutrispace --tail 30
docker compose logs api-nutrispace --tail 30
docker volume ls | grep nutrispace_data
```

Verificações:

- Serviço `db-nutrispace` com status `healthy`
- Log da API contendo `Started NutrispaceApplication`
- Volume `nutrispace_data` listado

### Passo 2 — Inspecionar os containers

Container da API:

```bash
docker container exec -it api-nutrispace-rm562673 sh
pwd
ls -l
whoami
exit
```

Resultado esperado: diretório `/app`, usuário `nutriuser`.

Container do banco:

```bash
docker container exec -it db-nutrispace-rm562673 bash
pwd
ls -l
whoami
exit
```

### Passo 3 — Acessar o Oracle no container

Na VM, na pasta do projeto:

```bash
cd ~/nutrispace-devops
docker exec -it db-nutrispace-rm562673 bash
sqlplus / as sysdba
```

No SQL*Plus, selecionar o banco da aplicação e consultar as tabelas:

```sql
ALTER SESSION SET CONTAINER = XEPDB1;
SELECT table_name FROM dba_tables WHERE owner = 'NUTRISPACE' AND table_name LIKE 'TB_NS%' ORDER BY table_name;
```

Sair do SQL*Plus e do container:

```sql
EXIT;
```

```bash
exit
```

Atalho (entra direto no SQL*Plus, sem passar pelo bash):

```bash
docker exec -it db-nutrispace-rm562673 sqlplus / as sysdba
```

O comando `ALTER SESSION SET CONTAINER = XEPDB1` é necessário porque o SQL*Plus abre no container raiz (`CDB$ROOT`); os dados da API ficam no pluggable database `XEPDB1`, schema `nutrispace`.

### Passo 4 — Autenticar na API

Swagger: `http://<IP_PUBLICO>:8080/swagger-ui/index.html`

Credenciais na seção [Credenciais padrão](#credenciais-padrão).

Via curl:

```bash
curl -X POST http://<IP_PUBLICO>:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"lucas@nutrispace.com","senha":"senha123"}'
```

No Swagger:

1. Executar **POST** `/auth/login` com as credenciais acima
2. Copiar o valor de `token` da resposta
3. Clicar em **Authorize** e informar `Bearer <token>`

### Passo 5 — CRUD de plantas

Substituir `<IP_PUBLICO>`, `<TOKEN>` e `<id>` pelos valores reais.

```bash
# CREATE
curl -X POST http://<IP_PUBLICO>:8080/plantas \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{"nomePlanta":"Tomate Lunar","tempMinIdeal":20,"tempMaxIdeal":30,"umiMinIdeal":50}'

# READ
curl http://<IP_PUBLICO>:8080/plantas -H "Authorization: Bearer <TOKEN>"

# UPDATE
curl -X PUT http://<IP_PUBLICO>:8080/plantas/<id> \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer <TOKEN>" \
  -d '{"nomePlanta":"Tomate Lunar V2","tempMinIdeal":20,"tempMaxIdeal":32,"umiMinIdeal":55}'

# DELETE
curl -X DELETE http://<IP_PUBLICO>:8080/plantas/<id> -H "Authorization: Bearer <TOKEN>"
```

Equivalente no Swagger: endpoints em `/plantas`.

### Passo 6 — Consultar dados no banco após escrita

Após cada operação de CREATE, UPDATE ou DELETE na API, repetir o acesso do [Passo 3](#passo-3--acessar-o-oracle-no-container) e executar:

```sql
ALTER SESSION SET CONTAINER = XEPDB1;
SELECT id_planta, nome_planta FROM nutrispace.tb_ns_planta;
SELECT id_estufa, nome_estufa FROM nutrispace.tb_ns_estufa;
SELECT id_astronauta, nome, email FROM nutrispace.tb_ns_astronauta;
```

---

## Comandos auxiliares

```bash
docker network ls
docker compose down
docker compose down -v
```

### Remover a VM (Resource Group)

No Azure Cloud Shell:

```bash
bash azure/cleanup-vm.sh
```

A exclusão roda em segundo plano e pode levar de 5 a 15 minutos.

Verificar se o Resource Group foi removido:

```bash
az group show -n rg-nutrispace-dev
```

| Resultado | Significado |
|-----------|-------------|
| Erro `ResourceGroupNotFound` | Resource Group apagado — pode executar `provision-vm.sh` |
| Exibe dados do Resource Group | Ainda existe — aguarde alguns minutos e execute o comando novamente |

Alternativa:

```bash
az group list -o table
```

Se `rg-nutrispace-dev` não aparecer na lista, a exclusão foi concluída.

| Script | Função |
|--------|--------|
| `azure/provision-vm.sh` | Cria VM, portas e Docker |
| `azure/deploy-app.sh` | Atalho: clona na VM e executa `docker compose up -d --build` via Azure CLI |
| `azure/cleanup-vm.sh` | Remove o Resource Group |

---
