# Sistema de Gerenciamento de Clínica Veterinária

Atividade prática da disciplina de Análise e Desenvolvimento de Sistemas — PUC Goiás.  
O sistema foi implementado usando Arquitetura Hexagonal (Ports and Adapters) com Java puro, sem frameworks.

---

## Como compilar e executar

Você precisa do Java 11 ou superior instalado.

A partir do diretório raiz do projeto:

```bash
cd src
find . -name "*.java" > sources.txt
javac -d ../out @sources.txt
java -cp ../out com.clinica.apresentacao.Main
```

---

## Estrutura do projeto

```
src/com/clinica/
├── dominio/
│   ├── modelo/        → entidades e enums
│   ├── excecao/       → exceções do domínio
│   ├── porta/
│   │   ├── entrada/   → casos de uso expostos
│   │   └── saida/     → contratos com a infraestrutura
│   └── servico/       → lógica de negócio
├── infraestrutura/
│   └── adaptador/
│       ├── persistencia/   → repositórios em memória
│       └── notificacao/    → console e CSV
└── apresentacao/
    └── Main.java      → ponto de entrada e composição
```

---

## Decisões arquiteturais

A ideia central da arquitetura hexagonal é manter o domínio completamente isolado. Nenhuma classe dentro do pacote `dominio` importa nada de `infraestrutura` ou `apresentacao` — se precisar confirmar isso, é só olhar os imports dos arquivos.

As regras de negócio ficam nas entidades, não nos serviços. Por exemplo, quem valida se uma consulta pode ser realizada ou cancelada é a própria classe `Consulta`, através dos métodos `realizar()` e `cancelar()`. O serviço só orquestra.

A injeção das dependências é feita no `Main.java`, que é o único lugar do sistema onde os adaptadores concretos aparecem. Isso ficou bem visível na troca do adaptador de notificação: o domínio não mudou nada, só passamos uma instância diferente pro construtor.

---

## Portas e adaptadores

### Portas de saída

| Porta | O que faz |
|---|---|
| `PortaAnimalRepositorio` | salvar, buscar e listar animais |
| `PortaVeterinarioRepositorio` | salvar e buscar veterinários |
| `PortaConsultaRepositorio` | salvar e buscar consultas |
| `PortaNotificacaoTutor` | notificar o tutor sobre agendamentos e cancelamentos |

### Porta de entrada

| Porta | O que faz |
|---|---|
| `PortaAgendaConsulta` | expõe os casos de uso: agendar, realizar, cancelar, histórico e agenda |

### Adaptadores implementados

**Persistência:**  
Os três repositórios usam `HashMap` como armazenamento interno. O ID é gerado automaticamente pelo repositório quando o objeto ainda não tem um.

**Notificação — Console:**  
Imprime as notificações direto no terminal. Usado para a consulta de rotina no fluxo de demonstração.

**Notificação — CSV:**  
Grava cada notificação como uma linha no arquivo `notificacoes.csv`, com os campos: `timestamp`, `tipo_evento`, `tutor`, `animal`, `veterinário` e `data_consulta`. Cria o cabeçalho automaticamente na primeira execução e usa modo `append` pra não sobrescrever os registros anteriores.

A troca entre os dois adaptadores acontece no `Main.java` sem alterar nenhuma linha do domínio — que é exatamente o ponto da arquitetura hexagonal.
