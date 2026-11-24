# Pet Bem-Estar Spring

Sistema de gestão para o Pet Bem-Estar.

Projeto desenvolvido para a disciplina de Programação para Web I. O sistema permite o gerenciamento de clientes (tutores) e dos seus pets de estimação, com autenticação de usuários.

## Nome do Banco de Dados

O projeto utiliza o MySQL. O banco configurado é: `petbemestarspring`

O banco é criado automaticamente na primeira execução (`createDatabaseIfNotExist=true`), e as tabelas são geradas pelo Hibernate (`ddl-auto=update`). Usuário e senha do banco ficam em `src/main/resources/application.properties` (padrão: usuário `root`, senha vazia). Ajuste se o seu MySQL for diferente.

## Como executar

1. Ligue o servidor MySQL na porta 3306.
2. No terminal, na raiz do projeto, execute: `./mvnw spring-boot:run` (ou `.\mvnw.cmd spring-boot:run` no Windows). Também é possível rodar a classe `PetBemEstarSpringApplication` pela IDE.
3. Acesse `http://localhost:8080/cadastro` e crie um usuário (na primeira execução não existe nenhum).
4. Acesse `http://localhost:8080/login` e entre com o usuário criado.

## O que foi feito nesta etapa (N2)

1. **Serviço no meio**: foram criadas as classes `TutorService`, `PetService` e `UsuarioService`. Os controllers (páginas e rotas JSON) delegam as operações para os serviços, e apenas os serviços acessam os DAOs.
2. **Regra de Négocio**: o sistema impede que um tutor com bichos seja removido. O `TutorService` lança uma `RegraDeNegocioException` com uma frase amigável, exibida na tela de listagem de clientes. O cliente continua lá, e cliente sem bichos é removido normalmente.
3. **Um telefone, um cliente**: o `TutorService` valida se o telefone já pertence a outro tutor, no cadastro e na edição. O aviso aparece no formulário sem perder o que foi digitado, e na edição o tutor pode manter o próprio telefone.
4. **Ficha do Tutor**: página de detalhes acessível pelo nome do tutor na lista, exibindo nome, telefone e todos os bichos vinculados. Os bichos vêm de uma consulta JPQL no `PetDAO` (`listarPorTutor`), filtrando pelo id do tutor.
5. **Login e cadastro**: páginas de cadastro de usuário e de login. A senha é gravada com hash, usando `BCryptPasswordEncoder`, e nunca como foi digitada. Um `LoginInterceptor` protege todas as rotas do sistema, redirecionando usuários não logados para a tela de login, e depois de entrar aparece o link para sair.
6. **Identidade Visual do Pet-Bem-Estar**: aplicação da paleta de cores da cliente Marlene (verde, laranja e fundo creme) em `static/css/petshop.css`, ligado a todas as páginas, inclusive o login, e do logo do Pet Bem-Estar no topo de todas as páginas.

## Remover um cliente com bichos

### Como era antes da N2

Ao tentar remover a cliente "Dona Rosa", que possui animais cadastrados, a aplicação exibia a seguinte mensagem de erro: *"Não foi possível remover o tutor. Verifique se existem animais vinculados."*

**Por que isso aconteceu?** Isso ocorre por causa de uma regra do banco de dados chamada **integridade referencial**. A tabela de animais (`Pet`) possui uma chave estrangeira (`tutor_id`) que aponta para o tutor. O banco de dados (MySQL) não permite que um registro "pai" (o Tutor) seja apagado enquanto existirem registros "filhos" (os Pets) dependendo dele.

O Spring tentava executar o comando de exclusão, o banco retornava um erro (violação de chave estrangeira) e a aplicação capturava esse erro (no bloco `try/catch` do Controller) para não quebrar o sistema, exibindo uma mensagem genérica na tela.

### Como ficou na N2

A regra agora é conferida **antes** de o banco ser acionado. O `TutorService.remover` consulta os animais do tutor e, se houver algum, lança a `RegraDeNegocioException` com a frase:

*"Este cliente não pode ser removido porque ainda tem animais cadastrados. Remova ou transfira os animais antes."*

O controller captura essa exceção e mostra a frase na lista de clientes. O cliente continua cadastrado e a ficha dos bichos é preservada. A chave estrangeira do banco continua existindo como proteção final, mas o erro do banco deixa de chegar à tela.

## Tecnologias Utilizadas

- Java 17 
- Spring Boot
- Spring Data JPA / Hibernate
- Thymeleaf
- MySQL
- HTML / CSS