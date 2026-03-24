# Checkpoint 01 - Android Kotlin Developer

## Objetivo da Prova
Aprender como implementar a navegação entre múltiplas telas em um aplicativo Android utilizando **Jetpack Compose** e **Navigation Compose**.

---

## Descrição do Projeto

### obs: o meu projeto possui algumas diferenças de cor por conta de customização

### Funcionalidades

- **Telas**
  - Login
  - Menu com navegação para outras telas
  - Tela de Perfil
  - Tela de Pedidos
  - Navegação controlada por **NavController**

---

### Explicação de Commits

1. **Limpeza inicial do MainActivity**  
   - Remove exemplos e configurações padrão para preparar a MainActivity para implementação do app.

2. **Criação da LoginScreen**  
   - Implementa o *composable* `LoginScreen`.  
   - Estrutura básica: **Box** ocupando toda a tela.  
   - Contém um botão `"ENTRAR"` (ação ainda não definida).  

3. **Criação da MenuScreen**  
   - Implementa `MenuScreen` com três botões: `"Perfil"`, `"Pedidos"`, `"Sair"`.  
   - Estrutura: **Column** central dentro de um **Box**.  
   - Botões ainda não possuem ações definidas.

4. **Criação da PerfilScreen**  
   - Implementa `PerfilScreen`.  
   - Estrutura: **Box** com botão `"Voltar"` central.  
   - Botão ainda sem ação.

5. **Criação da PedidosScreen**  
   - Implementa `PedidosScreen`.  
   - Estrutura: **Box** simples com título `"PEDIDOS"`.  

6. **Chamada da LoginScreen na MainActivity**  
   - Define `LoginScreen` como tela inicial dentro do **Scaffold**.

7. **Adição da dependência Navigation Compose**  
   - Inclui `androidx.navigation:navigation-compose` no `build.gradle.kts` para suporte a navegação entre *composables*.

8. **Implementa NavController na MainActivity**  
   - Cria `navController` com `rememberNavController()`.  
   - Configura **NavHost** com `startDestination = "login"`.  
   - Define rotas do app: `"login"`, `"menu"`, `"pedidos"`, `"perfil"`.  
   - Passa `innerPadding` do **Scaffold** para cada tela.

9. **Habilita navegação entre telas**  
   - Cada tela recebe `navController` para possibilitar chamadas de navegação.
     
10. **Passagem de parâmetros obrigatórios na tela de Perfil**  
    - **Objetivo:** Permitir que a tela de perfil receba informações específicas do usuário, garantindo que o valor de `nome` seja obrigatório.  
    - **Rota definida:** `"perfil/{nome}"`  
    - **Argumentos:**  
      - `nome` → String, obrigatório  
    - **Implementação:**  
      ```kotlin
      composable(route = "perfil/{nome}") {
          val nome: String? = it.arguments?.getString("nome", "Usuário Genérico")
          PerfilScreen(
              modifier = Modifier.padding(innerPadding),
              navController,
              nome!!
          )
      }
      ```
      - `it.arguments` extrai o valor do parâmetro da rota.  
      - `!!` garante que o valor não seja nulo ao passar para a **PerfilScreen**.  
      - Valor padrão `"Usuário Genérico"` usado caso a rota seja chamada sem o parâmetro (como fallback).

11. **Passagem de parâmetros opcionais na tela de Pedidos**  
    - **Objetivo:** Permitir que a tela de pedidos receba um parâmetro opcional `cliente`, possibilitando personalização sem quebrar a navegação.  
    - **Rota definida:** `"pedidos?cliente={cliente}"`  
    - **Argumentos:**  
      - `cliente` → String, opcional, valor padrão `"Cliente Genérico"`  
    - **Implementação:**  
      ```kotlin
      composable(
          route = "pedidos?cliente={cliente}",
          arguments = listOf(navArgument("cliente") {
              defaultValue = "Cliente Genérico"
          })
      ) {
          PedidosScreen(
              modifier = Modifier.padding(innerPadding),
              navController,
              it.arguments?.getString("cliente")
          )
      }
      ```
      - `navArgument()` declara o parâmetro e define o valor padrão.  
      - `it.arguments?.getString("cliente")` obtém dinamicamente o valor ao navegar.  
      - Permite chamadas como:  
        ```kotlin
        navController.navigate("pedidos?cliente=ClienteXPTO")
        ```

12. **Inserindo valor ao parâmetro opcional na tela de Pedidos**  
    - **Objetivo:** Demonstrar navegação dinâmica, enviando parâmetros opcionais quando necessário.  
    - Exemplo de uso:  
      ```kotlin
      Button(onClick = { 
          navController.navigate("pedidos?cliente=ClienteXPTO")
      }) { ... }
      ```
    - Se o parâmetro não for passado, a tela usa o valor padrão `"Cliente Genérico"`.

13. **Passagem de múltiplos parâmetros na tela de Perfil**  
    - **Objetivo:** Mostrar como enviar e receber múltiplos parâmetros obrigatórios em uma rota.  
    - **Rota definida:** `"perfil/{nome}/{idade}"`  
    - **Argumentos:**  
      - `nome` → String, obrigatório  
      - `idade` → Int, obrigatório  
    - **Implementação:**  
      ```kotlin
      composable(
          route = "perfil/{nome}/{idade}",
          arguments = listOf(
              navArgument("nome") { type = NavType.StringType },
              navArgument("idade") { type = NavType.IntType }
          )
      ) {
          val nome: String? = it.arguments?.getString("nome", "Usuário Genérico")
          val idade: Int? = it.arguments?.getInt("idade", 0)
          PerfilScreen(
              modifier = Modifier.padding(innerPadding),
              navController,
              nome!!,
              idade!!
          )
      }
      ```
      - `NavType.StringType` e `NavType.IntType` garantem tipagem correta dos argumentos.  
      - Valores padrão (`"Usuário Genérico"` e `0`) evitam crashes caso algum argumento não seja passado.  
      - **PerfilScreen** recebe os dois parâmetros de forma segura e exibirá o conteúdo correspondente ao usuário.

---
