<p align="center">
  <img src="https://github.com/selmanon/composeCleanArch/blob/master/screenshoots/ic_launcher.png" width="50" title="Home screen">
</p>

## Objectifs

**Clean Architecture** : couches presentation, domain et data et injection de
dépendance. [ref](https://fernandocejas.com/2018/05/07/architecting-android-reloaded/)

**Tests unitaires** : avec Junit et Mokk.

**Gestion des flux de données** : Coroutine, Flow et Paging.

**Gestion de la vue** : Jetpack Compose (Dark/Night).

**Gestion de la navigation** : Jetpack Navigation.

## Decoupage de l'implementation

# Partie 1. Dev de la couche domaine

Le domaine est le coeur de l'application qui contient la logique et les règles métier. Il est donc
important que le domaine soit indépendant des autres modules et d'autres bibliothèques liées à l'UI,
à Android, ... Mais aussi que toutes les classes (sauf les modèles) et les méthodes publiques soient
testées.

# Partie 2. Dev de la couche donnees

La couche de données va permettre de récupérer et persister les données de l'application aux travers
de Repositories et des Data Sources qui peuvent être locales avec un BDD ou distantes avec une API.
* En pratique "le repository pattern" doit être utilisé pour gérer différents source de données (DB, API, Cache), sinon il est mis en place car il y a une _feature_ pour supporter le "mode offline".

# Partie 3. Dev de la couche presentation

Le rôle de l'UI est d'afficher les données de l'application à l'écran et de servir de point
principal d'interaction utilisateur. Le pattern utilisé est MVVM (Model - View - ViewModel)/UDF.

![image](https://github.com/selmanon/composeCleanArch/assets/2206036/6d5d69e3-8a1b-4ff0-ac7d-ccd5e1df9fad)

## Rendu de l'application

<p align="center">
  <img src="https://github.com/selmanon/composeCleanArch/blob/master/screenshoots/home.png" width="250" title="Home screen">
  &nbsp; &nbsp; &nbsp; 
  <img src="https://github.com/selmanon/composeCleanArch/blob/master/screenshoots/post_screen.png" width="250" alt="Pets screen">
 &nbsp; &nbsp; &nbsp; 
  <img src="https://github.com/selmanon/composeCleanArch/blob/master/screenshoots/error.png" width="250" alt="Error screen">
</p>

# Points d'amélioration :

[Testing]

- TUs viewModel. [apparement un `viewModel` qui utilise du
  _paging_ ne peut être testé que coté UI](https://developer.android.com/topic/libraries/architecture/paging/test)
- Injecter le scope et le dispather pour faciliter les tests (unit/ui). ✅
- TUs pour le pagingSource. ✅
- Tests d'interface utilisateur.

[Archi]

- Supporter le mode "offline first".
- Avoir un module pour chaque couche. ✅

[UI]

- Ajouter un bouton "retry" sur l'écran d'erreur de chargement de données. ✅
- Ajouter le pull to refresh. ✅
- Gestion du scroll. ✅
- Remplacer le message d'erreur _text_ par un _snack bar_
- Ajouter la navigation dans l'AppBar. ✅
- Ajouter un boutton "up" pour remonter au début de la list. ✅
- Filtrer la liste des postes à base d'un tag. ✅

[Refacto]

- Enlever la dépendance "material". (car elle n'est utilisée que pour le "pull-to-refrsh" et utilisé celle de material3) ✅
- Améliorer la gestion des erreurs (via Retrofit CallAdapter). ✅
    - Utiliser une `sealed classe` qui fait la différence entre une _exception_ et une _erreur_. ✅
- Ajouter un buildSrc module pour la gestion des versions (Android/release). ✅
- Ajouter de la documentation.

[CI/CD]

- Configurer la CI via GitHub action
   - Formatage du code avec Spotless/Klint. ✅
   - Couverture des tests unitaires via Jacoco.
   - Générer la release.
 
[Bugs]
- Erreur de _threading_. <code>(Skipped 78 frames!)</code>  ✅

