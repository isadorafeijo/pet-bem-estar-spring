# Pet Bem-Estar Spring

Sistema de gestão para o Pet Bem-Estar.

## Remover um cliente com bichos

Ao tentar remover a cliente "Dona Rosa", que possui animais cadastrados, a aplicação exibiu a seguinte mensagem de erro: *"Não foi possível remover o tutor. Verifique se existem animais vinculados."*

**Por que isso aconteceu?**
Isso ocorre por causa de uma regra do banco de dados chamada **integridade referencial**. A tabela de animais (`Pet`) possui uma chave estrangeira (`tutor_id`) que aponta para o tutor. O banco de dados (MySQL) não permite que um registro "pai" (o Tutor) seja apagado enquanto existirem registros "filhos" (os Pets) dependendo dele. 

O Spring tenta executar o comando de exclusão, o banco retorna um erro (violação de chave estrangeira) e a nossa aplicação captura esse erro (no bloco `try/catch` do Controller) para não quebrar o sistema, exibindo a mensagem amigável na tela.