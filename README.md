<p align="center">
  <img src="https://github.com/selmanon/BluePets/assets/2206036/3289251a-5ec3-45b1-b9a4-f4b36c97c1e6" width="50" title="Home screen">
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

# Partie 3. Dev de la couche presentation

Le rôle de l'UI est d'afficher les données de l'application à l'écran et de servir de point
principal d'interaction utilisateur. Le pattern utilisé est MVVM (Model - View - ViewModel)/UDF.

![image](https://github.com/selmanon/composeCleanArch/assets/2206036/6d5d69e3-8a1b-4ff0-ac7d-ccd5e1df9fad)

## Rendu de l'application

<p align="center">
  <img src="https://github.com/selmanon/composeCleanArch/blob/master/screenshoots/home_screen.png" width="250" title="Home screen">
  &nbsp; &nbsp; &nbsp; 
  <img src="https://github.com/selmanon/composeCleanArch/blob/master/screenshoots/post.png" width="250" alt="Pets screen">
 &nbsp; &nbsp; &nbsp; 
  <img src="https://github.com/selmanon/composeCleanArch/blob/master/screenshoots/error_screen.png" width="250" alt="Error screen">
</p>

# Points d'amélioration :

[Testing]

- TUs viewModel.
- TUs pour le pagingSource. ✅
- Tests d'interface utilisateur.

[Archi]

- Supporter le mode "offline first".
- Avoir un module pour chaque couche. ✅

[UI]

- Ajouter un bouton "retry" sur l'écran d'erreur de chargement de données. ✅
- Ajouter le pull to refresh. ✅
- Gestion du scroll. ✅
- Enrichire l'UI/UX. _WIP_
- Ajouter la navigation dans l'AppBar. ✅
- Ajouter un boutton "up" pour remonter au début de la list. ✅
- Filtrer la liste des postes à base d'un tag. ✅

[Refacto]

- Enlever la dépendance "material". (car n'elle est utilisée que pour le "pull-to-refrsh") ✅
- Enrichir la gestion des erreurs.
- Ajouter un buildSrc module pour la gestion des versions (Android/release). ✅

[CI/CD]

- Configurer la CI via GitHub action.
