## Objectifs

**Clean Archi** : couches presentation, domain et data et injection de dépendance.

**tests unitaires** : avec Junit et Mockito.

**gestion des flux de données** : Coroutine, Flow et Paging.

**gestion de la vue** : Jetpack Compose (Dark/Night).

**gestion de la navigation** : Jetpack Navigation.


## Decoupage de l implementation

# Partie 1. Dev de la couche domaine

Le domaine est le coeur de l'application qui contient la logique et les règles métier. Il est donc important que le domaine soit indépendant des autres modules et d'autres bibliothèques liées à l'UI, à Android, ... Mais aussi que toutes les classes (sauf les modèles) et les méthodes publiques soient testées.

# Partie 2. Dev de la couche donnees
La couche de données va permettre de récupérer et persister les données de l'application aux travers de Repositories et des Data Sources qui peuvent être locales avec un BDD ou distantes avec une API.

# Partie 3. Dev de la couche presentation
Le rôle de l'UI est d'afficher les données de l'application à l'écran et de servir de point principal d'interaction utilisateur. Le pattern utilisé est MVVM (Model - View - ViewModel)/UFD avec :
A. Gestion de la navigation via Jetpack Navigation.
B. Gestion de la vue Jetpack Compose.


![image](https://github.com/selmanon/composeCleanArch/assets/2206036/6d5d69e3-8a1b-4ff0-ac7d-ccd5e1df9fad)

# Point d'amélioration :
- TU pour le viewModel/paging.
- TU d'interface utilisateur.
- Gestion du scroll.
- Ajout le pull to refresh.



