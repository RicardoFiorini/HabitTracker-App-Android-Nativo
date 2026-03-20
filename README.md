# Habit Tracker - Android Nativo
Uma solução robusta e performática de **rastreamento de hábitos**, construída inteiramente de forma nativa para a plataforma Android utilizando *Kotlin*. O objetivo deste projeto é oferecer uma ferramenta leve e integrada ao sistema para o gerenciamento de metas diárias.
## Funcionalidades principais

- Interface baseada em Material Design.
- Listagem dinâmica de hábitos com componentes nativos.
- Adição e exclusão rápida de metas diárias.
- Otimização de performance para dispositivos móveis.

## Status de Desenvolvimento
- [x] Implementação da arquitetura base em Kotlin
- [x] Desenvolvimento da UI com XML Layouts
- [x] Gerenciamento de listas com RecyclerView
- [x] Integração de componentes de navegação
- [ ] Persistência de dados com SQLite/Room
## Tecnologias Utilizadas
| Tecnologia | Aplicação |
| --- | --- |
| Kotlin | Linguagem core do aplicativo |
| Android SDK | Ferramentas de desenvolvimento nativas |
| Material Design | Padrões de design e componentes visuais |
| XML | Estruturação de layouts e recursos |
> [!TIP]
> Por ser um app nativo, ele oferece uma resposta tátil e visual muito mais fluida, aproveitando ao máximo o hardware do dispositivo Android.
## Como compilar o projeto
Clone o repositório e abra-o no **Android Studio**. Você pode rodar o app diretamente via terminal se preferir:
`./gradlew assembleDebug`
```kotlin

// Exemplo de como a estrutura de dados é tratada
data class Habit(
val id: Int,
val name: String,
val isCompleted: Boolean
)

```
